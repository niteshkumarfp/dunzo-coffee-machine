package com.company.io.input;

import com.company.exception.InvalidNumberOfOutletException;
import com.company.inventory.Beverage;

import java.util.ArrayList;
import java.util.Map;

/*
    Represents input to the CoffeeMachine.
    Json input is converted to MachineInput object which is passed to the CoffeeMachine for initialization
 */
public class MachineInput {

    private int outletCount;
    private Map<String, Integer> totalItemsQuantity;
    private ArrayList<Beverage> beverages;

    MachineInput(int outletCount, Map<String, Integer> totalItemsQuantity, ArrayList<Beverage> beverages) throws InvalidNumberOfOutletException {
        if(outletCount <= 0){
            throw new InvalidNumberOfOutletException(outletCount);
        }
        this.outletCount = outletCount;
        this.totalItemsQuantity = totalItemsQuantity;
        this.beverages = beverages;
    }

    public int getOutletCount() {
        return outletCount;
    }

    public Map<String, Integer> getTotalItemsQuantity() {
        return totalItemsQuantity;
    }

    public ArrayList<Beverage> getBeverages() {
        return beverages;
    }
}
