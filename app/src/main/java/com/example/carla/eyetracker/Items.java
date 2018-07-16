package com.example.carla.eyetracker;

public class Items {
    private long id;
    private String name;
    private long use;
    private Integer in;

    public Items (long id, String name, long use, Integer in )
    {
        super();
        this.id=id;
        this.name=name;
        this.use=use;
        this.in = in ;
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

    public long getUse() {
        return use;
    }

    public void setUse(long use) {
        this.use=use;
    }

    public Integer getIn() {return in;}

    public void SetIn (Integer in) {this.in=in;}
}
