package com.avic.mti.iron.main.domain.repo;

import com.avic.mti.iron.common.domain.repo.MesRepository;
import com.avic.mti.iron.main.domain.entity.MainMaterialVo;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

@Repository
public interface MainMaterialVoRepository extends MesRepository<MainMaterialVo> {
  Page<MainMaterialVo> findAll(Specification<MainMaterialVo> spec, Pageable page);

  List<MainMaterialVo> findAll(Specification<MainMaterialVo> spec);
}
