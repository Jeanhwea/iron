package com.avic.mti.iron.tool.domain.repo;

import com.avic.mti.iron.common.domain.repo.MesRepository;
import com.avic.mti.iron.tool.domain.entity.ToolRoom;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

@Repository
public interface ToolRoomRepository extends MesRepository<ToolRoom> {
  List<ToolRoom> findAll(Specification<ToolRoom> spec, Pageable page);

  List<ToolRoom> findAll(Specification<ToolRoom> spec);

  List<ToolRoom> findAllByParentRoomIdOrderByRoomCode(long parentRoomId);

  int countAllByParentRoomId(long parentRoomId);

  Optional<ToolRoom> findByRoomCode(String roomCode);
}
