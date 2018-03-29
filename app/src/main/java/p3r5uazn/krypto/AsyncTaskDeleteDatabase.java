package p3r5uazn.krypto;

import android.os.AsyncTask;

/**
 * Created by kenso on 3/29/2018.
 */

public class AsyncTaskDeleteDatabase extends AsyncTask<KryptoCurrency,Void,Void>
{
    KryptoDatabase database;


    public AsyncTaskDeleteDatabase(KryptoDatabase database)
    {
        this.database = database;
    }

    @Override
    protected Void doInBackground(KryptoCurrency... kryptoCurrency)
    {
        database.kryptoCurrencyDao().deleteKryptoCurrency(kryptoCurrency[0]);
        return null;
    }
}
