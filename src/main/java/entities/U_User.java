package entities;

import javax.persistence.*;

@Entity
@Table(name = "U_User")
public class U_User {
    @Id
    @Column(name = "U_ID", unique = true, nullable = false)
    private int id;

    @Column(name = "U_Username")
    private String username;

    @Column(name = "U_Userpasswort")
    private String password;

    @Column(name = "U_Score")
    private int score;

    @Column(name = "U_Level")
    private String level;

    // Konstruktor, Getter und Setter



    // Default-Konstruktor für JPA
    public U_User() {}

    public U_User(int id, String username, String password, int score, String level) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.score = score;
        this.level = level;
    }

    // Getter und Setter
    // ...
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}
