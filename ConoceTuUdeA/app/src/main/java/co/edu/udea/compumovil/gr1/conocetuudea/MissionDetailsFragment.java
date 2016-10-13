package co.edu.udea.compumovil.gr1.conocetuudea;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import co.edu.udea.compumovil.gr1.conocetuudea.domain.db.Mission;
import co.edu.udea.compumovil.gr1.conocetuudea.domain.util.MissionsProvider;


/**
 * A simple {@link Fragment} subclass.
 */
public class MissionDetailsFragment extends Fragment {

    private int missionId;
    private ImageView image;
    private TextView name;
    private TextView desc;

    public MissionDetailsFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_mission_detail, container, false);
        image = (ImageView) v.findViewById(R.id.mission_dimage);
        name = (TextView) v.findViewById(R.id.mission_dname);
        desc = (TextView) v.findViewById(R.id.mission_ddesc);

        missionId = getArguments().getInt("missionId");

        Mission m = MissionsProvider.getMissions()[missionId];

        image.setImageResource(m.getImageId());
        name.setText(m.getName());
        desc.setText(m.getDescription());
        return v;
    }

}
