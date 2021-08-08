package com.quince_tech.ulasbana.Model;

/**
 * Created by qapitall on 25.02.2018.
 */

public class Item {
    private String time;
    private String message;
    private String point;
    private int id;

    public Item(String time, String message, String point,int id){
        this.time =time;
        this.message=message;
        this.point=point;
        this.id = id;
    }


    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
