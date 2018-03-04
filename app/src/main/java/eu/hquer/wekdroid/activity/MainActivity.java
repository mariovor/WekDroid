package eu.hquer.wekdroid.activity;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import eu.hquer.wekdroid.R;
import eu.hquer.wekdroid.enums.AuthenticationEnum;
import eu.hquer.wekdroid.enums.SharedPrefEnum;

public class MainActivity extends BaseAcitvity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // try to get userId and basePath from data storage
        SharedPreferences sharedPreferences = this.getSharedPreferences(SharedPrefEnum.BASE_PREF.getName(), Context.MODE_PRIVATE);
        userId = getWekanSharedPreferencesString(SharedPrefEnum.USER_ID.getName());
        basePath = getWekanSharedPreferencesString(SharedPrefEnum.BASE_URL.getName());

        // If userID or basePath is missing, we need to get the data from the user.
        // This is currently done via the AccountsActivity
        // if everything is there, we can create the WekanService
        if (userId == null || basePath == null) {
            startActivity(new Intent(MainActivity.this, AccountsActivity.class));
        } else {
            createService();
        }

        // If there is no token, we try first to get it from the AccountManager.
        // If that fails, we ned to get the credentials from the user and start
        // the AccountsActivity
        if (token == null) {
            AccountManager accountManager = AccountManager.get(this);
            Account[] accounts = accountManager.getAccountsByType(AuthenticationEnum.accountType.getName());
            // We need to match exactly one Account, if there is less, we need to create an Account.
            // If there is more, we have a problem, but this currently not handled (fingers crossed).
            if (accounts.length == 1) {
                accountManager.getAuthToken(accounts[0], AuthenticationEnum.authTokenType.getName(), null, this, new AccountManagerCallback<Bundle>() {
                    @Override
                    public void run(AccountManagerFuture<Bundle> accountManagerFuture) {
                        try {
                            token = (String) accountManagerFuture.getResult().get("authtoken");
                            // We have the token in the credentials store, off we go to the board list
                            // todo Validate token
                            if (token != null) {
                                Intent intent = new Intent(getApplicationContext(), ListBoardsActivity.class);
                                startActivity(intent);
                            // There is no token in the credential store, we need to get the credentials from the user
                            } else {
                                startActivity(new Intent(getApplicationContext(), AccountsActivity.class));
                            }
                        // Something went really wrong...
                        } catch (Exception e) {
                            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, null);
            // So there not exactly one account, user input required
            } else {
                startActivity(new Intent(this, AccountsActivity.class));
            }
        // No token, again we need user input
        } else {
            startActivity(new Intent(MainActivity.this, ListBoardsActivity.class));
        }
    }
}
