package com.example.courseworklogistics;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.courseworklogistics.user.Route;
import com.example.courseworklogistics.user.RoutesData;

import java.util.Objects;

public class RoutesActivity extends AppCompatActivity {

    String role = "";
    String login = "";
    RoutesData routesData;
    ArrayAdapter<Route> adapter;
    ListView listViewRoutes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routes);

        SharedPreferences sPref = getSharedPreferences("User", MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        role = sPref.getString("role", "");
        SharedPreferences sPrefLogin = getSharedPreferences("User", MODE_PRIVATE);
        SharedPreferences.Editor edLogin = sPref.edit();
        login = sPref.getString("login", "");

        routesData = new RoutesData(this);

        listViewRoutes = findViewById(R.id.listViewRoutes);

        Button add = findViewById(R.id.btnAdd);
        Button upd = findViewById(R.id.btnEdit);
        Button del = findViewById(R.id.btnDelete);
        add.setVisibility(View.GONE);
        del.setVisibility(View.GONE);

        if (Objects.equals(role, "admin")) {
            add.setVisibility(View.VISIBLE);
            del.setVisibility(View.VISIBLE);
            adapter = new ArrayAdapter<Route>(this, android.R.layout.simple_list_item_single_choice,
                    routesData.findAllRoutes()); // Показываем все маршруты администратору
        } else {
            adapter = new ArrayAdapter<Route>(this, android.R.layout.simple_list_item_single_choice,
                    routesData.findAllRoutesByReceiverUsername(login)); // Фильтруем маршруты для пользователя
        }

        listViewRoutes.setAdapter(adapter);
        listViewRoutes.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        adapter.notifyDataSetChanged();

        add.setOnClickListener(v -> {
            Intent intent = new Intent(this, RouteActivity.class);
            startActivityForResult(intent, 99);
            adapter.notifyDataSetChanged();
        });

        upd.setOnClickListener(v -> {
            int route = -1;
            SparseBooleanArray sparseBooleanArray = listViewRoutes.getCheckedItemPositions();
            for (int i = 0; i < listViewRoutes.getCount(); ++i){
                if(sparseBooleanArray.get(i) == true){
                    route = adapter.getItem(i).getId();
                }
            }
            if (route == -1){
                return;
            }
            Intent intent = new Intent(this, RouteActivity.class);
            intent.putExtra("Id", route);
            startActivityForResult(intent, 99);
            adapter.notifyDataSetChanged();
            listViewRoutes.clearChoices();
        });

        del.setOnClickListener(v -> {
            int route = -1;
            SparseBooleanArray sparseBooleanArray = listViewRoutes.getCheckedItemPositions();
            for (int i = 0; i < listViewRoutes.getCount(); ++i) {
                if (sparseBooleanArray.get(i) == true) {
                    route = adapter.getItem(i).getId();
                }
            }
            if (route != -1) {
                int finalBrush = route;
                routesData.deleteRoute(finalBrush);
                listViewRoutes.clearChoices();
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        adapter.notifyDataSetChanged();
    }
}