package eu.hquer.wekdroid.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import eu.hquer.wekdroid.R;
import eu.hquer.wekdroid.adapter.CardListAdapter;
import eu.hquer.wekdroid.enums.ExtrasEnum;
import eu.hquer.wekdroid.model.Card;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListCardActivity extends RecyclerActivity {
    List<Card> cardList = new ArrayList<>();
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_card);
        // Get intent and extras
        Intent intent = getIntent();
        String parentBoardId = intent.getExtras().getString(ExtrasEnum.board_id.getName());
        String parentListId = intent.getExtras().getString(ExtrasEnum.list_id.getName());
        // Set the recycler view and adapter
        CardListAdapter mAdapter = new CardListAdapter(cardList, parentBoardId, parentListId);
        recyclerView = obtainRecycler(R.id.cardCardList, mAdapter);
        // Get the data from the server and update the view
        retrieveListsOfCards(parentBoardId, parentListId);
    }

    public void retrieveListsOfCards(String board_id, String list_id) {
        Call<List<Card>> listCall = wekanService.getListOfCards(board_id, list_id, token);
        listCall.enqueue(new Callback<List<Card>>() {
            @Override
            public void onResponse(Response<List<Card>> response) {
                List<Card> cardList = response.body();
                int i = 3;
                CardListAdapter recyclerViewAdapter = (CardListAdapter) recyclerView.getAdapter();
                recyclerViewAdapter.updateData(cardList);
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(ListCardActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
