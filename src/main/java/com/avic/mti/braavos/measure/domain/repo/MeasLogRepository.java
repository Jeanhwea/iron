package com.avic.mti.iron.measure.domain.repo;

import com.avic.mti.iron.common.domain.repo.MesRepository;
import com.avic.mti.iron.measure.domain.entity.MeasLog;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

@Repository
public interface MeasLogRepository extends MesRepository<MeasLog> {

  Page<MeasLog> findAll(Specification<MeasLog> spec, Pageable page);

  List<MeasLog> findAll(Specification<MeasLog> spec);
}
