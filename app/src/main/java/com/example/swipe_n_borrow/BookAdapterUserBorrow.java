package com.example.swipe_n_borrow;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class BookAdapterUserBorrow extends RecyclerView.Adapter<BookAdapterUserBorrow.MyHolder> {
    private Context context;
    private ArrayList<Book> arrayList;
    private LayoutInflater layoutInflater;
    private OnSelectButtonClickListener onSelectButtonClickListener;

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
        holder.bookAuthor.setText(arrayList.get(position).getAuthors());
        holder.bookLanguage.setText(arrayList.get(position).getLanguage());
        holder.bookNumPage.setText(arrayList.get(position).getNum_pages());
        String imageURL = arrayList.get(position).getImageURL();
        if (imageURL != null && !imageURL.isEmpty()) {
            Picasso.get()
                    .load(imageURL)
                    .placeholder(R.drawable.bookse) // Placeholder image while loading
                    .into(holder.imageView);
        } else {
            // Set a default placeholder if the imageURL is not available
            holder.imageView.setImageResource(R.drawable.bookse);
        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        TextView bookTitle, bookGenre , bookAuthor , bookLanguage ,bookNumPage;
        ImageView imageView;

        public MyHolder(View itemView) {
            super(itemView);
            bookTitle = itemView.findViewById(R.id.txt);
            bookGenre = itemView.findViewById(R.id.txt2);
            imageView = itemView.findViewById(R.id.bookImage);
            bookAuthor = itemView.findViewById(R.id.txt13);
            bookLanguage = itemView.findViewById(R.id.txt6);
            bookNumPage = itemView.findViewById(R.id.txt7);

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