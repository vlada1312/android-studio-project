package ru.mirea.sosnovskayave.mireaproject;

import android.media.Image;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Locale;
import com.squareup.picasso.Picasso;



public class getCatFotoFromAPI extends AsyncTask<String, Void, String>  {
    String breed;
    ImageView imageView;

    public getCatFotoFromAPI(String breed, ImageView imageView) {
        this.breed = breed;
        this.imageView = imageView;
    }

    @Override
    protected String doInBackground(String... strings) {
        InputStream inputStream = null;
        String data = "";

        try {
            if (breed == "") {
                return "Не удалось получить данные";
            }

            String catUrl = String.format("https://api.thecatapi.com/v1/images/search??key=live_fSN6WCs15GrjlHOUSEvWg94mS5pWXzkM1x2WV26FMXXs3OULwYSenWrL9RC6u0cj&breed_ids=%s", breed);

            Log.d("task", "Requesting from URL: " + catUrl);
            URL url = new URL(catUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(1000);
            connection.setConnectTimeout(1000);
            connection.setRequestMethod("GET");
            connection.setInstanceFollowRedirects(true);
            connection.setUseCaches(false);
            connection.setDoInput(true);
            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) { // 200 OK
                inputStream = connection.getInputStream();
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                int read = 0;
                while ((read = inputStream.read()) != -1) {
                    bos.write(read);
                }
                bos.close();
                data = bos.toString();
            } else {
                data = connection.getResponseMessage() + ". Error Code: " + responseCode;
            }
            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return data;
    }
    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        if (result == null || result.isEmpty()) {
            Toast.makeText(imageView.getContext(), "Ошибка получения данных", Toast.LENGTH_SHORT).show();
            return;
        }

        Log.d("Response", "Response: " + result);
        try {
            JSONArray jsonArray = new JSONArray(result);
            if (jsonArray.length() > 0) {
//                JSONObject cat = jsonObject.getJSONObject("url");
//                String imageUrl = jsonObject.getString("url");

                JSONObject firstItem = jsonArray.getJSONObject(0);
                String url = firstItem.getString("url");
                Picasso.get().load(url).into(imageView);

//                String text = currentWeather.getJSONObject("condition").getString("text");
//                double tempC = currentWeather.getDouble("temp_c");
//                double windKph = currentWeather.getDouble("wind_kph");
//                int humidity = currentWeather.getInt("humidity");

//                String weather = String.format(Locale.US,"Погода: %s, температура: %s, скорость ветра: %s, влажность: %s", text, tempC, windKph, humidity);

                Toast.makeText(imageView.getContext(), "Фото получено", Toast.LENGTH_SHORT).show();
//                textViewTemperature.setText(weather);
            } else {
//                textViewTemperature.setText("Информация о погоде недоступна");
            }
        } catch (JSONException e) {
            Log.e("FetchWeatherTask", "Error parsing weather data", e);
//            textViewTemperature.setText("Ошибка парсинга данных о погоде");
        }
    }
}
