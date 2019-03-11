package com.example.planning.Model;

//Declaration d'un item
public class Card {

    private String name;

    public Cursus getCursus() {
        return cursus;
    }

    public void setCursus(Cursus cursus) {
        this.cursus = cursus;
    }

    public enum Category {CAMPUS, SCHOOL, DEPARTMENT, TRAINING, GROUP}
    private Category category;
    private Cursus cursus;

    public Card(String name, Category category, Cursus cursus) {
        this.name = name;
        this.category = category;
        this.cursus = cursus;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }
}