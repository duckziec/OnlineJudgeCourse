package service;

import app.AppConfig;
import dao.CodingQuestionDAO;
import dao.LessonDAO;
import model.CodingQuestion;
import model.Lesson;

import java.util.List;
import java.util.stream.Collectors;

public class LessonService {
    private final LessonDAO lessonDAO;
    private List<Lesson> lessonsWithStatus;

    public LessonService(LessonDAO lessonDAO) {
        this.lessonDAO = lessonDAO;
    }

    /**
     * Lấy danh sách bài học theo course_id
     */
    public List<Lesson> getLessonsByCourse(int courseId) {
        return lessonDAO.getLessonsByCourse(courseId);
    }

    /**
     * Thêm bài học mới
     */
    public boolean addLesson(Lesson lesson) {
        return lessonDAO.addLesson(lesson);
    }

    /**
     * Cập nhật bài học
     */
    public boolean updateLesson(Lesson lesson) {
        return lessonDAO.updateLesson(lesson);
    }

    /**
     * Xoá bài học
     */
    public boolean deleteLesson(int lessonId) {
        return lessonDAO.deleteLesson(lessonId);
    }

    // get khoá học theo id
    public List<Lesson> getAllLessons() {
        return lessonsWithStatus;
    }

    // hiển thị danh sách bài học
    public void printAllLessonsByCourse() {
        printLessons(lessonsWithStatus);
    }

    // lọc theo category
    public void showFilterCategoryMenu() {

        List<String> list = lessonsWithStatus.stream().map(Lesson::getCategory).distinct().toList();

        if (list.isEmpty()) {
            System.out.println("Chưa có bài học nào trong khóa học này!");
            return;
        }

        for (int i = 0; i < list.size(); i++) {
            System.out.println((i + 1) + ". " + list.get(i));
        }
    }

    // get category
    public List<String> getCategoryNames() {
        return lessonsWithStatus.stream().map(Lesson::getCategory).distinct().collect(Collectors.toList());
    }

    // bài tập dựa vào category
    public void showLessonByCategory(String category) {
        List<Lesson> list = lessonsWithStatus.stream().filter(l -> l.getCategory().equals(category)).collect(Collectors.toList());
        printLessons(list);
    }

    // get bai tap dua vao category
    public List<Lesson> getLessonsByCategory(String category) {
        return lessonsWithStatus.stream().filter(l -> l.getCategory().equals(category)).collect(Collectors.toList());
    }

    // tìm theo title
    public Lesson findLessonByTitle(String title) {
        return lessonDAO.getLessonByTitle(title);
    }

    // reload lesson with status
    public void reloadLessonsWithStatus(int userId, int courseId) {
        lessonsWithStatus = AppConfig.getAnalyticsService().reloadLessonWithStatus(userId, courseId);
    }

    // show lesson chưa hoàn thành
    public void showLessonNotDone() {
        List<Lesson> listNotDone = lessonsWithStatus.stream().filter(i -> i.getStatus().equalsIgnoreCase("NotDone")).collect(Collectors.toList());
        printLessons(listNotDone);
    }

    // get lesson chưa hoàn thành
    public List<Lesson> getLessonsNotDone() {
        return lessonsWithStatus.stream().filter(i -> i.getStatus().equalsIgnoreCase("NotDone")).collect(Collectors.toList());
    }

    // show lesson đã hoàn thành
    public void showLessonDone() {
        List<Lesson> listDone = lessonsWithStatus.stream().filter(i -> i.getStatus().equalsIgnoreCase("Done")).collect(Collectors.toList());
        printLessons(listDone);
    }

    // get lesson đã hoàn thành
    public List<Lesson> getLessonsDone() {
        return lessonsWithStatus.stream().filter(i -> i.getStatus().equalsIgnoreCase("Done")).collect(Collectors.toList());
    }

    // ham in chung
    private void printLessons(List<Lesson> lessons) {
        if (lessons.isEmpty()) {
            System.out.println("Chưa có bài học nào trong khóa học này!");
            return;
        }

        for (int i = 0; i < lessons.size(); i++) {
            Lesson l = lessons.get(i);

            // Xác định điểm dựa theo độ khó
            int score = getScoreByDifficulty(l.getCategory());

            // Tạm thời đánh dấu trạng thái (giả định) cho đẹp
            String statusIcon = (i % 2 == 0) ? "✅" : "⏳";

            System.out.printf("%d. %s %s\n", i + 1, l.getTitle(), statusIcon);
            System.out.printf("   Category: %s • Độ khó: %s • %d điểm\n\n",
                    l.getCategory(),
                    l.getDifficulty(),
                    score
            );
        }
    }

    // Tính điểm theo độ khó
    public int getScoreByDifficulty(String category) {
        if (category == null) return 10;
        String level = category.toLowerCase();
        if (level.contains("dễ")) return 10;
        if (level.contains("trung")) return 20;
        if (level.contains("khó")) return 30;
        return 10; // mặc định
    }

    //=======================XỬ LÝ BÀI TẬP CHI TIẾT=========================
    // show các bài tập categories
    public void showExcercise(int lessonId, String category) {
        Lesson lesson;
        if (category.isEmpty() || category.equals("")) {
            lesson = lessonsWithStatus.get(lessonId);
        } else {
            lesson = lessonsWithStatus.stream().filter(l -> l.getCategory().equals(category)).toList().get(lessonId);
        }
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║  📝 Bài " + (lessonId + 1) + ": " + lesson.getTitle());
        System.out.println("╚════════════════════════════════════════╝");
        System.out.println("Category: " + lesson.getCategory());
        System.out.println("Độ khó: " + lesson.getDifficulty());
        System.out.println("Điểm: " + AppConfig.getLessonService().getScoreByDifficulty(lesson.getDifficulty()));
        System.out.println("Trạng thái: " + (lesson.getStatus().equalsIgnoreCase("NotDone") ? "Chưa hoàn thành" : "Đã hoàn thành"));
    }

    // hiển thị bài tập chi tiết
    public void showExcerciseDetails(int excerciseId) {
        CodingQuestion codingQuestion = getCodingQuestionById(excerciseId);
        System.out.println("\nTitle: " + codingQuestion.getTitle());
        System.out.println("Description: " + codingQuestion.getDescription());
        System.out.println("\n--- INPUT ---");
        System.out.println(codingQuestion.getInputFormat() + " | " + codingQuestion.getConstraints());
        System.out.println("\n--- OUTPUT ---");
        System.out.println(codingQuestion.getOutputFormat());
        System.out.println("\n--- VÍ DỤ ---");
        System.out.println("Input:\n" + codingQuestion.getSampleInput());
        System.out.println("Output:\n" + codingQuestion.getSampleOutput());

    }

    // get bài tập chi tiết
    public CodingQuestion getCodingQuestionById(int lessonId) {
        CodingQuestion codingQuestion = CodingQuestionDAO.getInstance().findQuestionById(lessonId);
        return codingQuestion;
    }

    public List<Lesson> getLessonByTitle(String title) {
        List<Lesson> lessons = lessonsWithStatus.stream()
                .filter(lesson -> lesson.getTitle().toLowerCase().contains(title.toLowerCase()))
                .collect(Collectors.toList());
        return lessons;
    }

}
