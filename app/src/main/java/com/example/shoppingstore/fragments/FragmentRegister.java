package com.example.shoppingstore.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.shoppingstore.PersonModel;
import com.example.shoppingstore.R;
import com.example.shoppingstore.activities.StoreActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentRegister#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentRegister extends Fragment {

    private Activity context;
    private TextInputEditText fullnameInput, usernameInput, phoneInput, passwordInput;

    FirebaseDatabase database;
    DatabaseReference reference;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentRegister() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentRegister.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentRegister newInstance(String param1, String param2) {
        FragmentRegister fragment = new FragmentRegister();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        context = getActivity();

        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_register, container, false);

        fullnameInput = view.findViewById(R.id.fullnameInput);
        usernameInput = view.findViewById(R.id.usernameInput);
        phoneInput = view.findViewById(R.id.phoneInput);
        passwordInput = view.findViewById(R.id.passwordInput);

        Button register = (Button) view.findViewById(R.id.registerBtn);
        Button login = (Button) view.findViewById(R.id.toLogbtn);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isAllFieldsChecked = checkInput();
                if (isAllFieldsChecked) {

                    database = FirebaseDatabase.getInstance();
                    reference = database.getReference("users");

                    String name = fullnameInput.getText().toString();
                    String username = usernameInput.getText().toString().trim();
                    String phone = phoneInput.getText().toString().trim();
                    String password = passwordInput.getText().toString();

                    PersonModel user = new PersonModel(name, username, phone, password);
                    reference.child(username).setValue(user);


                    Intent intent = new Intent(context, StoreActivity.class);
                    intent.putExtra("username",username);
                    startActivity(intent);
                }
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_fragmentRegister_to_fragmentLogin);
            }
        });

        return view;
    }
    private boolean checkInput() {
        if (fullnameInput.length() == 0) {
            fullnameInput.setError("This field is required");
            return false;
        }else if (fullnameInput.length() < 2) {
            fullnameInput.setError("Your full name is too short");
            return false;
        }
        if (usernameInput.length() == 0) {
            usernameInput.setError("This field is required");
            return false;
        }else if (usernameInput.length() < 5) {
            usernameInput.setError("Your username is too short");
            return false;
        }else if (usernameInput.length() >10) {
            usernameInput.setError("Your username is too long");
            return false;
        }

        if (phoneInput.length() < 10) {
            phoneInput.setError("Valid phone number is required");
            return false;
        } else if (phoneInput.length() > 11) {
            phoneInput.setError("Valid phone number is required");
            return false;
        }
        if (passwordInput.length() == 0) {
            passwordInput.setError("Password is required");
            return false;
        } else if (passwordInput.length() < 6) {
            passwordInput.setError("Password must be minimum 6 characters");
            return false;
        }else if (passwordInput.length() > 12) {
            passwordInput.setError("Password must be maximum 12 characters");
            return false;
        }

        // after all validation return true.
        return true;
    }
}