package com.example.darknotepad.dto;

import javafx.scene.layout.Pane;

public record IsTextSavedDto(boolean isTextSaved, String text, Pane pane) { }