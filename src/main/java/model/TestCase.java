package model;

public class TestCase {
    private int testId;
    private int questionId;
    private String inputData;
    private String expectedOutput;
    private boolean isPublic;

    public TestCase() {}

    public TestCase(int testId, int questionId, String inputData, String expectedOutput, boolean isPublic) {
        this.testId = testId;
        this.questionId = questionId;
        this.inputData = inputData;
        this.expectedOutput = expectedOutput;
        this.isPublic = isPublic;
    }

    public int getTestId() { return testId; }
    public void setTestId(int testId) { this.testId = testId; }

    public int getQuestionId() { return questionId; }
    public void setQuestionId(int questionId) { this.questionId = questionId; }

    public String getInputData() { return inputData; }
    public void setInputData(String inputData) { this.inputData = inputData; }

    public String getExpectedOutput() { return expectedOutput; }
    public void setExpectedOutput(String expectedOutput) { this.expectedOutput = expectedOutput; }

    public boolean isPublic() { return isPublic; }
    public void setPublic(boolean isPublic) { this.isPublic = isPublic; }

    @Override
    public String toString() {
        return "TestCase{id=" + testId + ", public=" + isPublic + "}";
    }
}
