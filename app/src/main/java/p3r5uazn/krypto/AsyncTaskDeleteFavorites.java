package p3r5uazn.krypto;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;

/**
 * Created by kenso on 3/27/2018.
 */

public class AsyncTaskDeleteFavorites extends AsyncTask<KryptoCurrency,Void,Void>
{
    FavoritesDatabase favoritesDatabase;
    Context context;
    public AsyncTaskDeleteFavorites(FavoritesDatabase fdb, Context context)
    {
        favoritesDatabase = fdb;
        this.context = context;
    }

    @Override
    protected void onPostExecute(Void blah)
    {
        super.onPostExecute(blah);

        //update views
        AsyncTaskQueryFavorites queryTask = new AsyncTaskQueryFavorites(favoritesDatabase,context);
        queryTask.execute();
    }
    @Override
    protected Void doInBackground(KryptoCurrency... kryptoCurrency)
    {
        if(kryptoCurrency[0] == null) // if null passed in
        {
            //Delete everything
            favoritesDatabase.kryptoCurrencyDao().deleteAllKryptoCurrencies();
        }
        else // if something else passed in
        {
            //Delete it from database
            favoritesDatabase.kryptoCurrencyDao().removeKryptoCurrency(kryptoCurrency[0]);
        }
        return null;
    }
}
