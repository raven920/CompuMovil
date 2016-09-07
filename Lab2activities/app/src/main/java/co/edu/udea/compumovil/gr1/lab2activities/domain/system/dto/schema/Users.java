package co.edu.udea.compumovil.gr1.lab2activities.domain.system.dto.schema;

import android.provider.BaseColumns;

/**
 * Created by raven on 23/08/16.
 */
public class Users {
    public static final String DB_NAME = "lab2activities.db";
    public static final int DB_VERSION = 11;
    public static final String TABLE = "users";

    public class Column{
        public static final String ID = BaseColumns._ID;
        public static final String NOMBRE_USUARIO = "nombre_usuario";
        public static final String CONTRASENA = "contrasena";
        public static final String EDAD = "edad";
        public static final String FOTO = "foto";
        public static final String EMAIL = "email";
    }
}
