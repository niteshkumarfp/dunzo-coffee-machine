package com.company.io.input;

import com.company.exception.InvalidNumberOfOutletException;
import com.company.inventory.Beverage;
import com.company.util.FileUtils;
import org.json.JSONObject;

import java.io.IOException;
import java.util.*;

/*
    This class is responsible for parsing the input json file
 */
public class InputParser {
    private int numOutlets;
    private Map<String, Integer> totalItemsQuantity;
    private Map<String, Object> beverages;

    /*
        Parse file content to a MachinInput object
     */
    public MachineInput parseMachineInput(String inputFile) throws IOException, InvalidNumberOfOutletException {
        String jsonContent = FileUtils.readFileToString(inputFile);

        JSONObject machineJson = new JSONObject(jsonContent).getJSONObject("machine");

        int outletCount = getOutletCountFromJson(machineJson);
        Map<String, Integer> totalItemsQuantity = getIngredientsFromJson(machineJson.getJSONObject("total_items_quantity"));
        ArrayList<Beverage> beverages = getBeveragesFromJson(machineJson);
        MachineInput machineInput = new MachineInput(outletCount, totalItemsQuantity, beverages);

        return machineInput;
    }

    private int getOutletCountFromJson(JSONObject machineJson){
        JSONObject outletsJson = machineJson.getJSONObject("outlets");
        return outletsJson.getInt("count_n");
    }

    private Map<String, Integer> getIngredientsFromJson(JSONObject totalItemsQuantityJson){
        Map<String, Integer> totalItemsQuantity = new HashMap<>();

        for(Object key : totalItemsQuantityJson.keySet()){
            String ingredientName = (String)key;
            int itemQuantity = totalItemsQuantityJson.getInt(ingredientName);
            totalItemsQuantity.put(ingredientName, itemQuantity);
        }

        return totalItemsQuantity;
    }

    private ArrayList<Beverage> getBeveragesFromJson(JSONObject machineJson){
        JSONObject beveragesJson = machineJson.getJSONObject("beverages");
        ArrayList<Beverage> beverages = new ArrayList<Beverage>();

        for(Object key : beveragesJson.keySet()){
            String beverageName = (String)key;
            Map<String, Integer> ingredients = getIngredientsFromJson(beveragesJson.getJSONObject((String)key));
            beverages.add(new Beverage(beverageName, ingredients));
        }

        return beverages;
    }


}
