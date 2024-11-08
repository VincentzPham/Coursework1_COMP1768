// src/main/java/com/example/lengthconverter/Conversion.java
package com.example.lengthconverter;

public class Conversion {
    private String input;
    private String fromUnit;
    private String toUnit;
    private String result;

    public Conversion(String input, String fromUnit, String toUnit, String result) {
        this.input = input;
        this.fromUnit = fromUnit;
        this.toUnit = toUnit;
        this.result = result;
    }

    // Getters
    public String getInput() { return input; }
    public String getFromUnit() { return fromUnit; }
    public String getToUnit() { return toUnit; }
    public String getResult() { return result; }
}
