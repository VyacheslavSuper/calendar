package calendar;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

public class NoteWindow {
    public static void openNote(LocalDate localDate) {
        try {
            Stage newWindow = new Stage();
            FXMLLoader loader = new FXMLLoader(NoteWindow.class.getResource("/fxml/note.fxml"));
            Parent root = loader.load();
            NoteController noteController = loader.getController();
            newWindow.setTitle("Заметки");
            newWindow.setScene(new Scene(root, 479, 400));
            newWindow.initModality(Modality.WINDOW_MODAL);
            Stage primaryStage = Main.getStage();
            newWindow.initOwner(primaryStage);
            newWindow.setX(primaryStage.getX() + 200);
            newWindow.setY(primaryStage.getY() + 100);
            newWindow.setResizable(false);
            noteController.setLocalDate(localDate);
            noteController.setStageNote(newWindow);
            noteController.initialize(null, null);
            newWindow.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
