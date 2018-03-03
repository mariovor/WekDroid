package eu.hquer.wekdroid.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import eu.hquer.wekdroid.R;
import eu.hquer.wekdroid.adapter.BoardsListAdapter;
import eu.hquer.wekdroid.model.Board;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListBoardsActivity extends RecyclerActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    List<Board> boardList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_boards);
        mAdapter = new BoardsListAdapter(boardList);
        recyclerView = obtainRecycler(R.id.boardCardList, mAdapter);
        // Get the data
        retrieveBoards();
    }



    public void retrieveBoards() {
        // Get boards
        Call<List<Board>> boardsCall = wekanService.getBoards(userId, token);
        boardsCall.enqueue(new Callback<List<Board>>() {
            @Override
            public void onResponse(Response<List<Board>> response) {
                boardList = response.body();
                // Update the displayed list
                BoardsListAdapter recyclerViewAdapter = (BoardsListAdapter) recyclerView.getAdapter();
                recyclerViewAdapter.updateData(boardList);
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(ListBoardsActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
