package ooc.webapp.possawat.model;

import javax.persistence.*;

@Entity
@Table(name = "APP_USER")
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;

    private String userName;
    private String encryptedPassword;

    public AppUser() {

    }

    public AppUser(Long userId, String userName, String encryptedPassword) {
        this.userId = userId;
        this.userName = userName;
        this.encryptedPassword = encryptedPassword;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public void setEncryptedPassword(String encrytedPassword) {
        this.encryptedPassword = encrytedPassword;
    }

    @Override
    public String toString() {
        return this.userName + "/" + this.encryptedPassword;
    }

}
