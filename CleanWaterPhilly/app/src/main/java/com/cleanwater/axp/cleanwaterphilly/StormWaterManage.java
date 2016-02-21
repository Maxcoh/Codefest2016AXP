package com.cleanwater.axp.cleanwaterphilly;

/**
 * Created by brendanbarnes on 2/20/16.
 */
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

//dataOut array string: system, monthDayOfInstall, yearOfInstall, lifeCycleStatus, owner, subtype, facID, shapeArea, shapeLen
public class StormWaterManage {

    public StormWaterManage() {

    }

    public String[][] run() {
        String[][] dataOut = new String[1241][10];
        String csvFile = "assets/stormWaterManage.csv";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";

        try {
            br = new BufferedReader(new FileReader(csvFile));
            int i=0;
            while ((line = br.readLine()) != null) {
                // use comma as separator
                String[] data = line.split(cvsSplitBy);

                //if(data.length<7){
                //System.out.println(data[6] + " at location (" + data[5] + "), coord x=" + data[0] + " y=" + data[1]);
                String[] toDataOut = {data[1],data[2],data[3],data[4],data[5],data[6],data[8],data[10],data[11]};
                //System.out.println(toDataOut[0]);
                for(int j=0; j<9; j++){
                    dataOut[i][j] = toDataOut[j];
                }
                i=i+1;
                //}
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
        for(int i=0; i<dataOut.length; i++){
            System.out.println(dataOut[i][0] + " " + dataOut[i][1] + " " + dataOut[i][2] + " " + dataOut[i][3] + " " + dataOut[i][4] + " " + dataOut[i][5] + " " + dataOut[i][6] + " " + dataOut[i][7] + " " + dataOut[i][8]);
        }
        System.out.println("Done");
        return dataOut;
    }

}
