package co.edu.udea.compumovil.gr1.lab2activities.domain.system.dto.schema;

import android.provider.BaseColumns;

/**
 * Created by raven on 23/08/16.
 */
public class Places {
    public static final String DB_NAME = "lab2activities.db";
    public static final String TABLE = "places";

    public class Column{
        public static final String ID = BaseColumns._ID;
        public static final String NOMBRE_LUGAR = "nombre_lugar";
        public static final String DESCRIPCION = "descripcion";
        public static final String PUNTUACION = "puntuacion";
        public static final String IMAGEN = "imagen";
        public static final String LATITUD = "latitud";
        public static final String LONGITUD = "longitud";
        public static final String ZOOM = "zoom";
        public static final String INFO_GENERAL = "info_gen";
        public static final String TEMPERATURA = "temperatura";
        public static final String SITIOS_RECOMENDADOS = "sitios_recomendados";
    }
}
