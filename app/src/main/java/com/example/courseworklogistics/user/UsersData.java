package com.example.courseworklogistics.user;

import android.content.Context;

import com.example.courseworklogistics.user.DB.UserDB;

import java.util.ArrayList;
import java.util.List;

public class UsersData {
    private static ArrayList<User> users = new ArrayList<>();
    private UserDB usersDB;

    public UsersData(Context context) {
        usersDB = new UserDB(context);
        readAll();
    }
    public User getUser(int id){
        User ur = new User();
        ur.setId(id);
        return usersDB.get(ur);
    }

    public List<User> findAllUsers() {
        return users;
    }

    public List<String> findAllUsernames(){
        List<String> usernames = new ArrayList<>();
        for (User user : users){
            usernames.add(user.getLogin());
        }
        return usernames;
    }

    public boolean registration(User user) {
        try {
            boolean ret = usersDB.registration(user);
            return ret;
        } catch (Exception ex) {
            return false;
        }
    }

    public User authorization(User user) {
        try {
            User ret = usersDB.authorization(user);
            return ret;
        } catch (Exception ex) {
            return null;
        }
    }

    private void readAllUsers() {
        List<User> usr = usersDB.readAll();
        users.clear();
        users.addAll(usr);
    }
    public void deleteAll(){
        users.clear(); // Очищаем список категорий в памяти
        usersDB.deleteAll(); // Удаляем все записи в базе данных
        readAll();
    }

    public void addUser(String login, String password, String role) {
        User user = new User();
        user.setLogin(login);
        user.setPassword(password);
        user.setRole(role);
        usersDB.add(user);
        readAll();
    }
    public void updateUser(int id, String login, String password, String role) {
        User user = new User();
        user.setId(id);
        user.setLogin(login);
        user.setPassword(password);
        user.setRole(role);
        usersDB.update(user);
        readAll();
    }

    public void deleteUser(int id) {
        User user = new User();
        user.setId(id);
        usersDB.delete(user);
        readAll();
    }

    public void readAll() {
        List<User> usr = usersDB.readAll();
        users.clear();
        for(User user : usr){
            users.add(user);
        }
    }
}
