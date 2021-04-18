package com.avic.mti.iron.common.domain.repo;

import com.avic.mti.iron.common.domain.entity.DummyEntity;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

/**
 * 占位符仓库类
 *
 * @author Jinghui Hu
 * @since 2021-02-24, JDK1.8
 */
@NoRepositoryBean
public interface DummyRepository extends Repository<DummyEntity, Long> {}
