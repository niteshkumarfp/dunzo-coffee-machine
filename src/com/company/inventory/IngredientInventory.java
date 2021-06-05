package com.company.inventory;

import com.company.exception.IngredientNotAvailableException;
import com.company.exception.IngredientNotSufficientException;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

/*
    Class for managing inventory in a threadsafe manner.
 */
public class IngredientInventory extends Observable {

    private static IngredientInventory instance;

    /*
        Map to store current ingredients in the inventory
     */
    private Map<String, Integer> ingredients;

    /*
        Initial state of inventory. Helpful for validation purposes
     */
    private Map<String, Integer> initialIngredients;

    /*
        private constructor for singleton creation
     */
    private IngredientInventory() {
        ingredients = new HashMap<>();
    }

    public static IngredientInventory getInstance() {
        if (instance == null) {
            synchronized (IngredientInventory.class) {
                if(instance==null) {
                    instance = new IngredientInventory();
                }
            }
        }
        return instance;
    }

    /*
        return quantity of an ingredient available in inventory
     */
    public synchronized int getIngredientQuantity(String name){
        if(ingredients.containsKey(name)){
            return ingredients.get(name);
        }
        return 0;
    }

    /*
        set an ingredient's quantity
     */
    public synchronized void setIngredientQuantity(String name, int val){
        ingredients.put(name, val);

        setChanged();
        notifyObservers(ingredients);
    }

    /*
        check if a set of ingredients is available in inventory
     */
    public synchronized boolean isIngredientsAvailable(Map<String, Integer> requiredIngredients) throws IngredientNotAvailableException, IngredientNotSufficientException {
        boolean ingredientsAvailable = true;
        for(Map.Entry e: requiredIngredients.entrySet()){
            String ingredientName = (String)e.getKey();
            int amountRequired = (Integer)e.getValue();
            boolean ingredientExists = this.ingredients.containsKey(ingredientName);
            boolean ingredientQuantitySufficient = getIngredientQuantity(ingredientName) >= amountRequired;
            boolean isIngredientZero = false;
            if(ingredientExists){
                isIngredientZero = this.ingredients.get(ingredientName) == 0;
            }
            if(!ingredientExists){
                setIngredientQuantity(ingredientName, 0);
                throw new IngredientNotAvailableException(ingredientName);
            }
            else if(isIngredientZero){
                throw new IngredientNotAvailableException(ingredientName);
            }
            else if(!ingredientQuantitySufficient) {
                throw new IngredientNotSufficientException(ingredientName);
            }
        }
        return ingredientsAvailable;
    }

    /*
        Take out ingredients from inventory and pass them to the caller
     */
    public synchronized boolean giveIngredients(Map<String, Integer> ingredients) throws IngredientNotAvailableException, IngredientNotSufficientException {
        if(isIngredientsAvailable(ingredients)){
            for(Map.Entry e: ingredients.entrySet()){
                String ingredientName = (String)e.getKey();
                int currQuantity = 0;
                currQuantity = this.ingredients.get(ingredientName);
                currQuantity -= (Integer)e.getValue();
                setIngredientQuantity(ingredientName, currQuantity);
            }

            return true;
        }
        return false;
    }

    /*
        Method for refilling of an ingredient by an amount
     */
    public synchronized void refillIngredient(String ingredientName, int refillAmount){
        int currentValue = 0;
        if(ingredients.containsKey(ingredientName)){
            currentValue += ingredients.get(ingredientName);
        }
        int updatedValue = currentValue + refillAmount;
        ingredients.put(ingredientName, updatedValue);
    }

    public Map<String, Integer> getIngredients() {
        return ingredients;
    }

    public synchronized void setInitialIngredients(){
        if(initialIngredients == null){
            initialIngredients = new HashMap<>();
            for(Map.Entry e: ingredients.entrySet()){
                initialIngredients.put((String)e.getKey(), (Integer)e.getValue());
            }
        }
    }

    public Map<String, Integer> getInitialIngredients() {
        return initialIngredients;
    }
}
