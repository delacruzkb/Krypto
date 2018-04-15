package p3r5uazn.krypto;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
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
        InputStreamReader isr = null;
        try{
            url = new URL("https://api.coinmarketcap.com/v1/ticker/");
            urlConnection = (HttpURLConnection) url.openConnection();
            isr = new InputStreamReader(urlConnection.getInputStream());
            BufferedReader in = new BufferedReader(isr);
            while((jsonStr = in.readLine()) != null){
                result += jsonStr;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            if(isr == null){
                try{
                    isr.close();
                }catch (IOException x){
                    x.printStackTrace();
                }
            }
            if(urlConnection == null){
                urlConnection.disconnect();
            }
        }
        return result;
    }

    //parses through string and put in json array.
    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        JSONArray returnedJsonString = null;
        ArrayList<KryptoCurrency> kryptos = null;
        try {
            returnedJsonString = new JSONArray(result) ;
            kryptos = new ArrayList<>();
            for(int i = 0; i < returnedJsonString.length(); ++i){
                KryptoCurrency krypto = new KryptoCurrency();
                JSONObject jsonObject = returnedJsonString.getJSONObject(i);
                krypto.setId(jsonObject.getString("id"));
                krypto.setName(jsonObject.getString("name"));
                krypto.setSymbol(jsonObject.getString("symbol"));
                krypto.setRank((Integer.parseInt(jsonObject.getString("rank"))));
                krypto.setPriceUSD(Double.parseDouble(jsonObject.getString("price_usd")));
                krypto.setPriceBTC(Double.parseDouble(jsonObject.getString("price_btc")));
                krypto.setVolume24(Double.parseDouble(jsonObject.getString("24_volume_usd")));
                krypto.setMarketCap(Double.parseDouble(jsonObject.getString("market_cap_usd")));
                krypto.setAvailableSupply(Double.parseDouble(jsonObject.getString("available_supply")));
                krypto.setTotalSupply(Double.parseDouble(jsonObject.getString("total_supply")));
                krypto.setPerChange1h(Double.parseDouble(jsonObject.getString("percent_change_1h")));
                krypto.setPerChange24h(Double.parseDouble(jsonObject.getString("percent_change_24h")));
                krypto.setPerChange7d(Double.parseDouble(jsonObject.getString("percent_change_7d")));
                krypto.setLastUpdated(Double.parseDouble(jsonObject.getString("last_updated")));
                kryptos.add(krypto);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("RESULT TEXT",result);
        System.out.println(result);
        //if (returnedJsonString.get("z")){




        /*
        ArrayList<KryptoCurrency> kryptos = new ArrayList<>();
        if(!result.equals("")){
            ArrayList<JSONObject> list = new ArrayList<>();
            try{
                JSONArray jsonArray = new JSONArray(result);
                for(int i = 0; i < jsonArray.length(); ++i){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    list.add(jsonObject);
                }
                for(int i = 0; i < list.size(); i++){
                    KryptoCurrency kryptoCurrency = new KryptoCurrency();
                    kryptoCurrency.setId(list.get(i).getString("id"));
                    kryptoCurrency.setName(list.get(i).getString("name"));
                }

            }catch (Exception e){
                e.printStackTrace();
            }
        }
        */
    }
}
