package com.avic.mti.iron.vendor.domain.repo;

import com.avic.mti.iron.common.domain.repo.MesRepository;
import com.avic.mti.iron.vendor.domain.entity.VendorDetail;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

@Repository
public interface VendorDetailRepository extends MesRepository<VendorDetail> {

  Page<VendorDetail> findAll(Specification<VendorDetail> spec, Pageable page);

  List<VendorDetail> findAll(Specification<VendorDetail> spec);
}
