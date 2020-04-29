package calendar;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.time.YearMonth;

public class Main extends Application {
    private static Stage stage;
    private static Repository repository;
    private static CalendarView calendarView;

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        primaryStage.setTitle("Full Calendar Example");
        calendarView = new CalendarView(YearMonth.now());
        primaryStage.setScene(new Scene(calendarView.getView()));
        primaryStage.show();
    }

    public static void main(String[] args) {
        repository = new Repository();
        launch(args);
    }

    public static CalendarView getCalendarView() {
        return calendarView;
    }

    public static Repository getRepository() {
        return repository;
    }

    public static Stage getStage() {
        return stage;
    }
}
