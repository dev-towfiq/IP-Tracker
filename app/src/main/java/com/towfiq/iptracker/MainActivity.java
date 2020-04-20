package com.towfiq.iptracker;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    TextView ip,country,ipdecimal,iso,city,country_eu,latitude,longitude,asn,asnOrg;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ip = findViewById(R.id.ip);
        country = findViewById(R.id.country);
        ipdecimal = findViewById(R.id.ip_decimal);
        iso = findViewById(R.id.country_iso);
        city = findViewById(R.id.city);
        country_eu = findViewById(R.id.country_eu);
        latitude = findViewById(R.id.latitude);
        longitude = findViewById(R.id.longitude);
        asn = findViewById(R.id.asn);
        asnOrg = findViewById(R.id.asn_org);
        progressBar = findViewById(R.id.progressBar);
        //calling the method
        ipApp();
    }
    private void ipApp() {
        progressBar.setVisibility(View.VISIBLE);

        //Creating a retrofit object
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://ifconfig.co/")
                .addConverterFactory(GsonConverterFactory.create()) //Here we are using the GsonConverterFactory to directly convert json data to object
                .build();
        //creating the api interface
        Api api = retrofit.create(Api.class);

        Call<Model> call = api.getMethod();

        call.enqueue(new Callback<Model>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<Model> call, Response<Model> response) {
                progressBar.setVisibility(View.GONE); //network call success

                Model model = response.body();

                ip.setText(model.getIp());
                country.setText(model.getCountry());
                ipdecimal.setText(model.getIpDecimal());
                iso.setText(model.getCountryIso());
                city.setText(model.getCity());
                country_eu.setText(model.getCountryEu().toString());
                latitude.setText(model.getLatitude().toString());
                longitude.setText(model.getLongitude().toString());
                asn.setText(model.getAsn());
                asnOrg.setText(model.getAsnOrg());
            }
            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }

        });
    }
}
