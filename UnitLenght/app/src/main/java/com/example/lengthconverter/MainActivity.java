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
import java.util.Arrays;
import java.util.List;

// old code: I just comment here (backup)
//public class MainActivity extends AppCompatActivity {
//
//    // UI Components
//    private EditText inputValue;
//    private Spinner spinnerFrom, spinnerTo;
//    private Button convertButton;
//    private TextView resultTextView;
//    private RecyclerView historyRecyclerView;
//    private ImageButton deleteHistoryButton;
//    // Adapters
//    private ArrayAdapter<CharSequence> spinnerAdapter;
//    private ConversionAdapter conversionAdapter;
//
//    // Data
//    private List<Conversion> conversionHistory;
//
//    // Selected Units
//    private String fromUnit, toUnit;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        // Initialize views
//        inputValue = findViewById(R.id.inputValue);
//        spinnerFrom = findViewById(R.id.spinnerFrom);
//        spinnerTo = findViewById(R.id.spinnerTo);
//        convertButton = findViewById(R.id.convertButton);
//        resultTextView = findViewById(R.id.resultTextView);
//        historyRecyclerView = findViewById(R.id.historyRecyclerView);
//
//        ImageButton deleteHistoryButton = findViewById(R.id.deleteHistoryButton);
//
//        // Set up Spinner Adapter
//        spinnerAdapter = ArrayAdapter.createFromResource(this,
//                R.array.units_array, android.R.layout.simple_spinner_item);
//        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinnerFrom.setAdapter(spinnerAdapter);
//        spinnerTo.setAdapter(spinnerAdapter);
//
//        // Set default selections
//        spinnerFrom.setSelection(0); // Metre
//        spinnerTo.setSelection(1);   // Millimetre
//
//        // Handle Spinner selections
//        spinnerFrom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//
//                fromUnit = adapterView.getItemAtPosition(i).toString();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//                fromUnit = "Metre";
//            }
//        });
//
//        spinnerTo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//
//                toUnit = adapterView.getItemAtPosition(i).toString();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//                toUnit = "Millimetre";
//            }
//        });
//
//        // Initialize history list and adapter
//        conversionHistory = new ArrayList<>();
//        conversionAdapter = new ConversionAdapter(conversionHistory);
//        historyRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        historyRecyclerView.setAdapter(conversionAdapter);
//
//        // Handle Convert Button click with validation
//        convertButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String inputStr = inputValue.getText().toString().trim();
//
//                // 1. Check for Empty Input
//                if (inputStr.isEmpty()) {
//                    Toast.makeText(MainActivity.this, "Please enter a value", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//
//                double input;
//                // 2. Validate Number Format
//                try {
//                    input = Double.parseDouble(inputStr);
//                } catch (NumberFormatException e) {
//                    Toast.makeText(MainActivity.this, "Invalid number format", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//
//                // 3. Handle Negative Values (Optional)
//                if (input < 0) {
//                    Toast.makeText(MainActivity.this, "Please enter a positive value", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//
//                // Perform the conversion using UnitConverter
//                double result = UnitConverter.convert(input, fromUnit, toUnit);
//
//                // Format the result to remove unnecessary trailing zeros
//                DecimalFormat df = new DecimalFormat("#.####");
//                df.setRoundingMode(RoundingMode.HALF_UP);
//                String resultStr = df.format(result);
//
//                // Update the result TextView
//                resultTextView.setText(getString(R.string.result, resultStr, toUnit));
//                resultTextView.setVisibility(View.VISIBLE);
//
//                // Add to history
//                Conversion conversion = new Conversion(inputStr, fromUnit, toUnit, resultStr);
//                conversionHistory.add(0, conversion); // Add to top of the list
//                conversionAdapter.notifyItemInserted(0);
//                historyRecyclerView.scrollToPosition(0); // Optional: Scroll to the top
//            }
//        });
//
//        // Handle Delete History Button click
//        deleteHistoryButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Clear the history list
//                conversionHistory.clear();
//
//                // Notify the adapter of data change
//                conversionAdapter.notifyDataSetChanged();
//
//                // Optionally, show a message
//                Toast.makeText(MainActivity.this, "Conversion history cleared", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//}


// This is current code I am using.
public class MainActivity extends AppCompatActivity {

    private EditText inputValue;
    private Spinner spinnerFrom, spinnerTo;
    private Button convertButton;
    private TextView resultTextView;
    private RecyclerView historyRecyclerView;
    private ImageButton deleteHistoryButton;

    private ArrayAdapter<CharSequence> spinnerAdapter;
    private ConversionAdapter conversionAdapter;

    private List<Conversion> conversionHistory;

    private String fromUnit, toUnit;

    // List of all units (including all available units)
    private List<CharSequence> allUnits;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputValue = findViewById(R.id.inputValue);
        spinnerFrom = findViewById(R.id.spinnerFrom);
        spinnerTo = findViewById(R.id.spinnerTo);
        convertButton = findViewById(R.id.convertButton);
        resultTextView = findViewById(R.id.resultTextView);
        historyRecyclerView = findViewById(R.id.historyRecyclerView);
        deleteHistoryButton = findViewById(R.id.deleteHistoryButton);

        // Initialize list of units
        allUnits = Arrays.asList("Metre", "Millimetre", "Centimetre", "Kilometre", "Mile", "Foot", "Yard", "Inch");

        // Set up the adapter for the spinners
        spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, allUnits.toArray(new CharSequence[0]));
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFrom.setAdapter(spinnerAdapter);
        spinnerTo.setAdapter(spinnerAdapter);

        // Set default selections
        spinnerFrom.setSelection(0); // Default: Metre
        spinnerTo.setSelection(1);   // Default: Millimetre

        spinnerFrom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                fromUnit = adapterView.getItemAtPosition(i).toString();
                updateToUnitSpinner(); // Update "To" unit spinner when "From" unit is selected
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

        // Initialize conversion history list and adapter
        conversionHistory = new ArrayList<>();
        conversionAdapter = new ConversionAdapter(conversionHistory);
        historyRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        historyRecyclerView.setAdapter(conversionAdapter);

        // Handle convert button click
        convertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String inputStr = inputValue.getText().toString().trim();

                if (inputStr.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter a value", Toast.LENGTH_SHORT).show();
                    return;
                }

                double input;
                try {
                    input = Double.parseDouble(inputStr);
                } catch (NumberFormatException e) {
                    Toast.makeText(MainActivity.this, "Invalid number format", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (input < 0) {
                    Toast.makeText(MainActivity.this, "Please enter a positive value", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Perform conversion
                double result = UnitConverter.convert(input, fromUnit, toUnit);

                DecimalFormat df = new DecimalFormat("#.####");
                df.setRoundingMode(RoundingMode.HALF_UP);
                String resultStr = df.format(result);
                resultTextView.setText(getString(R.string.result, resultStr, toUnit));
                resultTextView.setVisibility(View.VISIBLE);

                // Add conversion to history
                Conversion conversion = new Conversion(inputStr, fromUnit, toUnit, resultStr);
                conversionHistory.add(0, conversion);
                conversionAdapter.notifyItemInserted(0);
                historyRecyclerView.scrollToPosition(0);
            }
        });

        // Handle delete history button click
        deleteHistoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                conversionHistory.clear();
                conversionAdapter.notifyDataSetChanged();
                Toast.makeText(MainActivity.this, "Conversion history cleared", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Method to update the "To" unit spinner when "From" unit is selected
    private void updateToUnitSpinner() {
        // Create a list of available units for the "To" spinner (excluding the selected "From" unit)
        List<CharSequence> toUnits = new ArrayList<>(allUnits); // Use CharSequence instead of String
        toUnits.remove(fromUnit); // Remove the selected "From" unit

        // Update the "To" spinner with the new list of units
        ArrayAdapter<CharSequence> toUnitAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, toUnits.toArray(new CharSequence[0]));
        toUnitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTo.setAdapter(toUnitAdapter);

        // Ensure the "To" spinner doesn't select the "From" unit
        if (toUnits.contains(toUnit)) {
            spinnerTo.setSelection(toUnits.indexOf(toUnit));
        } else {
            spinnerTo.setSelection(0); // If the previous "To" unit is not available, select the first unit
        }
    }
}

