package dayz.repository;

import dayz.Weapon;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Component;

@Component
public interface WeaponRepositoryPaging extends PagingAndSortingRepository<Weapon,Long> {
}
