package com.woulter.document.controller;

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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.woulter.document.dto.ReferenceResponse;
import com.woulter.document.error.FileFormatNotSupportedException;
import com.woulter.document.service.DocumentReaderService;
import com.woulter.document.util.DataFeeder;

@ExtendWith(MockitoExtension.class)
public class DocumentReaderControllerTest {

  @Mock
  private DocumentReaderService documentReaderService;

  private DocumentReaderController documentReaderController;

  @BeforeEach
  public void setUp() {
    documentReaderController = new DocumentReaderController(documentReaderService);
  }

  @ParameterizedTest(name = "#{index} - Test retrieveDocumentData of DocumentReaderController with file: {0}, isValidScenario: {1}")
  @MethodSource("dataProvider")
  public void testRetrieveDocumentData(String fileName, boolean isValid) {

    List<ReferenceResponse> expectedReferenceResponseList = DataFeeder.getReferenceResponseData();
    MultipartFile multipartFile = DataFeeder.getMultipartFile(fileName);

    if (isValid) {
      when(documentReaderService.retrieveDocumentData(multipartFile)).thenReturn(expectedReferenceResponseList);
    } else {
      when(documentReaderService.retrieveDocumentData(multipartFile))
          .thenThrow(new FileFormatNotSupportedException("pdf"));
    }

    if (isValid) {
      ResponseEntity<List<ReferenceResponse>> listResponseEntity =
          documentReaderController.retrieveDocumentData(multipartFile);

      assertThat(listResponseEntity.getBody()).isEqualTo(expectedReferenceResponseList);
      assertThat(listResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    } else {
      assertThrows(FileFormatNotSupportedException.class,
          () -> documentReaderController.retrieveDocumentData(multipartFile));
    }

    verify(documentReaderService, times(1)).retrieveDocumentData(multipartFile);

  }

  private static Stream<Arguments> dataProvider() {
    return Stream.of(
        Arguments.of("testDoc.txt", Boolean.TRUE),
        Arguments.of("testDoc.pdf", Boolean.FALSE)
    );
  }
}
