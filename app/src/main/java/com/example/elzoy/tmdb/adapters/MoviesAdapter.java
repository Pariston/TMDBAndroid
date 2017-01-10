package com.example.elzoy.tmdb.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.elzoy.tmdb.R;
import com.example.elzoy.tmdb.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by elzoy on 08.01.2017.
 */

public class MoviesAdapter extends ArrayAdapter<Movie> {

    public MoviesAdapter(Context context, ArrayList<Movie> movies) {
        super(context, 0, movies);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Movie movie = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.listrow, parent, false);
        }

        TextView movie_title = (TextView) convertView.findViewById(R.id.movie_title);
        TextView movie_overview = (TextView) convertView.findViewById(R.id.movie_overview);
        ImageView movie_poster = (ImageView) convertView.findViewById(R.id.movie_poster);

        movie_title.setText(movie.getOriginal_title());
        movie_overview.setText(movie.getOverview());
        Picasso.with(getContext()).load("https://image.tmdb.org/t/p/w185_and_h278_bestv2" + movie.getPoster_path()).into(movie_poster);

        return convertView;
    }

    @Override
    public int getPosition(Movie item) {
        return super.getPosition(item);
    }
}

