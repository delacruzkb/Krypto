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
 * Created by kenso on 3/27/2018.
 */

public class AsyncTaskQueryFilteredData extends AsyncTask<Void,Void,ArrayList<KryptoCurrency>>
{
    private KryptoDatabase dataDatabase;
    private KryptoDatabase favoritesDatabase;
    private Context context;

    public AsyncTaskQueryFilteredData(KryptoDatabase dataDatabase, KryptoDatabase favoritesDatabase,Context context) {
        this.dataDatabase = dataDatabase;
        this.favoritesDatabase = favoritesDatabase;
        this.context = context;
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
        ArrayList<KryptoCurrency> filteredList = new ArrayList<>();
        ArrayList<KryptoCurrency> sourceList = (ArrayList<KryptoCurrency>) dataDatabase.kryptoCurrencyDao().getAllKryptoCurrencies();
        ArrayList<KryptoCurrency> filterOutList = (ArrayList<KryptoCurrency>) favoritesDatabase.kryptoCurrencyDao().getAllKryptoCurrencies();

        for(int i = 0; i< sourceList.size(); i++)
        {
            if(!filterOutList.contains(sourceList.get(i)))
            {
                filteredList.add(sourceList.get(i));
            }
        }

        return filteredList;
    }
}
