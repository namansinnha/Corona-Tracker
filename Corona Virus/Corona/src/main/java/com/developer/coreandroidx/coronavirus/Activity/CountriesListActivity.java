package com.developer.coreandroidx.coronavirus.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.developer.coreandroidx.coronavirus.Adapter.Adapter;
import com.developer.coreandroidx.coronavirus.Model.CountryModel;
import com.developer.coreandroidx.coronavirus.R;
import com.leo.simplearcloader.SimpleArcLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;

public class CountriesListActivity extends AppCompatActivity {

    private EditText edtSearch;
    private ListView listView;
    private SimpleArcLoader simpleArcLoader;

    public static List<CountryModel> countryModelList = new ArrayList<>();
    private CountryModel countryModel;
    private Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countries_list);

        edtSearch = findViewById(R.id.edtSearch);
        listView = findViewById(R.id.listview);
        simpleArcLoader = findViewById(R.id.loader);

        fetchData();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                startActivity(new Intent(getApplicationContext(), CountryDetailsActivity.class).putExtra("position", position));

            }
        });


        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                adapter.getFilter().filter(s);
                adapter.notifyDataSetChanged();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void fetchData() {

        String url = "https://corona.lmao.ninja/v2/countries/";
        simpleArcLoader.start();

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray jsonArray = new JSONArray(response);

                    for (int i=0; i<jsonArray.length(); i++) {

                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String countryname = jsonObject.getString("country");
                        String cases = jsonObject.getString("cases");
                        String todaycases = jsonObject.getString("todayCases");
                        String deaths = jsonObject.getString("deaths");
                        String todayDeaths = jsonObject.getString("todayDeaths");
                        String recovered = jsonObject.getString("recovered");
                        String active = jsonObject.getString("active");
                        String critical = jsonObject.getString("critical");

                        JSONObject object = jsonObject.getJSONObject("countryInfo");
                        String flagUrl = object.getString("flag");

                        countryModel = new CountryModel(flagUrl, countryname, cases,todaycases, deaths, todayDeaths, recovered, active, critical);
                        countryModelList.add(countryModel);

                    }

                    adapter = new Adapter(CountriesListActivity.this, countryModelList);
                    listView.setAdapter(adapter);
                    simpleArcLoader.stop();
                    simpleArcLoader.setVisibility(View.GONE);
                    listView.setVisibility(View.VISIBLE);

                } catch (JSONException e) {
                    e.printStackTrace();
                    simpleArcLoader.stop();
                    simpleArcLoader.setVisibility(View.GONE);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                simpleArcLoader.stop();
                simpleArcLoader.setVisibility(View.GONE);
                Toasty.error(CountriesListActivity.this, "Error :" + error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }

}
