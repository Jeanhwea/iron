package com.avic.mti.iron.main.domain.repo;

import com.avic.mti.iron.common.domain.repo.MesRepository;
import com.avic.mti.iron.main.domain.entity.MainMaterial;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MainMaterialRepository extends MesRepository<MainMaterial> {

  Page<MainMaterial> findAll(Specification<MainMaterial> spec, Pageable page);

  List<MainMaterial> findAll(Specification<MainMaterial> spec);

  List<MainMaterial> findAllByMainCodeIn(List<String> mainCodes);

  List<MainMaterial> findAllByMainCodeNotIn(List<String> mainCodes);

  Optional<Object> findFirstByMainCode(String mainCode);

  @Query(
      "select e from MainMaterial e where "
          + "e.validation = 'y' and "
          + "e.mainCode not in (select a.resRefCode from Resource a)")
  List<MainMaterial> findIdleMainMaterials();

  @Query(
      nativeQuery = true,
      value =
          "SELECT * FROM TB_RSRC_MAIN "
              + "WHERE VALIDATION = 'y' AND "
              + "MAIN_CODE NOT IN (SELECT A.PRDT_CODE FROM TB_QLTY_TEST_RULE_PRDT A)")
  List<MainMaterial> findRuleIdleMainMaterials();
}
