package com.company.inventory;

import java.util.HashMap;
import java.util.Map;

/*
    This class manages the data related to beverages processed by the coffee machine.
 */
public class BeverageRecordsInventory {

    private Map<Beverage, Boolean> beverageStatus;  // To keep track of weather a beverage was successfully served or not

    private Map<String, Integer> ingredientsUsed;  // To keep track of all the ingredient used while serving beverages

    private static BeverageRecordsInventory instance;

    private BeverageRecordsInventory(){
        beverageStatus = new HashMap<>();
        ingredientsUsed = new HashMap<>();
    }

    public static BeverageRecordsInventory getInstance() {
        if (instance == null) {
            synchronized (IngredientInventory.class) {
                if(instance==null) {
                    instance = new BeverageRecordsInventory();
                }
            }
        }
        return instance;
    }

    /*
        Update beverage records after a beverage is processed by the machine.
     */
    public synchronized void updateInventory(Beverage beverage, boolean status){

        beverageStatus.put(beverage, status);

        // if failed to serve beverage, no ingredient were used, so return
        if(!status){
            return;
        }

        // record all the ingredients used while serving the beverage
        Map<String, Integer> ingredients = beverage.getIngredients();
        for(Map.Entry e: ingredients.entrySet()){
            String ingredientName = (String)e.getKey();
            int usedAmount = (Integer)e.getValue();
            int currentQuantity = 0;
            if(ingredientsUsed.containsKey(ingredientName)){
                currentQuantity += ingredientsUsed.get(ingredientName);
            }
            int updatedAmount = currentQuantity + usedAmount;
            ingredientsUsed.put(ingredientName, updatedAmount);
        }
    }

    public Map<Beverage, Boolean> getBeverageStatus() {
        return beverageStatus;
    }

    public Map<String, Integer> getIngredientsUsed() {
        return ingredientsUsed;
    }

}
