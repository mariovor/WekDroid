package eu.hquer.wekdroid.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import eu.hquer.wekdroid.enums.ExtrasEnum;
import eu.hquer.wekdroid.model.Card;

import static eu.hquer.wekdroid.R.id;
import static eu.hquer.wekdroid.R.layout;

public class CardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_card);
        Intent intent = getIntent();
        Card card = intent.getParcelableExtra(ExtrasEnum.card_object.getName());

        TextView titleText  = findViewById(id.card_list_board_title);
        titleText.setText(card.getTitle());
    }
}
