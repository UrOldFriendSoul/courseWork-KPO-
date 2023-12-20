package com.example.courseworklogistics.user;
import android.content.Context;

import com.example.courseworklogistics.user.DB.RoutesDB;

import java.util.ArrayList;
import java.util.List;

public class RoutesData {

    private static ArrayList<Route> routes  = new ArrayList<Route>();
    RoutesDB routesDB;

    public RoutesData(Context context){
        routesDB = new RoutesDB(context);
        readAll();
    }

    public Route getRoute(int id){
        Route tr = new Route();
        tr.setId(id);
        return routesDB.get(tr);
    }

    public List<Route> findAllRoutes(){
        return routes;
    }

    public List<Route> findAllRoutesByReceiverUsername(String username){
        List<Route> filteredRoutes = new ArrayList<>();

        for (Route route : routes) {
            if (route.getReceiverUsername().equals(username)) {
                filteredRoutes.add(route);
            }
        }

        return filteredRoutes;
    }

    public void addRoute(String sPointName, String ePointName,String username, String status){
        Route route = new Route();
        route.setRouteStartPoint(sPointName);
        route.setRouteEndPoint(ePointName);
        route.setReceiverUsername(username);
        route.setStatus(status);
        routesDB.add(route);
        readAll();
    }
    public void updateRoute(int id, String sPointName, String ePointName,String username, String status){
        Route route = new Route();
        route.setId(id);
        route.setRouteStartPoint(sPointName);
        route.setRouteEndPoint(ePointName);
        route.setReceiverUsername(username);
        route.setStatus(status);
        routesDB.update(route);
        readAll();
    }
    public void deleteRoute(int id){
        Route route = new Route();
        route.setId(id);
        routesDB.delete(route);
        readAll();
    }
    private void readAll(){
        List<Route> rt = routesDB.readAll();
        routes.clear();
        for(Route route : rt){
            routes.add(route);
        }
    }
}
