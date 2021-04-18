package com.avic.mti.iron.main.domain.repo;

import com.avic.mti.iron.common.domain.repo.MesRepository;
import com.avic.mti.iron.main.domain.entity.MainRoom;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

@Repository
public interface MainRoomRepository extends MesRepository<MainRoom> {
  List<MainRoom> findAll(Specification<MainRoom> spec, Pageable page);

  List<MainRoom> findAll(Specification<MainRoom> spec);

  List<MainRoom> findAllByParentRoomIdOrderByRoomCode(long parentRoomId);

  int countAllByParentRoomId(long parentRoomId);

  Optional<MainRoom> findByRoomCode(String roomCode);
}
