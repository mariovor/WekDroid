package eu.hquer.wekdroid.adapter.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import eu.hquer.wekdroid.R;
import eu.hquer.wekdroid.model.Board;


/**
 * Created by mariovor on 01.03.18.
 */

// Provide a reference to the views for each data item
// Complex data items may need more than one view per item, and
// you provide access to all the views for a data item in a view holder
public class ListCardViewHolder extends RecyclerView.ViewHolder {
    // each data item is just a string in this case
    public TextView boards_list_title;
    public Board currentBoard;

    public ListCardViewHolder(View v) {
        super(v);
        boards_list_title = v.findViewById(R.id.boards_list_board_title);


//        v.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // item clicked
//                Intent intent = new Intent(v.getContext(), ListListsActivity.class);
//                intent.putExtra("board_title", currentBoard.getTitle());
//                intent.putExtra("board_id", currentBoard.get_id());
//                v.getContext().startActivity(intent);
//            }
//        });
    }
}
