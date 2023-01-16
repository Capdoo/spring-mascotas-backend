package com.mascotas.app.modules.partners;

import com.mascotas.app.modules.pets.PetModel;
import com.mascotas.app.modules.shelters.ShelterModel;
import com.mascotas.app.security.models.UserModel;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;

@Entity
@Table(name="partners")
public class PartnerModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private Timestamp registerDate;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "PARTNER_FK_USER"))
    private UserModel user;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "shelter_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "PARTNER_FK_SHELTER"))
    private ShelterModel shelter;

    public PartnerModel() {
        super();
    }

    public PartnerModel(Timestamp registerDate, UserModel user, ShelterModel shelter) {
        this.registerDate = registerDate;
        this.user = user;
        this.shelter = shelter;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Timestamp getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Timestamp registerDate) {
        this.registerDate = registerDate;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public ShelterModel getShelter() {
        return shelter;
    }

    public void setShelter(ShelterModel shelter) {
        this.shelter = shelter;
    }
}
