package eu.hquer.wekdroid.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import eu.hquer.wekdroid.R;
import eu.hquer.wekdroid.WekanService;
import eu.hquer.wekdroid.model.Board;
import eu.hquer.wekdroid.model.Token;
import eu.hquer.wekdroid.model.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.GsonConverterFactory;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    Retrofit retrofit;
    WekanService wekanService;
    final private String basePath = "http://192.168.1.2";
    static String userId = null;
    static String token = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createService();

    }

    public void getBoards(View view) {
        // Get boards
        Call<List<Board>> boardsCall = wekanService.getBoards(userId, token);

        boardsCall.enqueue(new Callback<List<Board>>() {
            @Override
            public void onResponse(Response<List<Board>> response) {
                Board board = response.body().get(0);
                Toast.makeText(MainActivity.this, board.getTitle(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                int i = 42;
            }
        });
    }

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

    private Call<Token> authenticate(WekanService wekanService) {
        User user = new User();
        user.setUsername("mariovor");
        user.setPassword("Start123");
        Call<Token> call = wekanService.authenticate(user);
        return call;
    }

    public void authenticate(View view){
        Call<Token> authenticateResponse = authenticate(wekanService);
        authenticateResponse.enqueue(new Callback<Token>() {
            @Override
            public void onResponse(Response<Token> response) {
                String tokenText = response.body().getToken();
                userId = response.body().getId();
                token = String.format("Bearer %s", tokenText);
                Toast.makeText(MainActivity.this, token, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(MainActivity.this, "FAIL", Toast.LENGTH_SHORT).show();
                int i = 42;
            }
        });

    }
}
