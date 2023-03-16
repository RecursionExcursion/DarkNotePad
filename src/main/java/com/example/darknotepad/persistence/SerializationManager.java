package com.example.darknotepad.persistence;

public enum SerializationManager {

    INSTANCE;

    private final ObjectSerializer<SerializableSettings> objectSerializer;
    private final String settingsFilePath = "src/main/resources/settings/";
    private final String fileName = "settings";
    private SerializableSettings settings;

    SerializationManager() {
        objectSerializer = new ObjectSerializer<>(settingsFilePath + fileName);

        try {
            settings = objectSerializer.load();
        } catch (Exception e) {
            objectSerializer.save(new SerializableSettings());
            settings = objectSerializer.load();
        }
    }

    public void save() {
        saveSettings(settings);
    }

    public SerializableSettings getSettings() {
        return settings;
    }

    private void saveSettings(SerializableSettings t) {
        objectSerializer.save(t);
    }
}
