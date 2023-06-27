package cinema.model;
import java.util.UUID;

public class Token {
    private UUID token;

    public Token() {
        this.token = UUID.randomUUID();
    }
    public Token(UUID token) {
        this.token = token;
    }

    public String getToken() {
        return token.toString();
    }

    public void setToken(UUID token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return String.valueOf(token);
    }

}
