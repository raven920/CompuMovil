package co.edu.udea.compumovil.gr1.lab2activities;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ConsoleMessage;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class InfoGeneralLugarFragment extends Fragment {


    private int placeId;

    public InfoGeneralLugarFragment() {
        // Required empty public constructor
    }

    public void setPlaceId(int placeId) {
        this.placeId = placeId;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        //LugaresFragment l = (LugaresFragment)getParentFragment();

        return inflater.inflate(R.layout.fragment_info_general_lugar, container, false);
    }

}
