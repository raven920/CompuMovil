package co.edu.udea.compumovil.gr1.lab2activities;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import co.edu.udea.compumovil.gr1.lab2activities.domain.system.dto.schema.Places;
import co.edu.udea.compumovil.gr1.lab2activities.services.DbBitmapUtility;
import co.edu.udea.compumovil.gr1.lab2activities.services.db.DbHelper;

public class NewPlaceActivity extends AppCompatActivity {

    private final int PICK_IMAGE = 150;

    private ImageView foto;
    private TextView nombre;
    private TextView descripcion;
    private TextView infoGen;
    private  TextView temperatura;
    private  TextView lugares;
    private  TextView puntuacion;
    private  TextView lon;
    private  TextView lat;

    private DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_place);

        foto = (ImageView) findViewById(R.id.fotoCreIV);
        nombre = (TextView) findViewById(R.id.nombreCreTV);
        descripcion = (TextView) findViewById(R.id.descCortaCreTV);
        infoGen = (TextView) findViewById(R.id.infoGeneralCreTV);
        temperatura = (TextView) findViewById(R.id.temperaturaCreTV);
        lugares = (TextView) findViewById(R.id.lugaresRecoCreTV);
        puntuacion = (TextView) findViewById(R.id.puntuacionCreTV);
        lon = (TextView) findViewById(R.id.longitudCreTV);
        lat = (TextView) findViewById(R.id.latitudCreTV);

        dbHelper = new DbHelper(this);

        foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
                getIntent.setType("image/*");

                Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                pickIntent.setType("image/*");

                Intent chooserIntent = Intent.createChooser(getIntent, "Seleccione Foto del Lugar");
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {pickIntent});

                startActivityForResult(chooserIntent, PICK_IMAGE);
            }
        });
    }

    public void registerNewPlace(View v){

        SQLiteDatabase db;
        boolean error = false;
        String incompleteErr = "Este campo no puede estar vacío.";
        String invalidErr = "Valor inválido.";

        String nombreStr = nombre.getText().toString().trim();
        String descrStr = descripcion.getText().toString().trim();
        String infoGenStr = infoGen.getText().toString().trim();
        String tempStr = temperatura.getText().toString().trim();
        String lugaresStr = lugares.getText().toString().trim();
        String puntuacionStr = puntuacion.getText().toString().trim();
        Bitmap fotoBitmap = ((BitmapDrawable)foto.getDrawable()).getBitmap();
        byte[] imageBytes;
        int puntInt = 0;
        String lonStr = lon.getText().toString().trim();
        double lonDb =0;
        String latStr = lat.getText().toString().trim();
        double latDb = 0;

        if("".equals(nombreStr)){
            nombre.setError(incompleteErr);
            error = true;
        }

        if("".equals(descrStr)){
            descripcion.setError(incompleteErr);
            error = true;
        }
        if("".equals(infoGenStr)){
            infoGen.setError(incompleteErr);
            error = true;
        }

        if("".equals(tempStr)){
            temperatura.setError(incompleteErr);
            error = true;
        }

        if("".equals(lugaresStr)){
            lugares.setError(incompleteErr);
            error = true;
        }
        if("".equals(puntuacionStr)){
            puntuacion.setError(incompleteErr);
            error = true;
        }else{
            try{
                puntInt = Integer.parseInt(puntuacionStr);
                if(puntInt < 0 || puntInt > 10){
                    puntuacion.setError(invalidErr);
                    error = true;
                }
            }catch (NumberFormatException e){
                puntuacion.setError(invalidErr);
                error = true;
            }
        }

        if("".equals(lonStr)){
            lon.setError(incompleteErr);
            error = true;
        }else{
            try{
                lonDb = Double.parseDouble(lonStr);
            }catch (NumberFormatException e){
                lon.setError(invalidErr);
                error = true;
            }
        }


        if("".equals(latStr)){
            lat.setError(incompleteErr);
            error = true;
        }else{
            try{
                latDb = Double.parseDouble(latStr);
            }catch (NumberFormatException e){
                lat.setError(invalidErr);
                error = true;
            }
        }

        if(error)
            return;

        ContentValues cv = new ContentValues();

        cv.put(Places.Column.NOMBRE_LUGAR,nombreStr);
        cv.put(Places.Column.DESCRIPCION,descrStr);
        cv.put(Places.Column.INFO_GENERAL,infoGenStr);
        cv.put(Places.Column.TEMPERATURA, tempStr);
        cv.put(Places.Column.SITIOS_RECOMENDADOS, lugaresStr);
        cv.put(Places.Column.PUNTUACION,puntInt);
        cv.put(Places.Column.LONGITUD, lonDb);
        cv.put(Places.Column.LATITUD,latDb);
        cv.put(Places.Column.ZOOM, 17);

        imageBytes = DbBitmapUtility.getBytes(fotoBitmap);

        cv.put(Places.Column.IMAGEN,imageBytes);

        db = dbHelper.getWritableDatabase();

        db.insertWithOnConflict(Places.TABLE, null, cv, SQLiteDatabase.CONFLICT_IGNORE);
        db.close();

        LugaresMainActivity.getInstance().recreate();

        Intent i = new Intent(this, LugaresMainActivity.class);

        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        finish();
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(this, LugaresMainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE && resultCode == AppCompatActivity.RESULT_OK) {
            if (data == null) {
                Toast.makeText(this, "No seleccionó una imagen", Toast.LENGTH_LONG).show();
                return;
            }
            foto.setImageURI(data.getData());
        }
    }
}
