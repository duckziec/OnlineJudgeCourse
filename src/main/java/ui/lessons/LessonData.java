package ui.lessons;

import app.AppConfig;

public class LessonData {
    private static LessonData instance;

    private LessonData() {

    }

    public static LessonData getInstance() {
        if (instance == null) {
            instance = new LessonData();
        }
        return instance;
    }


    //        AppConfig.getLessonService().reloadLessonsWithStatus(SessionManager.getInstance().getCurrentUser().getUserId(), lessonID);
    public void initializeData() {

    }

    public int getLessonIdByTitle(String title) {
        return AppConfig.getLessonService().getLessonByTitle(title).get(0).getLessonId();
    }
}
