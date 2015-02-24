package org.jorgechato.fightclub.base;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

/**
 * Created by jorge on 4/02/15.
 */
public class Dojo  implements Serializable {
    private String name;
    private String street;
    private Date inauguration;
    private List<Coach> coachs;
    private List<Boxer> boxers;

    public Dojo() {
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

    public List<Coach> getCoachs() {
        return coachs;
    }

    public void setCoachs(List<Coach> coachs) {
        this.coachs = coachs;
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
