package ru.mirea.sosnovskayave.mireaproject;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import android.net.ConnectivityManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NetworkResourceFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NetworkResourceFragment extends Fragment {


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Spinner spinner;
    String code;

    public NetworkResourceFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NetworkResourceFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NetworkResourceFragment newInstance(String param1, String param2) {
        NetworkResourceFragment fragment = new NetworkResourceFragment();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_network_resource, container, false);

        spinner = view.findViewById(R.id.spinner2);
        Map<String, String> breedToCodeMap = new HashMap<>();
        List<String> breedNames = new ArrayList<>();

        // Добавление пород и их кодов в Map
        breedToCodeMap.put("Бенгальская кошка", "beng");
        breedToCodeMap.put("Абиссинская кошка", "abys");
        breedToCodeMap.put("Эгейская кошка", "aege");
        breedToCodeMap.put("Американский бобтейл", "abob");
        breedToCodeMap.put("Американская керл", "acur");
        breedToCodeMap.put("Американская короткошерстная", "asho");
        breedToCodeMap.put("Американская проволочная", "awir");
        breedToCodeMap.put("Аравийский мау", "amau");
        breedToCodeMap.put("Австралийский мист", "amis");
        breedToCodeMap.put("Балинезийская кошка", "bali");
        breedToCodeMap.put("Бамбино", "banb");

        // Добавление названий пород в List для отображения в Spinner
        breedNames.add("Бенгальская кошка");
        breedNames.add("Абиссинская кошка");
        breedNames.add("Эгейская кошка");
        breedNames.add("Американский бобтейл");
        breedNames.add("Американская керл");
        breedNames.add("Американская короткошерстная");
        breedNames.add("Американская проволочная");
        breedNames.add("Аравийский мау");
        breedNames.add("Австралийский мист");
        breedNames.add("Балинезийская кошка");


        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, breedNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String breedName = (String) parent.getItemAtPosition(position);
                code = breedToCodeMap.get(breedName);

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        Button button = view.findViewById(R.id.button4);
        button.setOnClickListener(new View.OnClickListener()	{
            @Override
            public void onClick(View v)	{
                ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

                NetworkInfo networkinfo = null;
                if (connectivityManager != null) {
                    networkinfo = connectivityManager.getActiveNetworkInfo();
                }
                if (networkinfo != null && networkinfo.isConnected()) {
                    ImageView imageView = view.findViewById(R.id.imageView3);
                    new getCatFotoFromAPI(code, imageView).execute();
                } else {
                    Toast.makeText(getContext(), "Нет интернета", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }
}