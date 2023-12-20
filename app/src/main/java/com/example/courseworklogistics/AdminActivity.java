package com.example.courseworklogistics;

import android.app.Activity;
import android.content.Intent;
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

public class AdminActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    int id = -1;
    UsersData usersData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        usersData = new UsersData(this);

        Intent intent = getIntent();
        id = intent.getIntExtra("Id", -1);

        Button save = findViewById(R.id.btnSave);
        TextView textViewUserLoginPrint = findViewById(R.id.textViewUserLoginPrint);
        TextView textViewUserPasswordPrint = findViewById(R.id.textViewUserPasswordPrint);
        Spinner spinnerRole = findViewById(R.id.spinnerRole);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.users, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRole.setAdapter(adapter);
        spinnerRole.setOnItemSelectedListener(this);

        if (id != -1){
            User user = usersData.getUser(id);
            if (user != null){
                User newUser = new User();
                textViewUserLoginPrint.setText(newUser.getLogin());
                textViewUserPasswordPrint.setText(newUser.getPassword());
                spinnerRole.setSelection(getIndex(spinnerRole, String.valueOf(newUser.getRole())));
            }
        }

        save.setOnClickListener(v -> {
            if (textViewUserLoginPrint.getText().equals("")){
                Toast.makeText(this, "Введите логин",
                        Toast.LENGTH_LONG).show();
                return;
            }
            if (textViewUserPasswordPrint.getText().equals("")){
                Toast.makeText(this, "Введите пароль",
                        Toast.LENGTH_LONG).show();
                return;
            }
            if (spinnerRole.getSelectedItem().equals("")){
                Toast.makeText(this, "Выберите роль",
                        Toast.LENGTH_LONG).show();
                return;
            }
            String userLogin = textViewUserLoginPrint.getText().toString();
            String userPassword = textViewUserPasswordPrint.getText().toString();
            String role = spinnerRole.getSelectedItem().toString();
            if (id != -1){
                usersData.updateUser(id, userLogin, userPassword, role);
            }
            else {
                usersData.addUser(userLogin, userPassword, role);
            }
            Intent data = new Intent();
            setResult(Activity.RESULT_OK, data);
            finish();
        });
    }

    private int getIndex(Spinner spinner, String value) {
        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).equals(value)){
                return i;
            }
        }
        return -1;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        String text = adapterView.getItemAtPosition(position).toString();
        Toast.makeText(adapterView.getContext(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}