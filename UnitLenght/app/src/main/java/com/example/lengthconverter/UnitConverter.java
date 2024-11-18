// src/main/java/com/example/lengthconverter/UnitConverter.java
package com.example.lengthconverter;

import java.util.HashMap;
import java.util.Map;

public class UnitConverter {
    // I will set base unit: Metre
    private static final Map<String, Double> unitToMetreMap = new HashMap<>();

    static {
        unitToMetreMap.put("Metre", 1.0);
        unitToMetreMap.put("Millimetre", 0.001);
        unitToMetreMap.put("Mile", 1609.34);
        unitToMetreMap.put("Foot", 0.3048);

        //I also add more 4 units below: Centimetre, Kilometre, Yard, Inch to expand user experience
        unitToMetreMap.put("Centimetre", 0.01);
        unitToMetreMap.put("Kilometre", 1000.0);
        unitToMetreMap.put("Yard", 0.9144);
        unitToMetreMap.put("Inch", 0.0254);

    }

    public static double convert(double value, String fromUnit, String toUnit) {
        double valueInMetres = value * unitToMetreMap.get(fromUnit);
        return valueInMetres / unitToMetreMap.get(toUnit);
    }
}
