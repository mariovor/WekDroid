package eu.hquer.wekdroid.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;


import eu.hquer.wekdroid.R;
import eu.hquer.wekdroid.WekanService;
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
        authenticate();
    }



    private Call<Token> authenticate(WekanService wekanService) {
        User user = new User();
        user.setUsername("mariovor");
        user.setPassword("Start123");
        Call<Token> call = wekanService.authenticate(user);
        return call;
    }

    public void authenticate(){
        Call<Token> authenticateResponse = authenticate(wekanService);
        authenticateResponse.enqueue(new Callback<Token>() {
            @Override
            public void onResponse(Response<Token> response) {
                String tokenText = response.body().getToken();
                userId = response.body().getId();
                token = String.format("Bearer %s", tokenText);
                Toast.makeText(MainActivity.this, token, Toast.LENGTH_SHORT).show();
                if (token != null){
                    Intent intent = new Intent(getApplicationContext(), ListBoardsActivity.class);
                    startActivity(intent);

                }

            }

            @Override
            public void onFailure(Throwable t) {
                TextView textView = findViewById(R.id.textView);
                textView.setText(t.getMessage());
            }
        });

    }
}
