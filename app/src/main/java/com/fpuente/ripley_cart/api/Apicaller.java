package com.fpuente.ripley_cart.api;


import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;


public class Apicaller extends AsyncTask<JSONObject, Void, AsyncTaskResult<JSONObject>> {

    private static final String TAG = "Apicaller";
    private static final int HTTP_TIMEOUT = 50000;


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected AsyncTaskResult<JSONObject> doInBackground(JSONObject... params)  {
        JSONObject data = new JSONObject();
        String url;
        String method;
        String body;
        String auth;
        URL obj;
        HttpURLConnection con = null;
        try {
            JSONObject parametros = params[0];
            Log.d(TAG,parametros.toString());
            url = parametros.getString("url");
            method = (parametros.has("method"))?parametros.getString("method"):"GET";
            body = (parametros.has("body"))?parametros.getString("body"):"{ }";
            auth = (parametros.has("authorization"))?parametros.getString("authorization"):null;

            if ("GET".equals(method)) {
                Log.d(TAG, method + ": " + url);
                obj = new URL(url);
                con = (HttpURLConnection) obj.openConnection();
                con.setConnectTimeout(HTTP_TIMEOUT);
                con.setReadTimeout(HTTP_TIMEOUT);
                con.setRequestMethod(method);
                con.setRequestProperty("Cache-Control", "no-cache");
                con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                con.setRequestProperty("Accept", "application/json");
                if (auth != null) con.setRequestProperty("authorization", auth);
                int responseCode = con.getResponseCode();
                Log.d(TAG, method + "CODE: " + responseCode);
                BufferedReader in;
                if (responseCode == 200) {
                    in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                } else {
                    in = new BufferedReader(new InputStreamReader(con.getErrorStream()));
                }
                String inputLine;
                StringBuilder response = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                Log.d(TAG, method + "DATA:: " + response.toString());
                data.put("code", responseCode);
                data.put("body", new JSONArray(response.toString()));
                return new AsyncTaskResult<>(data);
            } else {
                Log.d(TAG, method + ": " + url);
                obj = new URL(url);
                con = (HttpURLConnection) obj.openConnection();
                con.setConnectTimeout(HTTP_TIMEOUT);
                con.setReadTimeout(HTTP_TIMEOUT);
                con.setRequestMethod(method);
                con.setRequestProperty("Cache-Control", "no-cache");
                con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                con.setRequestProperty("Accept", "application/json");
                if (auth != null) con.setRequestProperty("authorization", auth);
                con.setDoOutput(true);

                Writer wr;


                    wr = new OutputStreamWriter(
                            con.getOutputStream(), StandardCharsets.UTF_8);
                    wr.write(body);
                    wr.flush();
                    wr.close();



                int responseCode = con.getResponseCode();
                Log.d(TAG, method + "CODE: " + responseCode);
                BufferedReader in;
                if (responseCode == 200 || responseCode == 201) {
                    in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                } else {
                    in = new BufferedReader(new InputStreamReader(con.getErrorStream()));

                }
                String inputLine;
                StringBuilder response = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                Log.d(TAG, method + "DATA:: " + response.toString());

                data.put("code", responseCode);
                data.put("body", new JSONObject(response.toString()));
                return new AsyncTaskResult<>(data);
            }
        } catch (IOException | JSONException ex) {
            Log.e(TAG, ex.getMessage(),ex);
            return new AsyncTaskResult<>(ex);
        } finally{
            if (con!=null) con.disconnect();
        }

    }

    @Override
    protected void onPostExecute(AsyncTaskResult<JSONObject> result){
        super.onPostExecute(result);
        Log.e(TAG, result.toString());

    }
}








