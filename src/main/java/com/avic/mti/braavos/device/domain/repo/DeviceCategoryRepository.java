package com.avic.mti.iron.device.domain.repo;

import com.avic.mti.iron.common.domain.repo.MesRepository;
import com.avic.mti.iron.device.domain.entity.DeviceCategory;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceCategoryRepository extends MesRepository<DeviceCategory> {

  Page<DeviceCategory> findAll(Specification<DeviceCategory> spec, Pageable page);

  List<DeviceCategory> findAll(Specification<DeviceCategory> spec);

  Optional<Object> findFirstByCateCode(String cateCode);
}
