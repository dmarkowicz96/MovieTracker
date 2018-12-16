package pk.movietracker.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import pk.movietracker.R;
import pk.movietracker.activity.Calendar;
import pk.movietracker.activity.Favorites;

public class Menu extends Fragment {

    public Menu() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_menu, container, false);

        initializeAuthorButton(view);
        initializeShowMovieButton(view);
        initializeShowFavoriteButton(view);

        return view;
    }


    private void initializeAuthorButton(View view) {
        final View button = view.findViewById(R.id.authorsButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction() .replace(R.id.mainMenuFrame, new Authors())
                        .commit();
            }
        });
    }

    private void initializeShowMovieButton(View view) {
        final View button = view.findViewById(R.id.showMovieButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Calendar.class);
                startActivity(intent);
            }
        });
    }

    private void initializeShowFavoriteButton(View view) {
        final View button = view.findViewById(R.id.showFavoriteButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Favorites.class);
                startActivity(intent);
            }
        });
    }

}
