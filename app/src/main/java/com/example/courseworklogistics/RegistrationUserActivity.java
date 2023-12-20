package com.example.courseworklogistics;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.courseworklogistics.user.User;
import com.example.courseworklogistics.user.UsersData;

public class RegistrationUserActivity extends AppCompatActivity {
    UsersData usersData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_user);
        usersData = new UsersData(this);
        Button reg = findViewById(R.id.registrationButton);
        TextView login = findViewById(R.id.regEditTextLogin);
        TextView password = findViewById(R.id.regEditTextPassword);
        reg.setOnClickListener(v -> {
            String log = login.getText().toString();
            String pas = password.getText().toString();
            if (log.equals("") || pas.equals("") ||
                    log.length() < 5 || pas.length() < 5){
                Toast.makeText(this, "Введите в пароль хотя бы 5 символов",
                        Toast.LENGTH_LONG).show();
            }
            else{
                User user = new User();
                user.setLogin(log);
                user.setPassword(pas);
                user.setRole("admin");
                boolean ret = usersData.registration(user);
                if (ret){
                    Intent data = new Intent();
                    setResult(Activity.RESULT_OK, data);
                    Toast.makeText(this, "Успешный вход",
                            Toast.LENGTH_LONG).show();

                    finish();
                }
                else{
                    Toast.makeText(this, "Пользователь с таким логином уже существует",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}