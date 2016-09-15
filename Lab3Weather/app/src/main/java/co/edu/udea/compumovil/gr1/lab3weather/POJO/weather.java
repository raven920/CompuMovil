package co.edu.udea.compumovil.gr1.lab3weather.POJO;

/**
 * Created by duvan.bedoya on 12/09/16.
 */
public class weather {
    int id;
    String main, description,icon;

    public weather() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
