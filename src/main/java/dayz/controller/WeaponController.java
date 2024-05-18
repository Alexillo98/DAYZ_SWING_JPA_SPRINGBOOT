package dayz.controller;

import dayz.entity.Weapon;
import dayz.repository.WeaponKindRepositoryEntity;
import dayz.repository.WeaponRepositoryPaging;
import dayz.repository.WeaponTypeRepositoryEntity;
import org.hibernate.query.sql.internal.ParameterRecognizerImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class WeaponController {

    private final WeaponRepositoryPaging weaponRepositoryPaging;
    private final WeaponKindRepositoryEntity weaponKindRepositoryEntity;
    private final WeaponTypeRepositoryEntity weaponTypeRepositoryEntity;
    private int currentPage = 0;
    private int count;
    private Optional<Weapon> currentWeapon;

    public WeaponController (WeaponRepositoryPaging weaponRepositoryPaging,WeaponKindRepositoryEntity weaponKindRepositoryEntity,WeaponTypeRepositoryEntity weaponTypeRepositoryEntity){
        this.weaponRepositoryPaging = weaponRepositoryPaging;
        this.weaponKindRepositoryEntity = weaponKindRepositoryEntity;
        this.weaponTypeRepositoryEntity = weaponTypeRepositoryEntity;
        this.count = weaponRepositoryPaging.countAllRecords();
    }

    public Optional<Weapon> getWeapon(){
        PageRequest pr = PageRequest.of(currentPage, 1);
        currentWeapon = Optional.of(weaponRepositoryPaging.findAll(pr).getContent().get(0));
        return currentWeapon;
    }

    public Optional<Weapon> next(){
        this.count = weaponRepositoryPaging.countAllRecords();
        if (currentPage == this.count-1) return currentWeapon;
        currentPage++;
        return getWeapon();
    }

    public Optional<Weapon> previous(){
        if (currentPage == 0) {
            return currentWeapon;
        }else{
            currentPage--;
        }
        return getWeapon();
    }
}
