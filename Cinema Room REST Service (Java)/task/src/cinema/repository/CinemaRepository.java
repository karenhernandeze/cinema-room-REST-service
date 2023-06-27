package cinema.repository;
import cinema.model.Seat;
import cinema.model.Token;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CinemaRepository {
    private static final int rows = 9;
    private static final int cols = 9;
    private static final Map<String, Seat> seats_purchased;
    private static final List<Seat> total_seats;

    static {
        seats_purchased = new HashMap<>();
        total_seats = new ArrayList<>();
        for (int row = 1; row <= rows; row++) {
            for (int column = 1; column <= cols; column++) {
                total_seats.add(new Seat(
                                row, column,
                                row <= 4 ? 10 : 8
                        )
                );
            }
        }
    }

    public int getTotalRows() {
        return rows;
    }

    public int getTotalColumns() {
        return cols;
    }

    public List<Seat> getAllSeats() {
        return total_seats;
    }

    public Seat getSeat(String token) {
        return seats_purchased.get(token);
    }

    public Map<String, Object> createToken(String t, Seat s) {
        seats_purchased.put(t, s);
        return Map.of("token", t, "ticket", s);
    }

    public void deleteSeat(Token s) {
        seats_purchased.remove(s.getToken());
    }

}