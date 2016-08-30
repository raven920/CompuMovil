package co.edu.udea.compumovil.gr1.lab2activities;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import co.edu.udea.compumovil.gr1.lab2activities.domain.system.dto.schema.Users;
import co.edu.udea.compumovil.gr1.lab2activities.services.db.DbHelper;
import co.edu.udea.compumovil.gr1.lab2activities.services.db.util.SHA256HashProvider;

public class RegisterActivity extends AppCompatActivity {

    private DbHelper dbHelper;
   private EditText usuario;
    private EditText contrasena;
    private EditText email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        dbHelper = new DbHelper(this);

        usuario = (EditText)findViewById(R.id.usernameRegisterTxt);
        contrasena = (EditText)findViewById(R.id.passRegisterTxt);
        email = (EditText)findViewById(R.id.emailRegisterTxt);
    }

    public void handleRegister(View v){
        boolean error = false;
        String usrStr = usuario.getText().toString().trim();
        String passStr = contrasena.getText().toString().trim();
        String emailStr = email.getText().toString().trim();
        ContentValues contentValues;
        SQLiteDatabase db;

        if("".equals(usrStr)){
            error = true;
            usuario.setError("Este campo no puede estar vacío");
        }

        if("".equals(passStr)){
            error = true;
            contrasena.setError("Este campo no puede estar vacío");
        }

        if("".equals(emailStr)){
            error = true;
            email.setError("Este campo no puede estar vacío");
        }

        if(error){
            return;
        }

        if(existsInDB(Users.TABLE, Users.Column.NOMBRE_USUARIO, usrStr)){
            usuario.setError("El usuario ya existe en la base de datos.");
            return;
        }

        if(existsInDB(Users.TABLE, Users.Column.EMAIL, emailStr)){
            usuario.setError("Este email ya existe en la base de datos.");
            return;
        }

        contentValues = new ContentValues();
        contentValues.put(Users.Column.NOMBRE_USUARIO, usrStr);
        try {
            contentValues.put(Users.Column.CONTRASENA, SHA256HashProvider.getProvider().createHash(passStr));
        }catch(Exception e){
            contrasena.setError("Contraseña no válida.");
            return;
        }

        db = dbHelper.getWritableDatabase();
        db.insertWithOnConflict(Users.TABLE, null, contentValues, SQLiteDatabase.CONFLICT_IGNORE);

        Intent returnIntent = new Intent();
        setResult(AppCompatActivity.RESULT_OK,returnIntent);
        finish();
    }

    private boolean existsInDB(String table, String field, String value) {
        String[] values = new String[]{value};
        SQLiteDatabase sqldb = dbHelper.getReadableDatabase();
        String Query = "select * from " + table + " where "
                + field + " = ?";
        Cursor cursor = sqldb.rawQuery(Query, values);
        if(cursor == null || cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }
}
