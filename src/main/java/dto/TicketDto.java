package dto;

import entity.*;

import java.io.Serializable;
import java.sql.Timestamp;

public class TicketDto extends Ticket implements Serializable {
    private User user;
    private Exhibition exhibition;
    private Hall hall;
    private TicketState state;


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Exhibition getExhibition() {
        return exhibition;
    }

    public void setExhibition(Exhibition exhibition) {
        this.exhibition = exhibition;
    }

    public Hall getHall() {
        return hall;
    }

    public void setHall(Hall hall) {
        this.hall = hall;
    }


    public TicketState getState() {
        return state;
    }

    public void setState(TicketState state) {
        this.state = state;
    }

    public static class Builder {
        private final TicketDto dto;

        public Builder() {
            this.dto = new TicketDto();
        }

        public Builder id(int id) {
            dto.setId(id);
            return this;
        }

        public Builder user(User user) {
            dto.setUser(user);
            return this;
        }

        public Builder exhibition(Exhibition exhibition) {
            dto.setExhibition(exhibition);
            return this;
        }

        public Builder hall(Hall hall) {
            dto.setHall(hall);
            return this;
        }

        public Builder price(int price) {
            dto.setPrice(price);
            return this;
        }

        public Builder state(TicketState state) {
            dto.setState(state);
            return this;
        }

        public Builder operationDate(Timestamp date) {
            dto.setOperationDate(date);
            return this;
        }

        public TicketDto build() {
            return dto;
        }
    }
}
