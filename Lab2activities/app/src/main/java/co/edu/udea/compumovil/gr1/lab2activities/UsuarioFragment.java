package co.edu.udea.compumovil.gr1.lab2activities;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import co.edu.udea.compumovil.gr1.lab2activities.domain.system.dto.schema.Users;
import co.edu.udea.compumovil.gr1.lab2activities.services.DbBitmapUtility;
import co.edu.udea.compumovil.gr1.lab2activities.services.db.DbHelper;


/**
 * A simple {@link Fragment} subclass.
 */
public class UsuarioFragment extends Fragment {


    public UsuarioFragment() {
        // Required empty public constructor
    }

    private DbHelper dbHelper;

    private TextView nombre;
    private ImageView foto;
    private TextView edad;
    private TextView correo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_usuario, container, false);

        String user;
        dbHelper = new DbHelper(LugaresMainActivity.getInstance());

        nombre = (TextView) v.findViewById(R.id.userNameUserFragmentTV);
        foto = (ImageView) v.findViewById(R.id.userImgFragmentTV);
        edad = (TextView) v.findViewById(R.id.edadUserFragmentTV);
        correo = (TextView) v.findViewById(R.id.emailUserFragmentTV);

        user = LugaresMainActivity.getInstance().getUserName();
        setInfo(user);
        return  v;
    }

    private void setInfo(String user){

        SQLiteDatabase open = dbHelper.getReadableDatabase();
        String query = "SELECT * FROM "+ Users.TABLE +" WHERE "+Users.Column.NOMBRE_USUARIO+" = ?";
        Cursor c = open.rawQuery(query, new String[]{user});
        Bitmap image;
        byte[] imageByte;
        if(c.moveToFirst()){
            correo.setText("Email: "+c.getString(c.getColumnIndex(Users.Column.EMAIL)));
            edad.setText("Edad: "+c.getInt(c.getColumnIndex(Users.Column.EDAD)));
            imageByte = c.getBlob(c.getColumnIndex(Users.Column.FOTO));
            image = DbBitmapUtility.getImage(imageByte);
            foto.setImageBitmap(image);

        }
        nombre.setText(user);
    }

}
