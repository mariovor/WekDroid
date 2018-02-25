package eu.hquer.wekdroid.activity;

import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

import eu.hquer.wekdroid.R;
import eu.hquer.wekdroid.model.Board;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListBoardsActivity extends BaseAcitvity {
    List<Board> boardList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_boards);
        retrieveBoards();
    }

    public void retrieveBoards() {
        // Get boards
        Call<List<Board>> boardsCall = wekanService.getBoards(userId, token);
        boardsCall.enqueue(new Callback<List<Board>>() {
            @Override
            public void onResponse(Response<List<Board>> response) {
                boardList = response.body();
                TextView textView = findViewById(R.id.textView);
                textView.setText(boardList.get(0).getTitle());
            }

            @Override
            public void onFailure(Throwable t) {
                TextView textView = findViewById(R.id.textView);
                textView.setText("FAIL FAIL FAIL");
            }
        });
    }
}
