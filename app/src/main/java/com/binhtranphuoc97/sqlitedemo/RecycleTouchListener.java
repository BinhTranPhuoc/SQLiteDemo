package com.binhtranphuoc97.sqlitedemo;

import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by BinhTran on 10/19/2017.
 */

class RecycleTouchListener implements RecyclerView.OnItemTouchListener {
    public RecycleTouchListener(AddActivity addActivity, RecyclerView mRecyclerView, ClickListener clickListener) {

    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }

    public static abstract class ClickListener {
        public abstract void onClick(View view, int position);

        public abstract void onLongClick(View view, int position);
    }
}
