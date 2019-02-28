package com.cboefx.bats;

import java.io.IOException;
import java.io.OutputStream;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class OutputHelper {
    @FunctionalInterface
    private interface checkedConsumer<A> extends Consumer<A> {
        @Override
        default void accept(A a){
            try{
                acceptThrows(a);
            }catch(IOException e){
                System.err.println(e.getMessage());
            }
        };

        void acceptThrows(A a) throws IOException;
    }

    private  OutputHelper(){}
    //Print first N entries
    public static <K, V> void emmitFirstN(OutputStream os, Map<K, V> in, int n){
        in.entrySet().stream()
                .limit(n)
                .forEach((checkedConsumer<Map.Entry>)(entry) -> os.write((entry.getKey().toString()
                + "  "
                + entry.getValue().toString()
                + "\n").getBytes()));
    }

    //Generate Sorted Map
    public static <K extends Comparable<K>, V extends Comparable<V>>
        Map<K , V > sortByValue(Map<K, V> inputMap, boolean accending, BiFunction<K, K, Integer> handleEqual){
        final var entryList = new LinkedList<>(inputMap.entrySet());
        entryList.sort((a,b) ->{
            if(accending){
                if(a.getValue().compareTo(b.getValue()) == 0){
                    return handleEqual.apply(a.getKey(), b.getKey());
                }else{
                    return a.getValue().compareTo(b.getValue());
                }
            }else{
                if(a.getValue().compareTo(b.getValue()) == 0){
                    return handleEqual.apply(a.getKey(), b.getKey());
                }else{
                    return b.getValue().compareTo(a.getValue());
                }
            }
        });
        return entryList.stream().collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (a, b) -> b, LinkedHashMap::new));
    }

}
