package com.romware.hibernatetests.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity(name = "User")
@Table(name = "users")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "login", nullable = false)
    private String login;

    @Column(name = "pass", nullable = true)
    private String pass;

    @Column(name = "eloranking", nullable = false)
    private Integer eloranking;

    @Column(name = "wins", nullable = false)
    private Integer wins;

    @Column(name = "loses", nullable = false)
    private Integer loses;

    @Column(name = "isABot", nullable = false)
    private Integer isABot;

    @Column(name = "idIcone", nullable = false)
    private Integer idIcone;

    @Column(name = "horodatage", nullable = true)
    private Date horodatage;

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

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
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

    public Integer getIsABot() {
        return isABot;
    }

    public void setIsABot(Integer isABot) {
        this.isABot = isABot;
    }

    public Integer getIdIcone() {
        return idIcone;
    }

    public void setIdIcone(Integer idIcone) {
        this.idIcone = idIcone;
    }

    public Date getHorodatage() {
        return horodatage;
    }

    public void setHorodatage(Date horodatage) {
        this.horodatage = horodatage;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", login=" + login + "]";
    }

    public String getConcatenatedFields() {
        return getId() + ", '" + getLogin() + "', '" + getPass() + "', " + getEloranking() + ", " +
                getWins() + ", " + getLoses() + ", " + getIsABot() + ", " + getIdIcone() + ", null";
    }
}
