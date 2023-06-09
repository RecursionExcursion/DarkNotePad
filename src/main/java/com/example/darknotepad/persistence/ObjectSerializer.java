package com.example.darknotepad.persistence;

import java.io.*;

public class ObjectSerializer<T extends Serializable> {

    private final File filePath;

    public ObjectSerializer(String filePath) {
        this.filePath = new File(filePath + ".ser");
    }

    public T load() {
        return deserialize(filePath);
    }

    public void save(T object) {
        serialize(object, filePath);
    }

    private void serialize(T object, File file) {
        try (FileOutputStream fileOut = new FileOutputStream(file);
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(object);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unchecked")
    private T deserialize(File file) {
        try (FileInputStream fileIn = new FileInputStream(file);
             ObjectInputStream in = new ObjectInputStream(fileIn)) {
            return (T) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}