package com.avic.mti.iron.tool.domain.repo;

import com.avic.mti.iron.common.domain.repo.MesRepository;
import com.avic.mti.iron.tool.domain.entity.ToolOut;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

@Repository
public interface ToolOutRepository extends MesRepository<ToolOut> {

  Page<ToolOut> findAll(Specification<ToolOut> spec, Pageable page);

  List<ToolOut> findAll(Specification<ToolOut> spec);
}
