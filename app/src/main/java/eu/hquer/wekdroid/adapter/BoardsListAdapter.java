package eu.hquer.wekdroid.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import eu.hquer.wekdroid.R;
import eu.hquer.wekdroid.adapter.ViewHolder.BoardCardViewHolder;
import eu.hquer.wekdroid.model.Board;

/**
 * Created by mariovor on 25.02.18.
 *
 * Mostly stolen from https://developer.android.com/guide/topics/ui/layout/recyclerview.html
 */

public class BoardsListAdapter extends RecyclerView.Adapter<BoardCardViewHolder> {
    private List<Board> board_list;


    // Provide a suitable constructor (depends on the kind of dataset)
    public BoardsListAdapter(List<Board> newList) {
        board_list = newList;
    }


    // Create new views (invoked by the layout manager)
    @Override
    public BoardCardViewHolder onCreateViewHolder(ViewGroup viewGroup,
                                                           int viewType) {
        // create a new view
        View v =  LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.board_list_card_layout, viewGroup, false);
        BoardCardViewHolder vh = new BoardCardViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(BoardCardViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.boards_list_title.setText(board_list.get(position).getTitle());
        holder.currentBoard = board_list.get(position);

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
