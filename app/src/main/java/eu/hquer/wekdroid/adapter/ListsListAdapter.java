package eu.hquer.wekdroid.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import eu.hquer.wekdroid.R;
import eu.hquer.wekdroid.adapter.ViewHolder.ListCardViewHolder;
import eu.hquer.wekdroid.model.WekanList;

/**
 * Created by mariovor on 25.02.18.
 *
 * Mostly stolen from https://developer.android.com/guide/topics/ui/layout/recyclerview.html
 */

public class ListsListAdapter extends RecyclerView.Adapter<ListCardViewHolder> {
    private List<WekanList> list_list;
    private String board_id;


    // Provide a suitable constructor (depends on the kind of dataset)
    public ListsListAdapter(List<WekanList> newWekanList, String board_id) {
        this.list_list = newWekanList;
        this.board_id = board_id;
    }


    // Create new views (invoked by the layout manager)
    @Override
    public ListCardViewHolder onCreateViewHolder(ViewGroup viewGroup,
                                                           int viewType) {
        // create a new view
        View v =  LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.board_list_card_layout, viewGroup, false);
        ListCardViewHolder vh = new ListCardViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ListCardViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.boards_list_title.setText(list_list.get(position).getTitle());
        holder.currentList = list_list.get(position);
        holder.parentBoardId = board_id;

    }

    // Return the size of your data set (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return list_list.size();
    }

    public void updateData(List<WekanList> newData){
        list_list = newData;
        this.notifyDataSetChanged();
    }
}
