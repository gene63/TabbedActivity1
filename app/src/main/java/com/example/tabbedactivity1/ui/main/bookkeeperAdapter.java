package com.example.tabbedactivity1.ui.main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tabbedactivity1.R;

import java.util.ArrayList;

public class bookkeeperAdapter extends RecyclerView.Adapter<bookkeeperAdapter.BookHolder> {

    private ArrayList<bookItem> mData = null;

    public bookkeeperAdapter(ArrayList<bookItem> item) {
        this.mData = item;
    }

    public class BookHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView textView;

        BookHolder(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.inc_exp);
            textView = itemView.findViewById(R.id.value);
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

        bookItem item = mData.get(position);

        holder.imageView.setImageDrawable(item.getImg());
        holder.textView.setText(item.getVal());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}