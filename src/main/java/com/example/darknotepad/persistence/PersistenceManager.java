package com.example.darknotepad.persistence;

public class PersistenceManager {

    private static PersistenceManager INSTANCE = new PersistenceManager();

    private final MemorySerializationManager<SerSettings> memorySerializationManager;

    private final String settingsFilePath = "src/main/resources/settings/";
    private final String fileName = "settings";


    private PersistenceManager() {
        memorySerializationManager = new MemorySerializationManager<>(settingsFilePath + fileName);
    }

    public static PersistenceManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PersistenceManager();
        }
        return INSTANCE;
    }

    public SerSettings getObject() {
        try {
            return memorySerializationManager.load();
        } catch (Exception e) {
            memorySerializationManager.save(new SerSettings());
        }
        return getObject();
    }

    public void saveObject(SerSettings t) {
        memorySerializationManager.save(t);
    }


}
