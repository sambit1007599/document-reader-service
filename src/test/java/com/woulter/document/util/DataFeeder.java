package com.woulter.document.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.woulter.document.dto.ReferenceResponse;

@UtilityClass
@Slf4j
public class DataFeeder {

  private final ObjectMapper objectMapper = new ObjectMapper();

  public List<ReferenceResponse> getReferenceResponseData() {
    try {
      return objectMapper
          .readValue(new File(DataFeeder.class.getClassLoader().getResource("ReferenceResponse.json").getFile())
              , new TypeReference<>() {

              });

    } catch (IOException e) {
      log.error("IO exception occured while retrieving ReferenceResponse data: {} ", e);
      throw new RuntimeException(e);
    }
  }

  public MultipartFile getMultipartFile(String fileName) {

    File file =
        new File(DataFeeder.class.getClassLoader().getResource(fileName).getFile());

    try {
      return new MockMultipartFile("file", file.getName(), null, new FileInputStream(file));
    } catch (IOException e) {
      log.error("IO exception occured while retrieving multipart data: {} ", e);
      throw new RuntimeException(e);
    }
  }
}
