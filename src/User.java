import java.util.concurrent.Semaphore;

public class User {
    private String name;
    private String surname;
    private String tag;
    private String phoneNumber;
    private String email;
    private String password;
    private String passwordHash;

    public User(String name, String surname, String tag, String phoneNumber, String email, String password,
                String passwordHash) {
        this.name = name;
        this.surname = surname;
        this.tag = tag;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;
        this.passwordHash = passwordHash;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getInfo() {
        return "name " + name + ", surname " + surname + ", userTAG " + tag +
                ", phoneNumber " + phoneNumber + ", email " + email +
                ", passHash " + passwordHash + ", password " + password;
    }
}