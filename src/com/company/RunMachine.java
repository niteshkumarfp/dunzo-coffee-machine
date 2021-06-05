package com.company;

import com.company.exception.InvalidNumberOfOutletException;
import com.company.inventory.IngredientInventory;
import com.company.io.input.InputParser;
import com.company.io.input.MachineInput;
import com.company.monitoring.InventoryObserver;

import java.io.IOException;

public class RunMachine {


    public static void main(String[] args) {
        if(args.length == 0){
            System.out.println("Please provide the input json file name as argument");
            return;
        }
        String inputFile = args[0];
        runMachine(inputFile);
    }

    /*
        Reads input, creates observers and starts the coffeeMachine
    */
    public static void runMachine(String inputFile){
        try{
            InputParser inputParser = new InputParser();
            IngredientInventory ingredientInventory = IngredientInventory.getInstance();
            InventoryObserver inventoryObserver = new InventoryObserver();
            ingredientInventory.addObserver(inventoryObserver);

            MachineInput machineInput = inputParser.parseMachineInput(inputFile);
            CoffeeMachine coffeeMachine = new CoffeeMachine(machineInput);
            coffeeMachine.startMachine();

        } catch(IOException ex) {
            System.out.println("Error reading input file!");
        } catch(InvalidNumberOfOutletException ex){
            System.out.println(ex.getMessage());
        }
    }
}
