package com.example.elzoy.tmdb.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.elzoy.tmdb.GenrePageActivity;
import com.example.elzoy.tmdb.MoviePageActivity;
import com.example.elzoy.tmdb.R;
import com.example.elzoy.tmdb.models.Genre;

import java.util.ArrayList;

/**
 * Created by elzoy on 09.01.2017.
 */

public class GenresAdapter  extends ArrayAdapter<Genre> {
    Context context;
    public GenresAdapter(Context context, ArrayList<Genre> genres) {
        super(context, 0, genres);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Genre genre = getItem(position);

        if (convertView == null) {
            if(context.getClass().equals(MoviePageActivity.class)) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.movie_page_genreslist, parent, false);
            } else if(context.getClass().equals(GenrePageActivity.class)) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.genreslist, parent, false);
            }
        }

        TextView genre_name = (TextView) convertView.findViewById(R.id.genre_name);
        genre_name.setText(genre.getName());

        return convertView;
    }

    @Override
    public int getPosition(Genre item) {
        return super.getPosition(item);
    }
}
