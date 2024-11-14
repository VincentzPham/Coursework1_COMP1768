// src/main/java/com/example/lengthconverter/MainActivity.java
package com.example.lengthconverter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // UI Components
    private EditText inputValue;
    private Spinner spinnerFrom, spinnerTo;
    private Button convertButton;
    private TextView resultTextView;
    private RecyclerView historyRecyclerView;
    private ImageButton deleteHistoryButton;
    // Adapters
    private ArrayAdapter<CharSequence> spinnerAdapter;
    private ConversionAdapter conversionAdapter;

    // Data
    private List<Conversion> conversionHistory;

    // Selected Units
    private String fromUnit, toUnit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        inputValue = findViewById(R.id.inputValue);
        spinnerFrom = findViewById(R.id.spinnerFrom);
        spinnerTo = findViewById(R.id.spinnerTo);
        convertButton = findViewById(R.id.convertButton);
        resultTextView = findViewById(R.id.resultTextView);
        historyRecyclerView = findViewById(R.id.historyRecyclerView);

        ImageButton deleteHistoryButton = findViewById(R.id.deleteHistoryButton);

        // Set up Spinner Adapter
        spinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.units_array, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFrom.setAdapter(spinnerAdapter);
        spinnerTo.setAdapter(spinnerAdapter);

        // Set default selections
        spinnerFrom.setSelection(0); // Metre
        spinnerTo.setSelection(1);   // Millimetre

        // Handle Spinner selections
        spinnerFrom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                fromUnit = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                fromUnit = "Metre";
            }
        });

        spinnerTo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                toUnit = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                toUnit = "Millimetre";
            }
        });

        // Initialize history list and adapter
        conversionHistory = new ArrayList<>();
        conversionAdapter = new ConversionAdapter(conversionHistory);
        historyRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        historyRecyclerView.setAdapter(conversionAdapter);

        // Handle Convert Button click with validation
        convertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String inputStr = inputValue.getText().toString().trim();

                // 1. Check for Empty Input
                if (inputStr.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter a value", Toast.LENGTH_SHORT).show();
                    return;
                }

                double input;
                // 2. Validate Number Format
                try {
                    input = Double.parseDouble(inputStr);
                } catch (NumberFormatException e) {
                    Toast.makeText(MainActivity.this, "Invalid number format", Toast.LENGTH_SHORT).show();
                    return;
                }

                // 3. Handle Negative Values (Optional)
                if (input < 0) {
                    Toast.makeText(MainActivity.this, "Please enter a positive value", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Perform the conversion using UnitConverter
                double result = UnitConverter.convert(input, fromUnit, toUnit);

                // Format the result to remove unnecessary trailing zeros
                DecimalFormat df = new DecimalFormat("#.####");
                df.setRoundingMode(RoundingMode.HALF_UP);
                String resultStr = df.format(result);

                // Update the result TextView
                resultTextView.setText(getString(R.string.result, resultStr, toUnit));
                resultTextView.setVisibility(View.VISIBLE);

                // Add to history
                Conversion conversion = new Conversion(inputStr, fromUnit, toUnit, resultStr);
                conversionHistory.add(0, conversion); // Add to top of the list
                conversionAdapter.notifyItemInserted(0);
                historyRecyclerView.scrollToPosition(0); // Optional: Scroll to the top
            }
        });

        // Handle Delete History Button click
        // Handle Delete History Button click
        deleteHistoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Clear the history list
                conversionHistory.clear();

                // Notify the adapter of data change
                conversionAdapter.notifyDataSetChanged();

                // Optionally, show a message
                Toast.makeText(MainActivity.this, "Conversion history cleared", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
