package tools;

import android.content.SharedPreferences;

public class AppManager {
    private static SharedPreferences preferences;
//
//    private static RequestQueue requestQueue;
//
//    private static Gson gson;
//
//    private static ImageLoader imageLoader;

    public static SharedPreferences getPreferences() {
        return preferences;
    }

    public static void setPreferences(SharedPreferences preferences) {
        AppManager.preferences = preferences;
    }

//    public static RequestQueue getRequestQueue() {
//        return requestQueue;
//    }
//
//    public static void setRequestQueue(RequestQueue requestQueue) {
//        AppManager.requestQueue = requestQueue;
//    }
//
//    public static Gson getGson() {
//        return gson;
//    }
//
//    public static void setGson(Gson gson) {
//        AppManager.gson = gson;
//    }


    public static String getPreferenceBy(SharedPreferences preference, String name) {
        return preference.getString(name, "");
    }

    public static void setPreferenceTo(SharedPreferences preference, String name, String value) {
        SharedPreferences.Editor editor = preference.edit();
        editor.putString(name, value);
        editor.commit();
    }


//    public static ImageLoader getImageLoader() {
//        return imageLoader;
//    }
//
//    public static void setImageLoader(ImageLoader imageLoader) {
//        AppManager.imageLoader = imageLoader;
//    }

}
