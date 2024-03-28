package ru.mirea.sosnovskayave.mireaproject;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DataFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DataFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DataFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DataFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DataFragment newInstance(String param1, String param2) {
        DataFragment fragment = new DataFragment();
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
        View view = inflater.inflate(R.layout.fragment_data, container, false);
        TextView textView = view.findViewById(R.id.textView1);
        textView.setText("Отрасть товаров для домашних животных");
        textView = view.findViewById(R.id.textView2);
        textView.setText("Отрасль товаров для домашних животных предлагает широкий ассортимент продуктов и услуг для ухода, " +
                "развлечения и обеспечения комфорта домашних питомцев. Эта отрасль актуальна из-за постоянного роста количества людей, которые " +
                "содержат домашних животных и стремлятся обеспечить своим питомцам лучшие условия жизни.");
        textView = view.findViewById(R.id.textView3);
        textView.setText("Ключевые сегменты отрасли:");
        textView = view.findViewById(R.id.textView4);
        textView.setText("Продукты питания (корма, лакомства, витамины)");
        textView = view.findViewById(R.id.textView5);
        textView.setText("Товары для ухода (шампуни, расчески, наполнители)");
        textView = view.findViewById(R.id.textView6);
        textView.setText("Мебель и аксессуары (домики, лежанки, переноски, одежда и ошейники)");
        textView = view.findViewById(R.id.textView7);
        textView.setText("Услуги (груминг, ветеринарные услуги, питомникио и тели для животных)");
        textView = view.findViewById(R.id.textView8);
        textView.setText("Эта отрасль постоянно развивается и инновации в области продуктов и услуг для котов становятся все более популярными.");
        ;

        return view;
    }
}