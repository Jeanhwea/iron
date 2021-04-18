package com.avic.mti.iron.main.domain.repo;

import com.avic.mti.iron.common.domain.repo.MesRepository;
import com.avic.mti.iron.main.domain.entity.MainLog;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

@Repository
public interface MainLogRepository extends MesRepository<MainLog> {

  Page<MainLog> findAll(Specification<MainLog> spec, Pageable page);

  List<MainLog> findAll(Specification<MainLog> spec);
}
