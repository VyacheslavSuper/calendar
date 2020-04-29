package calendar.note;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Note {
    private int id;
    private String note;

    @Override
    public String toString() {
        return note;
    }
}
