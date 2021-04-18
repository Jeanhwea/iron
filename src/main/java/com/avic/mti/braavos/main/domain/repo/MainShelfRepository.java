package com.avic.mti.iron.main.domain.repo;

import com.avic.mti.iron.common.domain.repo.MesRepository;
import com.avic.mti.iron.main.domain.entity.MainShelf;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import javax.persistence.LockModeType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

@Repository
public interface MainShelfRepository extends MesRepository<MainShelf> {
  List<MainShelf> findAll(Specification<MainShelf> spec, Pageable page);

  int countAllByShelfCodeStartsWith(String roomCode);

  @Lock(LockModeType.PESSIMISTIC_WRITE)
  Optional<MainShelf> findByShelfCode(String shelfCode);

  @Lock(LockModeType.PESSIMISTIC_WRITE)
  List<MainShelf> findAllByShelfCodeIn(Set<String> shelfCodes);

  List<MainShelf> findAllByRoomCodeStartsWith(String roomCode);
}
