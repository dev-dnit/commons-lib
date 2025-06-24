package dnit.commons.model;

import dnit.commons.exception.CommonException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SNVTest {


    @Test
    void testValidSNV_ValidFormats() {
        // Valid SNV formats
        assertTrue(SNV.isValidSNV("010BSP0100"));
        assertTrue(SNV.isValidSNV("101ARJ0200"));
        assertTrue(SNV.isValidSNV("230CMG0300"));
        assertTrue(SNV.isValidSNV("364NRS0400"));
        assertTrue(SNV.isValidSNV("490USC0500"));
        assertTrue(SNV.isValidSNV("116VDF0600"));
    }



    @Test
    void testValidSNV_AllValidEixoTypes() {
        // Test all valid eixo types
        assertTrue(SNV.isValidSNV("116ASP0100"));
        assertTrue(SNV.isValidSNV("116BSP0200"));
        assertTrue(SNV.isValidSNV("116CSP0300"));
        assertTrue(SNV.isValidSNV("116NSP0400"));
        assertTrue(SNV.isValidSNV("116USP0500"));
        assertTrue(SNV.isValidSNV("116VSP0600"));
    }



    @Test
    void testInvalidSNV_NullOrEmpty() {
        // Null or empty input
        assertFalse(SNV.isValidSNV(null));
        assertFalse(SNV.isValidSNV(""));
        assertFalse(SNV.isValidSNV(" "));
    }



    @Test
    void testInvalidSNV_WrongLength() {
        // Wrong length
        assertFalse(SNV.isValidSNV("116ASP01")); // Too short
        assertFalse(SNV.isValidSNV("116ASP010000")); // Too long
    }



    @Test
    void testInvalidSNV_InvalidBR() {
        // Invalid BR part
        assertFalse(SNV.isValidSNV("000ASP0100")); // BR below range
        assertFalse(SNV.isValidSNV("999ASP0100")); // BR above range
        assertFalse(SNV.isValidSNV("ABCASP0100")); // Non-numeric BR
    }



    @Test
    void testInvalidSNV_InvalidEixo() {
        // Invalid eixo type
        assertFalse(SNV.isValidSNV("116DSP0100")); // D is not a valid eixo
        assertFalse(SNV.isValidSNV("1161SP0100")); // Numeric eixo
        assertFalse(SNV.isValidSNV("116aSP0100")); // Lowercase eixo
    }



    @Test
    void testInvalidSNV_InvalidUF() {
        // Invalid UF
        assertFalse(SNV.isValidSNV("116A000100")); // Invalid UF code
        assertFalse(SNV.isValidSNV("116AXX0100")); // Non-existent UF
        assertFalse(SNV.isValidSNV("116Axx0100")); // Lowercase UF
    }



    @Test
    void testInvalidSNV_InvalidDigitosTrecho() {
        // Invalid digitosTrecho
        assertFalse(SNV.isValidSNV("116ASPABCD")); // Non-numeric digitosTrecho
        assertFalse(SNV.isValidSNV("116ASP....")); // Special characters in digitosTrecho
    }



    @Test
    void testSanitizeSNV_ValidInputs() {
        // Valid inputs that need sanitization
        assertEquals("116ASP0100", SNV.sanitizeSNV("116ASP0100"));
        assertEquals("116ASP0100", SNV.sanitizeSNV("116-A-SP-01-00"));
        assertEquals("116ASP0100", SNV.sanitizeSNV("116 A SP 01 00"));
        assertEquals("116ASP0100", SNV.sanitizeSNV("116ASP0100"));
        assertEquals("116ASP0100", SNV.sanitizeSNV("116asp0100"));
    }



    @Test
    void testSanitizeSNV_AccentedCharacters() {
        // Test with accented characters
        assertEquals("116ASP0100", SNV.sanitizeSNV("116ÁSP0100"));
    }



    @Test
    void testSanitizeSNV_SpecialCharacters() {
        // Test with special characters
        assertEquals("116ASP0100", SNV.sanitizeSNV("116-A/SP.01.00"));
        assertEquals("116ASP0100", SNV.sanitizeSNV("116_A_SP_01_00"));
        assertEquals("116ASP0100", SNV.sanitizeSNV("116*A*SP*01*00"));
    }



    @Test
    void testSanitizeSNV_NullOrEmpty() {
        // Null or empty input should throw exception
        assertThrows(CommonException.class, () -> SNV.sanitizeSNV(null));
        assertThrows(CommonException.class, () -> SNV.sanitizeSNV(""));
        assertThrows(CommonException.class, () -> SNV.sanitizeSNV(" "));
    }



    @Test
    void testSanitizeSNV_InvalidAfterSanitization() {
        // Input that becomes invalid after sanitization
        CommonException exception = assertThrows(CommonException.class,
                () -> SNV.sanitizeSNV("000ASP0100")); // Invalid BR
        assertTrue(exception.getMessage().contains("000ASP0100"));

        exception = assertThrows(CommonException.class,
                () -> SNV.sanitizeSNV("116XSP0100")); // Invalid eixo
        assertTrue(exception.getMessage().contains("116XSP0100"));

        exception = assertThrows(CommonException.class,
                () -> SNV.sanitizeSNV("116AXX0100")); // Invalid UF
        assertTrue(exception.getMessage().contains("116AXX0100"));
    }


    @Test
    void testSanitizeSNV_NonAlphanumericOnly() {
        // Input with only non-alphanumeric characters
        assertThrows(CommonException.class, () -> SNV.sanitizeSNV("!@#$%^&*()"));
    }



    @Test
    void testSanitizeSNV_ExceptionMessageContainsInput() {
        // Verify exception message contains the input
        String invalidInput = "invalid-input";
        CommonException exception = assertThrows(CommonException.class,
                () -> SNV.sanitizeSNV(invalidInput));
        assertTrue(exception.getMessage().contains(invalidInput));
    }

}
