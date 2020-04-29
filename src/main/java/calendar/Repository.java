package calendar;

import calendar.note.MyNotes;
import lombok.Data;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Data
public class Repository {
    private Map<LocalDate, MyNotes> allBaseNotes;

    public Repository() {
        allBaseNotes = new HashMap<>();
        addForTest();
    }

    public void addEmptyNote(LocalDate localDate) {
        allBaseNotes.putIfAbsent(localDate, new MyNotes());
    }

    public MyNotes getByDate(LocalDate localDate) {
        if (!allBaseNotes.containsKey(localDate)) {
            addEmptyNote(localDate);
        }
        return allBaseNotes.get(localDate);
    }

    private void addForTest() {
        LocalDate localDate = LocalDate.now();
        MyNotes myNotes = getByDate(localDate);
        myNotes.addNote("Помыть посуду");
        myNotes.addNote("Поставить будильник перед сном");
    }
}
