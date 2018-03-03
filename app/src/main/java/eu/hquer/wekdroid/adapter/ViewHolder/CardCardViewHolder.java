package eu.hquer.wekdroid.adapter.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import eu.hquer.wekdroid.R;
import eu.hquer.wekdroid.model.Card;

/**
 * Created by mariovor on 03.03.18.
 */

public class CardCardViewHolder extends RecyclerView.ViewHolder{
    public Card currentCard;
    public String parentBoardId;
    public String parentListId;

    public TextView cardTitleTextView;
    public TextView cardDescriptionTextView;

    public View view;

    public CardCardViewHolder(View view) {
        super(view);
        this.view = view;
       cardTitleTextView = view.findViewById(R.id.card_list_board_title);
       cardDescriptionTextView = view.findViewById(R.id.card_list_board_description);
    }
}
