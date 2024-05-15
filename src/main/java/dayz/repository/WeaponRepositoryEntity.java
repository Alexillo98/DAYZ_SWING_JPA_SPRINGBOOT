package dayz.repository;

import dayz.Weapon;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

@Component
public interface WeaponRepositoryEntity extends CrudRepository<Weapon,Long> {
}
