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
    private String log;

    @Column (name = "USER_PASSWORD")
    private String pass;

    @Column (name = "IS_ADMIN")
    private boolean isAdmin;

    @Column (name = "IS_BLOCKED")
    private boolean isBlocked;


    public String getUserLog() {
        return log;
    }

    public void setUserLog(String userLog) {
        this.log = userLog;
    }

    public String getUserPass() {
        return pass;
    }

    public void setUserPass(String userPass) {
        this.pass = userPass;
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

}
