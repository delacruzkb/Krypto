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
    FavoritesDatabase favoritesDatabase;
    Context context;
    public AsyncTaskQueryFavorites(FavoritesDatabase favoritesDatabase, Context context)
    {
        this.favoritesDatabase = favoritesDatabase;
        this.context = context;
    }

    @Override
    protected void onPostExecute(ArrayList<KryptoCurrency> favorites)
    {
        super.onPostExecute(favorites);
        Collections.sort(favorites);
        View rootView = ((Activity)context).getWindow().getDecorView().findViewById(android.R.id.content);
        if(rootView.findViewById(R.id.currency_list) != null)
        {
            ListView listView = rootView.findViewById(R.id.currency_list);
            HomeScreenListAdapter homeScreenListAdapter = new HomeScreenListAdapter(context, favorites);
            listView.setAdapter(homeScreenListAdapter);
            ArrayAdapter<KryptoCurrency> searchBarAdapter = new ArrayAdapter<>(context, android.R.layout.simple_dropdown_item_1line, favorites);
            AutoCompleteTextView searchBar = rootView.findViewById(R.id.search_bar);
            searchBar.setAdapter(searchBarAdapter);
        }
    }

    @Override
    protected ArrayList<KryptoCurrency> doInBackground(Void... voids)
    {
        return (ArrayList<KryptoCurrency>) favoritesDatabase.kryptoCurrencyDao().getAllKryptoCurrencies();
    }
}
