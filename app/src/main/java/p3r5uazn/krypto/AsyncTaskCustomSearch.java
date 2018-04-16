package p3r5uazn.krypto;

import android.app.Activity;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ArrayAdapter;
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
    KryptoDatabase favoritesDatabase;
    String keyWord;
    ArrayList<KryptoCurrency> data;

    public AsyncTaskCustomSearch(Context context, String keyWord)
    {
        this.context = context;
        this.keyWord = keyWord;
        favoritesDatabase = Room.databaseBuilder(context, KryptoDatabase.class,"Favorites").build();
    }

    public AsyncTaskCustomSearch(Context context, String keyWord, ArrayList<KryptoCurrency> data)
    {
        this.context = context;
        this.keyWord = keyWord;
        this.data = data;
        favoritesDatabase = Room.databaseBuilder(context, KryptoDatabase.class,"Favorites").build();
    }

    @Override
    protected void onPostExecute(ArrayList<KryptoCurrency> kryptoCurrencies)
    {
        super.onPostExecute(kryptoCurrencies);
        //Sort list
        Collections.sort(kryptoCurrencies);

        //get root view of context for updating values
        View rootView = ((Activity)context).getWindow().getDecorView().findViewById(android.R.id.content);

        //Update ListView based on the activity
        if(rootView.findViewById(R.id.currency_list) != null) // if called from MainActivity
        {
            ListView listView = rootView.findViewById(R.id.currency_list);
            AutoCompleteTextView searchBar = rootView.findViewById(R.id.search_bar);
            HomeScreenListAdapter homeScreenListAdapter = new HomeScreenListAdapter(context, kryptoCurrencies);
            listView.setAdapter(homeScreenListAdapter);
            searchBar.setText("");
            listView.invalidateViews();
        }
        else if(rootView.findViewById(R.id.favorites_list) !=null) // if called from SettingsPage
        {
            ListView listView = rootView.findViewById(R.id.favorites_list);
            AutoCompleteTextView searchBar = rootView.findViewById(R.id.settings_search_bar);
            SettingsScreenListAdapter settingsScreenListAdapter = new SettingsScreenListAdapter(context, kryptoCurrencies);
            listView.setAdapter(settingsScreenListAdapter);
            searchBar.setText("");
            listView.invalidateViews();

        }
        else if(rootView.findViewById(R.id.data_list) !=null)// if called from the SearchPage
        {
            ListView listView = rootView.findViewById(R.id.data_list);
            AutoCompleteTextView searchBar = rootView.findViewById(R.id.add_search_bar);
            SearchScreenListAdapter searchScreenListAdapter = new SearchScreenListAdapter(context, kryptoCurrencies);
            searchBar.setText("");
            listView.setAdapter(searchScreenListAdapter);
        }

    }

    @Override
    protected ArrayList<KryptoCurrency> doInBackground(Void... voids)
    {
        ArrayList<KryptoCurrency> list;
        if( data == null) {
            list = (ArrayList<KryptoCurrency>) favoritesDatabase.kryptoCurrencyDao().getAllKryptoCurrencies();
        }
        else
        {
            list = data;
        }
        ArrayList<KryptoCurrency> tempSearch = new ArrayList<>();
        for(int i =0; i < list.size(); i++)
        {
            if( list.get(i).toString().toLowerCase().contains(keyWord.toLowerCase()))
            {
                tempSearch.add(list.get(i));
            }
        }
        return tempSearch;
    }
}
