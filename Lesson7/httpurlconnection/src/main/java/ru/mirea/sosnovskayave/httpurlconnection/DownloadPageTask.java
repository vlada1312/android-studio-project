package ru.mirea.sosnovskayave.httpurlconnection;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Locale;
import java.util.Objects;

public class DownloadPageTask extends AsyncTask<String, Void, String> {
    private String country;
    private String city;
    private TextView textViewTemperature; // TextView для отображения погоды

    public DownloadPageTask(String country, String city, TextView textViewTemperature) {
        this.country = country;
        this.city = city;
        this.textViewTemperature = textViewTemperature;
    }

    private double[] getCoordinates(String country, String city) {
        Log.d("FetchWeatherTask", "country: " + country + " city " + city);
        if (Objects.equals(country, "Russia")) {
            if (Objects.equals(city, "Moscow")) {
                return new double[]{55.7558, 37.6173}; // Координаты Москвы
            } else if (Objects.equals(city, "St. Petersburg")) {
                return new double[]{59.9343, 30.3351}; // Координаты Санкт-Петербурга
            } else if (Objects.equals(city, "Novosibirsk")) {
                return new double[]{55.0084, 82.9357}; // Координаты Новосибирска
            } else if (Objects.equals(city, "Ekaterinburg")) {
                return new double[]{56.8389, 60.6057}; // Координаты Екатеринбурга
            } else if (Objects.equals(city, "Kazan")) {
                return new double[]{55.8304, 49.0661}; // Координаты Казани
            } else if (Objects.equals(city, "Nizhny Novgorod")) {
                return new double[]{56.2965, 43.9361}; // Координаты Нижнего Новгорода
            }
        }
        return new double[]{55.7558, 37.6173};
    }

    @Override
    protected String doInBackground(String... strings) {
        InputStream inputStream = null;
        String data = "";
        try {
            // Получаем координаты по стране и городу (функция getCoordinates() должна быть реализована вами)
            double[] coordinates = getCoordinates(country, city);
            if (coordinates == null) {
                return "Не удалось получить координаты";
            }
            String weatherUrl = String.format("https://api.weatherapi.com/v1/current.json?key=d4a06f6371664617b4d133056242304&q=%s,%s", coordinates[0], coordinates[1]);

            Log.d("FetchWeatherTask", "Requesting weather from URL: " + weatherUrl);
            URL url = new URL(weatherUrl);
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
            Toast.makeText(textViewTemperature.getContext(), "Ошибка получения данных о погоде", Toast.LENGTH_SHORT).show();
            return;
        }

        Log.d("WeatherResponse", "Response: " + result);
        try {
            JSONObject jsonObject = new JSONObject(result);
            if (jsonObject.has("current")) {
                JSONObject currentWeather = jsonObject.getJSONObject("current");

                String text = currentWeather.getJSONObject("condition").getString("text");
                double tempC = currentWeather.getDouble("temp_c");
                double windKph = currentWeather.getDouble("wind_kph");
                int humidity = currentWeather.getInt("humidity");
                String weather = String.format(Locale.US,"Погода: %s, температура: %s, скорость ветра: %s, влажность: %s", text, tempC, windKph, humidity);
                Toast.makeText(textViewTemperature.getContext(), "Погода получена", Toast.LENGTH_SHORT).show();
                textViewTemperature.setText(weather);
            } else {
                textViewTemperature.setText("Информация о погоде недоступна");
            }
        } catch (JSONException e) {
            Log.e("FetchWeatherTask", "Error parsing weather data", e);
            textViewTemperature.setText("Ошибка парсинга данных о погоде");
        }
    }
}
