package eu.hquer.wekdroid.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.View;
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
    String parentBoardId;
    String parentListId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_card);
        // Get intent and extras
        Intent intent = getIntent();
        parentBoardId = intent.getExtras().getString(ExtrasEnum.board_id.getName());
        parentListId = intent.getExtras().getString(ExtrasEnum.list_id.getName());
        // Set the recycler view and adapter
        CardListAdapter mAdapter = new CardListAdapter(cardList, parentBoardId, parentListId);
        recyclerView = obtainRecycler(R.id.cardCardList, mAdapter);
        // Get the data from the server and update the view
        retrieveListsOfCards(parentBoardId, parentListId);

        setFloatingActionButtonProperties();
    }

    private void setFloatingActionButtonProperties() {
        FloatingActionButton floatingActionButton = findViewById(R.id.acivity_list_card_floatButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                addCard();
            }
        });
    }

    private void addCard() {
        Card newCard = new Card();
        newCard.set_id(null);
        newCard.setAuthorId(userId);
        newCard.setTitle("");
        newCard.setDescription("");
        newCard.setBoardId(parentBoardId);
        newCard.setListId(parentListId);
        Intent intent = new Intent(getCurrentFocus().getContext(), CardActivity.class);
        intent.putExtra(ExtrasEnum.card_object.getName(), newCard);
        getCurrentFocus().getContext().startActivity(intent);
    }

    public void retrieveListsOfCards(String board_id, String list_id) {
        Call<List<Card>> listCall = wekanService.getListOfCards(board_id, list_id, token);
        listCall.enqueue(new Callback<List<Card>>() {
            @Override
            public void onResponse(Response<List<Card>> response) {
                List<Card> cardList = response.body();
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
