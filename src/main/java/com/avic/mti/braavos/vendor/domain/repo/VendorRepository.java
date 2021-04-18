package com.avic.mti.iron.vendor.domain.repo;

import com.avic.mti.iron.common.domain.repo.MesRepository;
import com.avic.mti.iron.vendor.domain.entity.Vendor;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

@Repository
public interface VendorRepository extends MesRepository<Vendor> {

  Page<Vendor> findAll(Specification<Vendor> spec, Pageable page);

  List<Vendor> findAll(Specification<Vendor> spec);

  Optional<Object> findFirstByVendCode(String vendCode);
}
