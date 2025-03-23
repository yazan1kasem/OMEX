package entities;
import javax.persistence.*;

@Entity(name = "F_Fragen")
@Table(name = "F_Fragen")
public class F_Fragen {
    @Id
    @Column(name = "F_ID", unique = true, nullable = false)
    private int id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "F_K_HK_ID", referencedColumnName = "HK_ID")
    private HK_HauptKategorie hauptkategorie;

    private int hauptkategorieid(){
        return hauptkategorie.getId();
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "F_K_UK_ID", referencedColumnName = "UK_ID")
    private UK_UnterKategorie unterkategorie;
    @Column(name = "F_Frage")
    private String frage;
    @Column(name = "F_A1")
    private String antwort1;
    @Column(name = "F_A2")
    private String antwort2;
    @Column(name = "F_A3")
    private String antwort3;
    @Column(name = "F_A4")
    private String antwort4;
    @Column(name = "F_RA")
    private int richtigeAntwort;

    @Column(name = "F_Erklaeren")
    private String erklaeren;


    public F_Fragen(int frageid,HK_HauptKategorie hauptkategorieid, UK_UnterKategorie unterkategorieid, String frage, String antwort1, String antwort2, String antwort3, String antwort4, int richtigeAntwort,  String erklaeren) {
        this.id = frageid;
        this.frage = frage;
        this.antwort1 = antwort1;
        this.antwort2 = antwort2;
        this.antwort3 = antwort3;
        this.antwort4 = antwort4;
        this.richtigeAntwort = richtigeAntwort;
        this.hauptkategorie=hauptkategorieid;
        this.unterkategorie=unterkategorieid;
        this.erklaeren = erklaeren;
    }

    // Default-Konstruktor für JPA
    public F_Fragen() {

    }

    //getters and setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public HK_HauptKategorie getHauptkategorie() {
        return hauptkategorie;
    }

    public void setHauptkategorie(HK_HauptKategorie hauptkategorie) {
        this.hauptkategorie = hauptkategorie;
    }

    public UK_UnterKategorie getUnterkategorie() {
        return unterkategorie;
    }

    public void setUnterkategorie(UK_UnterKategorie unterkategorie) {
        this.unterkategorie = unterkategorie;
    }

    public String getFrage() {
        return frage;
    }

    public void setFrage(String frage) {
        this.frage = frage;
    }

    public String getAntwort1() {
        return antwort1;
    }

    public void setAntwort1(String antwort1) {
        this.antwort1 = antwort1;
    }

    public String getAntwort2() {
        return antwort2;
    }

    public void setAntwort2(String antwort2) {
        this.antwort2 = antwort2;
    }

    public String getAntwort3() {
        return antwort3;
    }

    public void setAntwort3(String antwort3) {
        this.antwort3 = antwort3;
    }

    public String getAntwort4() {
        return antwort4;
    }

    public void setAntwort4(String antwort4) {
        this.antwort4 = antwort4;
    }

    public int getRichtigeAntwort() {
        return richtigeAntwort;
    }

    public void setRichtigeAntwort(int richtigeAntwort) {
        this.richtigeAntwort = richtigeAntwort;
    }

    public String getErklaeren() {
        return erklaeren;
    }

    public void setErklaeren(String erklaeren) {
        this.erklaeren = erklaeren;
    }
}
