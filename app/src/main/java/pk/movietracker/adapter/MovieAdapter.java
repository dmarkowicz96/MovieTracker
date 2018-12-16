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

public class MovieAdapter extends ArrayAdapter<Movie> {
    private ArrayList<Movie> movieList;
    private Context mContext;
    private MovieDAO movieDAO;

    public MovieAdapter(@NonNull Context context, ArrayList<Movie> list) {
        super(context, 0, list);
        mContext = context;
        movieList = list;
        movieDAO = new MovieDAO();
    }

    public void refresh(ArrayList<Movie> arrayList) {
        arrayList.clear();
        arrayList.addAll(arrayList);
        notifyDataSetChanged();
    }

    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {
        View listItem = convertView;

        if(listItem == null) {
            listItem = LayoutInflater.from(getContext()).inflate(R.layout.movie, parent, false);
        }

        Movie student = movieList.get(position);
        TextView textView = listItem.findViewById(R.id.movieTextItem);
        textView.setText(student.getName() );

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFilm(position);
            }
        });


        final ImageView imageButton = listItem.findViewById(R.id.movieFavButton);
        updateFavoriteImage(imageButton,position);
        imageButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                changeFavoriteState(imageButton, position);
            }
        });


        return listItem;
    }

    private void changeFavoriteState(ImageView imageView, int position) {
        Movie movie = movieList.get(position);
        if(movie.isFavorite()) {
            movieList.get(position).setFavorite(false);
        } else {
            movieList.get(position).setFavorite(true);
        }

        updateFavoriteImage(imageView,position);
        movieDAO.updateState(movie);
    }

    private void updateFavoriteImage(ImageView imageView, int position) {
        Movie movie = movieList.get(position);
        if(movie.isFavorite()) {
            imageView.setImageResource(android.R.drawable.btn_star_big_on);
        } else {
            imageView.setImageResource(android.R.drawable.btn_star_big_off);
        }
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
