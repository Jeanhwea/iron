package com.avic.mti.iron.waste.domain.repo;

import com.avic.mti.iron.common.domain.repo.MesRepository;
import com.avic.mti.iron.waste.domain.entity.WprdOut;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

@Repository
public interface WprdOutRepository extends MesRepository<WprdOut> {

  Page<WprdOut> findAll(Specification<WprdOut> spec, Pageable page);

  List<WprdOut> findAll(Specification<WprdOut> spec);
}
