package com.example.carla.eyetracker;

public class Contacts {
    private long id;
    private String name;
    private String numberPhone;

    public Contacts (long id, String name, String numberPhone)
    {
        super();
        this.id=id;
        this.name=name;
        this.numberPhone=numberPhone;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumberPhone() {
        return numberPhone;
    }

    public void setNumberPhone(String numberPhone) {
        this.numberPhone = numberPhone;
    }
}
