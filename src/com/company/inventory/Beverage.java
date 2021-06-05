package com.company.inventory;

import java.util.HashMap;
import java.util.Map;

/*
    Class to represent a beverage.
 */
public class Beverage {

    private String name;
    private Map<String, Integer> ingredients;

    public Beverage(String name, Map<String, Integer> ingredients){
        this.name = name;
        this.ingredients = new HashMap<>();
        this.ingredients.putAll(ingredients);
    }

    public String getName() {
        return name;
    }

    public Map<String, Integer> getIngredients() {
        return ingredients;
    }
}
