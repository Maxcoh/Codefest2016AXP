package com.cleanwater.axp.cleanwaterphilly;

/**
 * Created by Nick Pingree on 2/20/16.
 */
import android.content.res.AssetManager;
import android.graphics.Point;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

public class RainBarrel {
    public static HashMap<LatLong, String> hashMap = new HashMap<LatLong, String>();

    public RainBarrel() {

    }

    public static String[][] getBarrelCsv(AssetManager assetManager) throws IOException{
        /**
         * Rain Barrel Columns:
         * 0 = X
         * 1 = Y
         * 2 = ObjectID
         * 3 = ID
         * 4 = Address
         * 5 = Zip
         * 6 = Lat
         * 7 = Long
         */
        // Generate array sized the same as csv file
        String[][] RainBarrel = new String[100][8];
        // I for columns, row for rows
        int i=0;
        int row=0;

        InputStream is = assetManager.open("RainBarrel_Installed.csv");
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String line = null;
        while ((line = br.readLine()) != null) {
            String[] values = line.split(",");
            for (String str : values) {
                //System.out.println(str);
                // as i goes up put data into cells. Reset i to 0 on final case and incremement the row.
                switch (i) {
                    case 0: RainBarrel[row][i] = str;
                        i++;
                        break;
                    case 1: RainBarrel[row][i] = str;
                        i++;
                        break;
                    case 2: RainBarrel[row][i] = str;
                        i++;
                        break;
                    case 3: RainBarrel[row][i] = str;
                        i++;
                        break;
                    case 4: RainBarrel[row][i] = str;
                        i++;
                        break;
                    case 5: RainBarrel[row][i] = str;
                        i++;
                        break;
                    case 6: RainBarrel[row][i] = str;
                        i++;
                        break;
                    case 7: RainBarrel[row][i] = str;
                        i=0;
                        row++;
                        break;
                }
            }

        }
        br.close();
        return RainBarrel;
    }
    public static String[][] getBarrelCoordinates(String[][] RainBarrel){
        String[][] RainBarrelCoordinates = new String[100][2];
        int i=0;
        for (int row = 0; row < RainBarrel.length; row++) {
            double lat =0;
            for (int column = 6; column < RainBarrel[row].length; column++) {
                RainBarrelCoordinates[row][i] = RainBarrel[row][column];
                if(column == 6) {
                    lat = Double.parseDouble(RainBarrel[row][column]);
                } else {
                    LatLong l = new LatLong(lat, Double.parseDouble(RainBarrel[row][column]));
                    hashMap.put(l, RainBarrel[row][4]);
                    lat = 0;
                }
                i++;
                if(i == 2){
                    i=0;
                }
            }}
        return RainBarrelCoordinates;

    }


}

