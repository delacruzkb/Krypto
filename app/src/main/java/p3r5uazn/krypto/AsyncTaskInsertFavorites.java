package p3r5uazn.krypto;

import android.os.AsyncTask;

public class AsyncTaskInsertFavorites extends AsyncTask<KryptoCurrency,Void,Void>
{
    FavoritesDatabase favoritesDatabase;

    public  AsyncTaskInsertFavorites(FavoritesDatabase fdb)
    {
        favoritesDatabase = fdb;
    }

    @Override
    protected Void doInBackground(KryptoCurrency... kryptoCurrency)
    {
        favoritesDatabase.kryptoCurrencyDao().insertKryptoCurrency(kryptoCurrency[0]);
        return null;
    }
}
