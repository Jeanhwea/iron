package com.avic.mti.iron.ledger.service.impl;

import com.avic.mti.iron.common.domain.query.MesConditionBuilder;
import com.avic.mti.iron.common.exception.BadRequestException;
import com.avic.mti.iron.common.exception.DatumNotExistException;
import com.avic.mti.iron.common.exception.UnknownEnumException;
import com.avic.mti.iron.common.helper.StringHelper;
import com.avic.mti.iron.common.http.request.FieldTypeEnum;
import com.avic.mti.iron.common.http.request.ParamReader;
import com.avic.mti.iron.common.symbol.resource.ResourceCateEnum;
import com.avic.mti.iron.common.symbol.resource.ResourceTypeEnum;
import com.avic.mti.iron.ledger.domain.entity.Resource;
import com.avic.mti.iron.ledger.domain.repo.ResourceRepository;
import com.avic.mti.iron.ledger.helper.ResourceHelper;
import com.avic.mti.iron.ledger.service.ResourceService;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ResourceServiceImpl implements ResourceService {

  public static final Logger logger = LoggerFactory.getLogger(ResourceServiceImpl.class);

  private final ResourceRepository resourceRepository;

  private final MesConditionBuilder<Resource> mesConditionBuilder;

  @Autowired
  public ResourceServiceImpl(
      ResourceRepository resourceRepository, MesConditionBuilder<Resource> mesConditionBuilder) {
    this.resourceRepository = resourceRepository;
    this.mesConditionBuilder = mesConditionBuilder;
  }

  private void validateResource(Resource resource) {
    if (ResourceCateEnum.of(resource.resCate()) == ResourceCateEnum.Unknown) {
      throw new UnknownEnumException("resCate", resource.resCate(), ResourceCateEnum.expect());
    }

    if (ResourceTypeEnum.of(resource.resType()) == ResourceTypeEnum.Unknown) {
      throw new UnknownEnumException("resType", resource.resType(), ResourceTypeEnum.expect());
    }
  }

  private void checkBeforeCreate(Resource resource) {
    // pass
  }

  private void checkBeforeReplace(Resource resource) {
    // pass
  }

  private void checkBeforeDelete(Resource resource) {
    // pass
  }

  @Override
  @Transactional(readOnly = true)
  public Page<Resource> findResources(
      Map<String, Object> params, Map<String, FieldTypeEnum> fields) {
    MesConditionBuilder<Resource> builder = this.mesConditionBuilder.init(params, fields);
    return this.resourceRepository.findAll(builder.spec(), builder.page());
  }

  @Override
  @Transactional(readOnly = true)
  public List<Resource> findAllResources(
      Map<String, Object> params, Map<String, FieldTypeEnum> fields) {
    MesConditionBuilder<Resource> builder = this.mesConditionBuilder.init(params, fields);
    return this.resourceRepository.findAll(builder.spec());
  }

  @Override
  @Transactional(readOnly = true)
  public Resource findById(long resourceId) {
    return this.resourceRepository
        .fetchById(resourceId)
        .orElseThrow(() -> new DatumNotExistException("Resource", resourceId));
  }

  @Override
  public Resource createResource(Map<String, Object> params) {
    Resource newResource = ResourceHelper.assignResource(new Resource(), params);

    boolean conflict =
        this.resourceRepository.findFirstByResCode(newResource.resCode()).isPresent();
    if (conflict) {
      throw new BadRequestException("资源编码 {0} 已经存在", newResource.resCode());
    }

    this.checkBeforeCreate(newResource);
    this.validateResource(newResource);
    return this.resourceRepository.save(newResource);
  }

  @Override
  public Resource replaceResource(long resourceId, Map<String, Object> params) {
    Resource prevResource = this.findById(resourceId);

    ParamReader reader = ParamReader.init(params);
    String newResourceCode = reader.stringFromKey("resCode").orElse(null);
    boolean conflict =
        StringHelper.isNonBlank(newResourceCode)
            && !newResourceCode.equals(prevResource.resCode())
            && this.resourceRepository.findFirstByResCode(newResourceCode).isPresent();
    if (conflict) {
      throw new BadRequestException("资源编码 {0} 已经存在", newResourceCode);
    }

    this.checkBeforeReplace(prevResource);
    Resource currResource = ResourceHelper.assignResource(prevResource, params);
    this.validateResource(currResource);
    return this.resourceRepository.save(currResource);
  }

  @Override
  public void deleteResource(long resourceId) {
    Resource prevResource = this.findById(resourceId);
    this.checkBeforeDelete(prevResource);
    this.resourceRepository.delete(prevResource);
  }
}
