package com.bookinggo.web.api.chat.translate.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import com.bookinggo.web.api.chat.engine.multilingual.MultilingualAdapter;
import com.bookinggo.web.api.chat.engine.multilingual.MultilingualResponse;
import com.bookinggo.web.api.chat.template.TemplateTestBase;
import com.bookinggo.web.api.chat.translate.dto.TranslateForm;
import com.bookinggo.web.api.chat.translate.dto.TranslateResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import reactor.core.publisher.Mono;

class TranslateServiceTest extends TemplateTestBase {

  @SpyBean private TranslateService service;
  @MockBean private MultilingualAdapter multilingualAdapter;

  @Test
  @DisplayName("Testing buildResponse")
  void test_buildResponse() throws Exception {
    mock();
    TranslateForm form =
        TranslateForm.builder().from("en").to("es").text("Hello 12:10:00 PM").build();
    MultilingualResponse response = MultilingualResponse.builder().translation("Hola").build();

    // with time
    TranslateResponse result = service.buildResponse(form, response);
    assertEquals("Hola 12:10:00 PM", result.getTranslatedText());

    // with time and extra spaces at the end
    form.setText("Hello 12:10:00 PM   ");
    assertEquals("Hola 12:10:00 PM", result.getTranslatedText());

    // without time
    form.setText("Hello");
    result = service.buildResponse(form, response);
    assertEquals("Hola", result.getTranslatedText());
  }

  @Test
  @DisplayName("Testing translate")
  void test_translate() throws Exception {
    mock();

    mockTranslate("Hola");
    String originalText = "Hello 12:10:00 PM";
    TranslateForm form = TranslateForm.builder().from("en").to("es").text(originalText).build();
    String text = "Hello";
    MultilingualResponse multilingualResponse = service.translate(form, text).block();
    TranslateResponse result = service.buildResponse(form, multilingualResponse);
    assertEquals("Hola 12:10:00 PM", result.getTranslatedText());
    assertEquals(originalText, result.getOriginalText());
  }

  @Test
  @DisplayName("Testing getTranslatedText")
  void test_getTranslatedText() throws Exception {
    mock();
    mockTranslate("Hola");
    String text = "Hello";
    MultilingualResponse result = service.getTranslatedText("en", "es", text).block();
    assertEquals("Hola", result.getTranslation());
  }

  private void mockTranslate(String translatedText) {
    given(multilingualAdapter.translate(any()))
        .willReturn(Mono.just(MultilingualResponse.builder().translation(translatedText).build()));
  }
}
