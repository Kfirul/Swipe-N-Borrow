package com.example.swipe_n_borrow;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BookAdapterUserBorrow extends RecyclerView.Adapter<BookAdapterUserBorrow.MyHolder> {
    private Context context;
    private ArrayList<Book> arrayList;
    private LayoutInflater layoutInflater;
    private OnSelectButtonClickListener onSelectButtonClickListener;  // Add this interface

    public BookAdapterUserBorrow(Context context, ArrayList<Book> arrayList, OnSelectButtonClickListener listener) {
        this.context = context;
        this.arrayList = arrayList;
        this.layoutInflater = LayoutInflater.from(context);
        this.onSelectButtonClickListener = listener;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.book_file_borrow_user, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        holder.bookTitle.setText(arrayList.get(position).getTitle());
        holder.bookGenre.setText(arrayList.get(position).getGenre());
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

            // Set click listener for selectButton
            itemView.findViewById(R.id.BTN_Borrow).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Call the listener method when the button is clicked
                    if (onSelectButtonClickListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            onSelectButtonClickListener.onSelectButtonClick(arrayList.get(position));
                        }
                    }
                }
            });
        }
    }

    public interface OnSelectButtonClickListener {
        void onSelectButtonClick(Book book);
    }
}
