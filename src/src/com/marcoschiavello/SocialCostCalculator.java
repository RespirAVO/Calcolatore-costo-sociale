package com.marcoschiavello;

import com.marcoschiavello.records.LocationRecord;
import com.opencsv.CSVReader;

import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

public class SocialCostCalculator {
    private double totalKM;
    private LocationRecord pivot;
    private Map<String, Double> costMap;

    public SocialCostCalculator(String recordsFile, LocationRecord pivot, Map<String, Double> costMap) {
        this.totalKM = 0;
        this.pivot   = pivot;
        this.costMap = costMap;
        
        this.readCSVFile(recordsFile);
    }

    private void readCSVFile(String file) {
        String[] line;
        int[] locationIndexes = new int[2];
        
        try (CSVReader reader = new CSVReader(new FileReader(file))) {
            line               = reader.readNext();
            locationIndexes[0] = Arrays.asList(line).indexOf("lat");
            locationIndexes[1] = Arrays.asList(line).indexOf("lon");

            while ((line = reader.readNext()) != null) {
                if (line[locationIndexes[0]].length() == 0 || line[locationIndexes[1]].length() == 0)
                    continue;

                this.totalKM += this.distanceFromPivot(new LocationRecord(Double.valueOf(line[locationIndexes[0]]), Double.valueOf(line[locationIndexes[1]])));
            }
        } catch (IOException e) { }
    }

    public double distanceFromPivot(LocationRecord point) {
        double earthRadius    = 6371e3;
        double lat1Radiant    = this.pivot.getLat() * Math.PI / 180;
        double lat2Radiant    = point.getLat() * Math.PI / 180;
        double latDiffRadiant = (point.getLat() - this.pivot.getLat()) * Math.PI / 180;
        double lonDiffRadiant = (point.getLon() - this.pivot.getLon()) * Math.PI / 180;

        double a = Math.sin(latDiffRadiant/2) * Math.sin(latDiffRadiant/2) +
                   Math.cos(lat1Radiant) * Math.cos(lat2Radiant)           *
                   Math.sin(lonDiffRadiant/2) * Math.sin(lonDiffRadiant/2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double distance = earthRadius * c; // in metri

        return distance / 1000;
    }

    public double calculateCostOf(String key) {
        if (this.costMap.keySet().contains(key)) {
            return this.costMap.get(key) * this.totalKM;
    
        System.out.println("Valore da calcolare non previsto nella mappa");
        return -1;
    }

    public void printCost(String key) {
        System.out.println(
            (this.costMap.keySet().contains(key)) ? (key + ": " + Double.valueOf((int) (this.calculateCostOf(key) * 100))/100 + " €") :
                                                    "Calcolo di cui si vuole stampare il risultato non è previsto nella mappa"
        );
    }

    public void printCost() {
        for (String key : this.costMap.keySet()) {
            this.printCost(key);
        }
    }
}
