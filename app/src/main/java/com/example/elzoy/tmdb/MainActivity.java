package com.example.elzoy.tmdb;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.elzoy.tmdb.adapters.MoviesAdapter;
import com.example.elzoy.tmdb.classes.JSONtask;
import com.example.elzoy.tmdb.interfaces.AsyncResponse;
import com.example.elzoy.tmdb.models.Movie;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AsyncResponse {
    private final String url = "https://api.themoviedb.org/3/movie/popular?api_key=d913a97a6a97ee3d9d233004f651bbdd&language=en-US&page=1";
    JSONtask task = new JSONtask();
    ViewGroup layout = null;
    ImageButton mainButton = null;
    ImageButton genresButton = null;
    ImageButton favouritesButton = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String something = "Hello darkness, my old friend.";
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        layout = (ViewGroup)findViewById(R.id.activity_main);
        mainButton = (ImageButton)findViewById(R.id.mainButton);
        genresButton = (ImageButton)findViewById(R.id.genresButton);
        favouritesButton = (ImageButton)findViewById(R.id.favouritesButton);

        mainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        genresButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GenrePageActivity.class);
                startActivity(intent);
            }
        });

        favouritesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FavouritesPageActivity.class);
                startActivity(intent);
            }
        });

        task.delegate = this;

        if(getIntent().hasExtra("genre_id")) {
            task.execute("movies", "https://api.themoviedb.org/3/genre/" + getIntent().getStringExtra("genre_id") +"/movies?api_key=d913a97a6a97ee3d9d233004f651bbdd&language=en-US&include_adult=false&sort_by=created_at.asc");
        } else {
            task.execute("movies", url);
        }
    }

    @Override
    public void processFinished(Object result) {
        final ArrayList<Movie> movies = new ArrayList<Movie>();
        final MoviesAdapter adapter = new MoviesAdapter(this, (ArrayList<Movie>) result);
        adapter.notifyDataSetChanged();
        ListView lv = (ListView) findViewById(R.id.movies);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Movie item = adapter.getItem(i);

                Intent intent = new Intent(MainActivity.this, MoviePageActivity.class);
                //based on item add info to intent

                intent.putExtra("id", "" + item.getId());
                startActivity(intent);
            }
        });
    }
}
