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

    public bookkeeperAdapter(List<bookEntity> item, Context context) {
        this.mCtx = context;
        this.bookList = item;
    }

    public class BookHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView valueView;
        TextView dateView;
        View background;

        BookHolder(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.inc_exp);
            valueView = itemView.findViewById(R.id.value);
            dateView = itemView.findViewById(R.id.date);
            background = itemView.findViewById(R.id.item_backround);


            // itemView.setOnClickListener(this);
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

        bookEntity be = bookList.get(position);
        if(be.getType()=="inc") {
            holder.imageView.setImageDrawable(mCtx.getResources().getDrawable(R.drawable.cat_icon));
            holder.background.setBackground(mCtx.getResources().getDrawable(R.drawable.round_edge_dark));
        }
        else {
            holder.imageView.setImageDrawable(mCtx.getResources().getDrawable(R.drawable.dog_icon));
            holder.background.setBackground(mCtx.getResources().getDrawable(R.drawable.round_edge_light));
        }
        int date = be.getDate();
        String strDate = Integer.toString(date/10000)
                +"."+Integer.toString((date%10000)/100)
                +"."+Integer.toString(date%100);
        holder.valueView.setText(be.getValue());
        holder.dateView.setText(strDate);
    }

    @Override
    public int getItemCount() {
        if(bookList==null)
            return 0;
        return bookList.size();
    }
}