package com.cboefx.bats;

import org.apache.commons.cli.*;

import java.io.*;

public class Application {
    public static void main(String ... args){
        Options options = new Options();
        Option input = new Option("i", "input", true, "input file");
        input.setRequired(false);
        options.addOption(input);
        Option output = new Option("o", "output", true, "output file");
        output.setRequired(false);
        options.addOption(output);
        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd = null;

        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp("BATS coding assignment", options);
            System.exit(1);
        }

        String inputFile = cmd.getOptionValue("input");
        String outputFile = cmd.getOptionValue("output");
        InputStream read = null;
        OutputStream out = null;
        if(inputFile == null || inputFile.isEmpty() || inputFile.isBlank()){
            read = System.in;
        }else{
            try{
                read = new FileInputStream(inputFile);
            }catch (IOException e){
                System.err.println("Error loading file: " + e.getMessage());
                System.exit(1);
            }
        }
        if(outputFile == null || outputFile.isEmpty() || outputFile.isBlank()){
            out = System.out;
        }else{
            try {
                out = new FileOutputStream(outputFile);
            }catch(IOException e){
                System.err.println("Error loading output file " + e.getMessage());
                System.exit(1);
            }
        }
        Processor p = new Processor();
        p.processTarget(read);
        OutputHelper.emmitFirstN(out, OutputHelper.sortByValue(p.getExecutedOrders(), false, String::compareTo), 10);
    }
}
