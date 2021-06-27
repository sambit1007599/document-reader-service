package com.woulter.document.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import com.woulter.document.config.DocumentConstants;
import com.woulter.document.dto.ReferenceResponse;
import com.woulter.document.error.FileFormatNotSupportedException;
import com.woulter.document.util.DataFeeder;

@ExtendWith(MockitoExtension.class)
public class DocumentReaderServiceTest {

  @Mock
  private DocumentConstants documentConstants;

  private DocumentReaderService documentReaderService;

  @BeforeEach
  public void setUp() {
    documentReaderService = new DocumentReaderService(documentConstants);
  }

  @ParameterizedTest(name = "#{index} - Test retrieveDocumentData of DocumentReaderService with file: {0}, isValidScenario: {1}")
  @MethodSource("dataProvider")
  public void testRetrieveDocumentData(String fileName, boolean isValid) {

    List<ReferenceResponse> expectedReferenceResponseList = DataFeeder.getReferenceResponseData();
    MultipartFile multipartFile = DataFeeder.getMultipartFile(fileName);

    when(documentConstants.getSupportedFileFormats()).thenReturn(List.of("txt"));

    if (isValid) {
      List<ReferenceResponse> actualReferenceResponseList = documentReaderService.retrieveDocumentData(multipartFile);
      assertThat(actualReferenceResponseList).isEqualTo(expectedReferenceResponseList);
    } else {
      assertThrows(FileFormatNotSupportedException.class,
          () -> documentReaderService.retrieveDocumentData(multipartFile));
    }

    verify(documentConstants, times(1)).getSupportedFileFormats();
  }

  private static Stream<Arguments> dataProvider() {
    return Stream.of(
        Arguments.of("testDoc.txt", Boolean.TRUE),
        Arguments.of("testDoc.pdf", Boolean.FALSE)
    );
  }
}
