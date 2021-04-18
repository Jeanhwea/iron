package com.avic.mti.iron.tool.domain.repo;

import com.avic.mti.iron.common.domain.repo.MesRepository;
import com.avic.mti.iron.tool.domain.entity.ToolShelf;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import javax.persistence.LockModeType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

@Repository
public interface ToolShelfRepository extends MesRepository<ToolShelf> {
  List<ToolShelf> findAll(Specification<ToolShelf> spec, Pageable page);

  int countAllByShelfCodeStartsWith(String roomCode);

  @Lock(LockModeType.PESSIMISTIC_WRITE)
  Optional<ToolShelf> findByShelfCode(String shelfCode);

  @Lock(LockModeType.PESSIMISTIC_WRITE)
  List<ToolShelf> findAllByShelfCodeIn(Set<String> shelfCodes);

  List<ToolShelf> findAllByRoomCodeStartsWith(String roomCode);
}
