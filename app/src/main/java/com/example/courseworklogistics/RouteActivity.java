package com.example.courseworklogistics;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.courseworklogistics.user.DB.UserDB;
import com.example.courseworklogistics.user.Route;
import com.example.courseworklogistics.user.RoutesData;
import com.example.courseworklogistics.user.User;
import com.example.courseworklogistics.user.UsersData;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RouteActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    int id = -1;
    RoutesData routesData;
    UsersData usersData;
    List<String> userLogins = new ArrayList<>();
    private int selectedUserPosition = -1;
    private int selectedStatusPosition = -1;
    String role = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);
        SharedPreferences sPref = getSharedPreferences("User", MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        role = sPref.getString("role", "");
        usersData = new UsersData(this);

        routesData = new RoutesData(this);
        userLogins.addAll(usersData.findAllUsernames());

        Intent intent = getIntent();
        id = intent.getIntExtra("Id", -1);

        Button back = findViewById(R.id.btnBack);
        Button save = findViewById(R.id.btnSave);

        save.setVisibility(View.GONE);
        if (Objects.equals(role, "admin")) {
            save.setVisibility(View.VISIBLE);
        }

        TextView textViewStartPointName = findViewById(R.id.textViewStartPointNamePrint);
        textViewStartPointName.setEnabled(false);
        if(Objects.equals(role,"admin")){
            textViewStartPointName.setEnabled(true);
        }
        TextView textViewEndPointName = findViewById(R.id.textViewEndPointNamePrint);
        textViewEndPointName.setEnabled(false);
        if(Objects.equals(role,"admin")){
            textViewEndPointName.setEnabled(true);
        }



        usersData.findAllUsernames();
        Spinner spinnerUser = findViewById(R.id.spinnerUsers);
        spinnerUser.setEnabled(false);
        if(Objects.equals(role,"admin")){
            spinnerUser.setEnabled(true);
        }
        ArrayAdapter<String> adapterUsers = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, userLogins);
        adapterUsers.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerUser.setAdapter(adapterUsers);
        spinnerUser.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Проверяем, что выбран элемент в спиннере
                if (position != AdapterView.INVALID_POSITION) {
                    selectedUserPosition = position;
                    String selectedUser = userLogins.get(selectedUserPosition);
                    // Делаем дополнительную проверку на null
                    if (selectedUser != null) {
                        selectedUser = userLogins.get(selectedUserPosition);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Обработка, если пользователь не выбран
                selectedUserPosition = -1;
            }
        });

        Spinner spinnerStatus = findViewById(R.id.spinnerStatus);
        spinnerStatus.setEnabled(false);
        if(Objects.equals(role,"admin")){
            spinnerStatus.setEnabled(true);
        }
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.strings, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerStatus.setAdapter(adapter);
        spinnerStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Проверяем, что выбран элемент в спиннере
                if (position != AdapterView.INVALID_POSITION) {
                    selectedStatusPosition = position;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Обработка, если статус не выбран
                selectedStatusPosition = -1;
            }
        });

        if (id != -1){
            Route route = routesData.getRoute(id);
            if (route != null){
                textViewStartPointName.setText(route.getRouteStartPoint());
                textViewEndPointName.setText(route.getRouteEndPoint());
                int userIndex = getIndex(spinnerUser, String.valueOf(route.getReceiverUsername()));
                if (userIndex != -1) {
                    spinnerUser.setSelection(userIndex);
                    selectedUserPosition = userIndex;
                }
                int statusIndex = getIndex(spinnerStatus, String.valueOf(route.getStatus()));
                if (statusIndex != -1) {
                    spinnerStatus.setSelection(statusIndex);
                    selectedStatusPosition = statusIndex;
                }
            }
        }

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String startPointName = textViewStartPointName.getText().toString();
                String endPointName = textViewEndPointName.getText().toString();
                String username = spinnerUser.getSelectedItem().toString();

                if (startPointName.isEmpty() || endPointName.isEmpty()) {
                    Toast.makeText(RouteActivity.this, "Please enter start and end point", Toast.LENGTH_SHORT).show();
                    return;
                }

                List<String> selectedUser1 = usersData.findAllUsernames();
                String selectedUser = spinnerUser.getSelectedItem().toString();
                String selectedStatus = spinnerStatus.getSelectedItem().toString();

                if (id != -1){
                    routesData.updateRoute(id, startPointName, endPointName, selectedUser, selectedStatus);
                    Toast.makeText(RouteActivity.this, "Route updated successfully", Toast.LENGTH_SHORT).show();
                } else {
                    routesData.addRoute(startPointName, endPointName, selectedUser, selectedStatus);
                    Toast.makeText(RouteActivity.this, "Route added successfully", Toast.LENGTH_SHORT).show();
                }
                Intent data = new Intent();
                setResult(Activity.RESULT_OK, data);
                finish();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private int getIndex(Spinner spinner, String value){
        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(value)){
                return i;
            }
        }
        return -1;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() == R.id.spinnerUsers) {
            selectedUserPosition = position;
        } else if (parent.getId() == R.id.spinnerStatus) {
            selectedStatusPosition = position;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
}