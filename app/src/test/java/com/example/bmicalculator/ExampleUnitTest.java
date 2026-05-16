package com.example.bmicalculator;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Local unit tests for BMI business logic.
 * These run on the JVM — no Android device or emulator required.
 */
public class ExampleUnitTest {

    /** BMI formula sanity check: 70 kg, 1.75 m → ~22.86 */
    @Test
    public void bmi_metric_calculation_isCorrect() {
        double weight = 70.0;      // kg
        double height = 1.75;      // metres
        double bmi    = weight / (height * height);
        assertEquals(22.86, bmi, 0.01);
    }

    /** Imperial conversion sanity check: 154 lbs, 5 ft 9 in → ~22.77 */
    @Test
    public void bmi_imperial_calculation_isCorrect() {
        double weightLbs    = 154.0;
        double heightFt     = 5.0;
        double heightIn     = 9.0;

        double weightKg     = weightLbs * 0.453592;
        double heightMetres = (heightFt * 12 + heightIn) * 0.0254;
        double bmi          = weightKg / (heightMetres * heightMetres);

        assertEquals(22.77, bmi, 0.1);
    }
}
