package com.avic.mti.iron.ledger.domain.repo;

import com.avic.mti.iron.common.domain.repo.MesRepository;
import com.avic.mti.iron.ledger.domain.entity.Resource;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

@Repository
public interface ResourceRepository extends MesRepository<Resource> {

  Page<Resource> findAll(Specification<Resource> spec, Pageable page);

  List<Resource> findAll(Specification<Resource> spec);

  Optional<Object> findFirstByResCode(String resCode);
}
