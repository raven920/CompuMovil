package co.edu.udea.compumovil.gr1.lab2activities;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class LugaresSelectionFragment extends Fragment {

    private LinearLayoutManager linearLayout;
    private RecyclerView reciclador;
    private LugaresListAdapter adaptador;

    public LugaresSelectionFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_lugares_selection, container, false);

        reciclador = (RecyclerView)view.findViewById(R.id.seleccionReciclador);
        linearLayout = new LinearLayoutManager(getActivity());
        reciclador.setLayoutManager(linearLayout);

        adaptador = new LugaresListAdapter(new LugaresListAdapter.OnItemClickListener() {
            @Override public void onItemClick(int id) {
                viewPlaceDetails(id);
            }
        });

        reciclador.setAdapter(adaptador);
        reciclador.addItemDecoration(new DecoracionLineaDivisoria(getActivity()));

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        LugaresListAdapter.LugarPrincipalDTO.getLugares();
        adaptador.notifyDataSetChanged();
    }

    public void viewPlaceDetails(int id){

        FragmentManager fm = getActivity().getSupportFragmentManager();
        Bundle bund = new Bundle();
        bund.putInt("place_id",id);
        Fragment lugaresF = new LugaresFragment();
        lugaresF.setArguments(bund);
        if(lugaresF != null){
            FragmentTransaction ft = fm.beginTransaction();
            ft.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
            ft.replace(R.id.lugaresFragmentContainer,lugaresF);
            ft.addToBackStack(null);
            ft.commit();
        }
    }


}
