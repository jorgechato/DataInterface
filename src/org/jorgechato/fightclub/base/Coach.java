package org.jorgechato.fightclub.base;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

/**
 * Created by jorge on 4/02/15.
 */
@Entity
@Table(name="coach")
public class Coach {
    private int id;
    private String name;
    private Date birthday;
    private Integer sperience;
    private Dojo dojo;
    private List<Boxer> boxers;

    public Coach() {
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
    @Column(name = "birthday")
    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    @Basic
    @Column(name = "sperience")
    public Integer getSperience() {
        return sperience;
    }

    public void setSperience(Integer sperience) {
        this.sperience = sperience;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Coach coach = (Coach) o;

        if (id != coach.id) return false;
        if (birthday != null ? !birthday.equals(coach.birthday) : coach.birthday != null) return false;
        if (name != null ? !name.equals(coach.name) : coach.name != null) return false;
        if (sperience != null ? !sperience.equals(coach.sperience) : coach.sperience != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (birthday != null ? birthday.hashCode() : 0);
        result = 31 * result + (sperience != null ? sperience.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "id_dojo", referencedColumnName = "id")
    public Dojo getDojo() {
        return dojo;
    }

    public void setDojo(Dojo dojo) {
        this.dojo = dojo;
    }

    @OneToMany(mappedBy = "coach")
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
