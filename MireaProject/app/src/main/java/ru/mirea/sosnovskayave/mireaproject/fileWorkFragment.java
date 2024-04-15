package ru.mirea.sosnovskayave.mireaproject;

import static android.graphics.Bitmap.CompressFormat.JPEG;
import static android.graphics.Bitmap.CompressFormat.PNG;
import static android.graphics.Bitmap.CompressFormat.WEBP;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fileWorkFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fileWorkFragment extends Fragment {
    private ImageView imageViewCat;
    private Spinner spinnerImageFormat;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public fileWorkFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fileWorkFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static fileWorkFragment newInstance(String param1, String param2) {
        fileWorkFragment fragment = new fileWorkFragment();
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
        View view = inflater.inflate(R.layout.fragment_file_work, container, false);

        imageViewCat = view.findViewById(R.id.imageViewCat);
        FloatingActionButton buttonLoadImage = view.findViewById(R.id.floatingActionButton);
        buttonLoadImage.setOnClickListener(v -> selectImage());

        spinnerImageFormat = view.findViewById(R.id.spinner);
        Button buttonConvertImage = view.findViewById(R.id.buttonConvertImage);
        buttonConvertImage.setOnClickListener(v -> convertAndSaveImage());
        return view;
    }

    private void selectImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 100);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == Activity.RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            imageViewCat.setImageURI(imageUri);
        }
    }

    private void convertAndSaveImage() {
        Bitmap bitmap = ((BitmapDrawable) imageViewCat.getDrawable()).getBitmap();
        Bitmap.CompressFormat format = getFormat(spinnerImageFormat.getSelectedItem().toString());
        try {
            File file = new File(getActivity().getExternalFilesDir(null), "cat_" + System.currentTimeMillis() + getExtension(format));
            try (FileOutputStream out = new FileOutputStream(file)) {
                bitmap.compress(format, 100, out);
                Toast.makeText(getActivity(), "Изображение сохранено в " + format, Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            Toast.makeText(getActivity(), "Ошибка при сохранении", Toast.LENGTH_SHORT).show();
        }
    }

    private Bitmap.CompressFormat getFormat(String format) {
        switch (format) {
            case "JPEG": return Bitmap.CompressFormat.JPEG;
            case "PNG": return Bitmap.CompressFormat.PNG;
            case "WEBP": return Bitmap.CompressFormat.WEBP;
            default: return Bitmap.CompressFormat.JPEG;
        }
    }

    private String getExtension(Bitmap.CompressFormat format) {
        switch (format) {
            case JPEG: return ".jpg";
            case PNG: return ".png";
            case WEBP: return ".webp";
            default: return ".jpg";
        }
    }
}