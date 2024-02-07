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

    public BookAdapter(Context context, ArrayList<Book> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_file, parent, false);
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
        }
    }
}
