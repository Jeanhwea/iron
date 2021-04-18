package com.avic.mti.iron.ledger.helper;

import com.avic.mti.iron.common.http.request.ParamReader;
import com.avic.mti.iron.ledger.domain.entity.Resource;
import java.util.Map;

public class ResourceHelper {

  public static Resource assignResource(Resource resource, Map<String, Object> params) {
    ParamReader reader = ParamReader.init(params);
    reader.stringFromKey("updateUser").ifPresent(resource::updateUser);

    reader.stringFromKey("resCode").ifPresent(resource::resCode);
    reader.stringFromKey("resName").ifPresent(resource::resName);
    reader.stringFromKey("resType").ifPresent(resource::resType);
    reader.stringFromKey("resCate").ifPresent(resource::resCate);
    reader.stringFromKey("resSpec").ifPresent(resource::resSpec);
    reader.stringFromKey("resDesc").ifPresent(resource::resDesc);
    reader.stringFromKey("resMeasure").ifPresent(resource::resMeasure);
    reader.stringFromKey("resTechType").ifPresent(resource::resTechType);
    reader.stringFromKey("connStatus").ifPresent(resource::connStatus);
    reader.stringFromKey("resRefCode").ifPresent(resource::resRefCode);
    reader.stringFromKey("note").ifPresent(resource::note);
    reader.stringFromKey("paramJson").ifPresent(resource::paramJson);

    return resource;
  }

  private ResourceHelper() {}
}
