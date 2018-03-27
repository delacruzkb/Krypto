package p3r5uazn.krypto;

import android.os.AsyncTask;

public class AsyncTaskInsertDatabase extends AsyncTask<KryptoCurrency,Void,Void>
{
    KryptoDatabase favoritesDatabase;

    public AsyncTaskInsertDatabase(KryptoDatabase fdb)
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
