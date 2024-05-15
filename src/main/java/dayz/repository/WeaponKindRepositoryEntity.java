package dayz.repository;

import dayz.entity.WeaponKind;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

@Component
public interface WeaponKindRepositoryEntity extends CrudRepository<WeaponKind,Long> {
}
