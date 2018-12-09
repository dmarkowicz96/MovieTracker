package pk.movietracker.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import pk.movietracker.R;
import pk.movietracker.model.Movie;

public class FavoriteAdapter extends ArrayAdapter<Movie> {
    private ArrayList<Movie> movieList;
    private Context mContext;

    public FavoriteAdapter(@NonNull Context context, ArrayList<Movie> list) {
        super(context, 0, list);
        mContext = context;
        movieList = list;
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
        // remove (from db&list) favorite
    }
}
