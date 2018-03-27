package p3r5uazn.krypto;

import android.os.AsyncTask;

/**
 * Created by kenso on 3/27/2018.
 */

public class AsyncTaskDeleteFavorites extends AsyncTask<KryptoCurrency,Void,Void>
{
    FavoritesDatabase favoritesDatabase;

    public AsyncTaskDeleteFavorites(FavoritesDatabase fdb)
    {
        favoritesDatabase = fdb;
    }

    @Override
    protected void onPostExecute(Void blah)
    {
        //Kill this task so that it does not keep deleting things
        this.cancel(true);
    }
    @Override
    protected Void doInBackground(KryptoCurrency... kryptoCurrency)
    {
        if(kryptoCurrency[0] == null)
        {
            favoritesDatabase.kryptoCurrencyDao().deleteAllKryptoCurrencies();
        }
        return null;
    }
}
