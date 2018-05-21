package eu.hquer.wekdroid.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import eu.hquer.wekdroid.R;
import eu.hquer.wekdroid.enums.ExtrasEnum;
import eu.hquer.wekdroid.model.Card;
import eu.hquer.wekdroid.model.Swimlane;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static eu.hquer.wekdroid.R.id;
import static eu.hquer.wekdroid.R.layout;

public class CardActivity extends BaseAcitvity {

    Card card;
    List<Swimlane> swimlaneList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_card);
        Intent intent = getIntent();
        card = intent.getExtras().getParcelable(ExtrasEnum.card_object.getName());
        // Get all swim lanes for this board and set it
        setSwimlaneSpiner();
        // Get all details of the card
        getCardDetails();


    }

    private void setSwimlaneSpiner() {
        Call<List<Swimlane>> swimlanesCall = wekanService.getSwimlanes(card.getBoardId(), token);
        swimlanesCall.enqueue(new Callback<List<Swimlane>>() {
            @Override
            public void onResponse(Response<List<Swimlane>> response) {
                swimlaneList = response.body();
                updateSwimlaneSpinner();
            }

            @Override
            public void onFailure(Throwable t) {
                failToast(t);
            }
        });
    }

    private void failToast(Throwable t) {
        Toast.makeText(CardActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
    }

    private void getCardDetails() {
        // Get the details of the card
        Call<Card> cardCall = wekanService.getCard(card.getBoardId(), card.getListId(), card.get_id(), token);
        cardCall.enqueue(new Callback<Card>() {
                             @Override
                             public void onResponse(Response<Card> response) {
                                 card = response.body();
                                 updateUI();
                             }

                             @Override
                             public void onFailure(Throwable t) {
                                 failToast(t);
                             }
                         }
        );
    }

    private void updateSwimlaneSpinner() {
        if (swimlaneList != null) {
            Spinner spinner = findViewById(id.activity_card_swimlanes);
            ArrayAdapter<Swimlane> adapter =
                    new ArrayAdapter<Swimlane>(getApplicationContext(),
                            android.R.layout.simple_spinner_dropdown_item, swimlaneList);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);

            // Set the selected swim lane to the of the card
            if (card.getSwimlaneId() != null) {
                for (int i = 0; i < swimlaneList.size(); i++){
                    if (card.getSwimlaneId().equals(swimlaneList.get(i).get_id())){
                        spinner.setSelection(i);
                        break;
                    }
                }
            }
        }
    }

    private void updateUI() {
        TextView titleText = findViewById(id.activity_card_title);
        titleText.setText(card.getTitle());

        TextView descriptionText = findViewById(id.activity_card_description);
        descriptionText.setText(card.getDescription());

        CheckBox checkBox = findViewById(id.activity_card_archived);
        checkBox.setChecked(card.getArchived());

        updateSwimlaneSpinner();

        // Set the progress bar to invisible
        findViewById(id.activity_card_progress).setVisibility(View.INVISIBLE);
    }

    public void updateCard(View view) {
        TextView titleText = findViewById(id.activity_card_title);
        card.setTitle(titleText.getText().toString());

        TextView descriptionText = findViewById(id.activity_card_description);
        card.setDescription(descriptionText.getText().toString());

        CheckBox checkBox = findViewById(id.activity_card_archived);
        card.setArchived(checkBox.isChecked());

        Spinner spinner = findViewById(id.activity_card_swimlanes);
        card.setSwimlaneId(swimlaneList.get(spinner.getSelectedItemPosition()).get_id());

        // Set the progress bar to invisible
        findViewById(id.activity_card_progress).setVisibility(View.VISIBLE);

        Call<Card> updateCardCall = wekanService.updateCard(card.getBoardId(), card.getListId(), card.get_id(), card, token);

        updateCardCall.enqueue(new Callback<Card>() {
            @Override
            public void onResponse(Response<Card> response) {
                // Set the progress bar to invisible
                findViewById(id.activity_card_progress).setVisibility(View.INVISIBLE);
                Toast.makeText(CardActivity.this, getString(R.string.update_success), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Throwable t) {
                // Set the progress bar to invisible
                findViewById(id.activity_card_progress).setVisibility(View.INVISIBLE);
                failToast(t);
            }
        });

    }
}
