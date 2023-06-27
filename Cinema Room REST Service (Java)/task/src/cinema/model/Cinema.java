package cinema.model;
import cinema.model.Seat;
import java.util.List;

public class Cinema {

    private int total_rows;
    private int total_columns;
    private List<Seat> available_seats;

    public Cinema(int row, int column, List<Seat> seats) {
        this.total_rows = row;
        this.total_columns = column;
        this.available_seats = seats;
    }

    public int gettotal_rows() {
        return total_rows;
    }

    public void settotal_rows(int total_rows) {
        this.total_rows = total_rows;
    }

    public int gettotal_columns() {
        return total_columns;
    }

    public void settotal_columns(int total_columns) {
        this.total_columns = total_columns;
    }

    public List<Seat> getavailable_seats() {
        return available_seats;
    }

    public void setavailable_seats(List<Seat> available_seats) {
        this.available_seats = available_seats;
    }

}

