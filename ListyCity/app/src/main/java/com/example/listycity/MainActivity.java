package com.example.listycity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    // Initiating all the variables
    ListView cityList;
    ArrayAdapter<String> cityAdapter;
    ArrayList<String> dataList;
    Button addCityButton;
    Button removeCityButton;
    Button confirmButton;
    TextInputEditText textInputEditText;
    String cityAdded;
    Boolean invisible = true;
    Boolean removeMode = false;

    @SuppressLint("ResourceAsColor")
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Find the views
        addCityButton = findViewById(R.id.add_city);
        confirmButton = findViewById(R.id.text_input_button);
        cityList = findViewById(R.id.city_list);
        textInputEditText = findViewById((R.id.text_input_edit_text));
        removeCityButton = findViewById(R.id.remove_city);

        addCityButton.setBackgroundColor(R.color.purple);
        removeCityButton.setBackgroundColor(R.color.purple);

        String[] cities =
                {"Edmonton", "Montréal"};
        dataList = new ArrayList<>();
        dataList.addAll(Arrays.asList(cities));

        cityAdapter = new ArrayAdapter<>(this, R.layout.content, dataList);
        cityList.setAdapter(cityAdapter);

        // Clicked buttons
        addCityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickedAddCity();
            }
        });

        removeCityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickedRemoveButton();
            }
        });

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cityAdded = clickedConfirmedButton();
                putCitiesInList(dataList, cityAdded);
            }
        });

    }
    protected void onStart() {
        super.onStart();

    }

    protected void onResume() {
        super.onResume();
    }


    private void putCitiesInList(ArrayList<String> dataList, String newCity) {
        if (!newCity.isEmpty()) {
            dataList.add(newCity);
        }
        cityAdapter = new ArrayAdapter<>(this, R.layout.content, dataList);
        cityList.setAdapter(cityAdapter);
    }

    private void clickedAddCity() {
        if (invisible) {
            textInputEditText.setVisibility(TextInputEditText.VISIBLE);
            confirmButton.setVisibility(Button.VISIBLE);
            invisible = false;
        }
        else {
            invisible = true;
            textInputEditText.setVisibility(TextInputEditText.INVISIBLE);
            confirmButton.setVisibility(Button.INVISIBLE);
        }
    }

    private String clickedConfirmedButton() {
        cityAdded = String.valueOf(textInputEditText.getText());
        textInputEditText.setText("");
        textInputEditText.setVisibility(TextInputEditText.INVISIBLE);
        confirmButton.setVisibility(Button.INVISIBLE);
        invisible = true;
        return cityAdded;
    }

    @SuppressLint("ResourceAsColor")
    private void clickedRemoveButton() {
        if (removeMode) {
            removeMode = false;
            removeCityButton.setBackgroundColor(R.color.purple);
        }
        else {
            removeMode = true;
            removeCityButton.setBackgroundColor(Color.RED);
            cityList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String selectedItem = String.valueOf(dataList.get(position));
                    if (removeMode) {
                        dataList.remove(position);
                        cityList.setAdapter(cityAdapter);
                    }
                    removeMode = false;
                    removeCityButton.setBackgroundColor(R.color.purple);
                }
            });
        }



    }


}