package com.woulter.document.error;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

import com.woulter.document.dto.ErrorAttributes;

/**
 * The type Error constants.
 */
@Component
@ConfigurationProperties("constants.error")
@Getter
@Setter
public class ErrorConstants {

  private ErrorAttributes fileFormatNotSupportedException;

}
