package com.avic.mti.iron.waste.domain.repo;

import com.avic.mti.iron.common.domain.repo.MesRepository;
import com.avic.mti.iron.waste.domain.entity.WprdLog;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

@Repository
public interface WprdLogRepository extends MesRepository<WprdLog> {

  Page<WprdLog> findAll(Specification<WprdLog> spec, Pageable page);

  List<WprdLog> findAll(Specification<WprdLog> spec);
}
