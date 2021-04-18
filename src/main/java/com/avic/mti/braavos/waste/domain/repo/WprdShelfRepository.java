package com.avic.mti.iron.waste.domain.repo;

import com.avic.mti.iron.common.domain.repo.MesRepository;
import com.avic.mti.iron.waste.domain.entity.WprdShelf;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import javax.persistence.LockModeType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

@Repository
public interface WprdShelfRepository extends MesRepository<WprdShelf> {
  List<WprdShelf> findAll(Specification<WprdShelf> spec, Pageable page);

  int countAllByShelfCodeStartsWith(String roomCode);

  @Lock(LockModeType.PESSIMISTIC_WRITE)
  Optional<WprdShelf> findByShelfCode(String shelfCode);

  @Lock(LockModeType.PESSIMISTIC_WRITE)
  List<WprdShelf> findAllByShelfCodeIn(Set<String> shelfCodes);

  List<WprdShelf> findAllByRoomCodeStartsWith(String roomCode);
}
