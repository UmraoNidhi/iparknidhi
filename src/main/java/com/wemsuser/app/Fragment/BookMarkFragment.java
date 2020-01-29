package com.wemsuser.app.Fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wemsuser.app.Activity.HomeActivity;
import com.wemsuser.app.Adapter.BookmarkAdapter;
import com.wemsuser.app.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class BookMarkFragment extends Fragment {

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private BookmarkAdapter adapter;
    private ArrayList<String>BookMark=new ArrayList<>();


    public BookMarkFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        HomeActivity.mMenu.findItem(R.id.action_Map).setVisible(false);
        HomeActivity.mMenu.findItem(R.id.action_List).setVisible(false);
        HomeActivity.mMenu.findItem(R.id.Refresh).setVisible(false);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_book_mark, container, false);
        getActivity().closeOptionsMenu();

        BookMark.add("Nova Services");
        BookMark.add("Nova Services");
        BookMark.add("Nova Services");
        BookMark.add("Nova Services");
        BookMark.add("Nova Services");


        recyclerView=view.findViewById(R.id.Bookmark);
        linearLayoutManager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        adapter=new BookmarkAdapter(this,BookMark);
        recyclerView.setAdapter(adapter);

        return view;
    }


}
