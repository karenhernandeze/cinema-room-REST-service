package cinema.controller;

import cinema.exceptions.InvalidPasswordException;
import cinema.exceptions.InvalidTokenException;
import cinema.model.Cinema;
import cinema.model.Seat;
import cinema.model.Stats;
import cinema.model.Token;
import cinema.service.CinemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class CinemaController {
    int rows = 9;
    int columns = 9;

    private final CinemaService cinemaService;

    @Autowired
    public CinemaController(CinemaService cinemaService) {
        this.cinemaService = cinemaService;
    }


    @GetMapping("/seats")
    public Cinema getSeatInformation() {
        return cinemaService.returnCinema();
    }


    @PostMapping("/purchase")
    public ResponseEntity<?> purchaseTicket(@RequestBody Seat seat) {
        int row = seat.getRow();
        int column = seat.getColumn();

        if (row < 1 || row > 9 || column < 1 || column > 9) {
            return new ResponseEntity<>(Map.of("error", "The number of a row or a column is out of bounds!"), HttpStatus.BAD_REQUEST);
        }

        if (!cinemaService.isSeatAvailable(row, column)) {
            return new ResponseEntity<>(Map.of("error", "The ticket has been already purchased!"), HttpStatus.BAD_REQUEST);
        }

        Map<String, Object> seatInfo = cinemaService.purchaseSeat(row, column);
        return new ResponseEntity<>( seatInfo, HttpStatus.OK);
    }

    @PostMapping("/return")
    public ResponseEntity<?> returnTicket(@RequestBody Token token) throws InvalidTokenException {
        try{
            Seat seat = cinemaService.returnTicket(token);
            return new ResponseEntity<>( Map.of("returned_ticket", seat), HttpStatus.OK);
        } catch (InvalidTokenException ex) {
            return new ResponseEntity<>(Map.of("error", "Wrong token!"), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(path = "/stats")
    public ResponseEntity<?> statistics(@RequestParam(required = false) String password) throws InvalidPasswordException {
//        return cinemaService.statistics(password);
        try{
            Stats stats = cinemaService.statistics(password);
            return new ResponseEntity<>( stats, HttpStatus.OK);
        } catch (InvalidPasswordException ex) {
            return new ResponseEntity<>(Map.of("error", "The password is wrong!"), HttpStatus.UNAUTHORIZED);
        }
    }

}
