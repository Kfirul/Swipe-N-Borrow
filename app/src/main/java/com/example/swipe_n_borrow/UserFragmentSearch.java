package com.example.swipe_n_borrow;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class UserFragmentSearch extends Fragment {
    private SearchView searchView;
    private RecyclerView recyclerView;
    private ArrayList<Book> bookArrayList = new ArrayList<>();
    private ArrayList<Book> searchList;

    String[] bookList = new String[]{"Harry Potter", "Donald"};
    String[] genreList = new String[]{"Fantasy", "Science"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_books, container, false);

        recyclerView = view.findViewById(R.id.recycleView);
        searchView = view.findViewById(R.id.searchView);
        searchView.setIconified(false);
        searchView.clearFocus();

        for (int i = 0; i < bookList.length; i++) {
            Book book = new Book();
            book.setTitle(bookList[i]);
            book.setGenre(genreList[i]);
            bookArrayList.add(book);
        }

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        BookAdapter bookAdapter = new BookAdapter(getActivity(), bookArrayList);
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

        return view;
    }

    private void performSearch(String query) {
        searchList = new ArrayList<>();
        if (query.length() > 0) {
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

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        BookAdapter bookAdapter = new BookAdapter(getActivity(), searchList);
        recyclerView.setAdapter(bookAdapter);
    }
}

//
//    private void readBookData(){
//        InputStream inputStream = getResources().openRawResource(R.raw.books);
//        BufferedReader reader = new BufferedReader(
//                new InputStreamReader(inputStream, Charset.forName("UTF-8"))
//        );
//        String line = "";
//        try {
//            while ( (line= reader.readLine()) != null ) {
//                String [] tokens = line.split(",");
//                Book book = new Book();
//                if (tokens[0].length()>0) {
//                    book.setBookID(Integer.parseInt(tokens[0]));
//                }else {
//                    book.setBookID(0);
//                }
//                if (tokens[1].length()>0) {
//                    book.setTitle(tokens[1]);
//                }else {
//                    book.setTitle("NO DATA");
//                }
//                if (tokens[2].length()>0) {
//                    book.setAuthors(tokens[2]);
//                }else {
//                    book.setAuthors("NO DATA");
//                }
//                if (tokens[3].length()>0) {
//                    book.setAverage_rating(Float.parseFloat(tokens[3]));
//                }else {
//                    book.setAverage_rating(0);
//                }
//                if (tokens[4].length()>0) {
//                    book.setIsbn(tokens[4]);
//                }else {
//                    book.setIsbn("NO DATA");
//                }
//                if (tokens[5].length()>0) {
//                    book.setIsbn13(tokens[5]);
//                }else {
//                    book.setIsbn13("NO DATA");
//                }
//                if (tokens[6].length()>0) {
//                    book.setLanguage_code(tokens[6]);
//                }else {
//                    book.setLanguage_code("NO DATA");
//                }
//                if (tokens[7].length()>0) {
//                    book.setNum_pages(Integer.parseInt(tokens[7]));
//                }else {
//                    book.setNum_pages(0);
//                }
//                if (tokens[8].length()>0) {
//                    book.setRatings_count(Integer.parseInt(tokens[8]));
//                }else {
//                    book.setRatings_count(0);
//                }
//                if (tokens[9].length()>0) {
//                    book.setText_reviews_count(Integer.parseInt(tokens[9]));
//                }else {
//                    book.setText_reviews_count(0);
//                }
//                if (tokens[10].length()>0) {
//                    book.setPublication_date(tokens[10]);
//                }else {
//                    book.setPublication_date("NO DATA");
//                }
//                if (tokens[11].length()>0) {
//                    book.setPublisher(tokens[11]);
//                }else {
//                    book.setPublisher("NO DATA");
//                }
//                if (tokens[12].length()>0) {
//                    book.setNumber_of_books(Integer.parseInt(tokens[12]));
//                }else {
//                    book.setNumber_of_books(0);
//                }
//                books.add(book);
//                Log.d("MyActivity","Just created:" + book);
//            }
//
//        } catch (IOException e){
//            Log.wtf("MyActivity", "Error reading data file on line" + line ,e);
//            e.printStackTrace();
//        }
//    }
//}