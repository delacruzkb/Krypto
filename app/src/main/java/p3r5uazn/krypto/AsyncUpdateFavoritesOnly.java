package p3r5uazn.krypto;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

public class AsyncUpdateFavoritesOnly extends AsyncTask<Void, Void, ArrayList<KryptoCurrency>>
{
    private Context context;
    private KryptoDatabase favoritesDatabase;

    public AsyncUpdateFavoritesOnly(Context context)
    {
        this.context = context;
        favoritesDatabase = Room.databaseBuilder(context, KryptoDatabase.class,"Favorites").build();
    }

    @Override
    protected ArrayList<KryptoCurrency> doInBackground(Void... voids)
    {
        ArrayList<KryptoCurrency> data = (ArrayList<KryptoCurrency>) favoritesDatabase.kryptoCurrencyDao().getAllKryptoCurrencies();
        BufferedReader in = null;
        StringBuffer buffer = null;
        JSONObject jsonObject = null;
        String jsonString;
        String crypto=null;
        KryptoCurrency krypto;
        ArrayList<KryptoCurrency> updatedList = new ArrayList<>();

       for(int i = 0; i< data.size(); i++)
       {
           crypto = data.get(i).getName().toLowerCase();
           try{
               URL url = new URL("https://api.coinmarketcap.com/v1/ticker/" + crypto +"/");
               in = new BufferedReader(new InputStreamReader(url.openStream()));
               buffer = new StringBuffer();
               int read;
               char[] chars = new char[1024];
               while ((read = in.read(chars)) != -1)
                   buffer.append(chars, 0, read);

           }catch(Exception e){
               e.printStackTrace();
           }finally {
               if(in != null) {
                   try {
                       in.close();
                   } catch (IOException e) {
                       e.printStackTrace();
                   }
               }

           }
           jsonString = buffer.toString();
           try {
               jsonObject = new JSONObject(jsonString);
           } catch (JSONException e) {
               e.printStackTrace();
           }
           krypto = processJSON(jsonObject);

           //if nothing is returned
           if(!krypto.getName().equalsIgnoreCase(new KryptoCurrency().getName()))
           {
               updatedList.add(krypto);
           }
       }
       return updatedList;
    }
    @Override
    protected void onPostExecute(ArrayList<KryptoCurrency> kryptoCurrencies)
    {
        super.onPostExecute(kryptoCurrencies);

        //Update all favorites in the database
        AsyncTaskInsertDatabase updateDatabaseTask = new AsyncTaskInsertDatabase(favoritesDatabase);
        updateDatabaseTask.execute(kryptoCurrencies);

        //Update the values wherever favorites is displayed
        AsyncTaskQueryFavorites refreshTask = new AsyncTaskQueryFavorites(context);
        refreshTask.execute();
    }
    private KryptoCurrency processJSON(JSONObject jsonObject)
    {
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
        return krypto;
    }
}
