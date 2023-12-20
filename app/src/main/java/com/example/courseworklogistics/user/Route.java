package com.example.courseworklogistics.user;

public class Route {
    private int id;
    private String sPointName;
    private String ePointName;
    private String status;
    private String adminUsername;
    private String receiverUsername;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRouteStartPoint() {
        return sPointName;
    }

    public void setRouteStartPoint(String sPointName) {
        this.sPointName = sPointName;
    }

    public String getRouteEndPoint() {
        return ePointName;
    }

    public void setRouteEndPoint(String ePointName) {
        this.ePointName = ePointName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReceiverUsername() {
        return receiverUsername;
    }

    public void setReceiverUsername(String receiverUsername) {
        this.receiverUsername = receiverUsername;
    }
    public String getAdminUsername() {
        return adminUsername;
    }

    public void setAdminUsername(String adminUsername) {
        this.adminUsername = adminUsername;
    }

    @Override
    public String toString() {
        return "Отправление: " + sPointName + ". Получение " + ePointName + ". Статус: " + status + ". Получатель: " + receiverUsername;
    }
}
