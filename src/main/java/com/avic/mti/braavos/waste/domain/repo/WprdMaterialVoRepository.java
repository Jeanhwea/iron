package com.avic.mti.iron.waste.domain.repo;

import com.avic.mti.iron.common.domain.repo.MesRepository;
import com.avic.mti.iron.waste.domain.entity.WprdMaterialVo;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

@Repository
public interface WprdMaterialVoRepository extends MesRepository<WprdMaterialVo> {
  Page<WprdMaterialVo> findAll(Specification<WprdMaterialVo> spec, Pageable page);

  List<WprdMaterialVo> findAll(Specification<WprdMaterialVo> spec);
}
