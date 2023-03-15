package com.example.darknotepad.persistence;

import java.io.Serializable;

public class SerSettings implements Serializable {

    private boolean darkMode;

    private String recentFileDir;


    public boolean isDarkMode() {
        return darkMode;
    }

    public void setDarkMode(boolean darkMode) {
        this.darkMode = darkMode;
    }

    public String getRecentFileDir() {
        return recentFileDir;
    }

    public void setRecentFileDir(String recentFileDir) {
        this.recentFileDir = recentFileDir;
    }
}