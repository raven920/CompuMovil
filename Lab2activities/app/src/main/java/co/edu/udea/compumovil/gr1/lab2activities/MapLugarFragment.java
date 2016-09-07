package co.edu.udea.compumovil.gr1.lab2activities;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import co.edu.udea.compumovil.gr1.lab2activities.domain.system.dto.schema.Places;
import co.edu.udea.compumovil.gr1.lab2activities.services.db.DbHelper;


/**
 * A simple {@link Fragment} subclass.
 */
public class MapLugarFragment extends Fragment implements OnMapReadyCallback {

    private int placeId;
    private GoogleMap mMap;

    public MapLugarFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public void setPlaceId(int placeId) {
        this.placeId = placeId;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_map_lugar, container, false);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.placesMap);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        DbHelper dbHelper = new DbHelper(LugaresMainActivity.getContext());
        SQLiteDatabase sqldb = dbHelper.getReadableDatabase();
        String Query = "select * from " + Places.TABLE+ " where "+Places.Column.ID +" = "+placeId;
        Cursor cursor = sqldb.rawQuery(Query, null);
        String nombre = "";
        double lat = 35.7100627d, lon =  139.8085117d;

        if(cursor.moveToFirst()){
            lat = cursor.getDouble(cursor.getColumnIndex(Places.Column.LATITUD));
            lon = cursor.getDouble(cursor.getColumnIndex(Places.Column.LONGITUD));
            nombre = cursor.getString(cursor.getColumnIndex(Places.Column.NOMBRE_LUGAR));
        }
        mMap = googleMap;
        LatLng sydney = new LatLng(lat,lon);
        mMap.addMarker(new MarkerOptions().position(sydney).title(nombre));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(17));
    }
}
