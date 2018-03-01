package eu.hquer.wekdroid.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import eu.hquer.wekdroid.R;

public class ListListsActivity extends BaseAcitvity {
    String board_title;
    String board_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_lists);
        Intent intent = getIntent();
        board_id = intent.getExtras().getString("board_id");
        board_title = intent.getExtras().getString("board_title");
    }
}
