package com.avic.mti.iron.main.domain.repo;

import com.avic.mti.iron.common.domain.repo.MesRepository;
import com.avic.mti.iron.main.domain.entity.MainIn;
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
public interface MainInRepository extends MesRepository<MainIn> {

  Page<MainIn> findAll(Specification<MainIn> spec, Pageable page);

  List<MainIn> findAll(Specification<MainIn> spec);

  List<MainIn> findByIdIn(List<Long> mainInIds);

  List<MainIn> findByMainId(long mainId);

  @Lock(LockModeType.PESSIMISTIC_WRITE)
  Optional<MainIn> findById(Long mainInId);

  List<MainIn> findAllByInCodeIn(List<String> mainCodes);

  List<MainIn> findAllByInCodeNotIn(List<String> mainCodes);

  Optional<Object> findFirstByInCode(String mainCode);

  @Modifying
  @Query(
      "update MainIn e set e.updateTime = current_timestamp, "
          + "e.retestId = null, e.retestStatus = ?1 "
          + "where e.retestId = ?2")
  int clearRetestStatus(String retestStatus, long retestId);

  @Modifying
  @Query(
      "update MainIn e set e.updateTime = current_timestamp, "
          + "e.retestStatus = ?1 "
          + "where e.mtlBatch = ?2 and e.mainId = ?3")
  int updateRetestStatus(String retestStatus, String mtlBatch, long mainId);
}
