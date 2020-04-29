package calendar;

import calendar.note.MyNotes;
import calendar.note.Note;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

public class NoteController implements Initializable {
    private Stage stageNote;
    private LocalDate localDate;
    private MyNotes myNotes;

    public void setLocalDate(LocalDate localDate) {
        this.localDate = localDate;
    }

    public void setStageNote(Stage stageNote) {
        this.stageNote = stageNote;
    }

    @FXML
    private Label datalabel;

    @FXML
    private ListView listView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (localDate != null) {
            datalabel.setText(" " + localDate.getDayOfMonth() + " " + Transcription.getMonthByInt(localDate.getMonthValue()) + " " + localDate.getYear() + " ");
            if (myNotes == null) {
                myNotes = Main.getRepository().getByDate(localDate);
            }
        }
        if (myNotes != null) {
            updateListView();
        }
    }

    private void updateListView() {
        ObservableList<Note> notes = FXCollections.observableArrayList(myNotes.getListNotes());
        listView.getItems().setAll(notes);
    }

    @FXML
    public void addNote(ActionEvent event) {
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("Добавить");
        dialog.setHeaderText(null);
        dialog.setContentText("Закладка: ");
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            String sNote = result.get().trim();
            if (!sNote.isEmpty()) {
                myNotes.addNote(sNote);
            }
        }
        updateListView();
    }

    @FXML
    public void remNote(ActionEvent event) {
        Note note = (Note) listView.getSelectionModel().getSelectedItem();
        if (note != null) {
            myNotes.remNote(note.getId());
        }
        updateListView();
    }

    @FXML
    public void remAllNotes(ActionEvent event) {
        ObservableList<Note> notes = (ObservableList<Note>) listView.getItems();
        if (notes != null && !notes.isEmpty()) {
            for (Note note : notes) {
                myNotes.remNote(note.getId());
            }
        }
        updateListView();
    }

    @FXML
    public void backOnCalendar(ActionEvent event) {
        if (stageNote != null) {
            stageNote.close();
        }
    }


}
