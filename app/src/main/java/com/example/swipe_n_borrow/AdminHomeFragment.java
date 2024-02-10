package com.example.swipe_n_borrow;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.AggregateField;
import com.google.firebase.firestore.AggregateQuery;
import com.google.firebase.firestore.AggregateQuerySnapshot;
import com.google.firebase.firestore.AggregateSource;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AdminHomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AdminHomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private TextView profilelName, profileAddress, phonenumber ,profileLibrary ,borrowsNumber ,bookNumber;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AdminHomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment page2.
     */
    // TODO: Rename and change types and number of parameters
    public static AdminHomeFragment newInstance(String param1, String param2) {
        AdminHomeFragment fragment = new AdminHomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        profilelName = getActivity().findViewById(R.id.profilelName);
        profileAddress = getActivity().findViewById(R.id.profileAddress);
        phonenumber = getActivity().findViewById(R.id.phonenumber);
        profileLibrary = getActivity().findViewById(R.id.profileLibrary);
        borrowsNumber = getActivity().findViewById(R.id.borrowsNumber);
        bookNumber = getActivity().findViewById(R.id.bookNumber);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_home_fragment, container, false);
        Button logOut, EditAdmin;
        LinearLayout bookListLayout;
        TextView textViewAddress, textViewFullName, textViewLibrary, textViewPhoneNumber;
        FirebaseAuth mAuth;

        logOut = view.findViewById(R.id.logOut);

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getActivity(), Login.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
        EditAdmin = view.findViewById(R.id.editButton);

        EditAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), EditAdminProfile.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
//        EditAdmin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                FirebaseAuth.getInstance().signOut();
//                Intent intent = new Intent(getActivity(), EditAdminProfile.class);
//                startActivity(intent);
//                getActivity().finish();
//            }
//        });

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String currentId = user.getUid();



        DocumentReference reference;
        CollectionReference borrowsNumberReference ;
        CollectionReference AvailableBooksReference ;
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();

        reference =firestore.collection("Admins").document(currentId);
        borrowsNumberReference = firestore.collection("Admins").document(currentId).collection("borrowedBooksAdmin");
        AvailableBooksReference =firestore.collection("Admins").document(currentId).collection("books");

        AvailableBooksReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    int AvailableBook = task.getResult().size();
                    bookNumber.setText(String.valueOf(AvailableBook));
                } else {
                    bookNumber.setText("Null");
                }
            }
        });
        borrowsNumberReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    int borrowsCount = task.getResult().size();
                    borrowsNumber.setText(String.valueOf(borrowsCount));
                } else {
                    borrowsNumber.setText("Null");
                }
            }
        });

        reference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.getResult().exists()){

                    String nameResult = task.getResult().getString("fullName");
                    String phoneResult = task.getResult().getString("phoneNumber");
                    String addressResult = task.getResult().getString("address");
                    String libraryResult = task.getResult().getString("library");

                    profileAddress.setText(addressResult);
                    profilelName.setText(nameResult);
                    phonenumber.setText(phoneResult);
                    profileLibrary.setText(libraryResult);

                }else{
                    profileAddress.setText("Null");
                    profilelName.setText("Null");
                    phonenumber.setText("Null");
                    profileLibrary.setText("Null");

                }
            }
        });

    }
}