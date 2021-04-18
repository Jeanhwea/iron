package com.avic.mti.iron.support.domain.repo;

import com.avic.mti.iron.common.domain.repo.MesRepository;
import com.avic.mti.iron.support.domain.entity.Support;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SupportRepository extends MesRepository<Support> {
  Page<Support> findAll(Specification<Support> spec, Pageable page);

  List<Support> findAll(Specification<Support> spec);

  List<Support> findAllBySuptCodeIn(List<String> suptCodes);

  List<Support> findAllBySuptCodeNotIn(List<String> suptCodes);

  Optional<Object> findFirstBySuptCode(String suptCode);

  @Query(
      "select e from Support e where "
          + "e.validation = 'y' and "
          + "e.suptCode not in (select a.resRefCode from Resource a)")
  List<Support> findIdleSupports();
}
