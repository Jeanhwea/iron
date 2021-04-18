package com.avic.mti.iron.main.domain.repo;

import com.avic.mti.iron.common.domain.repo.MesRepository;
import com.avic.mti.iron.main.domain.entity.MainOut;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

@Repository
public interface MainOutRepository extends MesRepository<MainOut> {

  Page<MainOut> findAll(Specification<MainOut> spec, Pageable page);

  List<MainOut> findAll(Specification<MainOut> spec);
}
