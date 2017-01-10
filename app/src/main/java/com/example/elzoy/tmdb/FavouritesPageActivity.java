package com.example.elzoy.tmdb;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.elzoy.tmdb.adapters.MoviesAdapter;
import com.example.elzoy.tmdb.classes.JSONtask;
import com.example.elzoy.tmdb.database.DBHelper;
import com.example.elzoy.tmdb.interfaces.AsyncResponse;
import com.example.elzoy.tmdb.models.Movie;

import java.util.ArrayList;
import java.util.List;

public class FavouritesPageActivity extends AppCompatActivity implements AsyncResponse {
    DBHelper db;
    JSONtask task;
    ArrayList<Movie> movies = new ArrayList<Movie>();
    ImageButton mainButton = null;
    ImageButton genresButton = null;
    ImageButton favouritesButton = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites_page);
        mainButton = (ImageButton)findViewById(R.id.mainButton);
        genresButton = (ImageButton)findViewById(R.id.genresButton);
        favouritesButton = (ImageButton)findViewById(R.id.favouritesButton);

        mainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FavouritesPageActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        genresButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FavouritesPageActivity.this, GenrePageActivity.class);
                startActivity(intent);
            }
        });

        favouritesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FavouritesPageActivity.this, FavouritesPageActivity.class);
                startActivity(intent);
            }
        });

        db = new DBHelper(this);
        List<String> ids = db.getAllCotacts();
        for(int i = 0; i < ids.size(); i++) {
            task = new JSONtask();
            task.delegate = this;
            task.execute("movie", "https://api.themoviedb.org/3/movie/" + ids.get(i) + "?api_key=d913a97a6a97ee3d9d233004f651bbdd&language=en-US");
        }
        final MoviesAdapter adapter = new MoviesAdapter(this, movies);
        adapter.notifyDataSetChanged();
        ListView lv = (ListView) findViewById(R.id.movies);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Movie item = adapter.getItem(i);

                Intent intent = new Intent(FavouritesPageActivity.this, MoviePageActivity.class);
                //based on item add info to intent

                intent.putExtra("id", "" + item.getId());
                startActivity(intent);
            }
        });
    }

    @Override
    public void processFinished(Object result) {
        movies.add((Movie) result);
        System.out.println(movies);
    }
}
