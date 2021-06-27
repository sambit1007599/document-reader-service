package com.woulter.document.config;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

/**
 * The type Document constants.
 */
@Component
@ConfigurationProperties("constants.document")
@Getter
@Setter
public class DocumentConstants {

  private List<String> supportedFileFormats;

}
