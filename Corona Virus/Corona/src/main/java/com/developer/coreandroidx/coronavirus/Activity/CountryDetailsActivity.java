package com.developer.coreandroidx.coronavirus.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.developer.coreandroidx.coronavirus.R;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import de.hdodenhof.circleimageview.CircleImageView;

public class CountryDetailsActivity extends AppCompatActivity {

    private int positionCountry;
    private TextView tv_cases, tv_recovered, tv_active, tv_death, tv_affected_countries, tv_critical, tv_today_cases, tv_death_today;
    private CircleImageView flag;
    private PieChart pieChart;
    private Button symptoms_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_details);

        Intent intent = getIntent();
        positionCountry = intent.getIntExtra("position", 0);

        initializeViews();

        tv_affected_countries.setText(CountriesListActivity.countryModelList.get(positionCountry).getCountry());
        tv_cases.setText(CountriesListActivity.countryModelList.get(positionCountry).getCases());
        tv_active.setText(CountriesListActivity.countryModelList.get(positionCountry).getActive());
        tv_recovered.setText(CountriesListActivity.countryModelList.get(positionCountry).getRecovered());
        tv_death.setText(CountriesListActivity.countryModelList.get(positionCountry).getDeaths());
        tv_critical.setText(CountriesListActivity.countryModelList.get(positionCountry).getCritical());
        tv_today_cases.setText(CountriesListActivity.countryModelList.get(positionCountry).getTodayCases());
        tv_death_today.setText(CountriesListActivity.countryModelList.get(positionCountry).getTodayDeath());
        Glide.with(getApplicationContext()).load(CountriesListActivity.countryModelList.get(positionCountry).getFlag()).into(flag);

        pieChart.addPieSlice(new PieModel("Cases",Integer.parseInt(tv_cases.getText().toString()), Color.parseColor("#ffa954")));
        pieChart.addPieSlice(new PieModel("Recovered",Integer.parseInt(tv_recovered.getText().toString()), Color.parseColor("#FF0B8B42")));
        pieChart.addPieSlice(new PieModel("Active",Integer.parseInt(tv_active.getText().toString()), Color.parseColor("#6200ee")));
        pieChart.addPieSlice(new PieModel("Deaths",Integer.parseInt(tv_death.getText().toString()), Color.parseColor("#FFFF0400")));

        symptoms_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(CountryDetailsActivity.this, SymptomsActivity.class));

            }
        });

    }

    private void initializeViews() {

        tv_cases = findViewById(R.id.cases_textView);
        tv_active = findViewById(R.id.active_textView);
        tv_recovered = findViewById(R.id.recovered_textView);
        tv_death = findViewById(R.id.death_textView);
        tv_affected_countries = findViewById(R.id.country_name);
        tv_critical = findViewById(R.id.critical_textView);
        tv_today_cases = findViewById(R.id.today_cases_textView);
        tv_death_today = findViewById(R.id.death_cases_today_textView);
        flag = findViewById(R.id.flag);
        pieChart = findViewById(R.id.piechart);
        symptoms_btn = findViewById(R.id.symptoms_btn);
    }

}
