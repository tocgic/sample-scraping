package com.tocgic.sample.scraping.views.interfaces;

import android.support.v7.widget.RecyclerView;

/**
 * Created by tocgic on 2016. 8. 6..
 */
public interface OnRecyclerItemLongClickListener {
    boolean onItemLongClick(RecyclerView.Adapter adapter, int position);
}
