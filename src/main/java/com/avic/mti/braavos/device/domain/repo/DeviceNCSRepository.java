package com.avic.mti.iron.device.domain.repo;

import com.avic.mti.iron.common.domain.repo.MesRepository;
import com.avic.mti.iron.device.domain.entity.DeviceNCS;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceNCSRepository extends MesRepository<DeviceNCS> {

  Page<DeviceNCS> findAll(Specification<DeviceNCS> spec, Pageable page);

  List<DeviceNCS> findAll(Specification<DeviceNCS> spec);

  Optional<Object> findFirstByNcsCode(String ncsCode);
}
