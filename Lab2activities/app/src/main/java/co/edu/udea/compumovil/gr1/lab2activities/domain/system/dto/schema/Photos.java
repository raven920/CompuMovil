package co.edu.udea.compumovil.gr1.lab2activities.domain.system.dto.schema;

import android.provider.BaseColumns;

/**
 * Created by raven on 23/08/16.
 */
public class Photos {
    public static final String DB_NAME = "lab2activities.db";
    public static final String TABLE = "photos";

    public class Column{
        public static final String ID = BaseColumns._ID;
        public static final String ID_LUGAR = "lugar";
        public static final String IMAGEN = "imagen";
    }
}
