package org.jorgechato.fightclub.base;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

/**
 * Created by jorge on 4/02/15.
 */
public class Fight implements Serializable{
    private String name;
    private String street;
    private Date day;
    private List<Boxer> boxers;

    public Fight() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Date getDay() {
        return day;
    }

    public void setDay(Date day) {
        this.day = day;
    }

    public List<Boxer> getBoxers() {
        return boxers;
    }

    public void setBoxers(List<Boxer> boxers) {
        this.boxers = boxers;
    }

    @Override
    public String toString() {
        return name;
    }
}
