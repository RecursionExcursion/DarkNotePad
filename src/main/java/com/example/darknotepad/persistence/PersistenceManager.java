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

    public SerSettings getSettings(){
        if(!memorySerializationManager.fileExists()){
            memorySerializationManager.save(new SerSettings());
        }
        return memorySerializationManager.load();
    }

    public void SaveSettings(SerSettings serSettings){
        memorySerializationManager.save(serSettings);
    }
}
