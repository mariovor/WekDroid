package eu.hquer.wekdroid;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import eu.hquer.wekdroid.authenticator.WekanAccountAuthenticator;


/**
 * Created by mariovor on 03.03.18.
 * Inspired by https://www.pilanites.com/android-account-manager/
 */

public class AuthenticationService extends Service {
    // Instance field that stores the authenticator object
    private WekanAccountAuthenticator mAuthenticator;

    @Override
    public void onCreate() {
        // Create a new authenticator object
        mAuthenticator = new WekanAccountAuthenticator(this);
    }

    /*
     * When the system binds to this Service to make the RPC call
     * return the authenticator's IBinder.
     */
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mAuthenticator.getIBinder();
    }
}
