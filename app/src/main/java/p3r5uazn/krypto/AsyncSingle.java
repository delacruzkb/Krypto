package p3r5uazn.krypto;

import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class AsyncSingle extends AsyncTask<Void, Void, String> {
    Context context;
    String crypto;

    public AsyncSingle(Context context, String crypto) {
        this.context = context;
        this.crypto = crypto.toLowerCase();

    }

    @Override
    protected String doInBackground(Void... voids) {
        URL url;
        HttpURLConnection urlConnection = null;
        String jsonStr = "";
        String result = "";
        InputStreamReader isr = null;
        try{
            url = new URL("https://api.coinmarketcap.com/v1/ticker/" + crypto +"/");
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
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(s);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        KryptoCurrency krypto = new KryptoCurrency();
        try {
            krypto.setId(jsonObject.getString("id"));
        } catch (Exception e) {
        }
        try {
            krypto.setName(jsonObject.getString("name"));
        } catch (Exception e) {
        }

        try {
            krypto.setSymbol(jsonObject.getString("symbol"));
        } catch (Exception e) {
        }
        try {
            krypto.setRank((Integer.parseInt(jsonObject.getString("rank"))));
        } catch (Exception e) {
        }
        try {
            krypto.setPriceUSD(Double.parseDouble(jsonObject.getString("price_usd")));
        } catch (Exception e) {
        }

        try {
            krypto.setPriceBTC(Double.parseDouble(jsonObject.getString("price_btc")));
        } catch (Exception e) {
            // Field did not exist
        }
        try {
            krypto.setVolume24(Double.parseDouble(jsonObject.getString("24_volume_usd")));
        } catch (Exception e) {
            // Field did not exist
        }
        try {
            krypto.setMarketCap(Double.parseDouble(jsonObject.getString("market_cap_usd")));
        } catch (Exception e) {
            // Field did not exist
        }
        try {
            krypto.setAvailableSupply(Double.parseDouble(jsonObject.getString("available_supply")));
        } catch (Exception e) {
            // Field did not exist
        }
        try {
            krypto.setTotalSupply(Double.parseDouble(jsonObject.getString("total_supply")));
        } catch (Exception e) {
            // Field did not exist
        }
        try {
            krypto.setMaxSupply(Double.parseDouble(jsonObject.getString("max_supply")));
        } catch (Exception e) {
            // Field did not exist
        }
        try {
            krypto.setPerChange1h(Double.parseDouble(jsonObject.getString("percent_change_1h")));
        } catch (Exception e) {
            // Field did not exist
        }
        try {
            krypto.setPerChange24h(Double.parseDouble(jsonObject.getString("percent_change_24h")));
        } catch (Exception e) {
            // Field did not exist
        }
        try {
            krypto.setPerChange7d(Double.parseDouble(jsonObject.getString("percent_change_7d")));
        } catch (Exception e) {
            // Field did not exist
        }
        try {
            krypto.setLastUpdated(Double.parseDouble(jsonObject.getString("last_updated")));
        } catch (Exception e) {
            // Field did not exist
        }

    }

}
