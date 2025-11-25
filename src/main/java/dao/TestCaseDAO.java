package dao;

import model.TestCase;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class TestCaseDAO extends BaseDAO<TestCase> implements RowMapper<TestCase> {

    private static TestCaseDAO testCaseDAO;

    private TestCaseDAO() {
    }

    public static TestCaseDAO getInstance() {
        if (testCaseDAO == null) {
            testCaseDAO = new TestCaseDAO();
        }
        return testCaseDAO;
    }

    @Override
    public TestCase mapRow(ResultSet rs) throws SQLException {
        TestCase testCase = new TestCase();
        testCase.setTestId(rs.getInt("test_id"));
        testCase.setQuestionId(rs.getInt("question_id"));
        testCase.setInputData(rs.getString("input_data"));
        testCase.setExpectedOutput(rs.getString("expected_output"));
        testCase.setPublic(rs.getBoolean("is_public"));
        return testCase;
    }

    // thêm testcase
    public boolean addTestCase(TestCase testCase) {
        String sql = "INSERT INTO test_cases (question_id, input_data, expected_output) VALUES (?, ?, ?)";
        return executeUpdate(sql, testCase.getQuestionId(), testCase.getInputData(), testCase.getExpectedOutput());
    }

    // update testcase
    public boolean updateTestCase(TestCase testCase) {
        String sql = "UPDATE test_cases SET question_id = ?, input_data = ?, expected_output = ? WHERE test_id = ?";
        return executeUpdate(sql, testCase.getQuestionId(), testCase.getInputData(), testCase.getExpectedOutput(), testCase.getTestId());
    }

    // xoá testcase theo id
    public boolean deleteTestCase(TestCase testCase) {
        String sql = "DELETE FROM test_cases WHERE test_id = ?";
        return executeUpdate(sql, testCase.getTestId());
    }

    // tìm testcase theo id
    public TestCase findTestCase(int testId) {
        String sql = "SELECT * FROM test_cases WHERE test_id = ?";
        List<TestCase> list = executeQuery(sql, this, testId);
        return list.isEmpty() ? null : list.get(0);
    }

    // lấy toàn bộ ds testcases
    public List<TestCase> findAllTestCases() {
        String sql = "SELECT * FROM test_cases";
        return executeQuery(sql, this);
    }

    // lấy danh sách test case
    public List<TestCase> findAllTestCasesByQuestionId(int questionId) {
        String sql = "SELECT * FROM test_cases WHERE question_id = ?";
        List<TestCase> list = executeQuery(sql, this, questionId);
        return list.isEmpty() ? null : list;
    }
}
