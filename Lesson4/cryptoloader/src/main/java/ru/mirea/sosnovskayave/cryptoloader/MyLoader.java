package ru.mirea.sosnovskayave.cryptoloader;

import android.content.Context;
import android.os.Bundle;
import android.os.SystemClock;

import androidx.annotation.NonNull;
import androidx.loader.content.AsyncTaskLoader;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public	class MyLoader	extends AsyncTaskLoader<String> {
    public static final String ARG_WORD = "word";
    byte[] encryptedText;
    byte[] key;

    public MyLoader(@NonNull Context context, Bundle args) {
        super(context);
        if (args != null)
            encryptedText = args.getByteArray(ARG_WORD);
            key = args.getByteArray("key");
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    public static String decryptMsg(byte[] cipherText, SecretKey secret){
        try	{
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE,secret);
            return	new	String(cipher.doFinal(cipherText));
        }	catch (NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException
                       | BadPaddingException | InvalidKeyException e)	{
            throw new RuntimeException(e);
        }
    }

    @Override
    public String loadInBackground() {
        //	emulate	long-running operation
        SystemClock.sleep(5000);

        SecretKey originalKey = new SecretKeySpec(key, 0, key.length, "AES");

        try {
            return decryptMsg(encryptedText, originalKey);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}