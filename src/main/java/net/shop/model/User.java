package net.shop.model;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class User extends BaseEntity {

    @Id
    @Column(name = "USER_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column (name = "USER_LOGIN")
    private String userLog;

    @Column (name = "USER_PASSWORD")
    private String userPass;

    @Column (name = "IS_ADMIN")
    private boolean isAdmin;

    @Column (name = "IS_BLOCKED")
    private boolean isBlocked;


    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public String getUserLog() {
        return userLog;
    }

    public void setUserLog(String userLog) {
        this.userLog = userLog;
    }

    public String getUserPass() {
        return userPass;
    }

    public void setUserPass(String userPass) {
        this.userPass = userPass;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public void setBlocked(boolean isBlocked) {
        this.isBlocked = isBlocked;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userLog='" + userLog + '\'' +
                ", userPass='" + userPass + '\'' +
                ", isAdmin=" + isAdmin +
                ", isBlocked=" + isBlocked +
                '}';
    }
}
