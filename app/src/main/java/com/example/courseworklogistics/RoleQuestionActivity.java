package com.example.courseworklogistics;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;


public class RoleQuestionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.role_question_activity);

        Button yes = findViewById(R.id.btnYes);
        Button no = findViewById(R.id.btnNo);

        yes.setOnClickListener(v -> {
            Intent intent = new Intent(this, AuthorizationAdminActivity.class);
            startActivity(intent);
        });

        no.setOnClickListener(v -> {
            Intent intent = new Intent(this, AuthorizationUserActivity.class);
            startActivity(intent);
        });
    }
}