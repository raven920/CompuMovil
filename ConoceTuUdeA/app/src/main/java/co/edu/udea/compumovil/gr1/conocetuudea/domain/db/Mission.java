package co.edu.udea.compumovil.gr1.conocetuudea.domain.db;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by raven on 12/10/16.
 */

@IgnoreExtraProperties
public class Mission {
    public int id;
    public String name;
    public String shortDescription;
    public String description;
    public double lat;
    public double lon;
    public int imageId;

    public Mission(){

    }

    public Mission(int id, String name, String shortDescription, String description, double lat, double lon, int imageId) {
        this.id = id;
        this.name = name;
        this.shortDescription = shortDescription;
        this.description = description;
        this.lat = lat;
        this.lon = lon;
        this.imageId = imageId;
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

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }
}
