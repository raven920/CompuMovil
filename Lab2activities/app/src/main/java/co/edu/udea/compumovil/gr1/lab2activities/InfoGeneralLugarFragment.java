package co.edu.udea.compumovil.gr1.lab2activities;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ConsoleMessage;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import co.edu.udea.compumovil.gr1.lab2activities.domain.system.dto.schema.Places;
import co.edu.udea.compumovil.gr1.lab2activities.services.db.DbHelper;


/**
 * A simple {@link Fragment} subclass.
 */
public class InfoGeneralLugarFragment extends Fragment {


    private int placeId;

    DbHelper dbHelper;
    TextView nombreTV;
    TextView infoGeneralTV;
    TextView temperaturaTV;
    TextView ubicacionTV;
    TextView sitiosRecomendadosTV;


    public InfoGeneralLugarFragment() {
        // Required empty public constructor
    }

    public void setPlaceId(int placeId) {
        this.placeId = placeId;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v =  inflater.inflate(R.layout.fragment_info_general_lugar, container, false);

        nombreTV = (TextView)v.findViewById(R.id.nombreTv);
        infoGeneralTV = (TextView)v.findViewById(R.id.infoGeneralTV);
        temperaturaTV = (TextView)v.findViewById(R.id.temperaturaTV);
        ubicacionTV = (TextView)v.findViewById(R.id.ubicacionTV);
        sitiosRecomendadosTV = (TextView)v.findViewById(R.id.sitiosRecomendadosTV);

        dbHelper = new DbHelper(LugaresMainActivity.getContext());
        SQLiteDatabase sqldb = dbHelper.getReadableDatabase();
        String Query = "select * from " + Places.TABLE+ " where "+Places.Column.ID +" = "+placeId;
        Cursor cursor = sqldb.rawQuery(Query, null);

        if(cursor.moveToFirst()){

            nombreTV.setText(cursor.getString(cursor.getColumnIndex(Places.Column.NOMBRE_LUGAR)));
            infoGeneralTV.setText("Información general: "+cursor.getString(cursor.getColumnIndex(Places.Column.INFO_GENERAL)));
            temperaturaTV.setText("Temperatura::" +cursor.getString(cursor.getColumnIndex(Places.Column.TEMPERATURA)));
            ubicacionTV.setText("Ubicación: Japón");
            sitiosRecomendadosTV.setText("Sitios Recomendados: \n"+cursor.getString(cursor.getColumnIndex(Places.Column.SITIOS_RECOMENDADOS)));
        }
        return v;
    }

}
