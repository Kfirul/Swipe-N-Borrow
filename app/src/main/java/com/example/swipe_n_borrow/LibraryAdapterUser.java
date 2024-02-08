package com.example.swipe_n_borrow;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class LibraryAdapterUser extends RecyclerView.Adapter<LibraryAdapterUser.MyHolder> {
    private Context context;
    private ArrayList<String> arrayList;
    private LayoutInflater layoutInflater;
    private OnSelectButtonClickListener onSelectButtonClickListener;

    public LibraryAdapterUser(Context context, ArrayList<String> arrayList, OnSelectButtonClickListener listener) {
        this.context = context;
        this.arrayList = arrayList;
        this.layoutInflater = LayoutInflater.from(context);
        this.onSelectButtonClickListener = listener;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.library_file_user, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LibraryAdapterUser.MyHolder holder, int position) {
        holder.library.setText(arrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public interface OnSelectButtonClickListener {
        void onSelectButtonClick(String library);
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        TextView library;
        Button selectButton;

        public MyHolder(View itemView) {
            super(itemView);
            library = itemView.findViewById(R.id.txt);
            selectButton = itemView.findViewById(R.id.BTN_Select);

            // Set click listener for selectButton
            selectButton.setOnClickListener(new View.OnClickListener() {
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
}
