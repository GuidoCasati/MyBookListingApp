package com.example.android.mybooklistingapp;

/**
 * Created by guido on 11/07/2017.
 */

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;

public class ItemRecyclerAdapter extends RecyclerView.Adapter<ItemRecyclerAdapter.ViewHolder> {

    private static OnItemClickListener onItemClickListener;
    ArrayList<Book> bookArrayList;
    MainActivity mainActivity;

    public ItemRecyclerAdapter(MainActivity context, ArrayList<Book> bookArrayList, OnItemClickListener onItemClickListener) {
        mainActivity = context;
        this.bookArrayList = bookArrayList;
        ItemRecyclerAdapter.onItemClickListener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemRecyclerAdapter.ViewHolder holder, int position) {
        Book book = bookArrayList.get(position);
        holder.titleTextView.setText(book.getTitle());
        holder.authorTextView.setText(book.getAuthor());
        Picasso.with(mainActivity).load(book.getCover()).into(holder.coverImageView);
        holder.bind(bookArrayList.get(position), onItemClickListener);
    }

    @Override
    public int getItemCount() {
        return bookArrayList.size();
    }

    public void clear() {
        bookArrayList.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<Book> book) {
        bookArrayList.addAll(book);
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(Book book);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView coverImageView;
        TextView titleTextView;
        TextView authorTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            coverImageView = (ImageView) itemView.findViewById(R.id.cover);
            titleTextView = (TextView) itemView.findViewById(R.id.title);
            authorTextView = (TextView) itemView.findViewById(R.id.author);
        }

        public void bind(final Book book, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(book);
                }
            });
        }
    }
}

