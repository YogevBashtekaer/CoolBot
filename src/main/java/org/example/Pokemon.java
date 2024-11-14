package org.example;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.awt.*;
import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown=true)

public class Pokemon {
    private int id;
    private String name;
    private int height;
    private int weight;


    public Pokemon() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
    public String getData(){
        return "name: " + this.name
                +"\nid: " + this.id
                +"\nheight: " + this.height
                +"\nweight: " + this.weight;
    }

}
