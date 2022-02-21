package com.videoGamesWeb.vgcore.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter @Setter
@DiscriminatorValue("1")
@Table(name="console")
public class Console extends Product {

    @JsonIgnore
    @ManyToMany
    @JoinTable(name="games_on_consoles",
            joinColumns = @JoinColumn(name = "console_id"),
            inverseJoinColumns = @JoinColumn(name="game_id"))
    private List<Game> games;

    public Console(){

    }

    public Console(long id){
        this.id = id;
    } //TODO : find how to fix this (autoincrement on both child tables)
}
