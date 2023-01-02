package entity;

import java.util.List;

public class Exhibition {
    private int id;
    private String title;
    private String description;
    private int themeId;
    private String startDate;
    private String finishDate;
    private String openTime;
    private String closeTime;
    private int price;
    private String image;
    private String stateFromDb;
    private List<Hall> halls;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getThemeId() {
        return themeId;
    }

    public void setThemeId(int themeId) {
        this.themeId = themeId;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(String finishDate) {
        this.finishDate = finishDate;
    }

    public String getOpenTime() {
        return openTime;
    }

    public void setOpenTime(String openTime) {
        this.openTime = openTime;
    }

    public String getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(String closeTime) {
        this.closeTime = closeTime;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getStateFromDb() {
        return stateFromDb;
    }

    public void setStateFromDb(String stateFromDb) {
        this.stateFromDb = stateFromDb;
    }

    public List<Hall> getHalls() {
        return halls;
    }

    public void setHalls(List<Hall> halls) {
        this.halls = halls;
    }

    public static class Builder {
        private final Exhibition exhibition;

        public Builder() {
            exhibition = new Exhibition();
        }

        public Builder id(int id) {
            exhibition.setId(id);
            return this;
        }

        public Builder title(String title) {
            exhibition.setTitle(title);
            return this;
        }

        public Builder description(String desc) {
            exhibition.setDescription(desc);
            return this;
        }

        public Builder themeId(int theme) {
            exhibition.setThemeId(theme);
            return this;
        }

        public Builder startDate(String date) {
            exhibition.setStartDate(date);
            return this;
        }

        public Builder finishDate(String date) {
            exhibition.setFinishDate(date);
            return this;
        }

        public Builder openTime(String time) {
            exhibition.setOpenTime(time);
            return this;
        }

        public Builder closeTime(String time) {
            exhibition.setCloseTime(time);
            return this;
        }

        public Builder price(int price) {
            exhibition.setPrice(price);
            return this;
        }

        public Builder image(String image) {
            exhibition.setImage(image);
            return this;
        }

        public Builder state(String state) {
            exhibition.setStateFromDb(state);
            return this;
        }

        public Builder halls(List<Hall> halls) {
            exhibition.setHalls(halls);
            return this;
        }

        public Exhibition build() {
            return exhibition;
        }
    }
}
