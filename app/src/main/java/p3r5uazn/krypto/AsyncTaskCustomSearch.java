package p3r5uazn.krypto;

import android.app.Activity;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by kenso on 4/15/2018.
 */

public class AsyncTaskCustomSearch extends AsyncTask<Void,Void,ArrayList<KryptoCurrency>>
{
    Context context;
    String keyWord;
    View rootView;

    public AsyncTaskCustomSearch(Context context, String keyWord)
    {
        this.context = context;
        this.keyWord = keyWord;
        rootView = ((Activity)context).getWindow().getDecorView().findViewById(android.R.id.content);
    }


    @Override
    protected void onPostExecute(ArrayList<KryptoCurrency> kryptoCurrencies)
    {
        super.onPostExecute(kryptoCurrencies);
        //Sort list
        Collections.sort(kryptoCurrencies);

        if(!keyWord.equalsIgnoreCase(""))
        {
            //Update ListView based on the activity
            if(rootView.findViewById(R.id.currency_list) != null) // if called from MainActivity
            {
                ListView listView = rootView.findViewById(R.id.currency_list);
                AutoCompleteTextView searchBar = rootView.findViewById(R.id.search_bar);
                HomeScreenListAdapter homeScreenListAdapter = new HomeScreenListAdapter(context, kryptoCurrencies);
                listView.setAdapter(homeScreenListAdapter);
                listView.invalidateViews();
                searchBar.setText("");
            }
            else if(rootView.findViewById(R.id.favorites_list) !=null) // if called from SettingsPage
            {
                ListView listView = rootView.findViewById(R.id.favorites_list);
                AutoCompleteTextView searchBar = rootView.findViewById(R.id.settings_search_bar);
                SettingsScreenListAdapter settingsScreenListAdapter = new SettingsScreenListAdapter(context, kryptoCurrencies);
                listView.setAdapter(settingsScreenListAdapter);
                listView.invalidateViews();
                searchBar.setText("");
            }
            else if(rootView.findViewById(R.id.data_list) !=null)// if called from the SearchPage
            {
                ListView listView = rootView.findViewById(R.id.data_list);
                AutoCompleteTextView searchBar = rootView.findViewById(R.id.add_search_bar);
                SearchScreenListAdapter searchScreenListAdapter = new SearchScreenListAdapter(context, kryptoCurrencies);
                listView.setAdapter(searchScreenListAdapter);
                listView.invalidateViews();
                searchBar.setText("");
            }
        }
        else
        {
            if(rootView.findViewById(R.id.data_list) !=null)// if called from the SearchPage
            {
                AsyncTaskQueryFilteredData refreshTask = new AsyncTaskQueryFilteredData(context);
                refreshTask.execute();
            }
            else
            {
                AsyncTaskQueryFavorites refreshTas = new AsyncTaskQueryFavorites(context);
                refreshTas.execute();
            }

        }

    }

    @Override
    protected ArrayList<KryptoCurrency> doInBackground(Void... voids)
    {

        KryptoDatabase database = Room.databaseBuilder(context, KryptoDatabase.class,"Kryptos").build();
        KryptoDatabase favoritesDatabase = Room.databaseBuilder(context, KryptoDatabase.class,"Favorites").build();
        ArrayList<KryptoCurrency> data = (ArrayList<KryptoCurrency>) database.kryptoCurrencyDao().getAllKryptoCurrencies();
        ArrayList<KryptoCurrency> favorites = (ArrayList<KryptoCurrency>) favoritesDatabase.kryptoCurrencyDao().getAllKryptoCurrencies();
        ArrayList<KryptoCurrency> tempSearch = new ArrayList<>();
        if(rootView.findViewById(R.id.data_list) !=null)// if called from the SearchPage
        {
            for (int i = 0; i < data.size(); i++) {
                if (data.get(i).toString().toLowerCase().contains(keyWord.toLowerCase())) {
                    tempSearch.add(data.get(i));
                }
            }
        }
        else {
            for (int i = 0; i < favorites.size(); i++) {
                if (favorites.get(i).toString().toLowerCase().contains(keyWord.toLowerCase())) {
                    tempSearch.add(favorites.get(i));
                }
            }
        }
        return tempSearch;
    }
}
