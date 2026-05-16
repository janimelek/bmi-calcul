package com.example.bmicalculator;

public class BmiCalculator {

    // Private constructor to prevent instantiation
    private BmiCalculator() {
    }

    public static double calculateBMI(double weightKg, double heightCm) {
        double heightM = heightCm / 100.0;
        return weightKg / (heightM * heightM);
    }

    public static String getCategory(double bmi) {
        if (bmi < 18.5) {
            return "Underweight";
        } else if (bmi < 25.0) {
            return "Normal Weight";
        } else if (bmi < 30.0) {
            return "Overweight";
        } else {
            return "Obese";
        }
    }

    public static double calculateBMIPrime(double bmi) {
        return bmi / 25.0;
    }

    public static float[] getHealthyWeightRange(double heightCm) {
        double heightM = heightCm / 100.0;
        float lower = (float) (18.5 * (heightM * heightM));
        float upper = (float) (24.9 * (heightM * heightM));
        return new float[]{lower, upper};
    }
}
