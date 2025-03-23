package entities;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "US_UserFragen")
public class US_UserFragen {
    @EmbeddedId
    private US_UserFragenId id;

    @Column(name = "US_Beantwortet")
    private boolean beantwortet;

    // Konstruktor, Getter und Setter
    // ...

    // Default-Konstruktor für JPA
    public US_UserFragen() {}

    public US_UserFragen(U_User UUser, F_Fragen fragen, boolean beantwortet) {
        this.id = new US_UserFragenId(UUser, fragen);
        this.beantwortet = beantwortet;
    }

    // Getter und Setter


    public US_UserFragenId getId() {
        return id;
    }

    public void setId(US_UserFragenId id) {
        this.id = id;
    }

    public boolean isBeantwortet() {
        return beantwortet;
    }

    public void setBeantwortet(boolean beantwortet) {
        this.beantwortet = beantwortet;
    }

    @Embeddable
    public static class US_UserFragenId implements Serializable {
        @ManyToOne
        @JoinColumn(name = "US_U_ID", referencedColumnName = "U_ID")
        private U_User U_User;

        @ManyToOne
        @JoinColumn(name = "US_F_ID", referencedColumnName = "F_ID")
        private F_Fragen f_fragen;

        public US_UserFragenId() {}

        public US_UserFragenId(U_User UUser, F_Fragen fragen) {
            this.U_User = UUser;
            this.f_fragen = fragen;
        }

        // Getter und Setter
        // ...

        public U_User getUUser() {
            return U_User;
        }

        public void setUUser(U_User UUser) {
            this.U_User = UUser;
        }

        public F_Fragen getFragen() {
            return f_fragen;
        }

        public void setFragen(F_Fragen fragen) {
            this.f_fragen = fragen;
        }
    }
}
