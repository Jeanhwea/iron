package com.avic.mti.iron.measure.domain.repo;

import com.avic.mti.iron.common.domain.repo.MesRepository;
import com.avic.mti.iron.measure.domain.entity.MeasOut;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

@Repository
public interface MeasOutRepository extends MesRepository<MeasOut> {

  Page<MeasOut> findAll(Specification<MeasOut> spec, Pageable page);

  List<MeasOut> findAll(Specification<MeasOut> spec);
}
