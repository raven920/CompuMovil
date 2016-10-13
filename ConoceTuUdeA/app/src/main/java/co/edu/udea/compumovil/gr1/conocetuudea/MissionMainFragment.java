package co.edu.udea.compumovil.gr1.conocetuudea;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import co.edu.udea.compumovil.gr1.conocetuudea.domain.db.Mission;
import co.edu.udea.compumovil.gr1.conocetuudea.domain.db.User;
import co.edu.udea.compumovil.gr1.conocetuudea.domain.util.MissionsProvider;


/**
 * A simple {@link Fragment} subclass.
 */
public class MissionMainFragment extends ListFragment {


    public MissionMainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        setListAdapter(new MissionsArrayAdapter(getActivity(), MissionsProvider.getMissions()));
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Bundle b = new Bundle();
        b.putInt("missionId",position);
        MissionDetailsFragment f = new MissionDetailsFragment();
        f.setArguments(b);
        ft.replace(R.id.misionesFragmentContainer, f);

        ft.commit();
    }


    public class MissionsArrayAdapter extends ArrayAdapter {
        private final Context context;
        private final Mission[] missions;

        public MissionsArrayAdapter(Context context, Mission[] missions) {
            super(context, -1, missions);
            this.context = context;
            this.missions = missions;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.mission_item, parent, false);
            TextView name = (TextView) rowView.findViewById(R.id.mission_lname);
            name.setText("Misión "+position+": "+missions[position].getName());

            return rowView;
        }
    }

    /*

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mission_main, container, false);
    }

    */

}
