package p3r5uazn.krypto;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by kenso on 3/26/2018.
 */

public class AsyncTaskQueryFavorites extends AsyncTask<Void,Void,ArrayList<KryptoCurrency>>
{
    KryptoDatabase favoritesDatabase;
    Context context;
    public AsyncTaskQueryFavorites(KryptoDatabase favoritesDatabase, Context context)
    {
        this.favoritesDatabase = favoritesDatabase;
        this.context = context;
    }

    @Override
    protected void onPostExecute(ArrayList<KryptoCurrency> favorites)
    {
        super.onPostExecute(favorites);

        //Sort list
        Collections.sort(favorites);

        //get root view of context for updating values
        View rootView = ((Activity)context).getWindow().getDecorView().findViewById(android.R.id.content);

        if(rootView.findViewById(R.id.currency_list) != null) // if called from MainActivity
        {
            //Update ListView
            ListView listView = rootView.findViewById(R.id.currency_list);
            HomeScreenListAdapter homeScreenListAdapter = new HomeScreenListAdapter(context, favorites);
            listView.setAdapter(homeScreenListAdapter);

            //Update Searchbar
            ArrayAdapter<KryptoCurrency> searchBarAdapter = new ArrayAdapter<>(context, android.R.layout.simple_dropdown_item_1line, favorites);
            AutoCompleteTextView searchBar = rootView.findViewById(R.id.search_bar);
            searchBar.setAdapter(searchBarAdapter);
        }
        else if(rootView.findViewById(R.id.favorites_list) !=null) // if called from SettingsPage
        {
            //Update ListView
            ListView listView = rootView.findViewById(R.id.favorites_list);
            SettingsScreenListAdapter settingsScreenListAdapter = new SettingsScreenListAdapter(context, favorites);
            listView.setAdapter(settingsScreenListAdapter);

            //Update Searchbar
            ArrayAdapter<KryptoCurrency> searchBarAdapter = new ArrayAdapter<>(context, android.R.layout.simple_dropdown_item_1line, favorites);
            AutoCompleteTextView searchBar = rootView.findViewById(R.id.settings_search_bar);
            searchBar.setAdapter(searchBarAdapter);
        }
    }

    @Override
    protected ArrayList<KryptoCurrency> doInBackground(Void... voids)
    {
        //return updated favorites list from database
        return (ArrayList<KryptoCurrency>) favoritesDatabase.kryptoCurrencyDao().getAllKryptoCurrencies();
    }
}
