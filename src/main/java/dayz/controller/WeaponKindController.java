package dayz.controller;

import dayz.repository.WeaponKindRepositoryEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WeaponKindController {
    private final WeaponKindRepositoryEntity weaponKindRepositoryEntity;
    @Autowired
    public WeaponKindController(WeaponKindRepositoryEntity weaponKindRepositoryEntity){
        this.weaponKindRepositoryEntity = weaponKindRepositoryEntity;
    }
}
