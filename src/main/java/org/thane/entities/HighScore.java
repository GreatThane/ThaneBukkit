package org.thane.entities;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import org.bukkit.plugin.Plugin;
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

    public void serialize(Plugin plugin) {
        if(!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdirs();
        }
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(new File(plugin.getDataFolder(),userName + ".ser"));
            ObjectOutputStream outputStream = new ObjectOutputStream(fileOutputStream);
            outputStream.writeObject(this);
            fileOutputStream.close();
            outputStream.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
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
