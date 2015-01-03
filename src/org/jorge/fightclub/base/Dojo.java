package org.jorge.fightclub.base;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by jorge on 10/11/14.
 *
 * Dojo class's the base class of dojo. This is where you work with getters and setters.
 * without builder.
 */
public class Dojo implements Serializable{
    private int id;
    private String name,street;

    private Date inauguration;
    private ArrayList<Coach> coachList;

    private ArrayList<Boxer> boxerList;

    public Dojo(){}

    public Dojo(int id, String name, String street, Date inauguration) {
        this.id = id;
        this.name = name;
        this.street = street;
        this.inauguration = inauguration;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Date getInauguration() {
        return inauguration;
    }

    public void setInauguration(Date inauguration) {
        this.inauguration = inauguration;
    }

    public ArrayList<Coach> getCoachList() {
        return coachList;
    }

    public void setCoachList(ArrayList<Coach> coachList) {
        this.coachList = coachList;
    }

    public ArrayList<Boxer> getBoxerList() {
        return boxerList;
    }

    public void setBoxerList(ArrayList<Boxer> boxerList) {
        this.boxerList = boxerList;
    }

    @Override
    public String toString(){
        return name + "     "+street;
    }
}
