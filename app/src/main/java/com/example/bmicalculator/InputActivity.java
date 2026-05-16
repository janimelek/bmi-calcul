package com.example.bmicalculator;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

public class InputActivity extends AppCompatActivity {

    public static final String EXTRA_HEIGHT = "EXTRA_HEIGHT";
    public static final String EXTRA_WEIGHT = "EXTRA_WEIGHT";
    public static final String EXTRA_AGE = "EXTRA_AGE";
    public static final String EXTRA_GENDER = "EXTRA_GENDER";

    private int height = 170;
    private int weight = 70;
    private int age = 25;
    private String gender = "male";

    private CardView cvMale, cvFemale;
    private LinearLayout llMalePanel, llFemalePanel;
    private TextView tvMaleLabel, tvFemaleLabel;
    private ImageView ivMaleIcon, ivFemaleIcon;

    private SeekBar sbHeight, sbWeight, sbAge;
    private TextView tvHeightValue, tvWeightValue, tvAgeValue;
    private ImageButton ibHeightMinus, ibHeightPlus, ibWeightMinus, ibWeightPlus, ibAgeMinus, ibAgePlus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        // Gender views
        cvMale = findViewById(R.id.cvMale);
        cvFemale = findViewById(R.id.cvFemale);
        llMalePanel = findViewById(R.id.llMalePanel);
        llFemalePanel = findViewById(R.id.llFemalePanel);
        tvMaleLabel = findViewById(R.id.tvMaleLabel);
        tvFemaleLabel = findViewById(R.id.tvFemaleLabel);
        ivMaleIcon = findViewById(R.id.ivMaleIcon);
        ivFemaleIcon = findViewById(R.id.ivFemaleIcon);

        // Height views
        sbHeight = findViewById(R.id.sbHeight);
        tvHeightValue = findViewById(R.id.tvHeightValue);
        ibHeightMinus = findViewById(R.id.ibHeightMinus);
        ibHeightPlus = findViewById(R.id.ibHeightPlus);

        // Weight views
        sbWeight = findViewById(R.id.sbWeight);
        tvWeightValue = findViewById(R.id.tvWeightValue);
        ibWeightMinus = findViewById(R.id.ibWeightMinus);
        ibWeightPlus = findViewById(R.id.ibWeightPlus);

        // Age views
        sbAge = findViewById(R.id.sbAge);
        tvAgeValue = findViewById(R.id.tvAgeValue);
        ibAgeMinus = findViewById(R.id.ibAgeMinus);
        ibAgePlus = findViewById(R.id.ibAgePlus);

        Button btnCalculateBmi = findViewById(R.id.btnCalculateBmi);

        // Initialize UI
        updateGenderUI();

        // Gender logic
        cvMale.setOnClickListener(v -> {
            gender = "male";
            updateGenderUI();
        });

        cvFemale.setOnClickListener(v -> {
            gender = "female";
            updateGenderUI();
        });

        // Height logic (range 100 to 220, offset 100)
        sbHeight.setProgress(height - 100);
        tvHeightValue.setText(height + " cm");

        sbHeight.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                height = progress + 100;
                tvHeightValue.setText(height + " cm");
            }
            @Override public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        ibHeightMinus.setOnClickListener(v -> {
            if (height > 100) {
                height--;
                sbHeight.setProgress(height - 100);
            }
        });

        ibHeightPlus.setOnClickListener(v -> {
            if (height < 220) {
                height++;
                sbHeight.setProgress(height - 100);
            }
        });

        // Weight logic (range 30 to 200, offset 30)
        sbWeight.setProgress(weight - 30);
        tvWeightValue.setText(weight + " kg");

        sbWeight.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                weight = progress + 30;
                tvWeightValue.setText(weight + " kg");
            }
            @Override public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        ibWeightMinus.setOnClickListener(v -> {
            if (weight > 30) {
                weight--;
                sbWeight.setProgress(weight - 30);
            }
        });

        ibWeightPlus.setOnClickListener(v -> {
            if (weight < 200) {
                weight++;
                sbWeight.setProgress(weight - 30);
            }
        });

        // Age logic (range 1 to 100, no offset)
        sbAge.setMax(100); // Ensure max is 100 since XML might have 99
        sbAge.setProgress(age);
        tvAgeValue.setText(age + " yrs");

        sbAge.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                age = progress;
                if (age < 1) {
                    age = 1;
                    seekBar.setProgress(age);
                }
                tvAgeValue.setText(age + " yrs");
            }
            @Override public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        ibAgeMinus.setOnClickListener(v -> {
            if (age > 1) {
                age--;
                sbAge.setProgress(age);
            }
        });

        ibAgePlus.setOnClickListener(v -> {
            if (age < 100) {
                age++;
                sbAge.setProgress(age);
            }
        });

        // Calculate logic
        btnCalculateBmi.setOnClickListener(v -> {
            if (height > 0 && weight > 0) {
                Intent intent = new Intent(InputActivity.this, ResultActivity.class);
                intent.putExtra(EXTRA_HEIGHT, height);
                intent.putExtra(EXTRA_WEIGHT, weight);
                intent.putExtra(EXTRA_AGE, age);
                intent.putExtra(EXTRA_GENDER, gender);
                startActivity(intent);
            }
        });
    }

    private void updateGenderUI() {
        if ("male".equals(gender)) {
            llMalePanel.setBackgroundResource(R.drawable.card_gender_selected);
            llFemalePanel.setBackgroundResource(R.drawable.card_gender_unselected);
            tvMaleLabel.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
            ivMaleIcon.setColorFilter(ContextCompat.getColor(this, R.color.colorPrimary));
            tvFemaleLabel.setTextColor(ContextCompat.getColor(this, R.color.hint));
            ivFemaleIcon.setColorFilter(ContextCompat.getColor(this, R.color.hint));
        } else {
            llFemalePanel.setBackgroundResource(R.drawable.card_gender_selected);
            llMalePanel.setBackgroundResource(R.drawable.card_gender_unselected);
            tvFemaleLabel.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
            ivFemaleIcon.setColorFilter(ContextCompat.getColor(this, R.color.colorPrimary));
            tvMaleLabel.setTextColor(ContextCompat.getColor(this, R.color.hint));
            ivMaleIcon.setColorFilter(ContextCompat.getColor(this, R.color.hint));
        }
    }
}
