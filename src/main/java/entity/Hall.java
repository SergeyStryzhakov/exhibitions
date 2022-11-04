package entity;

public class Hall {
    private int id;
    private String name;
    private String address;
    private int capacity;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    @Override
    public String toString() {
        return "Hall{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", capacity='" + capacity + '\'' +
                '}';
    }

    public static class Builder {
        private final Hall hall;

        public Builder() {
            this.hall = new Hall();
        }

        public Builder id(int id) {
            hall.setId(id);
            return this;
        }

        public Builder name(String name) {
            hall.setName(name);
            return this;
        }

        public Builder address(String address) {
            hall.setAddress(address);
            return this;
        }

        public Builder capacity(int capacity) {
            hall.setCapacity(capacity);
            return this;
        }

        public Hall build() {
            return hall;
        }
    }
}
