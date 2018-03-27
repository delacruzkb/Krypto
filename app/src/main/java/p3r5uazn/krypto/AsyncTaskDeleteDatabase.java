package p3r5uazn.krypto;

import android.content.Context;
import android.os.AsyncTask;

/**
 * Created by kenso on 3/27/2018.
 */

public class AsyncTaskDeleteDatabase extends AsyncTask<KryptoCurrency,Void,Void>
{
    KryptoDatabase kryptoDatabase;
    Context context;
    public AsyncTaskDeleteDatabase(KryptoDatabase fdb, Context context)
    {
        kryptoDatabase = fdb;
        this.context = context;
    }

    @Override
    protected void onPostExecute(Void blah)
    {
        super.onPostExecute(blah);

        //update views
        AsyncTaskQueryFavorites queryTask = new AsyncTaskQueryFavorites(kryptoDatabase,context);
        queryTask.execute();
    }
    @Override
    protected Void doInBackground(KryptoCurrency... kryptoCurrency)
    {
        if(kryptoCurrency[0] == null) // if null passed in
        {
            //Delete everything
            kryptoDatabase.kryptoCurrencyDao().deleteAllKryptoCurrencies();
        }
        else // if something else passed in
        {
            //Delete it from database
            kryptoDatabase.kryptoCurrencyDao().removeKryptoCurrency(kryptoCurrency[0]);
        }
        return null;
    }
}
