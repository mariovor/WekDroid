package eu.hquer.wekdroid.activity;

import android.accounts.Account;
import android.accounts.AccountAuthenticatorResponse;
import android.accounts.AccountManager;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import eu.hquer.wekdroid.R;
import eu.hquer.wekdroid.WekanService;
import eu.hquer.wekdroid.enums.AuthenticationEnum;
import eu.hquer.wekdroid.model.Token;
import eu.hquer.wekdroid.model.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends BaseAcitvity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createService();
//        authenticate();
        if (token == null) {
            AccountManager accountManager = AccountManager.get(this);
            Account[] accounts = accountManager.getAccountsByType(AuthenticationEnum.accountType.getName());
            if (accounts.length == 1) {
                accountManager.getAuthToken(new AccountAuthenticatorResponse(), accounts[0],AuthenticationEnum.authTokenType,this, new Bundle());
            } else {
                startActivity(new Intent(this, AccountsActivity.class));
            }
        }
    }

// Use accounts[0] (or whatever number of account) after checking that accounts.length &gt; 1}


    private Call<Token> authenticate(WekanService wekanService) {
        User user = new User();
        user.setUsername("mariovor");
        user.setPassword("Start123");
        Call<Token> call = wekanService.authenticate(user);
        return call;
    }

    public void authenticate() {
        Call<Token> authenticateResponse = authenticate(wekanService);
        authenticateResponse.enqueue(new Callback<Token>() {
            @Override
            public void onResponse(Response<Token> response) {
                String tokenText = response.body().getToken();
                userId = response.body().getId();
                token = String.format("Bearer %s", tokenText);
                Toast.makeText(MainActivity.this, token, Toast.LENGTH_SHORT).show();
                if (token != null) {
                    Intent intent = new Intent(getApplicationContext(), ListBoardsActivity.class);
                    startActivity(intent);

                }

            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}
