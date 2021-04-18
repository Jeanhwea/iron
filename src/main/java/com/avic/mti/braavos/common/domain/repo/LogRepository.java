package com.avic.mti.iron.common.domain.repo;

import com.avic.mti.iron.common.domain.entity.LogEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Transactional;

@NoRepositoryBean
public interface LogRepository<T extends LogEntity> extends JpaRepository<T, Long> {

  @Query("select e from #{#entityName} e where e.validation = 'y'")
  List<T> fetchAll();

  @Query("select e from #{#entityName} e where e.validation = 'y' and e.id = ?1")
  Optional<T> fetchById(long id);

  @Modifying
  @Transactional
  @Query("update #{#entityName} e set e.validation = 'n' where e.id = ?1")
  void hideById(long id);
}
