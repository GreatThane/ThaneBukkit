package org.thane.entities;

import java.io.Serializable;

/**
 * Created by GreatThane on 9/2/16.
 */
public class HighScore implements Comparable<HighScore>, Serializable {

    private int seconds;
    private String userName;

    public HighScore(String userName, int seconds) {
        this.userName = userName;
        this.seconds = seconds;
    }

    public int getSeconds() {
        return seconds;
    }

    public String getUserName() {
        return userName;
    }

    public int compareTo(HighScore otherHighScore) {
        return Integer.compare(this.getSeconds(), otherHighScore.getSeconds());
    }

}
