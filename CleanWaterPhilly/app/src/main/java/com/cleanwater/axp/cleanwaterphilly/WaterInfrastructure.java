package com.cleanwater.axp.cleanwaterphilly;

/**
 * Created by Nick Pingree on 2/20/16.
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class WaterInfrastructure {

    public WaterInfrastructure() {

    }

    public static String[][] getWaterInfrastructureCSV() throws IOException{
        /**
         * Rain Barrel Columns:
         * 0 = long
         * 1 = lat
         * 2 = ObjectID
         * 3 = Name
         * 4 = primary_feature
         * 5 = longitude
         * 6 = latitude
         */
        // Generate array sized the same as csv file
        String[][] WaterInfrastructure = new String[156][7];
        // I for columns, row for rows
        int i=0;
        int row=0;
        BufferedReader br = new BufferedReader(new FileReader("assets/Green_StWater_Infrastructure.csv"));
        String line = null;
        while ((line = br.readLine()) != null) {
            String[] values = line.split(",");
            for (String str : values) {
                //System.out.println(str);
                // as i goes up put data into cells. Reset i to 0 on final case and incremement the row.
                switch (i) {
                    case 0: WaterInfrastructure[row][i] = str;
                        i++;
                        break;
                    case 1: WaterInfrastructure[row][i] = str;
                        i++;
                        break;
                    case 2: WaterInfrastructure[row][i] = str;
                        i++;
                        break;
                    case 3: WaterInfrastructure[row][i] = str;
                        i++;
                        break;
                    case 4: WaterInfrastructure[row][i] = str;
                        i++;
                        break;
                    case 5: WaterInfrastructure[row][i] = str;
                        i++;
                        break;
                    case 6: WaterInfrastructure[row][i] = str;
                        i=0;
                        row++;
                        break;
                }
            }

        }
        br.close();
        return WaterInfrastructure;
    }
    public static String[][] getWaterInfrastructureCoordinates(String[][] WaterInfrastructureCSV){
        String[][] WaterInfrastructureCoordinates = new String[156][2];
        int i=0;
        for (int row = 0; row < WaterInfrastructureCSV.length; row++) {
            for (int column = 5; column < WaterInfrastructureCSV[row].length; column++) {
                WaterInfrastructureCoordinates[row][i] = WaterInfrastructureCSV[row][column];
                i++;
                if(i == 2){
                    i=0;
                }
            }}
        return WaterInfrastructureCoordinates;
    }

}

