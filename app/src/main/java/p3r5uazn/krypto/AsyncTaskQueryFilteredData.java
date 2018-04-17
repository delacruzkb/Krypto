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
 * Created by kenso on 3/27/2018.
 */

public class AsyncTaskQueryFilteredData extends AsyncTask<Void,Void,ArrayList<KryptoCurrency>>
{
    private KryptoDatabase favoritesDatabase;
    private KryptoDatabase kryptoDatabase;
    private Context context;

    public AsyncTaskQueryFilteredData( Context context)
    {
        this.context = context;
        favoritesDatabase = Room.databaseBuilder(context, KryptoDatabase.class,"Favorites").build();
        kryptoDatabase = Room.databaseBuilder(context, KryptoDatabase.class,"Kryptos").build();
    }

    @Override
    protected void onPostExecute(ArrayList<KryptoCurrency> kryptoCurrencies)
    {
        super.onPostExecute(kryptoCurrencies);
        Collections.sort(kryptoCurrencies);
        View rootView = ((Activity)context).getWindow().getDecorView().findViewById(android.R.id.content);
        ArrayAdapter<KryptoCurrency> searchBarAdapter = new ArrayAdapter<>(context, android.R.layout.simple_dropdown_item_1line, kryptoCurrencies);
        AutoCompleteTextView searchBar = rootView.findViewById(R.id.add_search_bar);
        searchBar.setAdapter(searchBarAdapter);

        SearchScreenListAdapter searchScreenListAdapter = new SearchScreenListAdapter(context, kryptoCurrencies);
        ListView listView = rootView.findViewById(R.id.data_list);
        listView.setAdapter(searchScreenListAdapter);
    }

    @Override
    protected ArrayList<KryptoCurrency> doInBackground(Void... voids)
    {
        //return list of non-favorites from database
        ArrayList<KryptoCurrency> returnValue= new ArrayList<>();
        ArrayList<KryptoCurrency> data = (ArrayList<KryptoCurrency>) kryptoDatabase.kryptoCurrencyDao().getAllKryptoCurrencies();
        ArrayList<KryptoCurrency> favorites = (ArrayList<KryptoCurrency>) favoritesDatabase.kryptoCurrencyDao().getAllKryptoCurrencies();
        for (int i =0; i < data.size(); i++)
        {
            if(!favorites.contains(data.get(i)))
            {
                returnValue.add(data.get(i));
            }
        }

        return returnValue;
    }
}
