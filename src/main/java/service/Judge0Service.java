package service;

import dao.SubmissionDAO;
import dao.TestCaseDAO;
import model.Submission;
import model.SubmissionResultData;
import model.TestCase;
import model.TestCaseResultData;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Judge0Service
 * ----------------
 * Chịu trách nhiệm xử lý nghiệp vụ chấm bài:
 * - Lấy test case từ DB
 * - Gọi ApiJudgeClient để chấm từng test
 * - Tổng hợp kết quả và cập nhật submission
 */
public class Judge0Service {

    private static TestCaseDAO testCaseDAO;
    private static SubmissionDAO submissionDAO;

    // Interface callback để cập nhật UI real-time
    public interface TestCaseProgressCallback {
        void onTestCaseCompleted(TestCaseResultData result, int currentIndex, int total);
    }

    public Judge0Service(SubmissionDAO submissionDAO, TestCaseDAO testCaseDAO) {
        Judge0Service.submissionDAO = submissionDAO;
        Judge0Service.testCaseDAO = testCaseDAO;
    }

    // =================== LẤY TEST CASE ===================
    public static List<TestCase> getTestCases(int questionId) {
        return testCaseDAO.findAllTestCasesByQuestionId(questionId);
    }

    // =================== CHẤM TOÀN BỘ TEST CASE CỦA MỘT SUBMISSION ===================
    public static void judgeSubmission(Submission sub) throws Exception {
        System.out.println("\n================= 🚀 BẮT ĐẦU CHẤM BÀI ===================");
        List<TestCase> tests = getTestCases(sub.getQuestionId());

        boolean allPassed = true;
        int passedCount = 0;
        int totalCount = tests.size();

        double totalTime = 0.0;
        double maxTime = 0.0;
        double totalMemory = 0.0;
        double pointOfQuestion = sub.getScore();

        int count = 1;

        for (TestCase t : tests) {
            JSONObject result = ApiJudgeClient.runTest(
                    sub.getCode(),
                    sub.getLanguage(),
                    t.getInputData(),
                    t.getExpectedOutput()
            );

            //System.out.println(result);
            try {
                String status = "Error";
                if (result.has("status")) {
                    JSONObject statusObj = result.getJSONObject("status");
                    status = statusObj.optString("description", "Unknown Error");
                } else if (result.has("message")) {
                    status = result.getString("message");
                } else if (result.has("stderr")) {
                    status = "Runtime Error";
                } else {
                    status = "Unknown Error";
                }

                double time = result.optDouble("time", 0.0);
                double memory = result.optDouble("memory", 0.0) / 1024.0;

                totalTime += time;
                totalMemory += memory;
                maxTime = Math.max(maxTime, time);

                boolean accepted = status.equals("Accepted");
                if (accepted) passedCount++;
                else allPassed = false;

                System.out.printf(
                        "▶ Test case #%d: %s [%.3fs, %.2f MB]%n",
                        count++, accepted ? "\u001B[32mAccepted\u001B[0m" : "\u001B[31m" + status + "\u001B[0m",
                        time, memory
                );

                if (status.equals("Compilation Error") || status.equals("Runtime Error")) {
                    allPassed = false;
                    break;
                }

            } catch (Exception e) {
                System.out.println("⚠️ Lỗi khi đọc kết quả test case: " + e.getMessage());
            }

        }

        // =================== Tổng kết ===================
        double avgMemory = totalMemory / totalCount;
        double score = ((double) passedCount / totalCount) * pointOfQuestion;

        String finalStatus = allPassed ? "Accepted" : "Wrong Answer";
        sub.setStatus(finalStatus);
        sub.setScore(score);
        sub.setPassedTest(passedCount);
        sub.setTotalTest(totalCount);
        submissionDAO.insertSubmission(sub);

        System.out.println("\n══════════════════════════════════════════════════════════");
        System.out.printf("Resources: %.3fs, %.2f MB%n", totalTime, avgMemory);
        System.out.printf("Maximum single-case runtime: %.3fs%n", maxTime);
        System.out.printf("Final score: %.0f/%.0f (%.0f/%d test cases passed)%n",
                score, pointOfQuestion, (double) passedCount, totalCount);
        System.out.printf("🎯 Final status: %s%n", finalStatus);
        System.out.println("══════════════════════════════════════════════════════════");
    }

    public static SubmissionResultData judgeSubmission(Submission sub, TestCaseProgressCallback callback) throws Exception {
        System.out.println("\n================= BẮT ĐẦU CHẤM BÀI ===================");

        SubmissionResultData resultData = new SubmissionResultData();
        List<TestCase> tests = getTestCases(sub.getQuestionId());

        boolean allPassed = true;
        int passedCount = 0;
        int totalCount = tests.size();

        double totalTime = 0.0;
        double maxTime = 0.0;
        double totalMemory = 0.0;
        double pointOfQuestion = sub.getScore();

        int count = 1;

        for (TestCase t : tests) {
            JSONObject result = null;

            try {
                // Gọi API Judge0
                result = ApiJudgeClient.runTest(
                        sub.getCode(),
                        sub.getLanguage(),
                        t.getInputData(),
                        t.getExpectedOutput()
                );

                // DEBUG: In ra response và so sánh output
                System.out.println("=== DEBUG Test Case #" + count + " Response ===");
                System.out.println(result.toString(2)); // Pretty print JSON
                System.out.println("--- COMPARISON ---");
                System.out.println("Expected Output: [" + t.getExpectedOutput() + "]");
                System.out.println("Actual Output:   [" + (result.has("stdout") ? result.getString("stdout") : "null") + "]");
                System.out.println("Match: " + (result.has("stdout") && t.getExpectedOutput().equals(result.getString("stdout"))));
                System.out.println("==========================================");

            } catch (JSONException jsonEx) {
                System.err.println("!!! LỖI JSON Parse tại test case #" + count);
                System.err.println("Chi tiết lỗi: " + jsonEx.getMessage());

                // Tạo test case result cho lỗi
                TestCaseResultData tcResult = new TestCaseResultData(
                        "Test case #" + count,
                        t.getInputData(),
                        t.getExpectedOutput(),
                        "JSON Parse Error: " + jsonEx.getMessage(),
                        "Error",
                        0.0,
                        0.0
                );
                resultData.addTestCase(tcResult);

                if (callback != null) {
                    callback.onTestCaseCompleted(tcResult, count, totalCount);
                }

                count++;
                allPassed = false;
                continue; // Tiếp tục với test case tiếp theo
            } catch (Exception ex) {
                System.err.println("!!! LỖI khi gọi API tại test case #" + count);
                System.err.println("Chi tiết lỗi: " + ex.getMessage());
                ex.printStackTrace();

                TestCaseResultData tcResult = new TestCaseResultData(
                        "Test case #" + count,
                        t.getInputData(),
                        t.getExpectedOutput(),
                        "API Error: " + ex.getMessage(),
                        "Error",
                        0.0,
                        0.0
                );
                resultData.addTestCase(tcResult);

                if (callback != null) {
                    callback.onTestCaseCompleted(tcResult, count, totalCount);
                }

                count++;
                allPassed = false;
                continue;
            }

            // Xử lý kết quả nếu không có lỗi
            try {
                String status = "Error";
                String actualOutput = "";

                // Kiểm tra các trường hợp status khác nhau
                if (result.has("status")) {
                    JSONObject statusObj = result.getJSONObject("status");
                    status = statusObj.optString("description", "Unknown Error");
                } else if (result.has("message")) {
                    status = result.getString("message");
                } else if (result.has("stderr") && !result.isNull("stderr") && !result.getString("stderr").trim().isEmpty()) {
                    status = "Runtime Error";
                } else if (result.has("compile_output") && !result.isNull("compile_output") && !result.getString("compile_output").trim().isEmpty()) {
                    status = "Compilation Error";
                } else {
                    status = "Unknown Error";
                }

                // Lấy output thực tế
                if (result.has("stdout") && !result.isNull("stdout")) {
                    actualOutput = result.getString("stdout").trim();
                } else if (result.has("stderr") && !result.isNull("stderr")) {
                    actualOutput = "Error: " + result.getString("stderr").trim();
                } else if (result.has("compile_output") && !result.isNull("compile_output")) {
                    actualOutput = "Compile Error: " + result.getString("compile_output").trim();
                } else {
                    actualOutput = "No output";
                }

                double time = result.optDouble("time", 0.0);
                double memory = result.optDouble("memory", 0.0) / 1024.0;

                totalTime += time;
                totalMemory += memory;
                maxTime = Math.max(maxTime, time);

                boolean accepted = status.equals("Accepted");
                if (accepted) passedCount++;
                else allPassed = false;

                // Thêm kết quả test case vào resultData
                TestCaseResultData tcResult = new TestCaseResultData(
                        "Test case #" + count,
                        t.getInputData(),
                        t.getExpectedOutput(),
                        actualOutput,
                        status,
                        time,
                        memory
                );
                resultData.addTestCase(tcResult);

                // GỌI CALLBACK
                if (callback != null) {
                    callback.onTestCaseCompleted(tcResult, count, totalCount);
                }

                System.out.printf(
                        "▶ Test case #%d: %s [%.3fs, %.2f MB]%n",
                        count++, accepted ? "\u001B[32mAccepted\u001B[0m" : "\u001B[31m" + status + "\u001B[0m",
                        time, memory
                );

                // Dừng lại nếu gặp lỗi compilation hoặc runtime nghiêm trọng
                if (status.equals("Compilation Error")) {
                    System.out.println("!!! Dừng chấm do lỗi biên dịch");
                    allPassed = false;
                    break;
                }

            } catch (Exception e) {
                System.err.println("!!! Lỗi khi đọc kết quả test case #" + count + ": " + e.getMessage());
                e.printStackTrace();

                TestCaseResultData tcResult = new TestCaseResultData(
                        "Test case #" + count,
                        t.getInputData(),
                        t.getExpectedOutput(),
                        "Error: " + e.getMessage(),
                        "Error",
                        0.0,
                        0.0
                );
                resultData.addTestCase(tcResult);

                if (callback != null) {
                    callback.onTestCaseCompleted(tcResult, count, totalCount);
                }

                count++;
                allPassed = false;
            }
        }

        // =================== Tổng kết ===================
        double avgMemory = totalCount > 0 ? totalMemory / totalCount : 0.0;
        double score = totalCount > 0 ? ((double) passedCount / totalCount) * pointOfQuestion : 0.0;

        String finalStatus = allPassed ? "Accepted" : "Wrong Answer";
        sub.setStatus(finalStatus);
        sub.setScore(score);
        sub.setPassedTest(passedCount);
        sub.setTotalTest(totalCount);

        System.out.println("=== DEBUG SUBMISSION ===");
        System.out.println("Enrollment ID: " + sub.getEnrollmentId());
        System.out.println("Question ID: " + sub.getQuestionId());
        System.out.println("Language: " + sub.getLanguage());
        System.out.println("Code length: " + (sub.getCode() != null ? sub.getCode().length() : 0));
        System.out.println("Status: " + sub.getStatus());
        System.out.println("Score: " + sub.getScore());
        System.out.println("Passed test: " + sub.getPassedTest());
        System.out.println("Test cases: " + sub.getTotalTest());
        System.out.println("========================");

        // Lưu vào database
        submissionDAO.insertSubmission(sub);

        // Cập nhật resultData
        resultData.setOverallStatus(finalStatus);
        resultData.setPassedCount(passedCount);
        resultData.setTotalCount(totalCount);
        resultData.setScore(score);
        resultData.setMaxScore(pointOfQuestion);
        resultData.setTotalTime(totalTime);
        resultData.setMaxTime(maxTime);
        resultData.setAvgMemory(avgMemory);

        System.out.println("\n╔═════════════════════════════════════════════════════════╗");
        System.out.printf("Resources: %.3fs, %.2f MB%n", totalTime, avgMemory);
        System.out.printf("Maximum single-case runtime: %.3fs%n", maxTime);
        System.out.printf("Final score: %.0f/%.0f (%.0f/%d test cases passed)%n",
                score, pointOfQuestion, (double) passedCount, totalCount);
        System.out.printf("==> Final status: %s%n", finalStatus);
        System.out.println("╚═════════════════════════════════════════════════════════╝");

        return resultData;
    }

    // Overload method để tương thích ngược (không dùng callback)
    public static SubmissionResultData getJudgeSubmission(Submission sub) throws Exception {
        return judgeSubmission(sub, null);
    }
}
