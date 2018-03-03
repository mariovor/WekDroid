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
import eu.hquer.wekdroid.enums.ExtrasEnum;
import eu.hquer.wekdroid.model.WekanList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListListsActivity extends RecyclerActivity {
    String board_title;
    String board_id;
    private RecyclerView recyclerView;
    List<WekanList> listList = new ArrayList<WekanList>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_boards);
        TextView textView = findViewById(R.id.board_name);
        // Set the head line
        textView.setText(getString(R.string.lists_name));
        // Get the intent information
        Intent intent = getIntent();
        board_id = intent.getExtras().getString(ExtrasEnum.board_id.getName());
        board_title = intent.getExtras().getString(ExtrasEnum.board_title.getName());
        // Set the recycler view and adapter
        ListsListAdapter mAdapter = new ListsListAdapter(listList, board_id);
        recyclerView = obtainRecycler(R.id.boardCardList, mAdapter);
        // retrieve data from server
        retrieveListsOfBoard(board_id);
    }

    public void retrieveListsOfBoard(String board_id){
        Call<List<WekanList>> listCall = wekanService.getListOfBoards(board_id, token);
        listCall.enqueue(new Callback<List<WekanList>>() {
            @Override
            public void onResponse(Response<List<WekanList>> response) {
                List<WekanList> listList = response.body();
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
