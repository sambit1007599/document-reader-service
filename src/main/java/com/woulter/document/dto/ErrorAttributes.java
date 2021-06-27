package com.woulter.document.dto;

import java.util.List;

import lombok.Data;

/**
 * The type Error attributes.
 */
@Data
public class ErrorAttributes {

  private String code;
  private String message;
  private int status;
  private List<String> params;
}
