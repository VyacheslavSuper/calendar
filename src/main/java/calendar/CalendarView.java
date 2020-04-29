package calendar;

import calendar.note.MyNotes;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.net.URL;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class CalendarView {

    private ArrayList<AnchorPaneNode> allCalendarDays;
    private VBox view;
    private Text calendarTitle;
    private YearMonth currentYearMonth;

    public CalendarView(YearMonth yearMonth) {
        currentYearMonth = yearMonth;
        init(currentYearMonth);
    }

    public void init(YearMonth yearMonth){
        allCalendarDays= new ArrayList<>();
        // Create the calendar grid pane
        GridPane calendar = new GridPane();
        calendar.setPrefSize(600, 400);
        calendar.setGridLinesVisible(true);
        // Create rows and columns with anchor panes for the calendar
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 7; j++) {
                AnchorPaneNode ap = new AnchorPaneNode();
                ap.setPrefSize(200, 200);
                calendar.add(ap, j, i);
                allCalendarDays.add(ap);
            }
        }
        // Days of the week labels
        Text[] dayNames = new Text[]{new Text("Понедельник"), new Text("Вторник"),
                new Text("Среда"), new Text("Четверг"), new Text("Пятница"),
                new Text("Суббота"), new Text("Воскресенье"),};
        GridPane dayLabels = new GridPane();
        dayLabels.setPrefWidth(600);
        Integer col = 0;
        for (Text txt : dayNames) {
            AnchorPane ap = new AnchorPane();
            ap.setPrefSize(200, 10);
            ap.setBottomAnchor(txt, 5.0);
            ap.getChildren().add(txt);
            dayLabels.add(ap, col++, 0);
        }
        // Create calendarTitle and buttons to change current month
        calendarTitle = new Text();
        Button previousMonth = new Button("<<");
        previousMonth.setOnAction(e -> previousMonth());
        Button nextMonth = new Button(">>");
        nextMonth.setOnAction(e -> nextMonth());
        HBox titleBar = new HBox(previousMonth, calendarTitle, nextMonth);
        titleBar.setAlignment(Pos.BASELINE_CENTER);
        // Populate calendar with the appropriate day numbers
        populateCalendar(yearMonth);
        // Create the calendar view
        view = new VBox(new Pane(), new VBox(titleBar, dayLabels, calendar));
    }

    public void populateCalendar(YearMonth yearMonth) {
        // Get the date we want to start with on the calendar
        LocalDate calendarDate = LocalDate.of(yearMonth.getYear(), yearMonth.getMonthValue(), 1);
        // Dial back the day until it is SUNDAY (unless the month starts on a sunday)
        while (!calendarDate.getDayOfWeek().toString().equals("MONDAY")) {
            calendarDate = calendarDate.minusDays(1);
        }
        // Populate the calendar with day numbers
        for (AnchorPaneNode ap : allCalendarDays) {
            if (ap.getChildren().size() != 0) {
                ap.getChildren().remove(0);
            }
            LocalDate date = calendarDate;
            Text dayText = new Text(String.valueOf(date.getDayOfMonth()));
            ap.setDate(calendarDate);
            AnchorPane.setTopAnchor(dayText, 5.0);
            AnchorPane.setLeftAnchor(dayText, 5.0);
            ap.getChildren().add(dayText);
            MyNotes myNotes = Main.getRepository().getByDate(date);
            if (myNotes.getCountNotes() > 0) {
                Text notes = new Text(String.valueOf(myNotes.getCountNotes()));
                notes.setFill(Color.GREEN);
                AnchorPane.setTopAnchor(notes, 35.0);
                AnchorPane.setLeftAnchor(notes, 35.0);
                ap.getChildren().add(notes);
            }
            calendarDate = calendarDate.plusDays(1);
        }
        // Change the title of the calendar
        calendarTitle.setText(" " + Transcription.getMonth(yearMonth) + " " + yearMonth.getYear() + " ");
    }

    private void previousMonth() {
        currentYearMonth = currentYearMonth.minusMonths(1);
        updateController();
    }

    private void nextMonth() {
        currentYearMonth = currentYearMonth.plusMonths(1);
        updateController();
    }

    public void updateController() {
        init(currentYearMonth);
        Main.getStage().setScene(new Scene(Main.getCalendarView().getView()));
    }

    public VBox getView() {
        return view;
    }
}
