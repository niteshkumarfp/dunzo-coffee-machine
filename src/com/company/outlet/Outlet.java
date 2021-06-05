package com.company.outlet;

import com.company.inventory.Beverage;
import com.company.exception.IngredientNotAvailableException;
import com.company.exception.IngredientNotSufficientException;
import com.company.inventory.BeverageRecordsInventory;
import com.company.inventory.IngredientInventory;
import com.company.io.output.Output;

/*
    Class for outlet operations
 */
public class Outlet implements Comparable{

    private int outletId;
    private boolean isServing = false;

    Outlet(int outletId){
        this.outletId = outletId;
    }

    /*
        calls BeverageServer to serve the beverage
     */
    public void serveBeverage(Beverage beverage){
        isServing = true;
        BeverageServer beverageServer = new BeverageServer(beverage);
        beverageServer.start();
    }

    @Override
    public int compareTo(Object o) {
        Outlet that = (Outlet)o;
        if(isServing == that.getIsServing()){
            return Integer.compare(outletId, that.outletId);
        }
        return Boolean.compare(isServing, that.getIsServing());
    }

    public boolean getIsServing(){
        return isServing;
    }

    /*
        BeverageServer serves a beverage in the outlet in a new thread
     */
    public class BeverageServer extends Thread{

        private final Beverage beverage;
        private final int PREPARE_BEVERAGE_TIME = 3;

        BeverageServer(Beverage beverage){
            this.beverage = beverage;
        }

        /*
            method to start thread execution
         */
        public void run() {
            try {
                serve();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        /*
            Try to get ingredients and serve a beverage.
         */
        private void serve() throws InterruptedException {

            StringBuilder outMessage = new StringBuilder();
            IngredientInventory ingredientInventory = IngredientInventory.getInstance();
            try {
                // Serve beverage if ingredients are available
                boolean ingredientsAvailable = ingredientInventory.giveIngredients(beverage.getIngredients());
                if(ingredientsAvailable){
                    sleep(PREPARE_BEVERAGE_TIME*1000);
                    outMessage.append(beverage.getName() + " is prepared");
                    BeverageRecordsInventory.getInstance().updateInventory(beverage, true);

                }
            } catch (IngredientNotAvailableException | IngredientNotSufficientException e) {
                // Ingredients not available
                outMessage.append(beverage.getName() + " cannot be prepared because ");
                outMessage.append(e.getMessage());
                BeverageRecordsInventory.getInstance().updateInventory(beverage, false);
            }
            Output.threadSafePrintln(outMessage.toString());

            isServing = false;

            // add the outlet back to the outlet queue to be used for other beverages
            OutletAssembly outletAssembly = OutletAssembly.getInstance();
            outletAssembly.addOutlet(Outlet.this);
        }
    }
}
