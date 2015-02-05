package org.jorgechato.fightclub.base;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

/**
 * Created by jorge on 4/02/15.
 */
@Entity
@Table(name="fight")
public class Fight {
    private int id;
    private String name;
    private String street;
    private Date day;
    private List<Boxer> boxers;

    @Id
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
    @Column(name = "day")
    public Date getDay() {
        return day;
    }

    public void setDay(Date day) {
        this.day = day;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Fight fight = (Fight) o;

        if (id != fight.id) return false;
        if (day != null ? !day.equals(fight.day) : fight.day != null) return false;
        if (name != null ? !name.equals(fight.name) : fight.name != null) return false;
        if (street != null ? !street.equals(fight.street) : fight.street != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (street != null ? street.hashCode() : 0);
        result = 31 * result + (day != null ? day.hashCode() : 0);
        return result;
    }

    @ManyToMany
    @JoinTable(name = "boxer_fight", catalog = "fightclub", schema = "", joinColumns = @JoinColumn(name = "id_fight", referencedColumnName = "id", nullable = false), inverseJoinColumns = @JoinColumn(name = "id_boxer", referencedColumnName = "id", nullable = false))
    public List<Boxer> getBoxers() {
        return boxers;
    }

    public void setBoxers(List<Boxer> boxers) {
        this.boxers = boxers;
    }
}
