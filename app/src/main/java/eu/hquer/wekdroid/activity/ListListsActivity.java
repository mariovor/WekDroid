package eu.hquer.wekdroid.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import eu.hquer.wekdroid.R;
import eu.hquer.wekdroid.adapter.ListsListAdapter;
import eu.hquer.wekdroid.model.Board;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListListsActivity extends RecyclerActivity {
    String board_title;
    String board_id;
    private RecyclerView recyclerView;
    List<Board> boardList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_boards);
        TextView textView = findViewById(R.id.board_name);
        // Set the head line
        textView.setText(getString(R.string.lists_name));
        // Get the intent information
        Intent intent = getIntent();
        board_id = intent.getExtras().getString("board_id");
        board_title = intent.getExtras().getString("board_title");
        // Set the recycler view and adapter
        ListsListAdapter mAdapter = new ListsListAdapter(boardList);
        recyclerView = obtainRecycler(R.id.cardList, mAdapter);
        // retrieve data from server
        retrieveListsOfBoard(board_id);
    }

    public void retrieveListsOfBoard(String board_id){
        Call<List<Board>> listCall = wekanService.getListOfBoards(board_id, token);
        listCall.enqueue(new Callback<List<Board>>() {
            @Override
            public void onResponse(Response<List<Board>> response) {
                List<Board> listList = response.body();
                ListsListAdapter recyclerViewAdapter = (ListsListAdapter) recyclerView.getAdapter();
                recyclerViewAdapter.updateData(listList);
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(ListListsActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
            
    }
}
