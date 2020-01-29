package com.wemsuser.app.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wemsuser.app.Fragment.BookMarkFragment;
import com.wemsuser.app.R;

import java.util.ArrayList;

public class BookmarkAdapter extends RecyclerView.Adapter<BookmarkAdapter.MyViewHolder> {
    private BookMarkFragment context;
    private ArrayList<String>mBookmark;

    public BookmarkAdapter(BookMarkFragment mcontext, ArrayList<String> bookMark) {
        this.mBookmark=bookMark;
        this.context=mcontext;
    }

    @Override
    public BookmarkAdapter.MyViewHolder onCreateViewHolder( ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.bookxml,viewGroup,false);
        MyViewHolder myViewHolder=new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder( BookmarkAdapter.MyViewHolder myViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return mBookmark.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public MyViewHolder( View itemView) {
            super(itemView);
        }
    }
}
