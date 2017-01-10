package com.example.elzoy.tmdb.classes;

import android.os.AsyncTask;

import com.example.elzoy.tmdb.interfaces.AsyncResponse;
import com.example.elzoy.tmdb.models.Genre;
import com.example.elzoy.tmdb.models.Movie;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class JSONtask extends AsyncTask<String, String, Object> {
    public AsyncResponse delegate = null;
    private String api_key = "d913a97a6a97ee3d9d233004f651bbdd";
    private String getPopularMoviesURL = "https://api.themoviedb.org/3/movie/popular?api_key=d913a97a6a97ee3d9d233004f651bbdd&language=en-US&page=undefined";

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected Object doInBackground(String... params) {
        //params[0] - category
        //params[1] - url
        if(params[0] == "movies") {
            return getMovies(params[1]);
        } else if(params[0] == "movie") {
            return getMovie(params[1]);
        } else if(params[0] == "genres") {
            return getGenres(params[1]);
        }
        else {
            return null;
        }
    }

    @Override
    protected void onPostExecute(Object result) {
        super.onPostExecute(result);
        delegate.processFinished(result);
    }

    protected ArrayList<Movie> getMovies(String apiurl) {
        HttpURLConnection connection = null;
        BufferedReader reader = null;

        try {
            URL url = new URL(apiurl);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            InputStream stream = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(stream));
            StringBuffer buffer = new StringBuffer();
            String line ="";
            while ((line = reader.readLine()) != null){
                buffer.append(line);
            }

            String finalJson = buffer.toString();

            JSONObject parentObject = new JSONObject(finalJson);
            JSONArray parentArray = parentObject.getJSONArray("results");
            Gson gson = new Gson();
            ArrayList<Movie> movies = new ArrayList<>();

            for(int i = 0; i < parentArray.length(); i++) {
                JSONObject obj = parentArray.getJSONObject(i);
                Movie movie = gson.fromJson(obj.toString(), Movie.class);
                movies.add(movie);
            }

            return movies;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            if(connection != null) {
                connection.disconnect();
            }
            try {
                if(reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return  null;
    }

    protected Movie getMovie(String apiurl) {
        HttpURLConnection connection = null;
        BufferedReader reader = null;

        try {
            URL url = new URL(apiurl);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            InputStream stream = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(stream));
            StringBuffer buffer = new StringBuffer();
            String line ="";
            while ((line = reader.readLine()) != null){
                buffer.append(line);
            }

            String finalJson = buffer.toString();

            JSONObject parentObject = new JSONObject(finalJson);
            //JSONArray parentArray = parentObject.getJSONArray("");
            Gson gson = new Gson();
            Movie movie = gson.fromJson(parentObject.toString(), Movie.class);

            return movie;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            if(connection != null) {
                connection.disconnect();
            }
            try {
                if(reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return  null;
    }

    protected ArrayList<Genre> getGenres(String apiurl) {
        HttpURLConnection connection = null;
        BufferedReader reader = null;

        try {
            URL url = new URL(apiurl);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            InputStream stream = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(stream));
            StringBuffer buffer = new StringBuffer();
            String line ="";
            while ((line = reader.readLine()) != null){
                buffer.append(line);
            }

            String finalJson = buffer.toString();

            JSONObject parentObject = new JSONObject(finalJson);
            JSONArray parentArray = parentObject.getJSONArray("genres");
            Gson gson = new Gson();
            ArrayList<Genre> genres = new ArrayList<>();

            for(int i = 0; i < parentArray.length(); i++) {
                JSONObject obj = parentArray.getJSONObject(i);
                Genre genre = gson.fromJson(obj.toString(), Genre.class);
                genres.add(genre);
            }

            return genres;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            if(connection != null) {
                connection.disconnect();
            }
            try {
                if(reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return  null;
    }
}
