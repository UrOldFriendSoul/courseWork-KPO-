package com.example.courseworklogistics.user.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.courseworklogistics.user.Route;
import com.example.courseworklogistics.user.User;

import java.util.ArrayList;
import java.util.List;

public class RoutesDB {
    
    private RoutesDB.DBHelper dbHelper;

    public RoutesDB(Context context){
        dbHelper = new RoutesDB.DBHelper(context);
    }

    public Route get(Route route){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor c = db.query("routes", null, "id = ?",
                new String[] {String.valueOf(route.getId())},
                null, null, null);
        if (c.moveToFirst()){
            int idIndex = c.getColumnIndex("id");
            int sPointNameIndex = c.getColumnIndex("sPointName");
            int ePointNameIndex = c.getColumnIndex("ePointName");
            int statusIndex = c.getColumnIndex("status");
            int usernameIndex = c.getColumnIndex("receiverUsername");
            Route tr = new Route();
            tr.setId(c.getInt(idIndex));
            tr.setRouteStartPoint(c.getString(sPointNameIndex));
            tr.setRouteEndPoint(c.getString(ePointNameIndex));
            tr.setStatus(c.getString(statusIndex));
            String username = c.getString(usernameIndex);
            User user = new User();
            String receiverUsername = route.getReceiverUsername();
            tr.setReceiverUsername(receiverUsername);
            user.setLogin(username);

            if (tr.getId() == (route.getId())) {
                dbHelper.close();
                return tr;
            }
        }
        dbHelper.close();
        return null;
    }

    public void add(Route route){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("sPointName", route.getRouteStartPoint());
        cv.put("ePointName", route.getRouteEndPoint());
        cv.put("status", route.getStatus());
        cv.put("receiverUsername", route.getReceiverUsername());
        long routeId = db.insert("routes", null, cv);
        dbHelper.close();
    }

    public void update(Route route){
        if (get(route) == null){
            return;
        }
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("sPointName", route.getRouteStartPoint());
        cv.put("ePointName", route.getRouteEndPoint());
        cv.put("status", route.getStatus());
        cv.put("receiverUsername", route.getReceiverUsername());
        db.update("routes", cv, "id = ?", new String[] {String.valueOf(route.getId())});
        dbHelper.close();
    }

    public void delete(Route route){
        if(get(route) == null){
            return;
        }
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("routes", "id = " + route.getId(), null);
        dbHelper.close();
    }

    public List<Route> readAll(){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        List<Route> routeList = new ArrayList<Route>();
        Cursor c = db.query("routes", null, null, null,
                null, null, null);
        if (c.moveToFirst()){
            int idIndex = c.getColumnIndex("id");
            int sPointNameIndex = c.getColumnIndex("sPointName");
            int ePointNameIndex = c.getColumnIndex("ePointName");
            int statusIndex = c.getColumnIndex("status");
            int usernameIndex = c.getColumnIndex("receiverUsername");
            do{
                Route tr = new Route();
                tr.setId(c.getInt(idIndex));
                tr.setRouteStartPoint(c.getString(sPointNameIndex));
                tr.setRouteEndPoint(c.getString(ePointNameIndex));
                tr.setStatus(c.getString(statusIndex));
                tr.setReceiverUsername(c.getString(usernameIndex));
                routeList.add(tr);
            } while(c.moveToNext());
        }
        dbHelper.close();
        return routeList;
    }

    class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context) {
            super(context, "routesq", null, 2);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("create table routes ("
                    + "id integer primary key autoincrement,"
                    + "sPointName text,"
                    + "ePointName text,"
                    + "adminUsername text,"
                    + "receiverUsername text,"
                    + "status text" + ");");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
    
}
