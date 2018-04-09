package p3r5uazn.krypto;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class AsyncAPI extends AsyncTask<Void, Void, String> {


    //returns a string of the json response
    @Override
    protected String doInBackground(Void...voids) {
        URL url;
        HttpURLConnection urlConnection = null;
        String jsonStr = null;
        String result = "";
        try{
            url = new URL("https://api.coinmarketcap.com/v1/ticker/");
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStreamReader isr = new InputStreamReader(urlConnection.getInputStream());
            BufferedReader in = new BufferedReader(isr);
            jsonStr = "";
            while((jsonStr = in.readLine()) != null){
                result += jsonStr;
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(urlConnection != null ){
                urlConnection.disconnect();
            }
        }
        return result;
    }

    //parses through string and put in json array.
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if(!s.equals("")){
            ArrayList<String> list = new ArrayList<>();
            try{
                JSONArray jsonArray = new JSONArray(s);
                for(int i = 0; i < jsonArray.length(); ++i){
                    list.add(jsonArray.get(i).toString());
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
