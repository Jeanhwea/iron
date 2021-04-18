package com.avic.mti.iron.waste.domain.repo;

import com.avic.mti.iron.common.domain.repo.MesRepository;
import com.avic.mti.iron.waste.domain.entity.WprdMaterial;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface WprdMaterialRepository extends MesRepository<WprdMaterial> {

  Page<WprdMaterial> findAll(Specification<WprdMaterial> spec, Pageable page);

  List<WprdMaterial> findAll(Specification<WprdMaterial> spec);

  List<WprdMaterial> findAllByWprdCodeIn(List<String> wprdCodes);

  List<WprdMaterial> findAllByWprdCodeNotIn(List<String> wprdCodes);

  Optional<Object> findFirstByWprdCode(String wprdCode);

  @Query(
      "select e from WprdMaterial e where "
          + "e.validation = 'y' and "
          + "e.wprdCode not in (select a.resRefCode from Resource a)")
  List<WprdMaterial> findIdleWprdMaterials();

  @Query(
      nativeQuery = true,
      value =
          "SELECT * FROM TB_RSRC_TOOL "
              + "WHERE VALIDATION = 'y' AND "
              + "WPRD_CODE NOT IN (SELECT A.PRDT_CODE FROM TB_QLTY_TEST_RULE_PRDT A)")
  List<WprdMaterial> findRuleIdleWprdMaterials();
}
