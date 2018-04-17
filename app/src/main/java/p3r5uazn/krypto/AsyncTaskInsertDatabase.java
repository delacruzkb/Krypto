package p3r5uazn.krypto;

import android.os.AsyncTask;

import java.util.ArrayList;

public class AsyncTaskInsertDatabase extends AsyncTask<ArrayList<KryptoCurrency>,Void,Void>
{
    KryptoDatabase database;

    public AsyncTaskInsertDatabase(KryptoDatabase database)
    {
        this.database = database;
    }

    @Override
    protected Void doInBackground(ArrayList<KryptoCurrency>... kryptoCurrency)
    {
        ArrayList<KryptoCurrency> data = kryptoCurrency[0];
        for(int i = 0; i < data.size();i++) {
            database.kryptoCurrencyDao().insertKryptoCurrency(data.get(i));
        }
        return null;
    }
}
