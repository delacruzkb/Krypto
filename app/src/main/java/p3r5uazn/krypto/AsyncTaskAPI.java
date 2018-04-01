package p3r5uazn.krypto;

import android.content.Context;
import android.os.AsyncTask;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by smainar on 3/17/2018.
 */

public class AsyncTaskAPI extends AsyncTask<Void, Void, Void> {


    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }

    @Override
    protected ArrayList<KryptoCurrency> doInBackground(Void... voids) {

        ArrayList<KryptoCurrency> kryptos = (ArrayList<KryptoCurrency>) database.kryptoCurrencyDao().getAllKryptoCurrencies();
        return kryptos;
    }

}
