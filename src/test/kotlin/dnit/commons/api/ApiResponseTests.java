package dnit.commons.api;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


class ApiResponseTests {


    @Test
    void shouldCreateResult_expectedToBeSuccess() {
        var instance = ApiResponse.success(true);
        assertEquals(InfoType.SUCCESS, instance.getInfo() != null ? instance.getInfo().getType() : null);
    }


    @Test
    void shouldCreateResult_expectedToBeErrorWithMessage() {
        var instance = ApiResponse.error("Something went wrong");

        assertEquals("Something went wrong", instance.getInfo() != null ? instance.getInfo().getMessage() : null);
        assertEquals(InfoType.ERROR, instance.getInfo() != null ? instance.getInfo().getType() : null);
    }

}
