/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package goeurotest;

import java.net.*;
import java.io.*;
import org.json.JSONArray;
import org.json.JSONObject;

public class Main {

    public static void main(String[] args) throws Exception {

        if (args.length == 0 || args[0].length() == 0) {
            System.out.println("Please insert the parameter...");
        } else {
            try{
            String jsonData = readUrl("http://api.goeuro.com/api/v2/position/suggest/en/" + args[0]);
            if (jsonData.length() != 0) {
                JSONArray jsonarray = new JSONArray(jsonData);
                File file = new File(args[0] + "_cities.csv");
                PrintWriter pw = new PrintWriter(file);
                StringBuilder sb = new StringBuilder();

                String id, name, type, latitude, longitude;

                JSONObject geoPosition;

                sb.append("_id,name,type,latitude,longitude");

                sb.append('\n');

                for (int i = 0; i < jsonarray.length(); i++) {

                    JSONObject jsonObject = jsonarray.getJSONObject(i);
                    id = jsonObject.getString("_id");
                    name = jsonObject.getString("name");
                    type = jsonObject.getString("type");
                    geoPosition = new JSONObject(jsonObject.getString("geo_position"));
                    latitude = geoPosition.getString("latitude");
                    longitude = geoPosition.getString("longitude");
                    sb.append(id + "," + name + "," + type + "," + latitude + "," + longitude);
                    sb.append('\n');
                }
                pw.write(sb.toString());
                pw.close();
                System.out.println("Done, check the O/P file in this path : " + file.getAbsolutePath());
            } else {
                System.out.println("there is no data in this path");
            }
        }catch(Exception exp){
                System.err.println("An Exception here in reading the json : " + exp.getMessage());
        }
        }
    }

    private static String readUrl(String urlString) throws Exception {
        BufferedReader reader = null;
        try {
            URL url = new URL(urlString);
            reader = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuffer buffer = new StringBuffer();
            int read;
            char[] chars = new char[1024];
            while ((read = reader.read(chars)) != -1) {
                buffer.append(chars, 0, read);
            }

            return buffer.toString();
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
    }
}
