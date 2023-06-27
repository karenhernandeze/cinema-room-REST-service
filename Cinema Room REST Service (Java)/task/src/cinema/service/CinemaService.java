package cinema.service;
import cinema.exceptions.InvalidPasswordException;
import cinema.exceptions.InvalidTokenException;
import cinema.model.Cinema;
import cinema.model.Seat;
import cinema.model.Stats;
import cinema.model.Token;
import cinema.repository.CinemaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class CinemaService {

    private static final String super_secret = "super_secret";
    private final CinemaRepository cinemaRepository;
    private int total_money = 0;
    private int total_tickets_sold = 0;


    @Autowired
    public CinemaService(CinemaRepository seatRepository) {
        this.cinemaRepository = seatRepository;
    }

    public Cinema returnCinema() {
        return new Cinema(cinemaRepository.getTotalRows(), cinemaRepository.getTotalColumns(), cinemaRepository.getAllSeats());
    }

    public Map<String, Object> purchaseSeat(int row, int column) {
        seatAvailability[row - 1][column - 1] = true;
        Seat seat = cinemaRepository
                .getAllSeats().get(((row-1)*9)+column-1);
        List<Seat> r = cinemaRepository.getAllSeats();
        System.out.println(r);
        total_tickets_sold += 1;
        total_money += seat.getPrice();
        return cinemaRepository.createToken(new Token().toString(), seat);
    }

    public Seat returnTicket(Token token) throws InvalidTokenException {
        try {
            Seat seat = cinemaRepository.getSeat(token.getToken());
            if (seat != null) {
                seatAvailability[seat.getRow() - 1][seat.getColumn() - 1] = false;
                cinemaRepository.deleteSeat(token);
                total_tickets_sold -= 1;
                total_money -= seat.getPrice();
                return cinemaRepository.getAllSeats().get(((seat.getRow()-1)*9)+seat.getColumn()-1);
            } else {
                throw new InvalidTokenException("Wrong token!");
            }
            // Make the HTTP request here
        } catch (HttpClientErrorException | NullPointerException ex) {
            throw new InvalidTokenException("Wrong token!");
        }
    }

    private boolean[][] seatAvailability = new boolean[9][9];

    public boolean isSeatAvailable(int row, int column) {
        System.out.println(seatAvailability[row-1][column-1]);
        return !seatAvailability[row-1][column-1];
    }

    public Stats statistics(String password) throws InvalidPasswordException {
        if (password == null || !isValidPassword(password)){
            throw new InvalidPasswordException("The password is wrong!");
        } else {
            Stats stats = new Stats();
            stats.setCurrent_income(total_money);
            stats.setNumber_of_available_seats(81-total_tickets_sold);
            stats.setNumber_of_purchased_tickets(total_tickets_sold);

            return stats;
        }

    }

    public boolean isValidPassword(String password) {
        return password.equals(super_secret);
    }
}
