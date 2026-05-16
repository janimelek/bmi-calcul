package com.example.bmicalculator;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

public class ResultActivity extends AppCompatActivity {

    private CardView cvBmiCircle;
    private TextView tvBmiNumber, tvBmiCategoryCircle;
    private TextView tvDetailBmiValue, tvDetailCategory, tvDetailRange, tvDetailBmiPrime;
    private TextView tvHealthTip;
    private Button btnRecalculate, btnShareResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        // Bind Views
        Toolbar toolbar = findViewById(R.id.toolbar);
        cvBmiCircle = findViewById(R.id.cvBmiCircle);
        tvBmiNumber = findViewById(R.id.tvBmiNumber);
        tvBmiCategoryCircle = findViewById(R.id.tvBmiCategoryCircle);
        tvDetailBmiValue = findViewById(R.id.tvDetailBmiValue);
        tvDetailCategory = findViewById(R.id.tvDetailCategory);
        tvDetailRange = findViewById(R.id.tvDetailRange);
        tvDetailBmiPrime = findViewById(R.id.tvDetailBmiPrime);
        tvHealthTip = findViewById(R.id.tvHealthTip);
        btnRecalculate = findViewById(R.id.btnRecalculate);
        btnShareResult = findViewById(R.id.btnShareResult);

        // Toolbar setup
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        toolbar.setNavigationOnClickListener(v -> finish());

        // Retrieve Extras
        Intent intent = getIntent();
        int height = intent.getIntExtra(InputActivity.EXTRA_HEIGHT, 170);
        int weight = intent.getIntExtra(InputActivity.EXTRA_WEIGHT, 70);
        int age = intent.getIntExtra(InputActivity.EXTRA_AGE, 25);
        String gender = intent.getStringExtra(InputActivity.EXTRA_GENDER);

        // Compute BMI using BmiCalculator
        double bmi = BmiCalculator.calculateBMI(weight, height);
        String bmiFormatted = String.format("%.1f", bmi);

        // Compute BMI Prime using BmiCalculator
        double bmiPrime = BmiCalculator.calculateBMIPrime(bmi);
        String bmiPrimeFormatted = String.format("%.2f", bmiPrime);

        // Compute Healthy Range using BmiCalculator
        float[] range = BmiCalculator.getHealthyWeightRange(height);
        String rangeFormatted = String.format("%.1f - %.1f kg", range[0], range[1]);

        // Determine Category using BmiCalculator
        String category = BmiCalculator.getCategory(bmi);

        // Determine Color and Tip based on Category
        int colorRes;
        String tip;

        if ("Underweight".equals(category)) {
            colorRes = R.color.bmiOverweight; // amber/orange as requested
            tip = "Consult a nutritionist and focus on calorie-dense whole foods to reach a healthy weight safely.";
        } else if ("Normal Weight".equals(category)) {
            colorRes = R.color.bmiNormal; // green
            tip = "Congratulations! Keep up the good work by maintaining your healthy lifestyle, balanced diet, and regular physical activity.";
        } else if ("Overweight".equals(category)) {
            colorRes = R.color.bmiOverweight; // amber/orange
            tip = "Consider incorporating regular aerobic exercise and reducing your intake of processed foods to gradually reach a healthier weight.";
        } else {
            colorRes = R.color.bmiObese; // red
            tip = "It is strongly recommended to seek medical advice. A healthcare professional can help you start a personalized plan, potentially beginning with low-impact exercise.";
        }

        // Apply to UI
        cvBmiCircle.setCardBackgroundColor(ContextCompat.getColor(this, colorRes));
        tvBmiNumber.setText(bmiFormatted);
        tvBmiCategoryCircle.setText(category);

        tvDetailBmiValue.setText(bmiFormatted);
        tvDetailCategory.setText(category);
        tvDetailRange.setText(rangeFormatted);
        tvDetailBmiPrime.setText(bmiPrimeFormatted);

        tvHealthTip.setText(tip);

        // Buttons
        btnRecalculate.setOnClickListener(v -> finish());

        btnShareResult.setOnClickListener(v -> {
            String shareText = "My BMI is " + bmiFormatted + " (" + category + "). My healthy weight range is " + rangeFormatted + ".";
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, shareText);
            sendIntent.setType("text/plain");
            Intent shareIntent = Intent.createChooser(sendIntent, "Share Result");
            startActivity(shareIntent);
        });
    }
}
