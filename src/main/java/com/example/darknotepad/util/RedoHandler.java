package com.example.darknotepad.util;

import java.util.Stack;

public class RedoHandler {

    private final Stack<String> stringStack = new Stack<>();

    public void storeState(String s) {
        stringStack.push(s);
    }

    public String getLastState() {
        return stringStack.size() > 0 ? stringStack.pop() : null;
    }

    public boolean isEmpty() {
        return stringStack.isEmpty();
    }

    public void clear() {
        stringStack.clear();
    }
}
