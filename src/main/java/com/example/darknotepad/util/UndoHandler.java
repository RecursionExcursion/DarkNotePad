package com.example.darknotepad.util;

import java.util.Stack;

public class UndoHandler {

    private final Stack<String> stringStack = new Stack<>();

    public void storeState(String s) {
        stringStack.push(s);
    }

    public String getLastState() {
        return stringStack.size() > 1 ? stringStack.pop() : stringStack.peek();
    }
}
