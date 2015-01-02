package org.jorge.fightclub.base;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by jorge on 10/11/14.
 *
 * Coach class's the base class of coach. This is where you work with getters and setters.
 * without builder.
 */
public class Coach implements Serializable{
    private int id;
    private String name;
    private Date birthday;
    private int years;

    private ArrayList<Boxer> boxerList;
    private Dojo dojo;

    public Coach(){}

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

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public int getYears() {
        return years;
    }

    public void setYears(int years) {
        this.years = years;
    }

    public ArrayList<Boxer> getBoxerList() {
        return boxerList;
    }

    public void setBoxerList(ArrayList<Boxer> boxerList) {
        this.boxerList = boxerList;
    }

    public Dojo getDojo() {
        return dojo;
    }

    public void setDojo(Dojo dojo) {
        this.dojo = dojo;
    }

    @Override
    public String toString(){
        return name +"      "+years+" experiencia";
    }
}
