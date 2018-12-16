package pk.movietracker.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import pk.movietracker.R;

public class Authors extends Fragment {

    public Authors() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_authors, container, false);


        final View button = view.findViewById(R.id.backToMenuButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction() .replace(R.id.mainMenuFrame, new Menu())
                        .commit();
            }
        });

        return view;
    }
}
