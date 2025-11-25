package model;

/**
 * TestCaseResult
 * ----------------
 * Chứa kết quả của từng test case
 */
public class TestCaseResultData {
    private String name;
    private String inputData;
    private String expectedOutput;
    private String actualOutput;
    private String status;
    private double time;
    private double memory;

    public TestCaseResultData(String name, String status, String expectedOutput, String actualOutput) {
        this.name = name;
        this.status = status;
        this.expectedOutput = expectedOutput;
        this.actualOutput = actualOutput;
    }

    public TestCaseResultData(String name, String inputData, String expectedOutput, String actualOutput,
                              String status, double time, double memory) {
        this.name = name;
        this.inputData = inputData;
        this.expectedOutput = expectedOutput;
        this.actualOutput = actualOutput;
        this.status = status;
        this.time = time;
        this.memory = memory;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInputData() {
        return inputData;
    }

    public void setInputData(String inputData) {
        this.inputData = inputData;
    }

    public String getExpectedOutput() {
        return expectedOutput;
    }

    public void setExpectedOutput(String expectedOutput) {
        this.expectedOutput = expectedOutput;
    }

    public String getActualOutput() {
        return actualOutput;
    }

    public void setActualOutput(String actualOutput) {
        this.actualOutput = actualOutput;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }

    public double getMemory() {
        return memory;
    }

    public void setMemory(double memory) {
        this.memory = memory;
    }

    // Format để hiển thị
    public String getStatusDisplay() {
        return status;
    }

    public String getTimeDisplay() {
        return String.format("%.3fs", time);
    }

    public String getMemoryDisplay() {
        return String.format("%.2f MB", memory);
    }
}