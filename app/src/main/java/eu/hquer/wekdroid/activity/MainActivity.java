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
        SharedPreferences sharedPreferences = this.getSharedPreferences(SharedPrefEnum.BASE_PREF.getName(), Context.MODE_PRIVATE);
        userId = sharedPreferences.getString(SharedPrefEnum.USER_ID.getName(), null);
        basePath = sharedPreferences.getString(SharedPrefEnum.BASE_URL.getName(), null);

        if (userId == null || basePath == null) {
            startActivity(new Intent(MainActivity.this, AccountsActivity.class));
        } else {
            createService();
        }
        if (token == null) {
            AccountManager accountManager = AccountManager.get(this);
            Account[] accounts = accountManager.getAccountsByType(AuthenticationEnum.accountType.getName());
            if (accounts.length == 1) {
                accountManager.getAuthToken(accounts[0], AuthenticationEnum.authTokenType.getName(), null, this, new AccountManagerCallback<Bundle>() {
                    @Override
                    public void run(AccountManagerFuture<Bundle> accountManagerFuture) {
                        try {
                            token = (String) accountManagerFuture.getResult().get("authtoken");
                            if (token != null) {
                                Intent intent = new Intent(getApplicationContext(), ListBoardsActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(MainActivity.this, "No token found in account manager, please login", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), AccountsActivity.class));
                            }
                        } catch (Exception e) {
                            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, null);
                int d = 3;
            } else {
                startActivity(new Intent(this, AccountsActivity.class));
            }
        } else {
            startActivity(new Intent(MainActivity.this, ListBoardsActivity.class));
        }
    }

// Use accounts[0] (or whatever number of account) after checking that accounts.length &gt; 1}


//    private Call<Token> authenticate(WekanService wekanService) {
//        User user = new User();
//        user.setUsername("mariovor");
//        user.setPassword("Start123");
//        Call<Token> call = wekanService.authenticate(user);
//        return call;
//    }

//    public void authenticate() {
//        Call<Token> authenticateResponse = authenticate(wekanService);
//        authenticateResponse.enqueue(new Callback<Token>() {
//            @Override
//            public void onResponse(Response<Token> response) {
//                String tokenText = response.body().getToken();
//                userId = response.body().getId();
//                token = String.format("Bearer %s", tokenText);
//                Toast.makeText(MainActivity.this, token, Toast.LENGTH_SHORT).show();
//                if (token != null) {
//                    Intent intent = new Intent(getApplicationContext(), ListBoardsActivity.class);
//                    startActivity(intent);
//
//                }
//
//            }
//
//            @Override
//            public void onFailure(Throwable t) {
//                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//
//    }
}
