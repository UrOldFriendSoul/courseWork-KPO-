package com.example.courseworklogistics;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.courseworklogistics.user.Route;
import com.example.courseworklogistics.user.RoutesData;

public class MainUserActivity extends AppCompatActivity {

    String login = "";
    RoutesData routesData;
    ArrayAdapter<Route> adapter;
    ListView listViewRoutes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_routes);

        SharedPreferences sPref = getSharedPreferences("User", MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        login = sPref.getString("login", "");

        routesData = new RoutesData(this);

        listViewRoutes = findViewById(R.id.listViewRoutes);

        Button check = findViewById(R.id.btnCheck);

        adapter = new ArrayAdapter<Route>(this, android.R.layout.simple_list_item_single_choice,
                routesData.findAllRoutes());
        listViewRoutes.setAdapter(adapter);
        listViewRoutes.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        adapter.notifyDataSetChanged();

        check.setOnClickListener(v -> {
            Intent intent = new Intent(this, RouteActivity.class);
            startActivityForResult(intent, 99);
            adapter.notifyDataSetChanged();
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        adapter.notifyDataSetChanged();
    }
}