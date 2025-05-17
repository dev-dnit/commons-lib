package dnit.commons.utils;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


class NanoIdUtilsTest {


    @Nested
    class RandomNanoId {

        @Test
        void shouldGenerateUUIDwithtouException() {
            for (int i = 0; i<= 50_000; i++) {
                String id = NanoIdUtils.randomNanoId();
                assertEquals(17, id.length());
            }
        }


        @Test
        void shouldGenerateUUIDWithDefinedSizewithtouException() {
            var uuidSize = UUID.randomUUID().toString().length();
            for (int i = 0; i<= 10_000; i++) {
                for (int j=3; j<=30; j++) {
                    String id = NanoIdUtils.randomNanoId(j);
                    assertTrue(uuidSize != id.length());
                }
            }
        }
    }

}
