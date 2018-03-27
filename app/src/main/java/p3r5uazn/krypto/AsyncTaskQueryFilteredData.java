package p3r5uazn.krypto;

import android.content.Context;
import android.os.AsyncTask;

import java.util.ArrayList;

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
    protected void onPostExecute(ArrayList<KryptoCurrency> kryptoCurrencies) {
        super.onPostExecute(kryptoCurrencies);
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
