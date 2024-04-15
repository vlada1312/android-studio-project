package ru.mirea.sosnovskayave.mireaproject;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link profileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class profileFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public profileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment profileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static profileFragment newInstance(String param1, String param2) {
        profileFragment fragment = new profileFragment();
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
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        SharedPreferences preferences = getActivity().getSharedPreferences("mirea_settings",	Context.MODE_PRIVATE);
        EditText catName = view.findViewById(R.id.name);
        EditText catBreed = view.findViewById(R.id.Breed);
        EditText catOld = view.findViewById(R.id.old);
        EditText ownerName = view.findViewById(R.id.OwnerName);

        catName.setText(preferences.getString("name", ""));
        catBreed.setText(preferences.getString("breed", ""));
        catOld.setText(preferences.getString("old", ""));
        ownerName.setText(preferences.getString("owner", ""));

        Button button = view.findViewById(R.id.button3);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPref = getActivity().getSharedPreferences("mirea_settings",	Context.MODE_PRIVATE);
                SharedPreferences.Editor editor	= sharedPref.edit();

                EditText catName = view.findViewById(R.id.name);
                String name = catName.getText().toString();
                EditText catBreed = view.findViewById(R.id.Breed);
                String breed = catBreed.getText().toString();
                EditText catOld = view.findViewById(R.id.old);
                String old = catOld.getText().toString();
                EditText ownerName = view.findViewById(R.id.OwnerName);
                String owner = ownerName.getText().toString();

                editor.putString("name", name);
                editor.putString("breed", breed);
                editor.putString("old", old);
                editor.putString("owner", owner);

                Toast.makeText(getContext(), "успешно", Toast.LENGTH_SHORT).show();

                editor.apply();
            }
        });

        return view;
    }

}