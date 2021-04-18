package com.avic.mti.iron.measure.domain.repo;

import com.avic.mti.iron.common.domain.repo.MesRepository;
import com.avic.mti.iron.measure.domain.entity.MeasIn;
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
public interface MeasInRepository extends MesRepository<MeasIn> {

  Page<MeasIn> findAll(Specification<MeasIn> spec, Pageable page);

  List<MeasIn> findAll(Specification<MeasIn> spec);

  List<MeasIn> findByIdIn(List<Long> measInIds);

  List<MeasIn> findByMeasId(long measId);

  @Lock(LockModeType.PESSIMISTIC_WRITE)
  Optional<MeasIn> findById(Long measInId);

  List<MeasIn> findAllByInCodeIn(List<String> measCodes);

  List<MeasIn> findAllByInCodeNotIn(List<String> measCodes);

  Optional<Object> findFirstByInCode(String measCode);

  @Modifying
  @Query(
      "update MeasIn e set e.updateTime = current_timestamp, "
          + "e.retestId = null, e.retestStatus = ?1 "
          + "where e.retestId = ?2")
  int clearRetestStatus(String retestStatus, long retestId);

  @Modifying
  @Query(
      "update MeasIn e set e.updateTime = current_timestamp, "
          + "e.retestStatus = ?1 "
          + "where e.mtlBatch = ?2 and e.measId = ?3")
  int updateRetestStatus(String retestStatus, String mtlBatch, long measId);
}
