package com.example.elzoy.tmdb;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.elzoy.tmdb.adapters.GenresAdapter;
import com.example.elzoy.tmdb.classes.JSONtask;
import com.example.elzoy.tmdb.database.DBHelper;
import com.example.elzoy.tmdb.interfaces.AsyncResponse;
import com.example.elzoy.tmdb.models.Genre;
import com.example.elzoy.tmdb.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MoviePageActivity extends AppCompatActivity implements AsyncResponse {
    Movie movie = null;
    JSONtask task = new JSONtask();
    ImageButton mainButton = null;
    ImageButton genresButton = null;
    ImageButton favouritesButton = null;
    Button addToFavouritesButton = null;
    String mode = "movie";
    private DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_page);
        final String id = getIntent().getStringExtra("id");
        db = new DBHelper(this);
        mainButton = (ImageButton)findViewById(R.id.mainButton);
        genresButton = (ImageButton)findViewById(R.id.genresButton);
        addToFavouritesButton = (Button)findViewById(R.id.addToFavourites);
        favouritesButton = (ImageButton)findViewById(R.id.favouritesButton);

        final Boolean isFavourite = db.getDataByMovieId(id).getCount() > 0;
        System.out.println(db.getDataByMovieId(id).getCount());
        if(isFavourite) {
            addToFavouritesButton.setText("Usuń z ulubionych");
        }

        db = new DBHelper(this);

        mainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MoviePageActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        addToFavouritesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isFavourite) {
                    db.deleteContact(id);
                    addToFavouritesButton.setText("Dodaj do ulubionych");
                } else {
                    db.insertContact(id);
                    addToFavouritesButton.setText("Usuń z ulubionych");
                }
            }
        });

        genresButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MoviePageActivity.this, GenrePageActivity.class);
                startActivity(intent);
            }
        });

        favouritesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MoviePageActivity.this, FavouritesPageActivity.class);
                startActivity(intent);
            }
        });

        task.delegate = this;
        task.execute(mode, "https://api.themoviedb.org/3/movie/" + id + "?api_key=d913a97a6a97ee3d9d233004f651bbdd&language=en-US");
    }

    @Override
    public void processFinished(Object result) {
        if(mode == "movie") {
            movie = (Movie) result;
            ImageView movie_poster = (ImageView)findViewById(R.id.movie_poster);
            TextView movie_title = (TextView)findViewById(R.id.movie_title);
            TextView movie_description = (TextView)findViewById(R.id.movie_description);
            TextView movie_vote_average = (TextView)findViewById(R.id.movie_vote_average);
            TextView movie_vote_count = (TextView)findViewById(R.id.movie_vote_count);
            TextView movie_popularity = (TextView)findViewById(R.id.movie_popularity);

            Picasso.with(this).load("https://image.tmdb.org/t/p/w185_and_h278_bestv2" + movie.getPoster_path()).into(movie_poster);
            movie_title.setText(movie.getOriginal_title());
            movie_description.setText(movie.getOverview());
            movie_description.setMovementMethod(new ScrollingMovementMethod());
            movie_vote_average.setText(""+ movie.getVote_average());
            movie_vote_count.setText(movie.getVote_count() + " voters");
            movie_popularity.setText("" + movie.getPopularity());
            mode = "genres";
            task = new JSONtask();
            task.delegate = this;
            task.execute(mode, "https://api.themoviedb.org/3/genre/movie/list?api_key=d913a97a6a97ee3d9d233004f651bbdd&language=en-US");
        } else {
            final ArrayList<Genre> genres = movie.getGenres();

            final GenresAdapter adapter = new GenresAdapter(this, genres);
            ListView lv = (ListView) findViewById(R.id.genres);
            lv.setAdapter(adapter);
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Genre item = adapter.getItem(i);

                    Intent intent = new Intent(MoviePageActivity.this, MainActivity.class);
                    //based on item add info to intent

                    intent.putExtra("genre_id", "" + item.getId());
                    startActivity(intent);
                }
            });
        }
    }
}