package com.example.courseworklogistics;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.courseworklogistics.user.User;
import com.example.courseworklogistics.user.UsersData;

public class AuthorizationUserActivity extends AppCompatActivity {

    UsersData usersData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorization_user);

        usersData = new UsersData(this);
        Button aut = findViewById(R.id.authorizationAut);
        Button reg = findViewById(R.id.authorizationReg);
        TextView login = findViewById(R.id.textViewLogin);
        TextView password = findViewById(R.id.textViewPassword);

        aut.setOnClickListener(v -> {
            String log = login.getText().toString();
            String pas = password.getText().toString();
            if (log.equals("") || pas.equals("")){
                Toast.makeText(this, "Введите логин и пароль",
                        Toast.LENGTH_LONG).show();
            }
            else{
                User user = new User();
                user.setLogin(log);
                user.setPassword(pas);
                User ret = usersData.authorization(user);
                if (ret == null){
                    Toast.makeText(this, "Неверный логин или пароль, или пользователя не существует",
                            Toast.LENGTH_LONG).show();
                }
                else{
                    SharedPreferences sPref = getSharedPreferences("User", MODE_PRIVATE);
                    SharedPreferences.Editor ed = sPref.edit();
                    ed.putString("login", ret.getLogin());
                    ed.putString("role", ret.getRole());
                    ed.commit();
                    Intent intent = new Intent(this, MainAdminActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        reg.setOnClickListener(v -> {
            Intent intent = new Intent(this, RegistrationUserActivity.class);
            startActivity(intent);
        });
    }
}