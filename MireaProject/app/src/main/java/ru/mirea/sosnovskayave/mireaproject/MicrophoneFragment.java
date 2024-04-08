package ru.mirea.sosnovskayave.mireaproject;

import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.io.File;
import java.io.IOException;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MicrophoneFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MicrophoneFragment extends Fragment {
    private	boolean	isWork = false;
    private	static final int REQUEST_CODE_PERMISSION = 100;
    private Button button1;
    private Button button2;
    private	String	recordFilePath = null;
    private Button recordButton =	null;
    private Button playButton =	null;
    private MediaRecorder recorder = null;
    private MediaPlayer player = null;
    boolean	isStartRecording = true;
    boolean	isStartPlaying = true;
    private	final String TAG = MainActivity.class.getSimpleName();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MicrophoneFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MicrophoneFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MicrophoneFragment newInstance(String param1, String param2) {
        MicrophoneFragment fragment = new MicrophoneFragment();
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
        View view = inflater.inflate(R.layout.fragment_microphone, container, false);
        recordButton =	view.findViewById(R.id.button);
        playButton = view.findViewById(R.id.button2);

        playButton.setEnabled(false);
        recordFilePath	= (new File(getActivity().getExternalFilesDir(Environment.DIRECTORY_MUSIC),
                "/audiorecordtest.3gp")).getAbsolutePath();

        int	audioPermissionStatus = ContextCompat.checkSelfPermission(getContext(),	android.Manifest.permission.RECORD_AUDIO);
        int	storagePermissionStatus	= ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if	(audioPermissionStatus	== PackageManager.PERMISSION_GRANTED && storagePermissionStatus == PackageManager.PERMISSION_GRANTED)	{
            isWork = true;
        } else {
            ActivityCompat.requestPermissions(getActivity(), new	String[] {android.Manifest.permission.RECORD_AUDIO,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_PERMISSION);
        }

        recordButton.setOnClickListener(new	View.OnClickListener()	{
            @Override
            public void onClick(View v)	{
                if	(isStartRecording) {
                    startRecording();
                    recordButton.setText("Нажмите чтобы закончить запись!");
                    playButton.setEnabled(false);
                }	else {
                    stopRecording();
                    recordButton.setText("Нажмите, чтобы записать мяуканье вашего котика!");
                    playButton.setEnabled(true);
                }
                isStartRecording = !isStartRecording;
            }
        });
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isStartPlaying) {
                    startPlaying();
                    playButton.setText("Нажмите, чтобы закончить проигрывание!");
                    recordButton.setEnabled(false);
                } else {
                    stopPlaying();
                    playButton.setText("Нажмите, чтобы воспроизвести мяуканье вашего котика!");
                    recordButton.setEnabled(true);
                }
                isStartPlaying = !isStartRecording;
            }
        });
        return view;
    }

    private	void startRecording()	{
        recorder =	new	MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setOutputFile(recordFilePath);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        try	{
            recorder.prepare();
        }	catch	(IOException e)	{
            Log.e(TAG, "Ошибка");
        }
        recorder.start();
    }

    private	void stopRecording() {
        if (recorder != null) {
            recorder.stop();
            recorder.release();
            recorder = null;
        }

    }

    private	void startPlaying() {
        player = new MediaPlayer();
        try {
            player.setDataSource(recordFilePath);
            player.prepare();
            player.start();
        } catch (IOException e) {
            Log.e(TAG, "Ошибка");
        }
    }

    private	void stopPlaying() {
        if (player != null) {
            player.release();
            player = null;
        }
    }

    @Override
    public void onRequestPermissionsResult(int	requestCode, @NonNull String[]	permissions, @NonNull int[]
            grantResults)	{
        super.onRequestPermissionsResult(requestCode,	permissions,	grantResults);
        switch	(requestCode){
            case REQUEST_CODE_PERMISSION:
                isWork = grantResults[0] ==	PackageManager.PERMISSION_GRANTED;
                break;
        }
    }
}