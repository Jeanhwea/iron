package com.avic.mti.iron.waste.domain.repo;

import com.avic.mti.iron.common.domain.repo.MesRepository;
import com.avic.mti.iron.waste.domain.entity.WprdIn;
import java.util.List;
import java.util.Optional;
import javax.persistence.LockModeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

@Repository
public interface WprdInRepository extends MesRepository<WprdIn> {

  Page<WprdIn> findAll(Specification<WprdIn> spec, Pageable page);

  List<WprdIn> findAll(Specification<WprdIn> spec);

  List<WprdIn> findByIdIn(List<Long> wprdInIds);

  List<WprdIn> findByWprdId(long wprdId);

  @Lock(LockModeType.PESSIMISTIC_WRITE)
  Optional<WprdIn> findById(Long wprdInId);

  List<WprdIn> findAllByInCodeIn(List<String> wprdCodes);

  List<WprdIn> findAllByInCodeNotIn(List<String> wprdCodes);

  Optional<Object> findFirstByInCode(String wprdCode);
}
