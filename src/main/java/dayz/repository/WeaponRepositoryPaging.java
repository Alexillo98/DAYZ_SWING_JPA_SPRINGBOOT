package dayz.repository;

import dayz.entity.Weapon;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Component;

@Component
public interface WeaponRepositoryPaging extends PagingAndSortingRepository<Weapon,Long> {
    @Query("SELECT COUNT (w) FROM Weapon w")
    public int countAllRecords();

    @Query("SELECT id FROM Weapon ORDER BY id desc LIMIT 1")
    public long biggerID();
}
