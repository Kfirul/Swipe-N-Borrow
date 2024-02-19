package com.example.swipe_n_borrow;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
        View view = layoutInflater.inflate(R.layout.book_borrow_admin, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        holder.bookTitle.setText(arrayList.get(position).getTitleBook());
        holder.userName.setText(arrayList.get(position).getUserName());
        holder.userEmail.setText(arrayList.get(position).getUserEmail());

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        TextView bookTitle, userName,userEmail;

        public MyHolder(View itemView) {
            super(itemView);
            bookTitle = itemView.findViewById(R.id.txt);
            userName = itemView.findViewById(R.id.txt2);
            userEmail = itemView.findViewById(R.id.txt3);


        }
    }


}
