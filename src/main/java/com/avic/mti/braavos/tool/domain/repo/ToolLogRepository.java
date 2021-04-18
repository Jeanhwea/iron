package com.avic.mti.iron.tool.domain.repo;

import com.avic.mti.iron.common.domain.repo.MesRepository;
import com.avic.mti.iron.tool.domain.entity.ToolLog;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

@Repository
public interface ToolLogRepository extends MesRepository<ToolLog> {

  Page<ToolLog> findAll(Specification<ToolLog> spec, Pageable page);

  List<ToolLog> findAll(Specification<ToolLog> spec);
}
