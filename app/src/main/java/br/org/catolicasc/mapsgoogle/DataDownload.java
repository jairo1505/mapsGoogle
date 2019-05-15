package br.org.catolicasc.mapsgoogle;


import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class DataDownload extends AsyncTask<String, Void, String> {
    private static final String TAG = "DataDownload";

    @Override
    protected String doInBackground(String... strings) {
        String jsonFeed = downloadJson(strings[0]);
        if (jsonFeed == null) {
            //Toast.makeText(MainActivity.this, "Erro ao baixar arquivo", Toast.LENGTH_SHORT).show();
        }
        return jsonFeed;
    }

    private String downloadJson(String urlString) {
        Log.d(TAG, "downloadJson: começou");
        StringBuilder json = new StringBuilder();
        try {
            Log.d(TAG, "downloadJson: início do try");
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            int resposta = connection.getResponseCode();
            Log.d(TAG, "downloadJson: resposta " + resposta);

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));

            int charsLidos;
            char[] inputBuffer = new char[5];
            while (true) {
                charsLidos = reader.read(inputBuffer);
                if (charsLidos < 0) {
                    break;
                }
                if (charsLidos > 0) {
                    json.append(
                            String.copyValueOf(inputBuffer, 0, charsLidos));
                }
            }
            reader.close();
            return json.toString();

        } catch (MalformedURLException e) {
            Log.e(TAG, "downloadJson: Url inválida" + e.getMessage());
            //Toast.makeText(MainActivity.this, "URL é inválida", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Log.e(TAG, "downloadJson: erro de IO" + e.getMessage());
            // Toast.makeText(MainActivity.this, "Ocorreu um erro de IO ao baixar dados", Toast.LENGTH_SHORT).show();
        }
        return null;
    }
}