package dayz.repository;

import dayz.entity.WeaponType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

@Component
public interface WeaponTypeRepositoryEntity extends CrudRepository<WeaponType,Long> {
}
