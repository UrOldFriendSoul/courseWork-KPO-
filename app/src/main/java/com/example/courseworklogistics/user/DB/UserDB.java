package com.example.courseworklogistics.user.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.courseworklogistics.R;
import com.example.courseworklogistics.user.Route;
import com.example.courseworklogistics.user.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserDB {

    private UserDB.DBHelper dbHelper;

    public UserDB(Context context){
        dbHelper = new UserDB.DBHelper(context);
    }

    public User get(User user){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor c = db.query("users", null, "id = ?",
                new String[] {String.valueOf(user.getId())},
                null, null, null);
        if (c.moveToFirst()){
            int idIndex = c.getColumnIndex("id");
            int logIndex = c.getColumnIndex("login");
            int passIndex = c.getColumnIndex("password");
            int rolIndex = c.getColumnIndex("role");
            User usr = new User();
            usr.setId(c.getInt(idIndex));
            usr.setRole(c.getString(rolIndex));
            usr.setLogin(c.getString(logIndex));
            usr.setPassword(c.getString(passIndex));
            if (usr.getId() == (user.getId())) {
                dbHelper.close();
                return usr;
            }
        }
        dbHelper.close();
        return null;
    }
    public boolean registration(User user){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selection = "login = ?";
        String[] selectionArgs = new String[] { user.getLogin() };
        Cursor c = db.query("users", null, selection,
                selectionArgs, null, null, null);
        if (c != null){
            if (c.moveToFirst()) {
                dbHelper.close();
                return false;
            }
            ContentValues cv = new ContentValues();
            cv.put("login", user.getLogin());
            cv.put("password", user.getPassword());
            cv.put("role", user.getRole());
            long userId =  db.insert("users", null, cv);
            dbHelper.close();
            return true;
        }
        dbHelper.close();
        return false;
    }
    public void add(User user){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("login", user.getLogin());
        cv.put("password", user.getPassword());
        cv.put("role", user.getRole());
        long userId =  db.insert("users", null, cv);
        dbHelper.close();
    }
    public void update(User user){
        if (get(user) == null){
            return;
        }
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("login", user.getLogin());
        cv.put("password", user.getPassword());
        cv.put("role", user.getRole());
        db.update("users", cv, "id = ?", new String[] {String.valueOf(user.getId())});
        dbHelper.close();
    }

    public User authorization(User user){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selection = "login = ?";
        String[] selectionArgs = new String[] { user.getLogin() };
        Cursor c = db.query("users", null, selection,
                selectionArgs, null, null, null);
        if (c != null){
            if (c.moveToFirst()) {
                if (Objects.equals(user.getLogin(), c.getString(1)) &&
                        Objects.equals(user.getPassword(), c.getString(2))){
                    User userr = new User();
                    userr.setLogin(user.getLogin());
                    userr.setPassword(user.getPassword());
                    userr.setRole(c.getString(3));
                    c.close();
                    db.close();
                    return userr;
                }
            }
            c.close();
            dbHelper.close();
            return null;
        }
        dbHelper.close();
        return null;
    }
    public void delete(User user){
        if(get(user) == null){
            return;
        }
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("users", "id = " + user.getId(), null);
        dbHelper.close();
    }

    public List<User> readAll() {
        SQLiteDatabase db = null;
        try {
            db = dbHelper.getWritableDatabase();
        } catch (SQLException e) {
            // Обработка ошибки
            e.printStackTrace();
        }
        List<User> userList = new ArrayList<User>();
        Cursor c = db.query("users", null, null,
                null, null, null, null);
        if (c.moveToFirst()) {
            int idIndex = c.getColumnIndex("id");
            int logIndex = c.getColumnIndex("login");
            int passIndex = c.getColumnIndex("password");
            int rolIndex = c.getColumnIndex("role");
            do {
                User usr = new User();
                usr.setId(c.getInt(idIndex));
                usr.setRole(c.getString(rolIndex));
                usr.setLogin(c.getString(logIndex));
                usr.setPassword(c.getString(passIndex));
                userList.add(usr);
            } while (c.moveToNext());
        }
        dbHelper.close();
        return userList;
    }
    public void deleteAll() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("users", null, null);
        dbHelper.close();
    }
    public List<User> getAllUsers(){
        List<User> userList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM users", null);
        if(cursor.moveToFirst()){
            do{
                int idIndex = cursor.getColumnIndex("id");
                int logIndex = cursor.getColumnIndex("login");
                int passIndex = cursor.getColumnIndex("password");
                int rolIndex = cursor.getColumnIndex("role");

                User usr = new User();
                usr.setId(cursor.getInt(idIndex));
                usr.setRole(cursor.getString(rolIndex));
                usr.setLogin(cursor.getString(logIndex));
                usr.setPassword(cursor.getString(passIndex));

                userList.add(usr);
            }while (cursor.moveToNext());
        }
        cursor.close();
        dbHelper.close();

        return userList;
    }
//    public List<Route> getUserRoutes(String username){
//        List<Route> routes = new ArrayList<>();
//
//        SQLiteDatabase db = dbHelper.getReadableDatabase();
//        String[] columns = {"id", "sPointName", "ePointName", "adminUsername", "receiverUsername", "status"};
//        String selection = username + "= ?";
//        String[] selectionArgs = { username };
//
//        Cursor cursor = db.query("routes",columns,selection,selectionArgs, null, null,null);
//        if (cursor.moveToNext()){
//            do{
//                Route route = new Route();
//                route.setId(cursor.getInt(cursor.getColumnIndex("id")));
//                route.setRouteStartPoint(cursor.getString(cursor.getColumnIndex("sPointName")));
//                route.setRouteEndPoint(cursor.getString(cursor.getColumnIndex("ePointName")));
//                route.setAdminUsername(cursor.getString(cursor.getColumnIndex("adminUsername")));
//                route.setReceiverUsername(cursor.getString(cursor.getColumnIndex("receiverUsername")));
//                route.getStatus(cursor.getString(cursor.getColumnIndex("status")));
//                route.add(route);
//            }
//        }
//    }


    class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context) {
            super(context, "userqq", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("create table users ("
                    + "id integer primary key autoincrement,"
                    + "login text,"
                    + "password text,"
                    + "role text" + ");");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}
