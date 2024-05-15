package dayz.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "weaponKinds")
public class WeaponKind {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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