package org.jorge.fightclub.base;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by jorge on 10/11/14.
 *
 * Boxer class's the base class of boxer. This is where you work with getters and setters.
 * without builder.
 */
public class Boxer implements Serializable{
    private String name;
    private int win,lose;
    private float weight;

    private Coach coach;
    private Dojo dojo;

    public Boxer() {
    }

    public int getWin() {
        return win;
    }

    public void setWin(int win) {
        this.win = win;
    }

    public int getLose() {
        return lose;
    }

    public void setLose(int lose) {
        this.lose = lose;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Coach getCoach() {
        return coach;
    }

    public void setCoach(Coach coach) {
        this.coach = coach;
    }

    public Dojo getDojo() {
        return dojo;
    }

    public void setDojo(Dojo dojo) {
        this.dojo = dojo;
    }

    @Override
    public String toString(){
        return name.toUpperCase()+"     "+win+" victorias";
    }
}
