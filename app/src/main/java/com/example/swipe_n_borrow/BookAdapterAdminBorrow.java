package com.example.swipe_n_borrow;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.WindowDecorActionBar;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class BookAdapterAdminBorrow extends RecyclerView.Adapter<BookAdapterAdminBorrow.MyHolder> {
    private Context context;
    private ArrayList<BorrowBook> arrayList;
    private LayoutInflater layoutInflater;

    public BookAdapterAdminBorrow(Context context, ArrayList<BorrowBook> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        this.layoutInflater = LayoutInflater.from(context);
    }



    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.book_file_borrow_admin, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        holder.bookTitle.setText(arrayList.get(position).getTitleBook());
        holder.userName.setText("Borrwed by: " +arrayList.get(position).getUserName());
        holder.userEmail.setText("User's  Email: "+ arrayList.get(position).getUserEmail());
        holder.userAddress.setText("User's  Address: "+ arrayList.get(position).getUserAddress());
        holder.dateBorrow.setText(" Return Untill : "+ arrayList.get(position).getDateBorrow());
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
        TextView bookTitle, userName,userEmail , dateBorrow, userAddress;
        ImageView imageView;

        public MyHolder(View itemView) {
            super(itemView);
            bookTitle = itemView.findViewById(R.id.txt);
            userName = itemView.findViewById(R.id.txt2);
            userEmail = itemView.findViewById(R.id.txt3);
            userAddress = itemView.findViewById(R.id.txt4);
            dateBorrow = itemView.findViewById(R.id.txt5);
            imageView = itemView.findViewById(R.id.img);
        }
    }


}
