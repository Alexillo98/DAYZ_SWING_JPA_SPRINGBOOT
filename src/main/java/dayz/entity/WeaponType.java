package dayz.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "weapon_types")
public class WeaponType {

    @Id
    private Long id;

    private String name;

    // Add getters and setters here

    public WeaponType (){}

    public WeaponType(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public WeaponType(Long id) {
        this.id = id;
    }

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

    @Override
    public  String toString(){
        return id + " - " + name;
    }
}