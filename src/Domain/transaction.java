package Domain;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class transaction {
    private String id;

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getAccountNummber() {
        return accountNummber;
    }

    public void setAccountNummber(String accountNummber) {
        this.accountNummber = accountNummber;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    private Type type;
    private String accountNummber;
    private Double amount;

    public transaction(String note, LocalDateTime timestamp, Double amount, String accountNummber, Type type, String id) {
        this.note = note;
        this.timestamp = timestamp;
        this.amount = amount;
        this.accountNummber = accountNummber;
        this.type = type;
        this.id = id;
    }

    private LocalDateTime timestamp;
    private String note;

    public transaction(String accountNumber, Double amount, String string, String note, LocalDateTime now, type type) {
    }
}
