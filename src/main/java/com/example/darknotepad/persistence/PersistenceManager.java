package com.example.darknotepad.persistence;

public class PersistenceManager {

    private static PersistenceManager INSTANCE;

    private final MemorySerializationManager<SerSettings> memorySerializationManager;

    private final String settingsFilePath = "src/main/resources/settings/settings";

    private PersistenceManager() {
        memorySerializationManager = new MemorySerializationManager<>(settingsFilePath);
    }

    public static PersistenceManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PersistenceManager();
        }
        return INSTANCE;
    }

    public SerSettings getSettings() {
        try {
            return memorySerializationManager.load();
        } catch (Exception e) {
            //Overwrites Settings.ser with new version if corrupt
            memorySerializationManager.save(new SerSettings());
        }
        return getSettings();
    }

    public void SaveSettings(SerSettings serSettings) {
        memorySerializationManager.save(serSettings);
    }
}
