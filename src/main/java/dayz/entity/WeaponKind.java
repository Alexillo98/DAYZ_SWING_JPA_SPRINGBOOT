package dayz.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "weapon_kinds")
public class WeaponKind {

    @Id
    private Long id;

    private String name;

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
}