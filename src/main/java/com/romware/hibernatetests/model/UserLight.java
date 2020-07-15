package com.romware.hibernatetests.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity(name = "UserLight")
@Table(name = "users")
public class UserLight implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "login", nullable = false)
    private String login;

    @Column(name = "eloranking", nullable = false)
    private Integer eloranking;

    @Column(name = "wins", nullable = false)
    private Integer wins;

    @Column(name = "loses", nullable = false)
    private Integer loses;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Integer getEloranking() {
        return eloranking;
    }

    public void setEloranking(Integer eloranking) {
        this.eloranking = eloranking;
    }

    public Integer getWins() {
        return wins;
    }

    public void setWins(Integer wins) {
        this.wins = wins;
    }

    public Integer getLoses() {
        return loses;
    }

    public void setLoses(Integer loses) {
        this.loses = loses;
    }

    @Override
    public String toString() {
        return "UserLight [id=" + id + ", login=" + login + "]";
    }
}
