package dto;

import entity.Exhibition;
import entity.ExhibitionState;
import entity.Hall;
import entity.Theme;

import java.io.Serializable;
import java.util.List;

public class ExhibitionDto extends Exhibition implements Serializable {
    private Theme theme;
    private ExhibitionState state;
    private List<Hall> halls;

    public Theme getTheme() {
        return theme;
    }

    public void setTheme(Theme theme) {
        this.theme = theme;
    }

    public ExhibitionState getState() {
        return state;
    }

    public void setState(ExhibitionState state) {
        this.state = state;
    }

    public List<Hall> getHalls() {
        return halls;
    }

    public void setHalls(List<Hall> halls) {
        this.halls = halls;
    }

    public static class Builder {
        private final ExhibitionDto dto;

        public Builder() {
            dto = new ExhibitionDto();
        }

        public Builder id(int id) {
            dto.setId(id);
            return this;
        }

        public Builder title(String title) {
            dto.setTitle(title);
            return this;
        }

        public Builder description(String desc) {
            dto.setDescription(desc);
            return this;
        }

        public Builder startDate(String date) {
            dto.setStartDate(date);
            return this;
        }

        public Builder finishDate(String date) {
            dto.setFinishDate(date);
            return this;
        }

        public Builder openTime(String time) {
            dto.setOpenTime(time);
            return this;
        }

        public Builder closeTime(String time) {
            dto.setCloseTime(time);
            return this;
        }

        public Builder price(int price) {
            dto.setPrice(price);
            return this;
        }

        public Builder image(String image) {
            dto.setImage(image);
            return this;
        }

        public Builder theme(Theme theme) {
            dto.setTheme(theme);
            return this;
        }

        public Builder halls(List<Hall> halls) {
            dto.setHalls(halls);
            return this;
        }

        public Builder state(ExhibitionState state) {
            dto.setState(state);
            return this;
        }

        public ExhibitionDto build() {
            return dto;
        }
    }
}

