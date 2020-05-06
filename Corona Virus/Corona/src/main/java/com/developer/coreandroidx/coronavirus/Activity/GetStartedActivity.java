package com.developer.coreandroidx.coronavirus.Activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;

import com.airbnb.lottie.LottieAnimationView;
import com.developer.coreandroidx.coronavirus.R;

public class GetStartedActivity extends AppCompatActivity {

    private Button dashboardActivity_btn;
    private LottieAnimationView app_logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeFunction();

        dashboardActivity_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                gotoDashboardActivity();

            }
        });

    }

    private void initializeFunction() {

        dashboardActivity_btn = findViewById(R.id.dashboard_activity_btn);
        app_logo = findViewById(R.id.app_animation);
    }

    private void gotoDashboardActivity() {

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            Intent intent = new Intent(GetStartedActivity.this, DashboardActivity.class);

            Pair[] group_pair = new Pair[1];
            group_pair[0] = new Pair<View, String>(app_logo, "app_logo");

            ActivityOptions group_options = null;

            group_options = ActivityOptions.makeSceneTransitionAnimation(GetStartedActivity.this, group_pair);
            startActivity(intent, group_options.toBundle());
            startActivity(intent);
        }
    }
}
