package dayz.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "weapon_kinds")
public class WeaponKind {

    @Id
    private Long id;

    private String name;

    // Add getters and setters here
    public WeaponKind(){}

    public WeaponKind(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public WeaponKind(Long id){
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
}