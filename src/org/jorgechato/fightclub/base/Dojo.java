package org.jorgechato.fightclub.base;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.List;

/**
 * Created by jorge on 4/02/15.
 */
@Entity
@Table(name="dojo")
public class Dojo  implements Serializable {
    private int id;
    private String name;
    private String street;
    private Date inauguration;
    private List<Coach> coachs;
    private List<Boxer> boxers;

    public Dojo() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "street")
    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    @Basic
    @Column(name = "inauguration")
    public Date getInauguration() {
        return inauguration;
    }

    public void setInauguration(Date inauguration) {
        this.inauguration = inauguration;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Dojo dojo = (Dojo) o;

        if (id != dojo.id) return false;
        if (inauguration != null ? !inauguration.equals(dojo.inauguration) : dojo.inauguration != null) return false;
        if (name != null ? !name.equals(dojo.name) : dojo.name != null) return false;
        if (street != null ? !street.equals(dojo.street) : dojo.street != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (street != null ? street.hashCode() : 0);
        result = 31 * result + (inauguration != null ? inauguration.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "dojo")
    public List<Coach> getCoachs() {
        return coachs;
    }

    public void setCoachs(List<Coach> coachs) {
        this.coachs = coachs;
    }

    @OneToMany(mappedBy = "dojo")
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
