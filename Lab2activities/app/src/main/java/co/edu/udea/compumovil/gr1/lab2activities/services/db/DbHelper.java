package co.edu.udea.compumovil.gr1.lab2activities.services.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Base64;
import android.util.Log;

import co.edu.udea.compumovil.gr1.lab2activities.R;
import co.edu.udea.compumovil.gr1.lab2activities.domain.system.dto.schema.Photos;
import co.edu.udea.compumovil.gr1.lab2activities.domain.system.dto.schema.Places;
import co.edu.udea.compumovil.gr1.lab2activities.domain.system.dto.schema.Users;


/**
 * Created by raven on 23/08/16.
 */
public class DbHelper extends SQLiteOpenHelper {
    private static final String TAG =DbHelper.class.getSimpleName();

    public DbHelper(Context context){
        super(context, Users.DB_NAME, null, Users.DB_VERSION);
        this.context = context;
    }

    private Context context;

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sqlUsers = String.format("create table %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s text, %s text, %s text, %s integer, %s blob)",
                Users.TABLE, Users.Column.ID, Users.Column.NOMBRE_USUARIO, Users.Column.CONTRASENA, Users.Column.EMAIL, Users.Column.EDAD, Users.Column.FOTO );
        String sqlPlaces = String.format("create table %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s text, %s text, %s integer, %s blob, %s double, %s double, %s text, %s text, %s text, %s text )",
                Places.TABLE, Places.Column.ID, Places.Column.NOMBRE_LUGAR, Places.Column.DESCRIPCION, Places.Column.PUNTUACION, Places.Column.IMAGEN,
                Places.Column.LONGITUD, Places.Column.LATITUD, Places.Column.ZOOM, Places.Column.INFO_GENERAL, Places.Column.TEMPERATURA, Places.Column.SITIOS_RECOMENDADOS );
        String sqlPhotos = String.format("create table %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s INTEGER, %s blob )",
                Photos.TABLE, Photos.Column.ID, Photos.Column.ID_LUGAR, Photos.Column.IMAGEN);
        Log.d(TAG,"onCreate Users with SQL: "+sqlUsers);
        Log.d(TAG,"onCreate Places with SQL: "+sqlPlaces);
        Log.d(TAG,"onCreate Photos with SQL: "+sqlPhotos);
        db.execSQL(sqlUsers);
        db.execSQL(sqlPlaces);
        db.execSQL(sqlPhotos);

        createInitialData(db);

    }

    private void createInitialData(SQLiteDatabase db){
        String imagen;
        byte[] decodedString;
        ContentValues cv = new ContentValues();
        cv.put(Places.Column.NOMBRE_LUGAR, "Tokyo Skytree (東京 スカイ ツリ)");
        cv.put(Places.Column.DESCRIPCION, "Es una torre de radiodifusión, restaurante y mirador construida en Sumida, Tokio, Japón.");
        cv.put(Places.Column.PUNTUACION, 9);
        cv.put(Places.Column.LATITUD, 35.7100627d);
        cv.put(Places.Column.LONGITUD, 139.8085117d);
        cv.put(Places.Column.ZOOM, 17);
        cv.put(Places.Column.INFO_GENERAL, "Es la estructura artificial más alta en Japón desde 2010. Con una altura de 634 m, fue completada el 29 de febrero de 2012 e inaugurada el 22 de mayo de 2012.\n" +
                "El proyecto fue liderado por Tobu Railway y un grupo de seis emisoras terrestres (encabezada por la cadena pública NHK). La estructura completa es el punto culminante de un desarrollo comercial masivo, ya que se encuentra equidistante entre las estaciones de Narihirabashi y Oshiage.\n" +
                "Uno de los propósitos principales de la Tokyo Skytree es ser una torre de televisión y radiodifusión. La torre de radiodifusión actual de Tokio, la Torre de Tokio, tenía una altura original de 333 m, aunque perdió su antena analógica el 14 de Julio de 2012, quedándose en 315 m de altura, y ya no es lo suficientemente alta como para dar cobertura digital completa, ya que está rodeada de muchos edificios de gran altura.\n" +
                "La Tokyo Skytree es la torre más alta del mundo.");
        cv.put(Places.Column.TEMPERATURA, "24° C. (Aire Acondicionado)");
        cv.put(Places.Column.SITIOS_RECOMENDADOS, "SkyTree Café: Deleitese con un delicioso café mientras disfruta de la vista.\n"+
                "Sky Restaurant: Disfrute de la cocina de Tokyo, un nuevo tipo de cocina inspirada en la tradición japonesa.\n" +
                "Piso de vidrio: Experimente la sensación única de mirar hacia el vacío por medio de una ventana en el piso a 340 metros de altura.");

        imagen = context.getResources().getString(R.string.skytreeImg);
        decodedString = Base64.decode(imagen.getBytes(),Base64.DEFAULT);
        cv.put(Places.Column.IMAGEN, decodedString);
        db.insertWithOnConflict(Places.TABLE, null, cv, SQLiteDatabase.CONFLICT_IGNORE);

        cv = new ContentValues();
        cv.put(Places.Column.NOMBRE_LUGAR, "Templo Senso-ji (金龍山浅草寺)");
        cv.put(Places.Column.DESCRIPCION, "Es un templo budista localizado en Asakusa.");
        cv.put(Places.Column.PUNTUACION, 8);
        cv.put(Places.Column.LATITUD, 35.7147651d);
        cv.put(Places.Column.LONGITUD, 139.7966553d);
        cv.put(Places.Column.ZOOM, 15);
        cv.put(Places.Column.INFO_GENERAL, "Es el templo más antiguo de Tokio y uno de los más importantes. Está asociado a la secta budista Tendai, de la que se independizó después de la Segunda Guerra Mundial. Junto al templo se encuentra el santuario sintoísta de Asakusa.\n" +
                "El templo está dedicado al bodisatva Kannon (Avalokitesvara). Según la leyenda, la estatua del Kannon fue encontrada en el río Sumida por dos pescadores (los hermanos Hinokuma Hamanari y Hinokuma Takenari) en el año 628. El jefe de la aldea, Hajino Nakamoto, reconoció la santidad de la estatua y la veneró remodelando su propia casa en un pequeño templo para que los habitantes de Asakusa le pudiesen rendir culto.\n" +
                "En el año 645 fue fundado el primer templo para su veneración, lo cual hace que este sea el más antiguo de Tokio. En los principios del shogunato Tokugawa, Tokugawa Ieyasu designó a Senso-ji como un templo tutelar para el clan Tokugawa.\n" +
                "Durante la Segunda Guerra Mundial el templo fue bombardeado y destruido, su reconstrucción fue símbolo de la paz y renacimiento del pueblo Japonés.");
        cv.put(Places.Column.TEMPERATURA, "0C ~ 32C según la temporada.");
        cv.put(Places.Column.SITIOS_RECOMENDADOS, "Kaminarimon: La puerta del trueno, con su linterna y estatuas recibe a los turistas que llegan a conocer el lugar.\n" +
                "Nakamise-Dori: Es la calle que lleva a Senso-ji, allí hay muchas pequeñas tiendas donde se pueden comprar souvenirs alusivos a Senso-ji.\n" +
                "Jardín del Templo: Disfrute del hermoso paisaje que brinda el jardín estilo japonés del templo.");
        imagen = context.getResources().getString(R.string.sensojiImg);
        decodedString = Base64.decode(imagen.getBytes(),Base64.DEFAULT);
        cv.put(Places.Column.IMAGEN, decodedString);
        db.insertWithOnConflict(Places.TABLE, null, cv, SQLiteDatabase.CONFLICT_IGNORE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop table if exists " + Users.TABLE);
        db.execSQL("drop table if exists " + Places.TABLE);
        db.execSQL("drop table if exists " + Photos.TABLE);
        onCreate(db);
    }
}
