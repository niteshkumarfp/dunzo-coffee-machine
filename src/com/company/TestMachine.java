package com.company;

import com.company.exception.InvalidNumberOfOutletException;
import com.company.inventory.Beverage;
import com.company.inventory.BeverageRecordsInventory;
import com.company.inventory.IngredientInventory;
import com.company.io.input.InputParser;
import com.company.io.input.MachineInput;
import com.company.monitoring.InventoryObserver;

import java.io.IOException;
import java.util.Map;

public class TestMachine {
    public static void main(String[] args){
        if(args.length == 0){
            System.out.println("Please provide the input json file name as argument");
            return;
        }
        String inputFile = args[0];

        System.out.println("\nRUNNING TEST CASE \n");
        boolean testCasePassed = runTestCase(inputFile);
        if(testCasePassed) System.out.println("TEST CASE PASSED!");
        else System.out.println("TEST CASE PASSED!");




    }

    // run the test case
    public static boolean runTestCase(String testFileName){
        try{
            System.out.println("_________________________________________________________________");
            InputParser inputParser = new InputParser();
            IngredientInventory ingredientInventory = IngredientInventory.getInstance();
            InventoryObserver inventoryObserver = new InventoryObserver();
            ingredientInventory.addObserver(inventoryObserver);

            MachineInput machineInput = inputParser.parseMachineInput(testFileName);
            CoffeeMachine coffeeMachine = new CoffeeMachine(machineInput);
            coffeeMachine.startMachine();
            System.out.println("_________________________________________________________________");

            return checkTestCase();

        } catch(IOException ex) {
            System.out.println("Error reading input file!");
            return false;
        } catch (InvalidNumberOfOutletException e) {
            System.out.println(e.getMessage());
            System.out.println("_________________________________________________________________");
            return true;
        }
    }

    // check this testcase
    public static boolean checkTestCase(){
        boolean isOutputValid = checkOutput();
        System.out.println("");
        if(isOutputValid) System.out.println("Valid serves check....PASSED");
        else System.out.println("Valid serves check....FAILED");

        boolean isConsistencyValid = checkConsistency();
        if(isOutputValid) System.out.println("Consistency check....PASSED");
        else System.out.println("Consistency check....FAILED");
        return isOutputValid && isConsistencyValid;
    }

    // check output for correctness
    public static boolean checkOutput(){
        BeverageRecordsInventory beverageRecordsInventory = BeverageRecordsInventory.getInstance();
        IngredientInventory ingredientInventory = IngredientInventory.getInstance();

        boolean isValidOutput = true;
        Map<Beverage, Boolean> beverageStatus = beverageRecordsInventory.getBeverageStatus();
        for(Map.Entry e: beverageStatus.entrySet()){
            if(!(Boolean)e.getValue()){
                checkValidBeverageServeFailure((Beverage)e.getKey(), ingredientInventory);
            }
        }

        return true;
    }

    // check for the validity of a beverage serve failure
    public static boolean checkValidBeverageServeFailure(Beverage beverage, IngredientInventory ingredientInventory){
        Map<String, Integer> beverageIngredients = beverage.getIngredients();
        Map<String, Integer> remainingIngredients = ingredientInventory.getIngredients();
        boolean isValidBererageServeFailure = true;
        for(Map.Entry e: beverageIngredients.entrySet()){
            String ingredient = (String)e.getKey();
            if(remainingIngredients.containsKey(ingredient)){
                int remainingAmount = remainingIngredients.get(ingredient);
                int beverageRequirement = beverageIngredients.get(ingredient);
                if(remainingAmount >= beverageRequirement) isValidBererageServeFailure = false;
            }
        }
        return isValidBererageServeFailure;
    }

    // checking for inventory consistency
    public static boolean checkConsistency(){
        Map<String, Integer> initialIngredients = IngredientInventory.getInstance().getInitialIngredients();
        Map<String, Integer> usedIngredients = BeverageRecordsInventory.getInstance().getIngredientsUsed();
        Map<String, Integer> remainingIngredients = IngredientInventory.getInstance().getIngredients();

        // putting zeros for whatever ingredient wasn't used during serving
        for(Map.Entry e: initialIngredients.entrySet()){
            String ingredient = (String)e.getKey();
            if(!usedIngredients.containsKey(ingredient)){
                usedIngredients.put(ingredient, 0);
            }
        }

        for(Map.Entry e: initialIngredients.entrySet()){
            String ingredient = (String)e.getKey();
            if(!remainingIngredients.containsKey(ingredient)) return false;
            if(initialIngredients.get(ingredient) != (usedIngredients.get(ingredient) + remainingIngredients.get(ingredient))) return false;
        }

        return true;
    }
}
