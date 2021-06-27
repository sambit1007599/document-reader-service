package com.woulter.document.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.woulter.document.config.DocumentConstants;
import com.woulter.document.dto.ReferenceResponse;
import com.woulter.document.error.FileFormatNotSupportedException;

/**
 * The type Document reader service.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class DocumentReaderService {

  private final DocumentConstants documentConstants;

  /**
   * extract all numbers from the content in an input document
   * and map to ReferenceResponse with its line number and index
   *
   * @param multipartFile the multipart file
   * @return the list
   */
  public List<ReferenceResponse> retrieveDocumentData(MultipartFile multipartFile) {

    validate(multipartFile);
    List<ReferenceResponse> referenceResponses = new ArrayList<>();

    try {

      String input = new String(multipartFile.getBytes());
      String[] lines = input.split("\n");
      int lineNumber = 1;

      for (String line : lines) {

        Pattern p = Pattern.compile("\\d+");
        Matcher m = p.matcher(line);
        List<Integer> indexes = new ArrayList<>();
        while (m.find()) {
          indexes.add(line.indexOf(m.group()));
        }
        referenceResponses.add(ReferenceResponse.builder().lineNumber(lineNumber).characterPositions(indexes).build());
        lineNumber++;
      }

    } catch (IOException e) {
      log.error("IOException occured while fetching data from file : {}", e);
    }
    return referenceResponses;
  }

  private void validate(MultipartFile multipartFile) {

    String extension = StringUtils.getFilenameExtension(multipartFile.getOriginalFilename());

    if (!documentConstants.getSupportedFileFormats().contains(extension)) {
      throw new FileFormatNotSupportedException(extension);
    }
  }
}


