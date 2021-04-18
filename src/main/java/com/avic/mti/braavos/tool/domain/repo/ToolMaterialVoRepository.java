package com.avic.mti.iron.tool.domain.repo;

import com.avic.mti.iron.common.domain.repo.MesRepository;
import com.avic.mti.iron.tool.domain.entity.ToolMaterialVo;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

@Repository
public interface ToolMaterialVoRepository extends MesRepository<ToolMaterialVo> {
  Page<ToolMaterialVo> findAll(Specification<ToolMaterialVo> spec, Pageable page);

  List<ToolMaterialVo> findAll(Specification<ToolMaterialVo> spec);
}
