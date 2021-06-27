package com.woulter.document.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.woulter.document.dto.ReferenceResponse;
import com.woulter.document.service.DocumentReaderService;

/**
 * The type Document reader controller.
 */
@RequestMapping("/v1")
@RestController
@RequiredArgsConstructor
@Slf4j
public class DocumentReaderController {

  private final DocumentReaderService documentReaderService;

  /**
   * Retrieve document data response entity.
   *
   * @param multipartFile the multipart file
   * @return the response entity
   */
  @PostMapping(value = "/document", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<ReferenceResponse>> retrieveDocumentData(
      @RequestPart("file") MultipartFile multipartFile) {

    log.info("retrieveDocumentData method of DocumentReaderController input request file name is :{}",
        multipartFile.getOriginalFilename());

    return new ResponseEntity<>(documentReaderService.retrieveDocumentData(multipartFile), HttpStatus.OK);
  }

}
