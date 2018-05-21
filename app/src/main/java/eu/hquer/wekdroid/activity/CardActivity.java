package eu.hquer.wekdroid.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import eu.hquer.wekdroid.enums.ExtrasEnum;
import eu.hquer.wekdroid.model.Card;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static eu.hquer.wekdroid.R.id;
import static eu.hquer.wekdroid.R.layout;

public class CardActivity extends BaseAcitvity {

    Card card;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_card);
        Intent intent = getIntent();
        card = intent.getExtras().getParcelable(ExtrasEnum.card_object.getName());

        // Get the details of the card

        Call<Card> cardCall = wekanService.getCard(card.getBoardId(), card.getListId(), card.get_id(), token);

        cardCall.enqueue(new Callback<Card>() {
                             @Override
                             public void onResponse(Response<Card> response) {
                                 card  = response.body();
                                 updateUI();
                             }

                             @Override
                             public void onFailure(Throwable t) {
                                 Toast.makeText(CardActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                             }
                         }
        );


    }

    private void updateUI() {
        TextView titleText  = findViewById(id.activity_card_title);
        titleText.setText(card.getTitle());

        TextView descriptionText = findViewById(id.activity_card_description);
        descriptionText.setText(card.getDescription());

        CheckBox checkBox = findViewById(id.activity_card_archived);
        checkBox.setChecked(card.getArchived());
    }
}
