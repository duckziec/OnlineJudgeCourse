package dao;

import model.Submission;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class SubmissionDAO extends BaseDAO<Submission> implements RowMapper<Submission> {

    private static SubmissionDAO submissionDAO;

    private SubmissionDAO() {
    }

    public static SubmissionDAO getInstance() {
        if (submissionDAO == null) {
            submissionDAO = new SubmissionDAO();
        }
        return submissionDAO;
    }


    @Override
    public Submission mapRow(ResultSet rs) throws SQLException {
        Submission submission = new Submission();
        submission.setSubmissionId(rs.getInt("submission_id"));
        submission.setEnrollmentId(rs.getInt("enrollment_id"));
        submission.setQuestionId(rs.getInt("question_id"));
        submission.setCode(rs.getString("code"));
        submission.setLanguage(rs.getString("language"));
        submission.setSubmitTime(rs.getTimestamp("submit_time").toLocalDateTime());
        submission.setStatus(rs.getString("status"));
        submission.setScore(rs.getDouble("score"));
        submission.setMessage(rs.getString("message"));
        return submission;
    }

    // thêm submission
    public boolean insertSubmission(Submission submission) {
        String sql = "INSERT INTO submissions (enrollment_id, question_id, code, language, status, test_passed) VALUES (?, ?, ?, ?, ?, ?)";
        return executeUpdate(sql, submission.getEnrollmentId(), submission.getQuestionId(), submission.getCode(), submission.getLanguage(), submission.getStatus(), submission.getPassedTest());
    }

    // cập nhật phiếu submiss
    public boolean updateSubmission(String status, int submisstionId) {
        String sql = "UPDATE submissions SET status=? WHERE submission_id=?";
        return executeUpdate(sql, status, submisstionId);
    }

    // xoá phiếu submit theo enrollment_id
    public boolean deleteSubmission(Submission submission) {
        String sql = "DELETE FROM submissions WHERE submission_id=?";
        return executeUpdate(sql, submission.getSubmissionId());
    }

    // lấy hết phiểu submission
    public List<Submission> getAllSubmissions() {
        String sql = "SELECT * FROM submissions";
        return executeQuery(sql, this);
    }

    // Tạo submission mới
    public boolean addSubmission(Submission sub) {
        String sql = """
                    INSERT INTO submissions (enrollment_id, question_id, code, language, status)
                    VALUES (?, ?, ?, ?, 'Pending')
                """;
        return executeUpdate(sql, sub.getEnrollmentId(), sub.getQuestionId(),
                sub.getCode(), sub.getLanguage());
    }

    // Cập nhật kết quả chấm bài
    public boolean updateSubmissionResult(int submissionId, String status, double score) {
        String sql = "UPDATE submissions SET status = ?, score = ? WHERE submission_id = ?";
        return executeUpdate(sql, status, score, submissionId);
    }
}
