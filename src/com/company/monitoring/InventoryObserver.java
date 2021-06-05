package com.company.monitoring;

import com.company.io.output.Output;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

/*
    Observer class to observe inventory status and create alerts.
    Observer pattern is implemented using java's Observer interface
 */
public class InventoryObserver implements Observer {

    private Map<String, Integer> currentIngredientAlerts;

    public InventoryObserver(){
        currentIngredientAlerts = new HashMap<>();
    }

    /*
        Observer interface method invoked when observer is notified.
        Check for low running ingredients whenever there is a change in inventory.
        Create alerts for any new alert on an ingredient.
     */
    @Override
    public void update(Observable o, Object arg) {
        Map<String, Integer> allIngredients = (Map<String, Integer>)arg;
        for(Map.Entry e: allIngredients.entrySet()){
            int currIngredientValue = (Integer)e.getValue();
            if(currIngredientValue < 50){
                String ingredientName = (String)e.getKey();


                if(currentIngredientAlerts.containsKey(ingredientName)){
                    int currAlertValue =  currentIngredientAlerts.get(ingredientName);

                    // Create alert only if it hasn't been created
                    if(currIngredientValue != currAlertValue){
                        createAlert(ingredientName, currIngredientValue);
                    }
                }
                else{
                    currentIngredientAlerts.put(ingredientName, currIngredientValue);
                    createAlert(ingredientName, currIngredientValue);
                }


            }
        }
    }

    private void createAlert(String ingredient, int value){
        Output.threadSafePrintln("     |ALERT| item running low: " + ingredient + " - " + value + " left!");
    }

}
