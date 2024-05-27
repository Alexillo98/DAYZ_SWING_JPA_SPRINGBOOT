package dayz.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "weapons")
public class Weapon {
    @Id
    private Long id;

    private String name;

    private String country;

    private String img_link;

    private String country_code;

    @ManyToOne
    @JoinColumn(name = "weapontype")
    private WeaponType weapon_types;

    @ManyToOne
    @JoinColumn(name = "weaponkind")
    private WeaponKind weapon_kinds;

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

    public WeaponType getWeapon_types() {
        return weapon_types;
    }

    public void setWeapon_types(WeaponType weapon_types) {
        this.weapon_types = weapon_types;
    }

    public WeaponKind getWeapon_kinds() {
        return weapon_kinds;
    }

    public void setWeapon_kinds(WeaponKind weapon_kinds) {
        this.weapon_kinds = weapon_kinds;
    }

    public String getImgLink() {
        return img_link;
    }

    public void setImgLink(String imgLink) {
        this.img_link = imgLink;
    }

    public String getImg_link() {
        return img_link;
    }

    public void setImg_link(String img_link) {
        this.img_link = img_link;
    }

    public String getCountry_code() {
        return country_code;
    }

    public void setCountry_code(String country_code) {
        this.country_code = country_code;
    }
}