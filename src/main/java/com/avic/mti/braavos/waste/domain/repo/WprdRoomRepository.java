package com.avic.mti.iron.waste.domain.repo;

import com.avic.mti.iron.common.domain.repo.MesRepository;
import com.avic.mti.iron.waste.domain.entity.WprdRoom;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

@Repository
public interface WprdRoomRepository extends MesRepository<WprdRoom> {
  List<WprdRoom> findAll(Specification<WprdRoom> spec, Pageable page);

  List<WprdRoom> findAll(Specification<WprdRoom> spec);

  List<WprdRoom> findAllByParentRoomIdOrderByRoomCode(long parentRoomId);

  int countAllByParentRoomId(long parentRoomId);

  Optional<WprdRoom> findByRoomCode(String roomCode);
}
