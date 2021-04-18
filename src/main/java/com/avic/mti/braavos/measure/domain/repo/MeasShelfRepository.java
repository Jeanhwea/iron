package com.avic.mti.iron.measure.domain.repo;

import com.avic.mti.iron.common.domain.repo.MesRepository;
import com.avic.mti.iron.measure.domain.entity.MeasShelf;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import javax.persistence.LockModeType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

@Repository
public interface MeasShelfRepository extends MesRepository<MeasShelf> {
  List<MeasShelf> findAll(Specification<MeasShelf> spec, Pageable page);

  int countAllByShelfCodeStartsWith(String roomCode);

  @Lock(LockModeType.PESSIMISTIC_WRITE)
  Optional<MeasShelf> findByShelfCode(String shelfCode);

  @Lock(LockModeType.PESSIMISTIC_WRITE)
  List<MeasShelf> findAllByShelfCodeIn(Set<String> shelfCodes);

  List<MeasShelf> findAllByRoomCodeStartsWith(String roomCode);
}
