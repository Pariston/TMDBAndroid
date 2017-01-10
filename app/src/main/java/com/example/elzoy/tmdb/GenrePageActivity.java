package com.example.elzoy.tmdb;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.elzoy.tmdb.adapters.GenresAdapter;
import com.example.elzoy.tmdb.classes.JSONtask;
import com.example.elzoy.tmdb.interfaces.AsyncResponse;
import com.example.elzoy.tmdb.models.Genre;

import java.util.ArrayList;

public class GenrePageActivity extends AppCompatActivity implements AsyncResponse {
    JSONtask task = new JSONtask();
    ImageButton mainButton = null;
    ImageButton genresButton = null;
    ImageButton favouritesButton = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genre_page);
        mainButton = (ImageButton)findViewById(R.id.mainButton);
        genresButton = (ImageButton)findViewById(R.id.genresButton);
        favouritesButton = (ImageButton)findViewById(R.id.favouritesButton);

        mainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GenrePageActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        genresButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GenrePageActivity.this, GenrePageActivity.class);
                startActivity(intent);
            }
        });

        favouritesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GenrePageActivity.this, FavouritesPageActivity.class);
                startActivity(intent);
            }
        });

        task.delegate = this;
        task.execute("genres", "https://api.themoviedb.org/3/genre/movie/list?api_key=d913a97a6a97ee3d9d233004f651bbdd&language=en-US");
    }

    @Override
    public void processFinished(Object result) {
        final ArrayList<Genre> genres = new ArrayList<Genre>();
        final GenresAdapter adapter = new GenresAdapter(this, (ArrayList<Genre>) result);
        ListView lv = (ListView) findViewById(R.id.genres);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Genre item = adapter.getItem(i);

                Intent intent = new Intent(GenrePageActivity.this, MainActivity.class);
                //based on item add info to intent

                intent.putExtra("genre_id", "" + item.getId());
                startActivity(intent);
            }
        });
    }
}
