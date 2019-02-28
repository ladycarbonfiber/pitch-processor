package com.cboefx.bats;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Processor {
    private Map<String, Long> excecutedTracker;
    private Map<String, SimpleOrder> activeOrders;
    public Processor(){
        excecutedTracker = new HashMap<>();
        activeOrders = new HashMap<>();

    }
    //Process PITCH data
    public void processTarget(InputStream in){
        try(BufferedReader br = new BufferedReader(new InputStreamReader(in))){
            String line = br.readLine();
            while(line != null && !line.isEmpty() && !line.isBlank()){
                readPitchLine(line);
                line = br.readLine();
            }
        }catch (IOException e){
            System.err.println("Input failed " + e.getMessage());
        }

    }

    private void readPitchLine(String line){
        //remove sample header
        if(line.charAt(0) =='S'){
            line = line.substring(1);
        }
        char type = line.charAt(8);
        String id;
        String symbol;
        long quantity;
        switch (type){
            //add order to tracker
            case 'A':
                id = line.substring(9,21);
                symbol = line.substring(28, 34).trim();
                quantity = Long.parseLong(line.substring(22, 28));
                activeOrders.put(id, new SimpleOrder(id, symbol, quantity));
            break;
            //execute order already tracked by active order
            case 'E':
                id = line.substring(9,21);
                quantity = Long.parseLong(line.substring(21, 27));
                var order = activeOrders.get(id);
                if(order != null) {
                    excecutedTracker.merge(order.getSymbol(), order.executeShares(quantity), (a, b) -> a + b);
                }
            break;
            //cancel order already tracked
            case 'X':
                id = line.substring(9,21);
                quantity = Long.parseLong(line.substring(21,27));
                activeOrders.get(id).removeShares(quantity);
            break;
            //execute order not tracked
            case 'P':
                quantity = Long.parseLong(line.substring(22, 28));
                symbol = line.substring(28, 34).trim();
                excecutedTracker.merge(symbol, quantity, (a,b) -> a+b);
            default:
                //ignore
        }
    }

    public Map<String, Long> getExecutedOrders(){
        return excecutedTracker;
    }


}
