package ru.mirea.sosnovskayave.audiorecord;

import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.File;
import java.io.IOException;

import ru.mirea.sosnovskayave.audiorecord.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private	boolean	isWork = false;
    private	static final int REQUEST_CODE_PERMISSION = 100;
    private ActivityMainBinding binding;
    private	String	recordFilePath = null;
    private	Button	recordButton =	null;
    private Button playButton =	null;
    private MediaRecorder recorder = null;
    private MediaPlayer player = null;
    boolean	isStartRecording = true;
    boolean	isStartPlaying = true;
    private	final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        recordButton =	binding.recordButton;
        playButton = binding.playButton;
        playButton.setEnabled(false);
        recordFilePath	= (new File(getExternalFilesDir(Environment.DIRECTORY_MUSIC),
                "/audiorecordtest.3gp")).getAbsolutePath();

        int	audioPermissionStatus = ContextCompat.checkSelfPermission(this,	android.Manifest.permission.RECORD_AUDIO);
        int	storagePermissionStatus	= ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if	(audioPermissionStatus	== PackageManager.PERMISSION_GRANTED && storagePermissionStatus == PackageManager.PERMISSION_GRANTED)	{
            isWork = true;
        } else {
            // Выполняется запрос к пользователь на получение необходимых разрешений
            ActivityCompat.requestPermissions(this, new	String[] {android.Manifest.permission.RECORD_AUDIO,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_PERMISSION);
        }

        recordButton.setOnClickListener(new	View.OnClickListener()	{
            @Override
            public void onClick(View v)	{
                if	(isStartRecording) {
                    startRecording();
                    recordButton.setText("Stop recording");
                    playButton.setEnabled(false);
                }	else {
                    stopRecording();
                    recordButton.setText("Start recording");
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
                    playButton.setText("Stop playing");
                    recordButton.setEnabled(false);
                } else {
                    stopPlaying();
                    playButton.setText("Start playing");
                    recordButton.setEnabled(true);
                }
                isStartPlaying = !isStartRecording;
            }
        });
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
            Log.e(TAG, "prepare()	failed");
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
            Log.e(TAG, "prepare()	failed");
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
        // производится	проверка полученного результата	от	пользователя на	запрос	разрешения Аудио
        super.onRequestPermissionsResult(requestCode,	permissions,	grantResults);
        switch	(requestCode){
            case REQUEST_CODE_PERMISSION:
                isWork = grantResults[0] ==	PackageManager.PERMISSION_GRANTED;
                break;
        }
        if (!isWork ) finish();
    }
}