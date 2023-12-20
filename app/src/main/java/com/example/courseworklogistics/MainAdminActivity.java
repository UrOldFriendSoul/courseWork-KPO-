package com.example.courseworklogistics;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Objects;

public class MainAdminActivity extends AppCompatActivity {

    String role = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_admin);

        SharedPreferences sPref = getSharedPreferences("User", MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        role = sPref.getString("role", "");

        Button btnRoutes = findViewById(R.id.btnRoutes);
        Button btnUsers = findViewById(R.id.btnUsers);

        btnUsers.setVisibility(View.GONE);

        if (Objects.equals(role, "admin")) {
            btnUsers.setVisibility(View.VISIBLE);
        }

        btnRoutes.setOnClickListener(v -> {
            Intent intent = new Intent(this, RoutesActivity.class);
            startActivity(intent);
        });
        btnUsers.setOnClickListener(v -> {
            Intent intent = new Intent(this, AdminsActivity.class);
            startActivity(intent);
        });
    }
}