package com.avic.mti.iron.main.domain.repo;

import com.avic.mti.iron.common.domain.repo.MesRepository;
import com.avic.mti.iron.main.domain.entity.RetestDetail;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RetestDetailRepository extends MesRepository<RetestDetail> {

  Page<RetestDetail> findAll(Specification<RetestDetail> spec, Pageable page);

  List<RetestDetail> findAll(Specification<RetestDetail> spec);

  Optional<RetestDetail> findFirstByRetestIdAndRetestStatus(long retestId, String retestStatus);

  @Modifying
  @Query("delete from RetestDetail where retestId = ?1")
  int deleteByRetestId(long retestId);
}
