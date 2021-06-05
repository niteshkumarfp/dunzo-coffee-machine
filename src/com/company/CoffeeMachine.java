package com.company;

import com.company.inventory.Beverage;
import com.company.inventory.IngredientInventory;
import com.company.io.input.MachineInput;
import com.company.outlet.OutletAssembly;

import java.util.*;

/*
    Coffee machine class for machine level operations
    fillInventory: creates/populates inventory according to input
    fillBeverageQueue: creates/populates beverage queue from input
    initializeOutletAssembly: initializes outlets assembly object
    startMachine: starts the machine and begins serving the beverages
 */
public class CoffeeMachine {

    private Queue<Beverage> beverageQueue = new LinkedList<>();

    CoffeeMachine(MachineInput machineInput){
        int outletCount = machineInput.getOutletCount();
        initializeOutletAssembly(outletCount);
        fillInventory(machineInput.getTotalItemsQuantity());
        fillBeverageQueue(machineInput.getBeverages());
    }

    /*
        Serves all the beverages one by one
     */
    public void startMachine(){
        OutletAssembly outletAssembly = OutletAssembly.getInstance();


        // serve all the beverages one by one
        while(beverageQueue.size() > 0){
            Beverage beverage = beverageQueue.poll();
            boolean isBeverageHandled = false;
            while(!isBeverageHandled){
                isBeverageHandled = outletAssembly.serve(beverage);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        // Waiting for all threads to finish
        while(outletAssembly.getAssemblyQueue().size() != outletAssembly.getNumOutlets()){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    };

    /*
        Populate inventory according to input to the machine
     */
    private void fillInventory(Map<String, Integer> totalItemsQuantity){
        IngredientInventory ingredientInventory = IngredientInventory.getInstance();
        for(Map.Entry e: totalItemsQuantity.entrySet()){
            ingredientInventory.setIngredientQuantity((String)e.getKey(), (Integer)e.getValue());
        }
        ingredientInventory.setInitialIngredients();

    }

    /*
        Create a queue of all the beverages to be served
     */
    private void fillBeverageQueue(ArrayList<Beverage> beverages){
        beverageQueue.addAll(beverages);
    }

    /*
        Create required outlets in the outlet assembly
     */
    private void initializeOutletAssembly(int size){
        OutletAssembly outletAssembly = OutletAssembly.getInstance();
        outletAssembly.initializeAssembly(size);
    }
}
