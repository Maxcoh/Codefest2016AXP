package com.cleanwater.axp.cleanwaterphilly;

/**
 * Created by Nick Pingree on 2/20/16.
 *  Edited by Brendan Barnes
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
    private AssetManager m;

    public RainBarrel(AssetManager assetManager) {
        m = assetManager;
    }

    public void getBarrelCsv() throws IOException{
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
        // I for columns, row for rows

        InputStream is = m.open("RainBarrel_Installed.csv");
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String line = null;
        while ((line = br.readLine()) != null) {
            String[] values = line.split(",");
            LatLong l = new LatLong(Double.parseDouble(values[6]), Double.parseDouble(values[7]));
            hashMap.put(l, values[4]);
        }

    }


}

