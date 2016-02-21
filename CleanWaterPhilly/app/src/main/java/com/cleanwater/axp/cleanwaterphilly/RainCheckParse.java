package com.cleanwater.axp.cleanwaterphilly;

/**
 * Created by brendanbarnes on 2/20/16.
 */
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

//dataOut array string: type, latitude, longitude, address
public class RainCheckParse {

    public RainCheckParse() {

    }

    public String[][] run() {
        String[][] dataOut = new String[646][6];
        String csvFile = "assets/RainCheck_Installed.csv";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";

        try {
            br = new BufferedReader(new FileReader(csvFile));
            int i=0;
            while ((line = br.readLine()) != null) {
                // use comma as separator
                String[] data = line.split(cvsSplitBy);

                if(data.length>6){
                    //System.out.println(data[6] + " at location (" + data[5] + "), coord x=" + data[0] + " y=" + data[1]);
                    String[] toDataOut = {data[6],data[1],data[0],data[5]};
                    //System.out.println(toDataOut[0]);
                    for(int j=0; j<4; j++){
                        dataOut[i][j] = toDataOut[j];
                    }
                    i=i+1;
                }
                if(data[0]==null){break;}
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
	/*for(int i=0; i<dataOut.length; i++){
		System.out.println(dataOut[i][0] + " " + dataOut[i][1] + " " + dataOut[i][2] + " " + dataOut[i][3]);
	}*/
        return dataOut;
    }

}
