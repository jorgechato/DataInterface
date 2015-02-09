package org.jorgechato.fightclub.base;

import javax.persistence.*;
import java.util.List;

/**
 * Created by jorge on 4/02/15.
 */
@Entity
@Table(name="boxer")
public class Boxer {
    private int id;
    private String name;
    private Integer wins;
    private Integer lose;
    private Double weight;
    private Coach coach;
    private Dojo dojo;
    private List<Fight> fights;

    public Boxer() {
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
    @Column(name = "wins")
    public Integer getWins() {
        return wins;
    }

    public void setWins(Integer wins) {
        this.wins = wins;
    }

    @Basic
    @Column(name = "lose")
    public Integer getLose() {
        return lose;
    }

    public void setLose(Integer lose) {
        this.lose = lose;
    }

    @Basic
    @Column(name = "weight")
    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Boxer boxer = (Boxer) o;

        if (id != boxer.id) return false;
        if (lose != null ? !lose.equals(boxer.lose) : boxer.lose != null) return false;
        if (name != null ? !name.equals(boxer.name) : boxer.name != null) return false;
        if (weight != null ? !weight.equals(boxer.weight) : boxer.weight != null) return false;
        if (wins != null ? !wins.equals(boxer.wins) : boxer.wins != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (wins != null ? wins.hashCode() : 0);
        result = 31 * result + (lose != null ? lose.hashCode() : 0);
        result = 31 * result + (weight != null ? weight.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "id_coach", referencedColumnName = "id")
    public Coach getCoach() {
        return coach;
    }

    public void setCoach(Coach coach) {
        this.coach = coach;
    }

    @ManyToOne
    @JoinColumn(name = "id_dojo", referencedColumnName = "id")
    public Dojo getDojo() {
        return dojo;
    }

    public void setDojo(Dojo dojo) {
        this.dojo = dojo;
    }

    @ManyToMany(mappedBy = "boxers")
    public List<Fight> getFights() {
        return fights;
    }

    public void setFights(List<Fight> fights) {
        this.fights = fights;
    }

    @Override
    public String toString() {
        return name;
    }
}
