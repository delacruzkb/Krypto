package p3r5uazn.krypto;


import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


/**
 ===============
 = Get Request =
 ===============

 //Initialize HashMap
     HashMap<String, String> HEADERS = new HashMap<>();

 // Put your headers in the HashMap
     HEADERS.put("content-type", "application/x-www-form-urlencoded");
     HEADERS.put("authorization", "bearer ");
     String URL = "https://www.example.com/api";

 // Create request, update delegate, execute request
     _AsyncCall.GetRequest getRequest = new _AsyncCall.GetRequest(CONTEXT, URL, HEADERS);
     getRequest.delegate = this;
     getRequest.execute((Void) null);

 // Create @Override methods to get results after the Async Call has been completed
     @Override
     public void processFinish(String RESPONSE){
         try {
             JSONObject JSON_RESPONSE = new JSONObject(RESPONSE);
         }catch (JSONException e) {
             e.printStackTrace();
         }
     }


 */


public class AsyncTasks {

    public interface AsyncResponse {
        void processFinish(String output);
    }

    public static class GetRequest extends AsyncTask<Void, Void, String> {

        private String TAG = "AsyncTasks.GetRequest";
        private Map<String, String> HEADERS;
        private String URL, JSON_RESPONSE;
        private Context CONTEXT;
        public AsyncResponse delegate;

        public GetRequest(Context context, String url, Map<String, String> headers){
            this.HEADERS = headers;
            this.URL = url;
            this.CONTEXT = context;
        }

        @Override
        protected String doInBackground(Void... params) {
            String result = "";//Default Value
            try {
                result = Requests.getRequest(CONTEXT, URL, HEADERS);
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(final String result) {
            this.JSON_RESPONSE = result;
            delegate.processFinish(result);
            Log.d(TAG, "Json Response: "+JSON_RESPONSE);
        }

        @Override
        protected void onCancelled() {
        }
    }
}