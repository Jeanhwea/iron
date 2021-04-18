package com.avic.mti.iron.main.domain.repo;

import com.avic.mti.iron.common.domain.repo.MesRepository;
import com.avic.mti.iron.main.domain.entity.Retest;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

@Repository
public interface RetestRepository extends MesRepository<Retest> {

  Page<Retest> findAll(Specification<Retest> spec, Pageable page);

  List<Retest> findAll(Specification<Retest> spec);

  Optional<Object> findFirstByRetestCode(String retestCode);
}
