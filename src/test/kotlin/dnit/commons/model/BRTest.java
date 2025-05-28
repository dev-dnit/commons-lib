package dnit.commons.model;

import dnit.commons.exception.CommonException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


class BRTest {

    @Test
    void testValidBR_WithinRange() {
        assertTrue(BR.isValidBR("010"));
        assertTrue(BR.isValidBR("100"));
        assertTrue(BR.isValidBR("490"));
    }


    @Test
    void testValidBR_Boundaries() {
        assertTrue(BR.isValidBR("010")); // limite inferior válido
        assertTrue(BR.isValidBR("490")); // limite superior válido
        assertTrue(BR.isValidBR("499")); // limite superior válido
        assertFalse(BR.isValidBR("009")); // abaixo do limite
        assertFalse(BR.isValidBR("500")); // acima do limite
    }


    @Test
    void testInvalidBR_NullOrEmpty() {
        assertFalse(BR.isValidBR(null));
        assertFalse(BR.isValidBR(""));
        assertFalse(BR.isValidBR("!"));
        assertFalse(BR.isValidBR("?"));
        assertFalse(BR.isValidBR(" "));
        assertFalse(BR.isValidBR("  "));
        assertFalse(BR.isValidBR(" - "));
    }


    @Test
    void testInvalidBR_WrongLength() {
        assertFalse(BR.isValidBR("1"));
        assertFalse(BR.isValidBR("12"));
        assertFalse(BR.isValidBR("1234"));
    }


    @Test
    void testInvalidBR_NonDigitCharacters() {
        assertFalse(BR.isValidBR("12a"));
        assertFalse(BR.isValidBR("a10"));
        assertFalse(BR.isValidBR("1b3"));
        assertFalse(BR.isValidBR("abc"));
    }


    @Test
    void testInvalidBR_ExactlyThreeDigitsButOutOfRange() {
        assertFalse(BR.isValidBR("000"));
        assertFalse(BR.isValidBR("009"));
        assertFalse(BR.isValidBR("999"));
    }


    @Test
    void testSanitizeBr_ValidInputs() {
        assertEquals("010", BR.sanitizeBr(10));
        assertEquals("100", BR.sanitizeBr(100));
        assertEquals("110", BR.sanitizeBr(110));
        assertEquals("210", BR.sanitizeBr(210));
        assertEquals("320", BR.sanitizeBr(320));
        assertEquals("490", BR.sanitizeBr(490));
    }


    @Test
    void testSanitizeBr_Boundaries() {
        assertEquals("010", BR.sanitizeBr(10));
        assertEquals("490", BR.sanitizeBr(490));
        assertEquals("499", BR.sanitizeBr(499));
    }


    @Test
    void testSanitizeBr_InvalidLow() {
        CommonException exception = assertThrows(CommonException.class, () -> BR.sanitizeBr(9));
        assertTrue(exception.getMessage().contains("Não foi possível sanitizar a BR: 9"));
    }


    @Test
    void testSanitizeBr_Invalid() {
        for (int i = 500; i <= 1000; i += 50) {
            final int br = i;
            CommonException exception = assertThrows(CommonException.class, () -> BR.sanitizeBr(br));
            assertTrue(exception.getMessage().contains("Não foi possível sanitizar a BR: " + br));
        }
    }


    @Test
    void testSanitizeBr_InvalidHigh() {
        CommonException exception = assertThrows(CommonException.class, () -> BR.sanitizeBr(500));
        assertTrue(exception.getMessage().contains("Não foi possível sanitizar a BR: 500"));
    }


    @Test
    void testSanitizeBr_NullInput() {
        String br = null;
        CommonException exception = assertThrows(CommonException.class, () -> BR.sanitizeBr(br));
        assertTrue(exception.getMessage().contains("Não foi possível sanitizar a BR: null"));
    }


    @Test
    void testSanitizeBr_FormattedCorrectly() {
        assertEquals("011", BR.sanitizeBr(11));
        assertEquals("099", BR.sanitizeBr(99));
        assertEquals("490", BR.sanitizeBr(490));
    }


    @Test
    void testSanitizeBr_ValidStringInputs() {
        assertEquals("010", BR.sanitizeBr("10"));
        assertEquals("100", BR.sanitizeBr("100"));
        assertEquals("490", BR.sanitizeBr("490"));
    }


    @Test
    void testSanitizeBr_WithNonDigitCharacters() {
        assertEquals("123", BR.sanitizeBr("BR-123"));
        assertEquals("045", BR.sanitizeBr("045A"));
        assertEquals("490", BR.sanitizeBr("BR 490"));
    }


    @Test
    void testSanitizeBr_ExtraWhitespace() {
        assertEquals("011", BR.sanitizeBr(" 011 "));
        assertEquals("100", BR.sanitizeBr("\t100\n"));
    }


    @Test
    void testSanitizeBr_TooSmallOrLarge() {
        CommonException e1 = assertThrows(CommonException.class, () -> BR.sanitizeBr("009"));
        assertTrue(e1.getMessage().contains("009"));

        CommonException e2 = assertThrows(CommonException.class, () -> BR.sanitizeBr("500"));
        assertTrue(e2.getMessage().contains("500"));
    }


    @Test
    void testSanitizeBr_NonNumericString() {
        CommonException e = assertThrows(CommonException.class, () -> BR.sanitizeBr("abc"));
        assertTrue(e.getMessage().contains("abc"));
    }


    @Test
    void testSanitizeBr_EmptyOrNull() {
        String br = null;
        assertThrows(CommonException.class, () -> BR.sanitizeBr(""));
        assertThrows(CommonException.class, () -> BR.sanitizeBr("   "));
        assertThrows(CommonException.class, () -> BR.sanitizeBr(br));
    }


    @Test
    void testSanitizeBr_MixedInputWithValidNumber() {
        assertEquals("123", BR.sanitizeBr("abc123xyz"));
        assertEquals("010", BR.sanitizeBr("BR010"));
        assertEquals("010", BR.sanitizeBr("BR-010"));
        assertEquals("010", BR.sanitizeBr("BR/010"));
        assertEquals("010", BR.sanitizeBr("BR - 010"));
    }


    @Test
    void testSanitizeBr_InvalidFormatAfterSanitization() {
        assertThrows(CommonException.class, () -> BR.sanitizeBr("abc999xyz")); // 999 não é válido
    }
}