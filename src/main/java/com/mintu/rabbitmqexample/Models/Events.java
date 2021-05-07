package com.mintu.rabbitmqexample.Models;

public class Events {

    private String name;
    private String id;
    private String time;

    @Override
    public String toString() {
        return "Events{" +
                "name='" + name + '\'' +
                ", id='" + id + '\'' +
                ", time='" + time + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
