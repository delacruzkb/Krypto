package p3r5uazn.krypto;

import android.content.Context;
import android.os.AsyncTask;

import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by smainar on 3/17/2018.
 */

public class AsyncTaskAPI extends AsyncTask<Void, Void, Void> {

    private KryptoDatabase database;
    private Context context;
    private URL url;
    public AsyncTaskAPI(KryptoDatabase database, Context context){
        this.database = database;
        this.context = context;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            url = new URL("https://api.coinmarketcap.com/v1/ticker/");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
