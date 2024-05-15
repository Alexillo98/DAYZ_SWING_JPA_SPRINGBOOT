package dayz;

import jakarta.persistence.*;

@Entity
@Table(name = "weapons")
public class Weapon {
    @Id
    private Long id;

    private String name;

    private String country;

    @ManyToOne
    @JoinColumn(name = "weaponType")
    private WeaponType weaponType;

    @ManyToOne
    @JoinColumn(name = "weaponKind")
    private WeaponKind weaponKind;

    // Add getters and setters here


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public WeaponType getWeaponType() {
        return weaponType;
    }

    public void setWeaponType(WeaponType weaponType) {
        this.weaponType = weaponType;
    }

    public WeaponKind getWeaponKind() {
        return weaponKind;
    }

    public void setWeaponKind(WeaponKind weaponKind) {
        this.weaponKind = weaponKind;
    }
}