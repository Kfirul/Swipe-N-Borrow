package com.example.swipe_n_borrow;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.MyHolder> {

    private Context context;
    private ArrayList<Book> arrayList;
    private LayoutInflater layoutInflater;
    private OnSelectButtonClickListener onSelectButtonClickListener;
    private OnRemoveButtonClickListener removeButtonClickListener;

    public BookAdapter(Context context, ArrayList<Book> arrayList, OnSelectButtonClickListener selectListener, OnRemoveButtonClickListener removeListener) {
        this.context = context;
        this.arrayList = arrayList;
        this.layoutInflater = LayoutInflater.from(context);
        this.onSelectButtonClickListener = selectListener;
        this.removeButtonClickListener = removeListener;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.book_file_admin, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        holder.bookTitle.setText(arrayList.get(position).getTitle());
        holder.bookGenre.setText(arrayList.get(position).getGenre());

        // Set click listener for selectButton
        holder.itemView.findViewById(R.id.BTN_Select).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Call the listener method when the "Select" button is clicked
                if (onSelectButtonClickListener != null) {
                    int position = holder.getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        onSelectButtonClickListener.onSelectButtonClick(arrayList.get(position));
                    }
                }
            }
        });

        // Set click listener for removeButton
        holder.itemView.findViewById(R.id.BTN_Remove).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Call the listener method when the "Remove" button is clicked
                if (removeButtonClickListener != null) {
                    int position = holder.getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        removeButtonClickListener.onRemoveButtonClick(arrayList.get(position));
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        TextView bookTitle, bookGenre;

        public MyHolder(View itemView) {
            super(itemView);
            bookTitle = itemView.findViewById(R.id.txt);
            bookGenre = itemView.findViewById(R.id.txt2);
        }
    }

    public interface OnSelectButtonClickListener {
        void onSelectButtonClick(Book book);
    }

    public interface OnRemoveButtonClickListener {
        void onRemoveButtonClick(Book book);
    }
}
