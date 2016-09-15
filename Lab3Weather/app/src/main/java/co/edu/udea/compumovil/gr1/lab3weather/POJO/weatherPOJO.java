package co.edu.udea.compumovil.gr1.lab3weather.POJO;

import java.util.List;

/**
 * Created by duvan.bedoya on 12/09/16.
 */
public class weatherPOJO {
    int id;
    String name;
    int cod;
    coord coord;
    main main;
    List<weather> weather;

    public weatherPOJO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public co.edu.udea.compumovil.gr1.lab3weather.POJO.coord getCoord() {
        return coord;
    }

    public void setCoord(co.edu.udea.compumovil.gr1.lab3weather.POJO.coord coord) {
        this.coord = coord;
    }

    public co.edu.udea.compumovil.gr1.lab3weather.POJO.main getMain() {
        return main;
    }

    public void setMain(co.edu.udea.compumovil.gr1.lab3weather.POJO.main main) {
        this.main = main;
    }

    public List<co.edu.udea.compumovil.gr1.lab3weather.POJO.weather> getWeather() {
        return weather;
    }

    public void setWeather(List<co.edu.udea.compumovil.gr1.lab3weather.POJO.weather> weather) {
        this.weather = weather;
    }
}
