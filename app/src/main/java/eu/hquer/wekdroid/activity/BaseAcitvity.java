package eu.hquer.wekdroid.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;

import eu.hquer.wekdroid.WekanService;
import eu.hquer.wekdroid.enums.SharedPrefEnum;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;

/**
 * Created by mariovor on 25.02.18.
 */

public class BaseAcitvity extends AppCompatActivity {
    static Retrofit retrofit;
    static WekanService wekanService;
    static String basePath; // = "http://192.168.1.2";
    static String userId = null;
    static String token = null;

    // This method create an instance of Retrofit
    // set the base url
    public void createService() {

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(basePath)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        wekanService = retrofit.create(WekanService.class);

    }

    protected SharedPreferences getWekanSharedPreferences(){
        return getSharedPreferences(SharedPrefEnum.BASE_PREF.getName(), Context.MODE_PRIVATE);
    }

    protected String getWekanSharedPreferencesString(String key){
        return getWekanSharedPreferences().getString(key, null);
    }

    protected void setWekanSharedPreferencesString(String key, String value){
        SharedPreferences.Editor edit = getWekanSharedPreferences().edit();
        edit.putString(key, value);
        edit.apply();
    }
}
