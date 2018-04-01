package p3r5uazn.krypto;


import android.content.Context;
import android.support.annotation.IntDef;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Map;

import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static okhttp3.Headers.of;


class Requests {
    @SuppressWarnings({"FieldCanBeLocal"})
    private static String GET_RESPONSE_CODE = "0";
    final private static String TAG_GET = "Requests.Get";

    static String getRequest(Context CONTEXT, String URL, Map<String, String> HEADERS) throws java.io.IOException, JSONException {

        if(!Tools.isNetworkAvailable(CONTEXT)){
            return "{\"Err\":\"NetworkConnection\"}";
        }

        OkHttpClient client = new OkHttpClient();
        Headers headers = of(HEADERS);
        Request request = new Request.Builder()
                .url(URL)
                .get()
                .headers(headers)
                .build();
        Response response = client.newCall(request).execute();
        Log.d(TAG_GET, " RESPONSE CODE : " + response.code());
        Log.d(TAG_GET, " RESPONSE MESSAGE : " + response.message());

        GET_RESPONSE_CODE = ""+response.code();

        return response.body().string();
    }

    static String getResponseCode(){
        return GET_RESPONSE_CODE;
    }
}
