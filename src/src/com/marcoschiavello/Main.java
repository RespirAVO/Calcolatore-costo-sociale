package com.marcoschiavello;

import com.marcoschiavello.records.LocationRecord;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {

        Map<String, Double> AutomobileCosts = new HashMap<>();
        AutomobileCosts.put("Uncompensated crash damages", 0.01);
        AutomobileCosts.put("Air pollution", 0.10);
        AutomobileCosts.put("Noise", 0.01);
        AutomobileCosts.put("Land use and infrastructure", 0.08);
        AutomobileCosts.put("Traffic infrastructure maintenance", 0.00);
        AutomobileCosts.put("Barrier effects", 0.02);
        AutomobileCosts.put("Curbside parking", 0.07);
        AutomobileCosts.put("Resource requirements", 0.01);
        AutomobileCosts.put("Subsidies", 0.00);
        AutomobileCosts.put("Climate change", 0.02);



        SocialCostCalculator calculator = new SocialCostCalculator("Automobile.csv", new LocationRecord(45.071012,7.6930767), AutomobileCosts);
        calculator.printCost();

    }
}
