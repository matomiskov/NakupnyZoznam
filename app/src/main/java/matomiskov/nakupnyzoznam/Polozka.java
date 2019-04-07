package matomiskov.nakupnyzoznam;

public class Polozka {
    private int id;
    private String name;
    private String poznamka;
    private int category;
    private String photo;
    private int zoznamId;

    public Polozka(String name, String poznamka, int category, String photo, int zoznamId) {
        this.name = name;
        this.poznamka = poznamka;
        this.category = category;
        this.photo = photo;
        this.zoznamId = zoznamId;
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

    public String getPoznamka() {
        return poznamka;
    }

    public void setPoznamka(String poznamka) {
        this.poznamka = poznamka;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public int getZoznamId() {
        return zoznamId;
    }

    public void setZoznamId(int zoznamId) {
        this.zoznamId = zoznamId;
    }
}
