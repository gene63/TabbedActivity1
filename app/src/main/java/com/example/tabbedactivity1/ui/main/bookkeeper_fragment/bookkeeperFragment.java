package com.example.tabbedactivity1.ui.main.bookkeeper_fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tabbedactivity1.R;
import com.example.tabbedactivity1.ui.main.phoneNumber_fragment.PageViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

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
        FloatingActionButton fab = root.findViewById(R.id.fab);
        Log.d("@@@@@@","1");
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                show();
            }
        });
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

    void show(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());
        builder.setTitle("수입/지출 입력");
        //타이틀설정
        String tv_text = "안녕하세요";
        builder.setMessage(tv_text);
        //내용설정
        final EditText name = new EditText(this.getActivity());
        builder.setView(name);

        builder.setNeutralButton("날짜변경",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                OnClickHandler(getView());
            }
        });

        builder.setPositiveButton("저장",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getActivity().getApplicationContext(),"저장완료",Toast.LENGTH_LONG).show();
                    }
                });

        builder.setNegativeButton("취소",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getActivity().getApplicationContext(),"취소됨",Toast.LENGTH_LONG).show();
                    }
                });
        builder.show();
    }

    public void OnClickHandler(View view)
    {
        DatePickerDialog.OnDateSetListener callbackMethod;
        DatePickerDialog dialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                //
            }
        }, 2019, 5, 24);

        dialog.show();
    }
}



