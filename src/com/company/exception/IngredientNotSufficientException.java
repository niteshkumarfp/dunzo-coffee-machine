package com.company.exception;

/*
    Exception for the case when an ingredient is not in enough quantity
 */
public class IngredientNotSufficientException extends Exception {
    public IngredientNotSufficientException(String ingredientName){
        super( "item " + ingredientName + " is not sufficient");
    }
}
