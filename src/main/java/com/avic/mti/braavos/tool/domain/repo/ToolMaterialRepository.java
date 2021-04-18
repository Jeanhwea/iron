package com.avic.mti.iron.tool.domain.repo;

import com.avic.mti.iron.common.domain.repo.MesRepository;
import com.avic.mti.iron.tool.domain.entity.ToolMaterial;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ToolMaterialRepository extends MesRepository<ToolMaterial> {

  Page<ToolMaterial> findAll(Specification<ToolMaterial> spec, Pageable page);

  List<ToolMaterial> findAll(Specification<ToolMaterial> spec);

  List<ToolMaterial> findAllByToolCodeIn(List<String> toolCodes);

  List<ToolMaterial> findAllByToolCodeNotIn(List<String> toolCodes);

  Optional<Object> findFirstByToolCode(String toolCode);

  @Query(
      "select e from ToolMaterial e where "
          + "e.validation = 'y' and "
          + "e.toolCode not in (select a.resRefCode from Resource a)")
  List<ToolMaterial> findIdleToolMaterials();

  @Query(
      nativeQuery = true,
      value =
          "SELECT * FROM TB_RSRC_TOOL "
              + "WHERE VALIDATION = 'y' AND "
              + "TOOL_CODE NOT IN (SELECT A.PRDT_CODE FROM TB_QLTY_TEST_RULE_PRDT A)")
  List<ToolMaterial> findRuleIdleToolMaterials();
}
