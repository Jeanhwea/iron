package com.avic.mti.iron.ledger.domain.repo;

import com.avic.mti.iron.common.domain.repo.MesRepository;
import com.avic.mti.iron.ledger.domain.entity.LedgerStub;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LedgerStubRepository extends MesRepository<LedgerStub> {

  Page<LedgerStub> findAll(Specification<LedgerStub> spec, Pageable page);

  List<LedgerStub> findAll(Specification<LedgerStub> spec);

  Optional<Object> findFirstByStubCode(String stubCode);

  @Modifying
  @Query(
      "update LedgerStub e set "
          + "e.stubStatus = ?1,"
          + "e.updateTime = current_timestamp "
          + "where e.id = ?2")
  int updateStubStatusByStubId(String stubStatus, long stubId);
}
