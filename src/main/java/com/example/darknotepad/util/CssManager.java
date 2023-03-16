package com.example.darknotepad.util;

import com.example.darknotepad.persistence.SerializationManager;
import com.example.darknotepad.persistence.SerializableSettings;

import java.net.URL;

public enum CssManager {

    INSTANCE;

    private final String lightCssPath = "/css/light.css";
    private final String darkCssPath = "/css/dark.css";

    private final SerializableSettings settings = SerializationManager.INSTANCE.getSettings();

    public String getCssUrl() {
        URL cssUrl = !settings.isDarkMode() ?
                getClass().getResource(lightCssPath) : getClass().getResource(darkCssPath);
        return returnStringOrExit(cssUrl);
    }

    public String getOppositeCssUrl() {
        URL cssUrl = settings.isDarkMode() ?
                getClass().getResource(lightCssPath) : getClass().getResource(darkCssPath);
        return returnStringOrExit(cssUrl);
    }

    private static String returnStringOrExit(URL cssUrl) {
        if (cssUrl == null) {
            System.out.println("Resource not found. Aborting application.");
            System.exit(-1);
        }
        return cssUrl.toExternalForm();
    }
}
