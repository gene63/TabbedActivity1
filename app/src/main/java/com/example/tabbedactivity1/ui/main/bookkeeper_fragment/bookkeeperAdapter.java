package com.example.tabbedactivity1.ui.main.bookkeeper_fragment;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tabbedactivity1.R;
import com.example.tabbedactivity1.data.bookEntity;

import java.util.ArrayList;
import java.util.List;

public class bookkeeperAdapter extends RecyclerView.Adapter<bookkeeperAdapter.BookHolder> {

    private Context mCtx;
    private List<bookEntity> bookList;

    public List<bookEntity> getBookList() {
        return this.bookList;
    }

    public bookkeeperAdapter(List<bookEntity> item, Context context) {
        this.mCtx = context;
        this.bookList = item;
    }

    public class BookHolder extends RecyclerView.ViewHolder{
        TextView typeView;
        TextView valueView;
        TextView dateView;
        View background;

        BookHolder(View itemView) {
            super(itemView);

            typeView = itemView.findViewById(R.id.inc_exp);
            valueView = itemView.findViewById(R.id.value);
            dateView = itemView.findViewById(R.id.date);
            background = itemView.findViewById(R.id.item_backround);


            // itemView.setOnClickListener(this);
        }
    }

    //아이템 클릭시 실행 함수
    private ItemClick itemClick;
    public interface ItemClick {
        public void onClick(View view,int position);
    }

    //아이템 클릭시 실행 함수 등록 함수
    public void setItemClick(ItemClick itemClick) {
        this.itemClick = itemClick;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        View view;
        public ViewHolder(View view) {
            super(view);
            this.view = view;
        }
    }

     @Override
    public BookHolder onCreateViewHolder(ViewGroup container, int viewType) {
        LayoutInflater inflater = (LayoutInflater) LayoutInflater.from(container.getContext());
        View cell = inflater.inflate(R.layout.bookkeeper_item, container, false);
        return new BookHolder(cell);
    }

    @Override
    public void onBindViewHolder(@NonNull final BookHolder holder, final int position) {
        final int Position = position;
        bookEntity be = bookList.get(position);
        if(be.getType().equals("inc")) {
            holder.typeView.setText("수입");
            holder.background.setBackground(mCtx.getResources().getDrawable(R.drawable.round_edge_dark));
        }
        else {
            holder.typeView.setText(be.getCategory());
            holder.background.setBackground(mCtx.getResources().getDrawable(R.drawable.round_edge_light));
        }
        int date = be.getDate();
        String strDate = Integer.toString(date/10000)
                +"."+Integer.toString((date%10000)/100)
                +"."+Integer.toString(date%100);
        holder.valueView.setText(Integer.toString(be.getValue()));
        holder.dateView.setText(strDate);

        holder.background.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(itemClick != null){
                    itemClick.onClick(v, Position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if(bookList==null)
            return 0;
        return bookList.size();
    }
}