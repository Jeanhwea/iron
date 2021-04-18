package com.avic.mti.iron.mould.domain.repo;

import com.avic.mti.iron.common.domain.repo.MesRepository;
import com.avic.mti.iron.mould.domain.entity.Mould;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MouldRepository extends MesRepository<Mould> {
  Page<Mould> findAll(Specification<Mould> spec, Pageable page);

  List<Mould> findAll(Specification<Mould> spec);

  List<Mould> findAllByMoldCodeIn(List<String> moldCodes);

  List<Mould> findAllByMoldCodeNotIn(List<String> moldCodes);

  Optional<Object> findFirstByMoldCode(String moldCode);

  @Query(
      "select e from Mould e where "
          + "e.validation = 'y' and "
          + "e.moldCode not in (select a.resRefCode from Resource a)")
  List<Mould> findIdleMoulds();
}
