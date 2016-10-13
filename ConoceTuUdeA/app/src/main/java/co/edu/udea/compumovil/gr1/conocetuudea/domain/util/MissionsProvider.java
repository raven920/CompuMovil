package co.edu.udea.compumovil.gr1.conocetuudea.domain.util;

import co.edu.udea.compumovil.gr1.conocetuudea.R;
import co.edu.udea.compumovil.gr1.conocetuudea.domain.db.Mission;

/**
 * Created by raven on 13/10/16.
 */

public class MissionsProvider {

    private static Mission[] missions = null;

    public static Mission[] getMissions() {
        if(missions == null){
            setMissions();
        }
        return missions;
    }

    private static void setMissions(){
        missions = new Mission[2];
        Mission m1 = new Mission();
        m1.setName("Sembrando estrellas");
        m1.setId(0);
        m1.setDescription("En tierras de seres cuya existencia se ve amenazada por la existencia de campos electromagnéticos resistentes a la aplicación del conocimiento, se levanta un heroe que brinda esperanzas a los jóvenes pobladores. Encuentra al sembrador de estrellas.");
        m1.setLat(0.3);
        m1.setLon(1.2);
        m1.setShortDescription("Encuentra al sembrador de estrellas");
        m1.setImageId(R.drawable.sembrador);

        Mission m2 = new Mission();
        m2.setId(1);
        m2.setName("Nevermore");
        m2.setDescription("Observante, Mariamulata no ignora a ninguno de los transeuntes que la admiran día a día. Encuentra a Mariamulata.");
        m2.setShortDescription("Encuentra a Mariamulata.");
        m2.setLat(1.4);
        m2.setLon(4.2);
        m2.setImageId(R.drawable.mariamulata);

        missions[0] = m1;
        missions[1] = m2;
    }
}
