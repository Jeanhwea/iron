package com.avic.mti.iron.tool.domain.repo;

import com.avic.mti.iron.common.domain.repo.MesRepository;
import com.avic.mti.iron.tool.domain.entity.ToolIn;
import java.util.List;
import java.util.Optional;
import javax.persistence.LockModeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ToolInRepository extends MesRepository<ToolIn> {

  Page<ToolIn> findAll(Specification<ToolIn> spec, Pageable page);

  List<ToolIn> findAll(Specification<ToolIn> spec);

  List<ToolIn> findByIdIn(List<Long> toolInIds);

  List<ToolIn> findByToolId(long toolId);

  @Lock(LockModeType.PESSIMISTIC_WRITE)
  Optional<ToolIn> findById(Long toolInId);

  List<ToolIn> findAllByInCodeIn(List<String> toolCodes);

  List<ToolIn> findAllByInCodeNotIn(List<String> toolCodes);

  Optional<Object> findFirstByInCode(String toolCode);

  @Modifying
  @Query(
      "update ToolIn e set e.updateTime = current_timestamp, "
          + "e.retestId = null, e.retestStatus = ?1 "
          + "where e.retestId = ?2")
  int clearRetestStatus(String retestStatus, long retestId);

  @Modifying
  @Query(
      "update ToolIn e set e.updateTime = current_timestamp, "
          + "e.retestStatus = ?1 "
          + "where e.mtlBatch = ?2 and e.toolId = ?3")
  int updateRetestStatus(String retestStatus, String mtlBatch, long toolId);
}
