package dao;

import model.CodingQuestion;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CodingQuestionDAO extends BaseDAO<CodingQuestion> implements RowMapper<CodingQuestion> {

    private static CodingQuestionDAO codingQuestionDAO;

    private CodingQuestionDAO() {
    }

    public static CodingQuestionDAO getInstance() {
        if (codingQuestionDAO == null) {
            codingQuestionDAO = new CodingQuestionDAO();
        }
        return codingQuestionDAO;
    }


    @Override
    public CodingQuestion mapRow(ResultSet rs) throws SQLException {
        CodingQuestion questionDAO = new CodingQuestion();
        questionDAO.setQuestionId(rs.getInt("question_id"));
        questionDAO.setLessonId(rs.getInt("lesson_id"));
        questionDAO.setTitle(rs.getString("title"));
        questionDAO.setDescription(rs.getString("description"));
        questionDAO.setInputFormat(rs.getString("input_format"));
        questionDAO.setOutputFormat(rs.getString("output_format"));
        questionDAO.setSampleInput(rs.getString("sample_input"));
        questionDAO.setSampleOutput(rs.getString("sample_output"));
        questionDAO.setConstraints(rs.getString("constraints"));
        questionDAO.setDifficulty(rs.getString("difficult"));
        return questionDAO;
    }

    // thêm question
    public boolean addQuestion(CodingQuestion question) {
        String sql = "INSERT INTO coding_questions (lesson_id, title, description, input_format, output_format, sample_input, sample_output, constraints) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        return executeUpdate(sql, question.getLessonId(), question.getTitle(), question.getDescription(), question.getInputFormat(), question.getOutputFormat(), question.getSampleInput(), question.getSampleOutput(), question.getConstraints());
    }

    // cập nhật question
    public boolean updateQuestion(CodingQuestion question) {
        String sql = "UPDATE coding_questions SET lesson_id=?, title=?, description=?, input_format=?, output_format=?, sample_input=?, sample_output=?, constraints=? WHERE question_id=?";
        return executeUpdate(sql, question.getLessonId(), question.getTitle(), question.getDescription(), question.getInputFormat(), question.getOutputFormat(), question.getSampleInput(), question.getSampleOutput(), question.getConstraints(), question.getQuestionId());
    }

    // xoá question
    public boolean deleteQuestion(CodingQuestion question) {
        String sql = "DELETE FROM coding_questions WHERE question_id=?";
        return executeUpdate(sql, question.getQuestionId());
    }

    // tìm question
    public CodingQuestion findQuestionById(int lessonId) {
        String sql = "SELECT * FROM coding_questions WHERE question_id=?";
        List<CodingQuestion> list = executeQuery(sql, this, lessonId);
        return list.isEmpty() ? null : list.get(0);
    }

    public CodingQuestion findQuestionById(int lessonId, int questionId) {
        String sql = "SELECT * FROM coding_questions WHERE lesson_id=? AND question_id=?";
        List<CodingQuestion> list = executeQuery(sql, this, lessonId, questionId);
        return list.isEmpty() ? null : list.get(0);
    }

    // tìm tất cả danh sách
    public List<CodingQuestion> findAllQuestions() {
        String sql = "SELECT * FROM coding_questions";
        return executeQuery(sql, this);
    }

    // lấy danh sách bài tập chi tiết
    public List<CodingQuestion> getAllQuestions(int lessonId) {
        String sql = "SELECT * FROM coding_questions WHERE lesson_id=?";
        return executeQuery(sql, this, lessonId);
    }
}
