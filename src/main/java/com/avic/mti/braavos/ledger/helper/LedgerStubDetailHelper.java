package com.avic.mti.iron.ledger.helper;

import com.avic.mti.iron.common.http.request.ParamReader;
import com.avic.mti.iron.ledger.domain.entity.LedgerStubDetail;
import java.util.Map;

public class LedgerStubDetailHelper {

  public static LedgerStubDetail assignLedgerStubDetail(
      LedgerStubDetail ledgerStubDetail, Map<String, Object> params) {

    ParamReader reader = ParamReader.init(params);
    reader.stringFromKey("updateUser").ifPresent(ledgerStubDetail::updateUser);

    reader.longFromKey("stubId").ifPresent(ledgerStubDetail::stubId);
    reader.stringFromKey("stubCode").ifPresent(ledgerStubDetail::stubCode);
    reader.stringFromKey("stubDetStatus").ifPresent(ledgerStubDetail::stubDetStatus);
    reader.stringFromKey("stubDetCate").ifPresent(ledgerStubDetail::stubDetCate);
    reader.stringFromKey("stubDetType").ifPresent(ledgerStubDetail::stubDetType);
    reader.stringFromKey("mtlCode").ifPresent(ledgerStubDetail::mtlCode);
    reader.stringFromKey("mtlName").ifPresent(ledgerStubDetail::mtlName);
    reader.stringFromKey("mtlType").ifPresent(ledgerStubDetail::mtlType);
    reader.stringFromKey("mtlSpec").ifPresent(ledgerStubDetail::mtlSpec);
    reader.stringFromKey("mtlMark").ifPresent(ledgerStubDetail::mtlMark);
    reader.stringFromKey("mtlBatch").ifPresent(ledgerStubDetail::mtlBatch);
    reader.longFromKey("inId").ifPresent(ledgerStubDetail::inId);
    reader.longFromKey("qdNum").ifPresent(ledgerStubDetail::qdNum);
    reader.longFromKey("outNum").ifPresent(ledgerStubDetail::outNum);
    reader.stringFromKey("measure").ifPresent(ledgerStubDetail::measure);
    reader.stringFromKey("creatorNC").ifPresent(ledgerStubDetail::creatorNC);
    reader.timeFromKey("createDate").ifPresent(ledgerStubDetail::createDate);
    reader.stringFromKey("finishOperNC").ifPresent(ledgerStubDetail::finishOperNC);
    reader.timeFromKey("finishDate").ifPresent(ledgerStubDetail::finishDate);
    reader.boolFromKey("isOverflow").ifPresent(ledgerStubDetail::isOverflow);
    reader.stringFromKey("taskCode").ifPresent(ledgerStubDetail::taskCode);
    reader.stringFromKey("brgnCode").ifPresent(ledgerStubDetail::brgnCode);
    reader.stringFromKey("projCode").ifPresent(ledgerStubDetail::projCode);
    reader.stringFromKey("outReason").ifPresent(ledgerStubDetail::outReason);
    reader.stringFromKey("note").ifPresent(ledgerStubDetail::note);

    return ledgerStubDetail;
  }

  private LedgerStubDetailHelper() {}
}
