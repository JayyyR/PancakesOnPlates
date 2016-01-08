package com.joeracosta.library;

final public class Preconditions {

    public static void checkNotNull(Object o, String message) {
        if (o == null) {
            throw new NullPointerException(message);
        }
    }

    public static void checkStringNotEmpty(String s, String message) {
        if (s == null || s.isEmpty()) {
            throw new IllegalArgumentException(message);
        }
    }

    private Preconditions() {
        throw new AssertionError("No instances");
    }
}
