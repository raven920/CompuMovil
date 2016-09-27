package co.edu.udea.compumovil.gr1.lab3weather.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;

import co.edu.udea.compumovil.gr1.lab3weather.MainActivity;
import co.edu.udea.compumovil.gr1.lab3weather.R;
import co.edu.udea.compumovil.gr1.lab3weather.Services.WeatherService;


/**
 * A simple {@link Fragment} subclass.
 */
public class settings extends Fragment {
    Spinner spinnerTime;
    AutoCompleteTextView autoc;
    private String[] times;
    private String[] ciudades;
     Button btnEnviar;
    public settings() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View thisview=inflater.inflate(R.layout.fragment_settings, container, false);
        spinnerTime=(Spinner) thisview.findViewById(R.id.spinner_time);
        times = getResources().getStringArray(R.array.spinner_time);
        ArrayAdapter<String> adaptertiempo= new ArrayAdapter<String>(this.getActivity(),android.R.layout.simple_spinner_item, times);
        adaptertiempo.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinnerTime.setAdapter(adaptertiempo);

        autoc=(AutoCompleteTextView) thisview.findViewById(R.id.Ciudades);
        ciudades = getResources().getStringArray(R.array.ciudades_array);
        ArrayAdapter<String> adapterciudades= new ArrayAdapter<String>(this.getActivity(),android.R.layout.simple_list_item_1, ciudades);

        autoc.setThreshold(1);
        autoc.setAdapter(adapterciudades);

        btnEnviar=(Button) thisview.findViewById(R.id.btn_enviar);
        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getActivity(), WeatherService.class);
                i.putExtra(MainActivity.TIME_TAG,Integer.parseInt(spinnerTime.getSelectedItem().toString()));
                i.putExtra(MainActivity.CITY_TAG,autoc.getText().toString());
                getActivity().startService(i);
                Fragment fragment = new weatherFr();
                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.content_main, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });

        return thisview;
    }


}
