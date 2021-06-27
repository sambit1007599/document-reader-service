package com.woulter.document.error;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * The type File format not supported exception.
 */
@Slf4j
@Getter
public class FileFormatNotSupportedException extends RuntimeException {

  /**
   * Instantiates a new File format not supported exception.
   *
   * @param fileFormat the file format
   */
  public FileFormatNotSupportedException(final String fileFormat) {
    super(fileFormat);
  }
}
