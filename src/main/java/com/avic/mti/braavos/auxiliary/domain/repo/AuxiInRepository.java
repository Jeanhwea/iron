package com.avic.mti.iron.auxiliary.domain.repo;

import com.avic.mti.iron.auxiliary.domain.entity.AuxiIn;
import com.avic.mti.iron.common.domain.repo.MesRepository;
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
public interface AuxiInRepository extends MesRepository<AuxiIn> {

  Page<AuxiIn> findAll(Specification<AuxiIn> spec, Pageable page);

  List<AuxiIn> findAll(Specification<AuxiIn> spec);

  List<AuxiIn> findByIdIn(List<Long> auxiInIds);

  List<AuxiIn> findByAuxiId(long auxiId);

  @Lock(LockModeType.PESSIMISTIC_WRITE)
  Optional<AuxiIn> findById(Long auxiInId);

  List<AuxiIn> findAllByInCodeIn(List<String> auxiCodes);

  List<AuxiIn> findAllByInCodeNotIn(List<String> auxiCodes);

  Optional<Object> findFirstByInCode(String auxiCode);

  @Modifying
  @Query(
      "update AuxiIn e set e.updateTime = current_timestamp, "
          + "e.retestId = null, e.retestStatus = ?1 "
          + "where e.retestId = ?2")
  int clearRetestStatus(String retestStatus, long retestId);

  @Modifying
  @Query(
      "update AuxiIn e set e.updateTime = current_timestamp, "
          + "e.retestStatus = ?1 "
          + "where e.mtlBatch = ?2 and e.auxiId = ?3")
  int updateRetestStatus(String retestStatus, String mtlBatch, long auxiId);
}
