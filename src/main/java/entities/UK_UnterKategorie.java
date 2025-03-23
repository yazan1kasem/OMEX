package entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "UK_UnterKategorie")
public class UK_UnterKategorie {
    @Id
    private int UK_id;

    @Column(name = "UK_Bez")
    private String bezeichnung;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "UK_HK_ID")
    private HK_HauptKategorie HK_id;
    // Konstruktor, Getter und Setter

    // Default-Konstruktor für JPA
    public UK_UnterKategorie() {}

    public UK_UnterKategorie(int id, HK_HauptKategorie hkid, String bezeichnung) {
        this.UK_id = id;
        this.HK_id =hkid;
        this.bezeichnung = bezeichnung;
    }

    @OneToMany(mappedBy = "unterkategorie")
    private List<F_Fragen> fragenListe;

    // Getter und Setter

    public int getId() {
        return UK_id;
    }

    public void setId(int id) {
        this.UK_id = id;
    }

    public String getBezeichnung() {
        return bezeichnung;
    }

    public void setBezeichnung(String bezeichnung) {
        this.bezeichnung = bezeichnung;
    }

    public HK_HauptKategorie getHK_id() {
        return HK_id;
    }

    public void setHK_id(HK_HauptKategorie HK_id) {
        this.HK_id = HK_id;
    }
    @Override
    public String toString() {
        return String.valueOf(UK_id);
    }

}

