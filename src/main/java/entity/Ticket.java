package entity;

import java.sql.Timestamp;

public class Ticket {
    private int id;
    private int userId;
    private int exhibitionId;
    private int hallId;
    private int price;
    private String stateFromDb = "ACTIVE";
    private Timestamp operationDate;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getExhibitionId() {
        return exhibitionId;
    }

    public void setExhibitionId(int exhibitionId) {
        this.exhibitionId = exhibitionId;
    }

    public int getHallId() {
        return hallId;
    }

    public void setHallId(int hallId) {
        this.hallId = hallId;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getStateFromDb() {
        return stateFromDb;
    }

    public void setStateFromDb(String stateFromDb) {
        this.stateFromDb = stateFromDb;
    }

    public Timestamp getOperationDate() {
        return operationDate;
    }

    public void setOperationDate(Timestamp operationDate) {
        this.operationDate = operationDate;
    }

    public static class Builder {
        private final Ticket ticket;

        public Builder() {
            this.ticket = new Ticket();
        }

        public Builder id(int id) {
            ticket.setId(id);
            return this;
        }

        public Builder userId(int id) {
            ticket.setUserId(id);
            return this;
        }

        public Builder exhibitionId(int id) {
            ticket.setExhibitionId(id);
            return this;
        }

        public Builder hallId(int id) {
            ticket.setHallId(id);
            return this;
        }

        public Builder price(int price) {
            ticket.setPrice(price);
            return this;
        }

        public Builder stateFromDb(String state) {
            ticket.setStateFromDb(state);
            return this;
        }

        public Builder operationDate(Timestamp date) {
            ticket.setOperationDate(date);
            return this;
        }

        public Ticket build() {
            return ticket;
        }
    }
}
