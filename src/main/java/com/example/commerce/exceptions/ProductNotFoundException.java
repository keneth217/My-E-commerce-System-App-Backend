package com.example.commerce.exceptions;

public class ProductNotFoundException extends  RuntimeException{
    public  ProductNotFoundException(String message){
        super(message);
    }
}
