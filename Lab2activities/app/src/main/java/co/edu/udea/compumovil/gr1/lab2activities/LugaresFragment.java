package co.edu.udea.compumovil.gr1.lab2activities;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class LugaresFragment extends Fragment {

    private AppBarLayout appBar;
    private TabLayout pestanas;
    private ViewPager viewPager;

    private int placeId;


    public LugaresFragment() {
        // Required empty public constructor
    }

    public int getPlaceId() {
        return placeId;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        placeId = getArguments().getInt("place_id");
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_lugares, container, false);

        if (savedInstanceState == null) {
            insertarTabs(container);

            viewPager = (ViewPager) view.findViewById(R.id.lugaresPager);
            poblarViewPager(viewPager);
            pestanas.setupWithViewPager(viewPager);
        }


        return view;
    }

    private void insertarTabs(ViewGroup container) {
        View padre = (View) container.getParent();
        appBar = (AppBarLayout) padre.findViewById(R.id.lugaresMainAppBar);
        pestanas = new TabLayout(getActivity());
        pestanas.setTabTextColors(Color.parseColor("#FFFFFF"), Color.parseColor("#FFFFFF"));
        appBar.addView(pestanas);
    }

    private void poblarViewPager(ViewPager viewPager) {

        AdaptadorSecciones adapter = new AdaptadorSecciones(getFragmentManager());

        InfoGeneralLugarFragment igLF = new InfoGeneralLugarFragment();
        FotosLugarFragment flF= new FotosLugarFragment();
        MapLugarFragment mlF = new MapLugarFragment();
        igLF.setPlaceId(placeId);
        flF.setPlaceId(placeId);
        mlF.setPlaceId(placeId);
        adapter.addFragment(igLF, getString(R.string.titulo_lugares_general));
        adapter.addFragment(flF, getString(R.string.titulo_lugares_fotos));
        adapter.addFragment(mlF, getString(R.string.titulo_lugares_mapa));
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        appBar.removeView(pestanas);
    }

    public class AdaptadorSecciones extends FragmentStatePagerAdapter {
        private final List<Fragment> fragmentos = new ArrayList<>();
        private final List<String> titulosFragmentos = new ArrayList<>();

        public AdaptadorSecciones(FragmentManager fm) {
            super(fm);
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            return fragmentos.get(position);
        }

        @Override
        public int getCount() {
            return fragmentos.size();
        }

        public void addFragment(android.support.v4.app.Fragment fragment, String title) {
            fragmentos.add(fragment);
            titulosFragmentos.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titulosFragmentos.get(position);
        }
    }

}
