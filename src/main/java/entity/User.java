package entity;

public class User {

    private int id;
    private String login;
    private String pass;
    private String firstName;
    private String lastName;
    private String email;
    private String role;
    private double balance = 0.0;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return User.Role.valueOf(role);
    }

    public void setRole(Role role) {
        this.role = role.name();
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                ", balance=" + balance +
                '}';
    }

    public enum Role {
        ROLE_ADMIN,
        ROLE_USER
    }

    public static class Builder {
        private final User user;

        public Builder() {
            this.user = new User();
        }

        public Builder id(int id) {
            user.setId(id);
            return this;
        }

        public Builder login(String login) {
            user.setLogin(login);
            return this;
        }

        public Builder pass(String pass) {
            user.setPass(pass);
            return this;
        }

        public Builder firstName(String fname) {
            user.setFirstName(fname);
            return this;
        }

        public Builder lastName(String lname) {
            user.setLastName(lname);
            return this;
        }

        public Builder email(String email) {
            user.setEmail(email);
            return this;
        }

        public Builder role(Role role) {
            user.setRole(role);
            return this;
        }

        public Builder balance(double balance) {
            user.setBalance(balance);
            return this;
        }

        public User build() {
            return user;
        }
    }
}
