package eu.hquer.wekdroid.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import eu.hquer.wekdroid.R;
import eu.hquer.wekdroid.adapter.ViewHolder.CardCardViewHolder;
import eu.hquer.wekdroid.model.Card;

/**
 * Created by mariovor on 25.02.18.
 * <p>
 * Mostly stolen from https://developer.android.com/guide/topics/ui/layout/recyclerview.html
 */

public class CardListAdapter extends RecyclerView.Adapter<CardCardViewHolder> {
    private List<Card> card_list;
    private String parent_board_id;
    private String parent_list_id;


    // Provide a suitable constructor (depends on the kind of dataset)
    public CardListAdapter(List<Card> cardList, String parent_board_id, String parent_list_id) {
        this.card_list = cardList;
        this.parent_board_id = parent_board_id;
        this.parent_list_id = parent_list_id;
    }


    // Create new views (invoked by the layout manager)
    @Override
    public CardCardViewHolder onCreateViewHolder(ViewGroup viewGroup,
                                                 int viewType) {
        // create a new view
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_list_card_layout, viewGroup, false);
        CardCardViewHolder vh = new CardCardViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(CardCardViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.cardTitleTextView.setText(card_list.get(position).getTitle());
        String description = card_list.get(position).getDescription();
        if (description == null) {
            holder.cardDescriptionTextView.setText(holder.view.getContext().getString(R.string.no_description));
        } else {
            holder.cardDescriptionTextView.setText(description);
        }
        holder.currentCard = card_list.get(position);
        holder.currentCard.setBoardId(parent_board_id);
        holder.currentCard.setListId(parent_list_id);
    }

    // Return the size of your data set (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return card_list.size();
    }

    public void updateData(List<Card> newData) {
        card_list = newData;
        this.notifyDataSetChanged();
    }
}
