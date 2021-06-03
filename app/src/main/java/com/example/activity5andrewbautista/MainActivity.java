package com.example.activity5andrewbautista;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.activity5andrewbautista.Retrofit.RetrofitBuilder;
import com.example.activity5andrewbautista.Retrofit.RetrofitInterface;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    Button button_convert;
    EditText text_initial_currency,text_converted_currency;
    Spinner spin_from_convert, spin_to_convert;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text_initial_currency = (EditText) findViewById(R.id.txt_initial_currency);
        text_converted_currency = (EditText) findViewById(R.id.txt_converted_currency);
        spin_from_convert = (Spinner) findViewById(R.id.spinner_fromcurrency);
        spin_to_convert = (Spinner) findViewById(R.id.spinner_to_currency);
        button_convert = (Button) findViewById(R.id.btn_convert);

        String [] dropDownList = {"USD","PHP","EUR","VES","MZN"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item,dropDownList);
        spin_to_convert.setAdapter(adapter);
        spin_from_convert.setAdapter(adapter);

        button_convert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RetrofitInterface retrofitInterface = RetrofitBuilder.getRetrofitInstance().create(RetrofitInterface.class);

                Call<JsonObject> call = retrofitInterface.getExchangeCurrency(spin_from_convert.getSelectedItem().toString());
                call.enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        Log.d("response",String.valueOf(response.body()));
                        Toast.makeText(MainActivity.this, "Computed", Toast.LENGTH_SHORT).show();
                        JsonObject res = response.body();
                        JsonObject rates = res.getAsJsonObject("conversion_rates");
                        Double currency = Double.valueOf(text_initial_currency.getText().toString());
                        Double multiplier = Double.valueOf(rates.get(spin_to_convert.getSelectedItem().toString()).toString());
                        Double result = currency * multiplier;
                        text_converted_currency.setText(String.valueOf(result));
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {

                    }
                });

            }
        });
    }
}