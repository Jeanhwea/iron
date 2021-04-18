package com.avic.mti.iron.measure.domain.repo;

import com.avic.mti.iron.common.domain.repo.MesRepository;
import com.avic.mti.iron.measure.domain.entity.MeasRoom;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

@Repository
public interface MeasRoomRepository extends MesRepository<MeasRoom> {
  List<MeasRoom> findAll(Specification<MeasRoom> spec, Pageable page);

  List<MeasRoom> findAll(Specification<MeasRoom> spec);

  List<MeasRoom> findAllByParentRoomIdOrderByRoomCode(long parentRoomId);

  int countAllByParentRoomId(long parentRoomId);

  Optional<MeasRoom> findByRoomCode(String roomCode);
}
