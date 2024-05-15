package dayz.repository;

import dayz.WeaponKind;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

@Component
public interface WeaponKindRepositoryEntity extends CrudRepository<WeaponKind,Long> {
}
