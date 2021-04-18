package com.avic.mti.iron.measure.domain.repo;

import com.avic.mti.iron.common.domain.repo.MesRepository;
import com.avic.mti.iron.measure.domain.entity.MeasMaterialVo;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

@Repository
public interface MeasMaterialVoRepository extends MesRepository<MeasMaterialVo> {
  Page<MeasMaterialVo> findAll(Specification<MeasMaterialVo> spec, Pageable page);

  List<MeasMaterialVo> findAll(Specification<MeasMaterialVo> spec);
}
