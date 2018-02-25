package eu.hquer.wekdroid.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import eu.hquer.wekdroid.R;
import eu.hquer.wekdroid.model.Board;

/**
 * Created by mariovor on 25.02.18.
 *
 * Mostly stolen from https://developer.android.com/guide/topics/ui/layout/recyclerview.html
 */

public class BoardsListAdapter extends RecyclerView.Adapter<BoardsListAdapter.ViewHolder> {
    private List<Board> board_list;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView boards_list_title;
        public ViewHolder(View v) {
            super(v);
            boards_list_title =  v.findViewById(R.id.boards_list_board_title);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public BoardsListAdapter(List<Board> newList) {
        board_list = newList;
    }


    // Create new views (invoked by the layout manager)
    @Override
    public BoardsListAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup,
                                                           int viewType) {
        // create a new view
        View v =  LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.board_list_card_layout, viewGroup, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.boards_list_title.setText(board_list.get(position).getTitle());

    }

    // Return the size of your data set (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return board_list.size();
    }

    public void updateData(List<Board> newData){
        board_list = newData;
        this.notifyDataSetChanged();
    }
}
