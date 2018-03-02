package eu.hquer.wekdroid.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by mariovor on 02.03.18.
 */

public class RecyclerActivity extends BaseAcitvity {

    protected RecyclerView obtainRecycler(int viewId, RecyclerView.Adapter adapter) {
        RecyclerView recyclerView = (RecyclerView) findViewById(viewId);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLayoutManager);

        // Set adapter

        recyclerView.setAdapter(adapter);

        return recyclerView;
    }
}
