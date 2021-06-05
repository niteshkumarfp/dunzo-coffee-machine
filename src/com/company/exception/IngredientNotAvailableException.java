package com.company.exception;

/*
    Exception for the case when an ingredient is not available
 */
public class IngredientNotAvailableException extends Exception{
    public IngredientNotAvailableException(String ingredientName) {
        super(ingredientName + " is not available");
    }

}
