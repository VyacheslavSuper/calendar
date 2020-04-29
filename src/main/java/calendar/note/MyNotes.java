package calendar.note;

import calendar.CalendarView;
import calendar.Main;
import lombok.Data;

import java.util.*;

@Data
public class MyNotes {
    private int startId = 0;
    private Map<Integer, Note> notes;

    public MyNotes() {
        notes = new HashMap<>();
    }

    public MyNotes(Map<Integer, Note> notes) {
        this.notes = notes;
    }

    public List<Note> getListNotes() {
        return new ArrayList<>(notes.values());
    }

    public void addNote(String string) {
        notes.put(++startId, new Note(startId,string));
        updateController();
    }

    public void remNote(int id) {
        notes.remove(id);
        updateController();
    }

    private void updateController(){
        CalendarView calendarView = Main.getCalendarView();
        if(calendarView!=null){
            calendarView.updateController();
        }
    }

    public int getCountNotes() {
        return notes.keySet().size();
    }
}
