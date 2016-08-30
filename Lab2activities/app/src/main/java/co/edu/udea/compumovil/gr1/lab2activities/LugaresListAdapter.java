package co.edu.udea.compumovil.gr1.lab2activities;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import co.edu.udea.compumovil.gr1.lab2activities.domain.system.dto.schema.Places;
import co.edu.udea.compumovil.gr1.lab2activities.services.DbBitmapUtility;
import co.edu.udea.compumovil.gr1.lab2activities.services.db.DbHelper;

/**
 * Created by raven on 26/08/16.
 */
public class LugaresListAdapter extends RecyclerView.Adapter<LugaresListAdapter.ViewHolder> {





    public interface OnItemClickListener {
        void onItemClick(int id);
    }

    public LugaresListAdapter(OnItemClickListener listener) {
        this.listener = listener;
    }


    private final OnItemClickListener listener;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Campos respectivos de un item
        public TextView nombre;
        public TextView descripcion;
        public ImageView foto;
        public ImageView star1;
        public ImageView star2;
        public ImageView star3;
        public ImageView star4;
        public ImageView star5;

        public LinearLayout mainLayout;



        public ViewHolder(View v) {
            super(v);
            nombre = (TextView) v.findViewById(R.id.texto_ppal_nombre);
            descripcion = (TextView) v.findViewById(R.id.texto_ppal_descripcion);
            star1 = (ImageView) v.findViewById(R.id.star1ListPlaces);
            star2 = (ImageView) v.findViewById(R.id.star2ListPlaces);
            star3 = (ImageView) v.findViewById(R.id.star3ListPlaces);
            star4 = (ImageView) v.findViewById(R.id.star4ListPlaces);
            star5 = (ImageView) v.findViewById(R.id.star5ListPlaces);
            foto = (ImageView) v.findViewById(R.id.fotoPrincipalLugar);
            mainLayout = (LinearLayout) v.findViewById(R.id.lugares_lista_mainlayout);
        }


    }



    @Override
    public int getItemCount() {
        return LugarPrincipalDTO.LUGARES.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_lista_lugares, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        final LugarPrincipalDTO item = LugarPrincipalDTO.LUGARES.get(i);
        viewHolder.nombre.setText(item.nombre);
        viewHolder.descripcion.setText(item.descripcion);
        int punt = Integer.parseInt(item.puntuacion);
        if(punt == 1){
            viewHolder.star1.setImageResource(R.drawable.ic_star_half);
        }
        if(punt >= 2){
            viewHolder.star1.setImageResource(R.drawable.ic_star);
        }
        if(punt == 3){
            viewHolder.star2.setImageResource(R.drawable.ic_star_half);
        }
        if(punt >= 4){
            viewHolder.star2.setImageResource(R.drawable.ic_star);
        }
        if(punt == 5){
            viewHolder.star3.setImageResource(R.drawable.ic_star_half);
        }
        if(punt >= 6){
            viewHolder.star3.setImageResource(R.drawable.ic_star);
        }
        if(punt == 7){
            viewHolder.star4.setImageResource(R.drawable.ic_star_half);
        }
        if(punt >= 8){
            viewHolder.star4.setImageResource(R.drawable.ic_star);
        }
        if(punt == 9){
            viewHolder.star5.setImageResource(R.drawable.ic_star_half);
        }
        if(punt >= 10){
            viewHolder.star5.setImageResource(R.drawable.ic_star);
        }

        viewHolder.foto.setImageBitmap(item.fotoPrincipal);

        viewHolder.mainLayout.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                listener.onItemClick(item.id);
            }
        });
    }

    /**
     * Modelo de datos para probar el adaptador
     */
    public static class LugarPrincipalDTO {
        public String nombre;
        public String descripcion;
        public String puntuacion;
        public Bitmap fotoPrincipal;
        public int id;

        public LugarPrincipalDTO(){}

        public LugarPrincipalDTO(String nombre, String descripcion, String puntuacion) {
            this.nombre = nombre;
            this.descripcion = descripcion;
            this.puntuacion = puntuacion;
            this.fotoPrincipal = null;
        }

        public LugarPrincipalDTO(String nombre, String descripcion, String puntuacion, Bitmap fotoPrincipal) {
            this.nombre = nombre;
            this.descripcion = descripcion;
            this.puntuacion = puntuacion;
            this.fotoPrincipal = fotoPrincipal;
        }

        private final static List<LugarPrincipalDTO> LUGARES = new ArrayList<>();

        static {
            getLugares();
        }

        public static List<LugarPrincipalDTO> getLugares() {
            DbHelper dbHelper = new DbHelper(LugaresMainActivity.getContext());
            SQLiteDatabase sqldb = dbHelper.getReadableDatabase();
            String Query = "select * from " + Places.TABLE;
            LugarPrincipalDTO l;
            byte[] imagen;
            Cursor cursor = sqldb.rawQuery(Query, null);
            if(cursor == null || cursor.getCount() <= 0 || cursor.getCount() == LUGARES.size()){
                cursor.close();
                return LUGARES;
            }

            try {
                LUGARES.clear();
                if (cursor.moveToFirst()) {
                    do{
                        l = new LugarPrincipalDTO();
                        l.id = cursor.getInt(cursor.getColumnIndex(Places.Column.ID));
                        l.nombre = cursor.getString(cursor.getColumnIndex(Places.Column.NOMBRE_LUGAR));
                        l.descripcion = cursor.getString(cursor.getColumnIndex(Places.Column.DESCRIPCION));
                        l.puntuacion = cursor.getString(cursor.getColumnIndex(Places.Column.PUNTUACION));
                        imagen = cursor.getBlob(cursor.getColumnIndex(Places.Column.IMAGEN));

                        l.fotoPrincipal = DbBitmapUtility.getImage(imagen);
                        LUGARES.add(l);
                    }while(cursor.moveToNext());
                }
            }catch(Exception e){}

            cursor.close();
            return LUGARES;
        }
    }


}
