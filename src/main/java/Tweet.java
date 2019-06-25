import com.datastax.driver.core.LocalDate;

import java.util.Date;
import java.util.UUID;

public class Tweet {
    private UUID id;
    private String name;
    private String message;
    private LocalDate date;

    public Tweet( String name, String message, LocalDate date) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.message = message;
        this.date = date;
    }

    public UUID getUUID() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

}
