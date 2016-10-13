package co.edu.udea.compumovil.gr1.conocetuudea;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;

import co.edu.udea.compumovil.gr1.conocetuudea.domain.db.User;


/**
 * A simple {@link Fragment} subclass.
 */
public class RankingFragment extends ListFragment {

    private DatabaseReference mDatabase;

    public RankingFragment() {
        // Required empty public constructor
    }

/*
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ranking, container, false);
    }*/

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        Query q = mDatabase.child("users");

        getListView().addHeaderView(getLayoutInflater(savedInstanceState).inflate(R.layout.ranking_header, null));


        q.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User[] usrs = new User[(int)dataSnapshot.getChildrenCount()];

                int i = 0;


                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {

                    User u = postSnapshot.getValue(User.class);
                    usrs[i] = u;
                    i++;

                }

                Arrays.sort(usrs);

                setListAdapter(new MySimpleArrayAdapter(getActivity(), usrs));

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message

                // ...
            }
        });

        //MySimpleArrayAdapter adapter = new MySimpleArrayAdapter(getActivity(), values);
        //setListAdapter(adapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        // TODO implement some logic
    }

    public class MySimpleArrayAdapter extends ArrayAdapter {
        private final Context context;
        private final User[] users;

        public MySimpleArrayAdapter(Context context, User[] users) {
            super(context, -1, users);
            this.context = context;
            this.users = users;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.ranking_item, parent, false);
            TextView name = (TextView) rowView.findViewById(R.id.rank_name);
            TextView score = (TextView) rowView.findViewById(R.id.rank_score);
            ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
            name.setText(users[position].getUsername());
            score.setText(users[position].getScore()+"");


            return rowView;
        }
    }

}
