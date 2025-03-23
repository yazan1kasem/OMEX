package entities;

import javax.persistence.*;

@Entity
@Table(name = "HK_HauptKategorie")
public class HK_HauptKategorie {
    @Id
    @Column(name = "HK_ID")
    private int id;

    @Column(name = "HK_BEZ")
    private String bezeichnung;

    // Konstruktor, Getter und Setter


    // Default-Konstruktor für JPA
    public HK_HauptKategorie() {}

    public HK_HauptKategorie(int id, String bezeichnung) {
        this.id = id;
        this.bezeichnung = bezeichnung;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getBezeichnung() {
        return bezeichnung;
    }
    public void setBezeichnung(String bezeichnung) {
        this.bezeichnung = bezeichnung;
    }
    @Override
    public String toString() {
        return String.valueOf(id);
    }
}
