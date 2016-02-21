package com.cleanwater.axp.cleanwaterphilly;

/**
 * Created by Dan Panfili on 2/20/16.
 *  Edited by Brendan Barnes
 */
import android.content.res.AssetManager;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

//dataOut array string: type, latitude, longitude, address
public class RainCheckParse {
    public static HashMap<String[], String> hashMap = new HashMap<String[], String>();
    private AssetManager m;

    public RainCheckParse(AssetManager manager) {
        m = manager;
    }

    public void run() throws IOException {
        InputStream is = m.open("RainCheck_Installed.csv");
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String line = null;
        while ((line = br.readLine()) != null) {
            String[] values = line.split(",");
            String[] l = {values[1], values[0]};
            hashMap.put(l, values[5]);
        }
    }

}
