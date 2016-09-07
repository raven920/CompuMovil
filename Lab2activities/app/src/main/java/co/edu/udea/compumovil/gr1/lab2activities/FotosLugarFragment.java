package co.edu.udea.compumovil.gr1.lab2activities;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import co.edu.udea.compumovil.gr1.lab2activities.domain.system.dto.schema.Places;
import co.edu.udea.compumovil.gr1.lab2activities.services.DbBitmapUtility;
import co.edu.udea.compumovil.gr1.lab2activities.services.db.DbHelper;


/**
 * A simple {@link Fragment} subclass.
 */
public class FotosLugarFragment extends Fragment {


    private int placeId;

    public FotosLugarFragment() {
        // Required empty public constructor
    }

    public void setPlaceId(int placeId) {
        this.placeId = placeId;
    }

    private ImageView iv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_fotos_lugar, container, false);

        iv = (ImageView)v.findViewById(R.id.fotoFragemntIV);
        setImage(placeId);
        return v;
    }

    private void setImage(int id){
        DbHelper dbHelper = new DbHelper(LugaresMainActivity.getContext());
        SQLiteDatabase sqldb = dbHelper.getReadableDatabase();
        String Query = "select * from " + Places.TABLE+ " where "+Places.Column.ID +" = "+id;
        Cursor cursor = sqldb.rawQuery(Query, null);
        byte[] imageByte;
        Bitmap bitmap;
        if(cursor.moveToFirst()){
            imageByte = cursor.getBlob(cursor.getColumnIndex(Places.Column.IMAGEN));
            bitmap = DbBitmapUtility.getImage(imageByte);
            iv.setImageBitmap(bitmap);
        }
    }

}
