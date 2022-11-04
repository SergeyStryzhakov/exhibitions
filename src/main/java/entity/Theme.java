package entity;

public class Theme {
    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Theme{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public static class Builder {
        private final Theme theme;

        public Builder() {
            this.theme = new Theme();
        }

        public Builder id(int id) {
            theme.setId(id);
            return this;
        }

        public Builder name(String name) {
            theme.setName(name);
            return this;
        }

        public Theme build() {
            return theme;
        }
    }
}
