package org.jorgechato.fightclub.base;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

/**
 * Created by jorge on 4/02/15.
 */
public class Coach implements Serializable {
    private String name;
    private Date birthday;
    private Integer sperience;
    private Dojo dojo;
    private List<Boxer> boxers;

    public Coach() {
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

    public Integer getSperience() {
        return sperience;
    }

    public void setSperience(Integer sperience) {
        this.sperience = sperience;
    }

    public Dojo getDojo() {
        return dojo;
    }

    public void setDojo(Dojo dojo) {
        this.dojo = dojo;
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
