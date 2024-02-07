package com.example.swipe_n_borrow;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.SearchView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class AdminSearch extends AppCompatActivity {
    private SearchView searchView;
    private RecyclerView recyclerView;
    private ArrayList<Book> bookArrayList = new ArrayList<>();
    private ArrayList<Book> searchList;

    String[] bookList = new String[]{"Harry Potter", "Donald"};
    String[] genreList = new String[]{"Fantasy", "Science"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_search);

        recyclerView = findViewById(R.id.recycleView);
        searchView = findViewById(R.id.searchView);
        searchView.setIconified(false);
        searchView.clearFocus();

        for (int i = 0; i < bookList.length; i++) {
            Book book = new Book();
            book.setTitle(bookList[i]);
            book.setGenre(genreList[i]);
            bookArrayList.add(book);
        }

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        BookAdapter bookAdapter = new BookAdapter(this, bookArrayList);
        recyclerView.setAdapter(bookAdapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                performSearch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                performSearch(newText);
                return false;
            }
        });
    }

    private void performSearch(String query) {
        searchList = new ArrayList<>();
        if (!TextUtils.isEmpty(query)) {
            for (int i = 0; i < bookArrayList.size(); i++) {
                if (bookArrayList.get(i).getTitle().toUpperCase().contains(query.toUpperCase())
                        || bookArrayList.get(i).getGenre().toUpperCase().contains(query.toUpperCase())) {
                    Book book = new Book();
                    book.setTitle(bookArrayList.get(i).getTitle());
                    book.setGenre(bookArrayList.get(i).getGenre());
                    searchList.add(book);
                }
            }
        } else {
            searchList.addAll(bookArrayList);
        }

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        BookAdapter bookAdapter = new BookAdapter(this, searchList);
        recyclerView.setAdapter(bookAdapter);
    }
}
