package com.avic.mti.iron.auxiliary.domain.repo;

import com.avic.mti.iron.auxiliary.domain.entity.AuxiMaterial;
import com.avic.mti.iron.common.domain.repo.MesRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AuxiMaterialRepository extends MesRepository<AuxiMaterial> {

  Page<AuxiMaterial> findAll(Specification<AuxiMaterial> spec, Pageable page);

  List<AuxiMaterial> findAll(Specification<AuxiMaterial> spec);

  List<AuxiMaterial> findAllByAuxiCodeIn(List<String> auxiCodes);

  List<AuxiMaterial> findAllByAuxiCodeNotIn(List<String> auxiCodes);

  Optional<Object> findFirstByAuxiCode(String auxiCode);

  @Query(
      "select e from AuxiMaterial e where "
          + "e.validation = 'y' and "
          + "e.auxiCode not in (select a.resRefCode from Resource a)")
  List<AuxiMaterial> findIdleAuxiMaterials();
}
