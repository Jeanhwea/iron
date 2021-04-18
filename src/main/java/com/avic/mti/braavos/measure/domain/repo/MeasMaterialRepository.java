package com.avic.mti.iron.measure.domain.repo;

import com.avic.mti.iron.common.domain.repo.MesRepository;
import com.avic.mti.iron.measure.domain.entity.MeasMaterial;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MeasMaterialRepository extends MesRepository<MeasMaterial> {

  Page<MeasMaterial> findAll(Specification<MeasMaterial> spec, Pageable page);

  List<MeasMaterial> findAll(Specification<MeasMaterial> spec);

  List<MeasMaterial> findAllByMeasCodeIn(List<String> measCodes);

  List<MeasMaterial> findAllByMeasCodeNotIn(List<String> measCodes);

  Optional<Object> findFirstByMeasCode(String measCode);

  @Query(
      "select e from MeasMaterial e where "
          + "e.validation = 'y' and "
          + "e.measCode not in (select a.resRefCode from Resource a)")
  List<MeasMaterial> findIdleMeasMaterials();

  @Query(
      nativeQuery = true,
      value =
          "SELECT * FROM TB_RSRC_MEAS "
              + "WHERE VALIDATION = 'y' AND "
              + "MEAS_CODE NOT IN (SELECT A.PRDT_CODE FROM TB_QLTY_TEST_RULE_PRDT A)")
  List<MeasMaterial> findRuleIdleMeasMaterials();
}
