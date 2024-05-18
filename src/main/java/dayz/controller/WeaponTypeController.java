package dayz.controller;

import dayz.repository.WeaponTypeRepositoryEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WeaponTypeController {
    private final WeaponTypeRepositoryEntity weaponTypeRepositoryEntity;
    @Autowired
    public WeaponTypeController(WeaponTypeRepositoryEntity weaponTypeRepositoryEntity){
        this.weaponTypeRepositoryEntity = weaponTypeRepositoryEntity;
    }
}
