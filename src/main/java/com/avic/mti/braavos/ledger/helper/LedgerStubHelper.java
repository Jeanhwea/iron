package com.avic.mti.iron.ledger.helper;

import com.avic.mti.iron.common.http.request.ParamReader;
import com.avic.mti.iron.ledger.domain.entity.LedgerStub;
import java.util.Map;

public class LedgerStubHelper {

  public static LedgerStub assignLedgerStub(LedgerStub ledgerStub, Map<String, Object> params) {

    ParamReader reader = ParamReader.init(params);
    reader.stringFromKey("updateUser").ifPresent(ledgerStub::updateUser);

    reader.stringFromKey("stubCode").ifPresent(ledgerStub::stubCode);
    reader.stringFromKey("stubName").ifPresent(ledgerStub::stubName);
    reader.stringFromKey("stubStatus").ifPresent(ledgerStub::stubStatus);
    reader.stringFromKey("stubCate").ifPresent(ledgerStub::stubCate);
    reader.stringFromKey("stubType").ifPresent(ledgerStub::stubType);
    reader.stringFromKey("stubFlag").ifPresent(ledgerStub::stubFlag);
    reader.stringFromKey("stubTag").ifPresent(ledgerStub::stubTag);
    reader.boolFromKey("isOverflow").ifPresent(ledgerStub::isOverflow);
    reader.stringFromKey("creatorNC").ifPresent(ledgerStub::creatorNC);
    reader.timeFromKey("createDate").ifPresent(ledgerStub::createDate);
    reader.stringFromKey("finishOperNC").ifPresent(ledgerStub::finishOperNC);
    reader.timeFromKey("finishDate").ifPresent(ledgerStub::finishDate);
    reader.stringFromKey("note").ifPresent(ledgerStub::note);

    return ledgerStub;
  }

  private LedgerStubHelper() {}
}
