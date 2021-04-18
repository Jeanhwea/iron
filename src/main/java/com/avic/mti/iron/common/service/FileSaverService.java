package com.avic.mti.iron.common.service;

public interface FileSaverService {
  String store(byte[] fileBytes, String basePath, String fileName);

  String store(byte[] fileBytes, String fileName);

  String store(byte[] fileBytes);

  byte[] withdraw(String location);
}
