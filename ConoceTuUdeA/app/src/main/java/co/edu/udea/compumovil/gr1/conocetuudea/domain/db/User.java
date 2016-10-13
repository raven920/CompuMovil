package co.edu.udea.compumovil.gr1.conocetuudea.domain.db;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by raven on 12/10/16.
 */

@IgnoreExtraProperties
public class User implements Comparable<User> {
    public String username;
    public String email;
    public int charId;
    public int score;

    public User() {

    }

    public User(String username, String email, int charId, int score) {
        this.username = username;
        this.email = email;
        this.charId = charId;
        this.score = score;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getCharId() {
        return charId;
    }

    public void setCharId(int charId) {
        this.charId = charId;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }


    @Override
    public int compareTo(User o) {
        return o.getScore()-score;
    }
}
