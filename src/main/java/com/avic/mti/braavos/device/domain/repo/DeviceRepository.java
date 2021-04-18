package com.avic.mti.iron.device.domain.repo;

import com.avic.mti.iron.common.domain.repo.MesRepository;
import com.avic.mti.iron.device.domain.entity.Device;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceRepository extends MesRepository<Device> {

  Page<Device> findAll(Specification<Device> spec, Pageable page);

  List<Device> findAll(Specification<Device> spec);

  List<Device> findAllByDevcCodeIn(List<String> devcCodes);

  List<Device> findAllByDevcCodeNotIn(List<String> devcCodes);

  Optional<Device> findFirstByDevcCode(String devcCode);

  @Query(
      "select e from Device e where "
          + "e.validation = 'y' and "
          + "e.devcCode not in (select a.resRefCode from Resource a)")
  List<Device> findIdleDevices();
}
