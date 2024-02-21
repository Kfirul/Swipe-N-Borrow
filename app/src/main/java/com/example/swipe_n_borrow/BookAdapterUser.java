package com.example.swipe_n_borrow;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class BookAdapterUser extends RecyclerView.Adapter<BookAdapterUser.MyHolder> {
    private Context context;
    private ArrayList<Book> arrayList;
    private LayoutInflater layoutInflater;
    private OnSelectButtonClickListener onSelectButtonClickListener;

    public BookAdapterUser(Context context, ArrayList<Book> arrayList, OnSelectButtonClickListener listener) {
        this.context = context;
        this.arrayList = arrayList;
        this.layoutInflater = LayoutInflater.from(context);
        this.onSelectButtonClickListener = listener;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.book_file_user, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        holder.bookTitle.setText(arrayList.get(position).getTitle());
        holder.bookGenre.setText(arrayList.get(position).getGenre());
        holder.libraryBelongs.setText("Return To: " + arrayList.get(position).getBelongs());

        // Convert Date to a formatted String
        String formattedDate = formatDate(arrayList.get(position).getDate());
        holder.bookDate.setText("Return Until: " + formattedDate);
    }

    private String formatDate(Date date) {
        // You can use SimpleDateFormat or any other date formatting method
        // This is just an example, adjust the pattern as needed
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return sdf.format(date);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        TextView bookTitle, bookGenre,bookDate, libraryBelongs;

        public MyHolder(View itemView) {
            super(itemView);
            bookTitle = itemView.findViewById(R.id.txt);
            bookGenre = itemView.findViewById(R.id.txt2);
            bookDate = itemView.findViewById(R.id.txt3);
            libraryBelongs = itemView.findViewById(R.id.txt4);

            // Set click listener for selectButton
            itemView.findViewById(R.id.BTN_Return).setOnClickListener(new View.OnClickListener() {
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
