package com.avic.mti.iron.ledger.domain.repo;

import com.avic.mti.iron.common.domain.repo.MesRepository;
import com.avic.mti.iron.ledger.domain.entity.LedgerStubDetail;
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
public interface LedgerStubDetailRepository extends MesRepository<LedgerStubDetail> {

  Page<LedgerStubDetail> findAll(Specification<LedgerStubDetail> spec, Pageable page);

  List<LedgerStubDetail> findAll(Specification<LedgerStubDetail> spec);

  @Lock(LockModeType.PESSIMISTIC_WRITE)
  Optional<LedgerStubDetail> findById(Long id);

  int countAllByStubIdAndStubDetStatus(Long stubId, String stubDetStatus);

  int countAllByStubIdAndStubDetStatusNot(Long stubId, String stubDetStatus);

  @Modifying
  @Query(
      "update LedgerStubDetail e set "
          + "e.stubDetStatus = ?1,"
          + "e.updateTime = current_timestamp "
          + "where e.stubId = ?2")
  int updateStubDetStatusByStubId(String stubDetStatus, long stubId);
}
