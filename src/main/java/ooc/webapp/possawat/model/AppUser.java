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
    /*
    * just for fun
     */
    private String status;


    public AppUser() {

    }

    public AppUser(Long userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }

    public AppUser(Long userId, String userName, String encryptedPassword) {
        this.userId = userId;
        this.userName = userName;
        this.encryptedPassword = encryptedPassword;
    }

    public AppUser(Long userId, String userName, String encryptedPassword, String status) {
        this.userId = userId;
        this.userName = userName;
        this.encryptedPassword = encryptedPassword;
        this.status = status;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return this.userName + "/" + this.encryptedPassword;
    }

}
