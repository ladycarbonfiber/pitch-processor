package com.cboefx.bats;

import java.util.Objects;

//data class for dealing with orders, only concerned with volume so we ignore the price and side fields
public class SimpleOrder {
    private final String id;
    private final String symbol;
    private long quantity;
    public SimpleOrder(String id, String symbol, long quantity){
        this.id = id;
        this.symbol = symbol;
        this.quantity = quantity;
    }
    //add shares
    public void addShares(long amount){
        quantity  += amount;
    }

    //remove shares and return number executed
    public long executeShares(long amount){
        if(amount > quantity){
            quantity = 0;
            return quantity;
        }else{
            quantity -= amount;
            return amount;
        }
    }

    public void removeShares(long amount){
        quantity -=amount;
    }
    public String getId(){
        return id;
    }
    public String getSymbol(){
        return symbol;
    }

    public long getQuantity(){
        return quantity;
    }

    public boolean isEmpty(){
        return quantity <= 0;
    }
}
