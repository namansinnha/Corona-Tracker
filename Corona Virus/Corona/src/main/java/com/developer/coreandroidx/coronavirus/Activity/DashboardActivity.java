package com.developer.coreandroidx.coronavirus.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.developer.coreandroidx.coronavirus.R;
import com.leo.simplearcloader.SimpleArcLoader;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;
import org.json.JSONException;
import org.json.JSONObject;

import es.dmoral.toasty.Toasty;

public class DashboardActivity extends AppCompatActivity {

    private TextView tv_cases, tv_recovered, tv_active, tv_death, tv_affected_countries, tv_critical, tv_today_cases, tv_death_today;
    private SimpleArcLoader simpleArcLoader;
    private ScrollView scrollView;
    private PieChart pieChart;
    private Button track_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        initializeViews ();

        fetchData ();

        track_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(DashboardActivity.this, CountriesListActivity.class));

            }
        });

    }

    private void initializeViews () {

        tv_cases = findViewById(R.id.cases_textView);
        tv_active = findViewById(R.id.active_textView);
        tv_recovered = findViewById(R.id.recovered_textView);
        tv_death = findViewById(R.id.death_textView);
        tv_affected_countries = findViewById(R.id.countries_affected_textView);
        tv_critical = findViewById(R.id.critical_textView);
        tv_today_cases = findViewById(R.id.today_cases_textView);
        tv_death_today = findViewById(R.id.death_cases_today_textView);
        track_btn = findViewById(R.id.more_btn);

        simpleArcLoader = findViewById(R.id.loader);
        scrollView = findViewById(R.id.scroll_stats);
        pieChart = findViewById(R.id.piechart);
    }

    private void fetchData () {

        String url = "https://corona.lmao.ninja/v2/all";
        simpleArcLoader.start();

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    tv_cases.setText(jsonObject.getString("cases"));
                    tv_recovered.setText(jsonObject.getString("recovered"));
                    tv_active.setText(jsonObject.getString("active"));
                    tv_death.setText(jsonObject.getString("deaths"));
                    tv_critical.setText(jsonObject.getString("critical"));
                    tv_affected_countries.setText(jsonObject.getString("affectedCountries"));
                    tv_today_cases.setText(jsonObject.getString("todayCases"));
                    tv_death_today.setText(jsonObject.getString("todayDeaths"));

                    pieChart.addPieSlice(new PieModel("Cases",Integer.parseInt(tv_cases.getText().toString()), Color.parseColor("#ffa954")));
                    pieChart.addPieSlice(new PieModel("Recovered",Integer.parseInt(tv_recovered.getText().toString()), Color.parseColor("#FF0B8B42")));
                    pieChart.addPieSlice(new PieModel("Deaths",Integer.parseInt(tv_death.getText().toString()), Color.parseColor("#FFFF0400")));
                    pieChart.addPieSlice(new PieModel("Active",Integer.parseInt(tv_active.getText().toString()), Color.parseColor("#6200ee")));

                    simpleArcLoader.stop();
                    simpleArcLoader.setVisibility(View.GONE);
                    scrollView.setVisibility(View.VISIBLE);

                } catch (JSONException e) {
                    e.printStackTrace();
                    simpleArcLoader.stop();
                    simpleArcLoader.setVisibility(View.GONE);
                    scrollView.setVisibility(View.VISIBLE);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                simpleArcLoader.stop();
                simpleArcLoader.setVisibility(View.GONE);
                scrollView.setVisibility(View.VISIBLE);

                Toasty.error(DashboardActivity.this, "Error :" + error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);


    }

}
