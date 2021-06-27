package com.woulter.document.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The type Reference response.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReferenceResponse {

  private int lineNumber;
  private List<Integer> characterPositions;

}
