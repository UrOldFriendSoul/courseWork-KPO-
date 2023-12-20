package com.example.courseworklogistics;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.courseworklogistics.user.User;
import com.example.courseworklogistics.user.UsersData;

public class AdminsActivity extends AppCompatActivity {

    String role = "";
    UsersData usersData;
    User login;
    ArrayAdapter<User> adapter;
    ListView listViewUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        SharedPreferences sPref = getSharedPreferences("User", MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        role = sPref.getString("role", "");

        usersData = new UsersData(this);

        listViewUsers = findViewById(R.id.listViewUsers);

        Button add = findViewById(R.id.btnAddUser);
        Button upd = findViewById(R.id.btnEditUser);
        Button del = findViewById(R.id.btnDeleteUser);


        adapter = new ArrayAdapter<User>(this, android.R.layout.simple_list_item_single_choice, usersData.findAllUsers());
        listViewUsers.setAdapter(adapter);
        listViewUsers.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        adapter.notifyDataSetChanged();

        add.setOnClickListener(v -> {
            Intent intent = new Intent(this, RegistrationUserActivity.class);
            startActivityForResult(intent, 99);
            adapter.notifyDataSetChanged();

        });

        upd.setOnClickListener(v -> {
            int user = -1;
            SparseBooleanArray sparseBooleanArray = listViewUsers.getCheckedItemPositions();
            for (int i = 0; i < listViewUsers.getCount(); ++i){
                if(sparseBooleanArray.get(i) == true){
                    user = adapter.getItem(i).getId();
                }
            }
            if (user == -1){
                return;
            }
            Intent intent = new Intent(this, AdminActivity.class);
            intent.putExtra("Id", user);
            startActivityForResult(intent, 99);
            adapter.notifyDataSetChanged();
            listViewUsers.clearChoices();
        });

        del.setOnClickListener(v -> {
            int user = -1;
            SparseBooleanArray sparseBooleanArray = listViewUsers.getCheckedItemPositions();
            for (int i = 0; i < listViewUsers.getCount(); ++i) {
                if (sparseBooleanArray.get(i) == true) {
                    user = adapter.getItem(i).getId();
                }
            }
            if (user != -1) {
                int finalUser = user;
                usersData.deleteUser(finalUser);
                listViewUsers.clearChoices();
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
