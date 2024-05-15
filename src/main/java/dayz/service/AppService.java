package dayz.service;

import dayz.repository.WeaponKindRepositoryEntity;
import dayz.repository.WeaponRepositoryEntity;
import dayz.repository.WeaponRepositoryPaging;
import dayz.repository.WeaponTypeRepositoryEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class AppService {

    private final WeaponTypeRepositoryEntity weaponTypeRepositoryEntity;
    private final WeaponKindRepositoryEntity weaponKindRepositoryEntity;
    private final WeaponRepositoryEntity weaponRepositoryEntity;
    private final WeaponRepositoryPaging weaponRepositoryPaging;

    @Autowired
    public AppService(WeaponTypeRepositoryEntity weaponTypeRepositoryEntity, WeaponKindRepositoryEntity weaponKindRepositoryEntity,
                      WeaponRepositoryEntity weaponRepositoryEntity, WeaponRepositoryPaging weaponRepositoryPaging) {
        this.weaponTypeRepositoryEntity = weaponTypeRepositoryEntity;
        this.weaponKindRepositoryEntity = weaponKindRepositoryEntity;
        this.weaponRepositoryEntity = weaponRepositoryEntity;
        this.weaponRepositoryPaging = weaponRepositoryPaging;
    }

    public WeaponTypeRepositoryEntity getWeaponTypeRepositoryEntity() {
        return weaponTypeRepositoryEntity;
    }

    public WeaponKindRepositoryEntity getWeaponKindRepositoryEntity() {
        return weaponKindRepositoryEntity;
    }

    public WeaponRepositoryEntity getWeaponRepositoryEntity() {
        return weaponRepositoryEntity;
    }

    public WeaponRepositoryPaging getWeaponRepositoryPaging() {
        return weaponRepositoryPaging;
    }
}
