package com.example.tabbedactivity1.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tabbedactivity1.R;

import java.util.ArrayList;

public class bookkeeperFragment extends Fragment {

    RecyclerView bookkeeperRecycler = null;
    bookkeeperAdapter bookkeeperAdapter = null;
    ArrayList<bookItem> arrItem = new ArrayList<>();

    private static final String ARG_SECTION_NUMBER = "section_number";
    private PageViewModel pageViewModel;

    public static bookkeeperFragment newInstance(int index) {
        bookkeeperFragment fragment = new bookkeeperFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = ViewModelProviders.of(this).get(PageViewModel.class);
        int index = 3;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        pageViewModel.setIndex(index);

        for (int i=0; i<100; i++){
            bookItem item = new bookItem();
            item.setVal(String.format("%d", i));
            if (i%2==0) {
                item.setImg(this.getResources().getDrawable(R.drawable.cat_icon));
            }
            else {
                item.setImg(this.getResources().getDrawable(R.drawable.dog_icon));
            }
            arrItem.add(item);
        }
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_bookkeeper, container, false);
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        bookkeeperRecycler = (RecyclerView) getView().findViewById(R.id.bookkeeperRecycler);
        bookkeeperAdapter = new bookkeeperAdapter(arrItem);
        bookkeeperRecycler.setAdapter(bookkeeperAdapter);
        bookkeeperRecycler.setLayoutManager(new LinearLayoutManager(this.getActivity()));
    }
    /*
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        bookkeeperRecycler = (RecyclerView) getView().findViewById(R.id.bookkeeperRecycler);
        bookkeeperRecycler.setAdapter(bookkeeperAdapter);
        bookkeeperRecycler.setLayoutManager(new LinearLayoutManager(this.getActivity()));
    }
     */
}

