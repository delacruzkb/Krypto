package p3r5uazn.krypto;


import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import static android.content.Context.CONNECTIVITY_SERVICE;

public class Tools {

    //some tools for you guys to use if you want.

    @SuppressWarnings("ConstantConditions")
    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

    public static void lockOrientationLandscape(Activity activity) {
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

    public static void lockOrientationPortrait(Activity activity) {
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    public static void unlockOrientation(Activity activity) {
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

    }

    public static Boolean isNetworkAvailable(Context CONTEXT) {
        ConnectivityManager connectivityManager = (ConnectivityManager) CONTEXT.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}
