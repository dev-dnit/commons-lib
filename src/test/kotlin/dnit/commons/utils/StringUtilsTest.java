package dnit.commons.utils;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


class StringUtilsTest {

    @Nested
    class IsNullOrEmpty {

        @Test
        void testNullString() {
            assertTrue(StringUtils.isNullOrEmpty(null));
        }


        @Test
        void testEmptyString() {
            assertTrue(StringUtils.isNullOrEmpty(""));
        }


        @Test
        void testBlankString() {
            assertTrue(StringUtils.isNullOrEmpty("   "));
        }


        @Test
        void testNonEmptyString() {
            assertFalse(StringUtils.isNullOrEmpty("OpenAI"));
        }


        @Test
        void testStringWithSpacesAroundText() {
            assertFalse(StringUtils.isNullOrEmpty("  text  "));
        }

    }


    @Nested
    class Unnacent {

        @Test
        void unnacentSomeCommonWords() {
            String word = "ãâáàíìéêèòóõúç";
            assertEquals("aaaaiieeeooouc", StringUtils.unnacent(word));
        }


        @Test
        public void testUnnacent_withAccents() {
            assertEquals("aeiou", StringUtils.unnacent("áéíóú"));
            assertEquals("AEIOU", StringUtils.unnacent("ÁÉÍÓÚ"));
            assertEquals("c", StringUtils.unnacent("ç"));
            assertEquals("C", StringUtils.unnacent("Ç"));
            assertEquals("nao e facil", StringUtils.unnacent("não é fácil"));
        }


        @Test
        public void testUnnacent_withoutAccents() {
            assertEquals("Hello World", StringUtils.unnacent("Hello World"));
            assertEquals("12345", StringUtils.unnacent("12345"));
        }


        @Test
        public void testUnnacent_emptyString() {
            assertEquals("", StringUtils.unnacent(""));
        }


        @Test
        public void testUnnacent_nullInput() {
            assertNull(StringUtils.unnacent(null));
        }


        @Test
        public void testUnnacent_mixedLanguages() {
            assertEquals("Jose", StringUtils.unnacent("José"));
            assertEquals("Munchen", StringUtils.unnacent("München"));
            assertEquals("Garcon", StringUtils.unnacent("Garçon"));
        }


        @Test
        public void testUnnacent_specialCharacters() {
            assertEquals("@#!$", StringUtils.unnacent("@#!$"));
            assertEquals("a@b#c!", StringUtils.unnacent("á@b#ç!"));
        }
    }


    @Nested
    class RemoveLetters {

        @Test
        public void testRemoveLetters_withLettersAndDigits() {
            assertEquals("123", StringUtils.removeLetras("abc123"));
        }


        @Test
        public void testRemoveLetters_onlyLetters() {
            assertEquals("", StringUtils.removeLetras("abcdefXYZ"));
        }


        @Test
        public void testRemoveLetters_onlyDigits() {
            assertEquals("123456", StringUtils.removeLetras("123456"));
        }


        @Test
        public void testRemoveLetters_mixedCharacters() {
            assertEquals("123!@#", StringUtils.removeLetras("A1B2C3!@#"));
        }


        @Test
        public void testRemoveLetters_emptyString() {
            assertEquals("", StringUtils.removeLetras(""));
        }


        @Test
        public void testRemoveLetters_nullInput() {
            assertNull(StringUtils.removeLetras(null));
        }
    }


    @Nested
    class RemoveDigits {

        @Test
        public void testRemoveDigits_withLettersAndDigits() {
            assertEquals("abc", StringUtils.removeDigitos("abc123"));
        }


        @Test
        public void testRemoveDigits_onlyDigits() {
            assertEquals("", StringUtils.removeDigitos("123456"));
        }


        @Test
        public void testRemoveDigits_onlyLetters() {
            assertEquals("abcdef", StringUtils.removeDigitos("abcdef"));
        }


        @Test
        public void testRemoveDigits_mixedCharacters() {
            assertEquals("ABC!@#", StringUtils.removeDigitos("A1B2C3!@#"));
        }


        @Test
        public void testRemoveDigits_emptyString() {
            assertEquals("", StringUtils.removeDigitos(""));
        }


        @Test
        public void testRemoveDigits_nullInput() {
            assertNull(StringUtils.removeDigitos(null));
        }

    }


    @Nested
    class KeepOnlyLettersAndDigits {

        @Test
        public void testKeepOnlyLettersAndDigits_withSpecialCharacters() {
            assertEquals("abc123", StringUtils.mantemApenasLetrasEDigitos("abc123!@#"));
        }


        @Test
        public void testKeepOnlyLettersAndDigits_onlyLettersAndDigits() {
            assertEquals("Java2025", StringUtils.mantemApenasLetrasEDigitos("Java2025"));
        }


        @Test
        public void testKeepOnlyLettersAndDigits_onlySpecialCharacters() {
            assertEquals("", StringUtils.mantemApenasLetrasEDigitos("!@#$%^&*()"));
        }


        @Test
        public void testKeepOnlyLettersAndDigits_withSpacesAndSymbols() {
            assertEquals("OpenAI123", StringUtils.mantemApenasLetrasEDigitos(" OpenAI 123!! "));
        }


        @Test
        public void testKeepOnlyLettersAndDigits_emptyString() {
            assertEquals("", StringUtils.mantemApenasLetrasEDigitos(""));
        }


        @Test
        public void testKeepOnlyLettersAndDigits_nullInput() {
            assertNull(StringUtils.mantemApenasLetrasEDigitos(null));
        }

    }


    @Nested
    class CapitalizeFirst {

        @Test
        public void testCapitalizeFirst_withLowercase() {
            assertEquals("Hello", StringUtils.capitalizeFirst("hello"));
        }


        @Test
        void testNullString() {
            assertNull(StringUtils.capitalizeFirst(null));
        }


        @Test
        void testEmptyString() {
            assertEquals("", StringUtils.capitalizeFirst(""));
        }


        @Test
        void testBlankString() {
            assertEquals("   ", StringUtils.capitalizeFirst("   "));
        }


        @Test
        void testSingleLowercaseLetter() {
            assertEquals("A", StringUtils.capitalizeFirst("a"));
        }


        @Test
        void testSingleUppercaseLetter() {
            assertEquals("A", StringUtils.capitalizeFirst("A"));
        }


        @Test
        void testLowercaseWord() {
            assertEquals("Openai", StringUtils.capitalizeFirst("openai"));
        }


        @Test
        void testWordWithFirstUppercase() {
            assertEquals("Openai", StringUtils.capitalizeFirst("Openai"));
        }


        @Test
        void testWordWithFirstUppercaseWithSomeUppercaseInMiddle() {
            assertEquals("Openai", StringUtils.capitalizeFirst("OpeNai"));
        }


        @Test
        void testStringWithLeadingSpaces() {
            assertEquals("  Openai   ", StringUtils.capitalizeFirst("  Openai   "));
        }


        @Test
        void testStringWithNonLetterFirstChar() {
            assertEquals("1openai", StringUtils.capitalizeFirst("1openai"));
        }
    }


    @Nested
    class CapitalizeEachWord {
        @Test
        void testNullString() {
            assertNull(StringUtils.capitalizeWords(null));
        }


        @Test
        void testEmptyString() {
            assertEquals("", StringUtils.capitalizeWords(""));
        }


        @Test
        void testSingleWordLowercase() {
            assertEquals("Hello", StringUtils.capitalizeWords("hello"));
        }


        @Test
        void testMultipleWordsLowercase() {
            assertEquals("Hello World", StringUtils.capitalizeWords("hello world"));
        }


        @Test
        void testWordsWithExtraSpaces() {
            assertEquals("Hello   World", StringUtils.capitalizeWords("hello   world"));
        }


        @Test
        void testWordsAlreadyCapitalized() {
            assertEquals("Hello World", StringUtils.capitalizeWords("Hello World"));
        }


        @Test
        void testMixedCaseWords() {
            assertEquals("Java Is Fun", StringUtils.capitalizeWords("jAVa is fUN"));
        }


        @Test
        void testStringWithLeadingAndTrailingSpaces() {
            assertEquals("  Hello World", StringUtils.capitalizeWords("  hello world"));
        }


        @Test
        void testOnlySpaces() {
            assertEquals("   ", StringUtils.capitalizeWords("   "));
        }


        @Test
        void testWordsWithNumbers() {
            assertEquals("User1 Test2", StringUtils.capitalizeWords("user1 test2"));
        }

    }


}