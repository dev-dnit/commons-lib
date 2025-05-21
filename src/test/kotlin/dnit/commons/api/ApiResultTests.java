package dnit.commons.api;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


class ApiResultTests {


    @Test
    void shouldCreateResult_expectedToBeSuccess() {
        var instance = ApiResult.success(true);
        assertEquals(InfoType.SUCCESS, instance.getInfo() != null ? instance.getInfo().getType() : null);
    }


    @Test
    void shouldCreateResult_expectedToBeErrorWithMessage() {
        var instance = ApiResult.error("Something went wrong");

        assertEquals("Something went wrong", instance.getInfo() != null ? instance.getInfo().getMessage() : null);
        assertEquals(InfoType.ERROR, instance.getInfo() != null ? instance.getInfo().getType() : null);
    }


    @Test
    void shouldCreateResult_tryCreateErrorWithoutMessage_expectedToThrowException() {
        assertThrows(NullPointerException.class, () -> ApiResult.error(null));
    }

}
