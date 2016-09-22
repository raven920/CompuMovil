package co.edu.udea.compumovil.gr1.lab3weather.POJO;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by duvan.bedoya on 12/09/16.
 */
public class weatherPOJO implements Parcelable {
    int id;
    String name;
    int cod;
    coord coord;
    main main;
    List<weather> weather;

    public weatherPOJO() {
    }

    protected weatherPOJO(Parcel in) {
        id = in.readInt();
        name = in.readString();
        cod = in.readInt();
    }

    public static final Creator<weatherPOJO> CREATOR = new Creator<weatherPOJO>() {
        @Override
        public weatherPOJO createFromParcel(Parcel in) {
            return new weatherPOJO(in);
        }

        @Override
        public weatherPOJO[] newArray(int size) {
            return new weatherPOJO[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeInt(cod);
    }
}
