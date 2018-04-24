package p3r5uazn.krypto;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class AsyncUpdateFavoritesOnly extends AsyncTask<Void, Void, ArrayList<KryptoCurrency>>
{
    private Context context;
    private KryptoDatabase favoritesDatabase;
    boolean showToast = false;

    public AsyncUpdateFavoritesOnly(Context context,boolean showToast)
    {
        this.context = context;
        favoritesDatabase = Room.databaseBuilder(context, KryptoDatabase.class,"Favorites").build();
        this.showToast = showToast;
    }

    @Override
    protected ArrayList<KryptoCurrency> doInBackground(Void... voids)
    {
        ArrayList<KryptoCurrency> data = (ArrayList<KryptoCurrency>) favoritesDatabase.kryptoCurrencyDao().getAllKryptoCurrencies();
        KryptoCurrency krypto;
        ArrayList<KryptoCurrency> updatedList = new ArrayList<>();
        URL url;
        HttpURLConnection urlConnection = null;
        String jsonStr;
        String result="";
        String crypto;
        InputStreamReader isr = null;
        JSONObject jsonObject=null;
        JSONArray returnedJsonString;
        for (int i = 0; i<data.size();i++)
        {
            result ="";
            isr=null;
            jsonObject=null;
            returnedJsonString= null;
            crypto = data.get(i).getId();
            try {
                url = new URL("https://api.coinmarketcap.com/v1/ticker/" + crypto +"/");
                urlConnection = (HttpURLConnection) url.openConnection();
                isr = new InputStreamReader(urlConnection.getInputStream());
                BufferedReader in = new BufferedReader(isr);
                while((jsonStr = in.readLine()) != null) {
                    result += jsonStr;
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            finally {
                if (isr == null) {
                    try {
                        isr.close();
                    }
                    catch (IOException x) {
                        x.printStackTrace();
                    }
                }
                if (urlConnection == null) {
                    urlConnection.disconnect();
                }
            }
            try {
                returnedJsonString = new JSONArray(result);
                jsonObject = returnedJsonString.getJSONObject(0);
            }
            catch (JSONException e) {
                e.printStackTrace();
            }
            krypto = processJSON(jsonObject);

            //don't add if nothing is returned
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

        if(showToast){
            Toast.makeText(context, context.getString(R.string.update_complete_message), Toast.LENGTH_SHORT).show();
        }
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
