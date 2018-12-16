package pk.movietracker.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import pk.movietracker.R;
import pk.movietracker.db.dao.MovieDAO;
import pk.movietracker.model.Movie;

public class FavoriteAdapter extends ArrayAdapter<Movie> {
    private ArrayList<Movie> movieList;
    private Context mContext;
    private MovieDAO movieDAO;

    public FavoriteAdapter(@NonNull Context context, ArrayList<Movie> list) {
        super(context, 0, list);
        mContext = context;
        movieList = list;
        movieDAO = new MovieDAO();
    }

    public void refresh() {
        movieList.clear();
        movieList.addAll(movieDAO.getFavoriteMovies());
        notifyDataSetChanged();
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View listItem = convertView;

        if(listItem == null) {
            listItem = LayoutInflater.from(getContext()).inflate(R.layout.favorite, parent, false);
        }

        Movie student = movieList.get(position);
        TextView textView = listItem.findViewById(R.id.favoriteTextItem);
        textView.setText(student.getName() );

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFilm(position);
            }
        });


        ImageView imageButton = listItem.findViewById(R.id.favoriteDelButton);
        imageButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                removeFavorite(position);
            }
        });

        return listItem;
    }

    private void removeFavorite(int position) {
        Movie movie = movieList.get(position);
        movie.setFavorite(false);
        movieDAO.updateState(movie);
        movieList.remove(movie);
        notifyDataSetChanged();
    }

    private void showFilm(int position) {
        Movie movie = movieList.get(position);

        Intent intent = new Intent(mContext, pk.movietracker.activity.Movie.class);
        Bundle bundle = new Bundle();
        bundle.putLong("movieId",movie.getId());

        intent.putExtras(bundle);
        mContext.startActivity(intent);
    }
}
