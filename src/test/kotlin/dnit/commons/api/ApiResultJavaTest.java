package dnit.commons.api;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;


class ApiResultJavaTest {


    @Nested
    class Success {

        @Test
        void testDefaultConstructor() {
            ApiResult<String> defaultConstructor = new ApiResult<>();
            assertNotNull(defaultConstructor);
            assertNull(defaultConstructor.getResult());
            assertNull(defaultConstructor.getInfo());
            assertNull(defaultConstructor.getPagination());
        }


        @Test
        void testSuccessConstructorWithResult() {
            ApiResult<String> success1 = ApiResult.success("test");
            assertNotNull(success1);
            assertEquals("test", success1.getResult());
            assertEquals(InfoType.SUCCESS, success1.getInfo().getType());
            assertNull(success1.getInfo().getMessage());
            assertNull(success1.getPagination());
        }


        @Test
        void testSuccessConstructorWithListAsResult() {
            List<String> list = List.of("test1", "test2");
            ApiResult<List<String>> success1 = ApiResult.success(list);
            assertNotNull(success1);
            assertEquals(list, success1.getResult());
            assertEquals(InfoType.SUCCESS, success1.getInfo().getType());
            assertNull(success1.getInfo().getMessage());
            assertNull(success1.getPagination());
        }


        @Test
        void testSuccessConstructorWithResultAndMessage() {
            ApiResult<String> success2 = ApiResult.success("test", "message");
            assertNotNull(success2);
            assertEquals("test", success2.getResult());
            assertEquals(InfoType.SUCCESS, success2.getInfo().getType());
            assertEquals("message", success2.getInfo().getMessage());
            assertNull(success2.getPagination());
        }


        @Test
        void testSuccessConstructorWithResultAndPagination() {
            PageMetadata pagination = new PageMetadata();
            ApiResult<String> success3 = ApiResult.success("test", pagination);
            assertNotNull(success3);
            assertEquals("test", success3.getResult());
            assertEquals(InfoType.SUCCESS, success3.getInfo().getType());
            assertNull(success3.getInfo().getMessage());
            assertEquals(pagination, success3.getPagination());
        }


        @Test
        void testSuccessConstructorWithAllParameters() {
            PageMetadata pagination = new PageMetadata();
            ApiResult<String> success4 = ApiResult.success("test", "message", pagination);
            assertNotNull(success4);
            assertEquals("test", success4.getResult());
            assertEquals(InfoType.SUCCESS, success4.getInfo().getType());
            assertEquals("message", success4.getInfo().getMessage());
            assertEquals(pagination, success4.getPagination());
        }

    }


    @Nested
    class Info {

        @Test
        void testInfoConstructorWithResult() {
            ApiResult<String> info1 = ApiResult.info("test");
            assertNotNull(info1);
            assertEquals("test", info1.getResult());
            assertEquals(InfoType.INFO, info1.getInfo().getType());
            assertNull(info1.getInfo().getMessage());
            assertNull(info1.getStackTrace());
            assertNull(info1.getPagination());
        }


        @Test
        void testInfoConstructorWithResultAndMessage() {
            ApiResult<String> info2 = ApiResult.info("test", "message");
            assertNotNull(info2);
            assertEquals("test", info2.getResult());
            assertEquals(InfoType.INFO, info2.getInfo().getType());
            assertEquals("message", info2.getInfo().getMessage());
            assertNull(info2.getStackTrace());
            assertNull(info2.getPagination());
        }


        @Test
        void testInfoConstructorWithListAsResultAndMessage() {
            var list = List.of("test1", "test2");
            ApiResult<List<String>> info2 = ApiResult.info(list, "message");
            assertNotNull(info2);
            assertEquals(list, info2.getResult());
            assertEquals(InfoType.INFO, info2.getInfo().getType());
            assertEquals("message", info2.getInfo().getMessage());
            assertNull(info2.getStackTrace());
            assertNull(info2.getPagination());
        }

    }


    @Nested
    class Error {

        @Test
        void testErrorConstructorWithResult() {
            ApiResult<String> error1 = ApiResult.error("test");
            assertNotNull(error1);
            assertNotNull(error1.getInfo());
            assertEquals("test", error1.getInfo().getMessage());
            assertEquals(InfoType.ERROR, error1.getInfo().getType());
            assertNull(error1.getPagination());
            assertNull(error1.getStackTrace());
        }


        @Test
        void testThrowErrorWithStackTrace() {
            // Arrange: Criar um ApiResult com uma exceção armazenada
            RuntimeException exception = new RuntimeException("Erro na API");
            ApiResult<String> errorResult = new ApiResult<>(null, null, null, exception);

            // Act & Assert: Verificar se throwError lança a exceção correta
            Exception thrown = assertThrows(RuntimeException.class, () -> errorResult.throwError());
            assertEquals("Erro na API", thrown.getMessage());
        }


        @Test
        void testThrowErrorWithoutStackTrace() {
            // Arrange: Criar um ApiResult sem stackTrace (nulo)
            ApiResult<String> errorResult = ApiResult.error("test");

            // Act & Assert: Verificar se throwError lança IllegalStateException
            IllegalStateException thrown = assertThrows(IllegalStateException.class, errorResult::throwError);
            assertEquals("Nenhum erro para lançar; stackTrace é nulo.", thrown.getMessage());
        }

    }


    @Nested
    class Warn {

        @Test
        void testWarnConstructorWithResult() {
            ApiResult<String> warn1 = ApiResult.warn("test");
            assertNotNull(warn1);
            assertEquals("test", warn1.getInfo().getMessage());
            assertEquals(InfoType.WARNING, warn1.getInfo().getType());
            assertNull(warn1.getStackTrace());
            assertNull(warn1.getPagination());
        }


        @Test
        void testWarnConstructorWithResultMessageAndStackTrace() {
            Throwable throwable = new Throwable("test throwable");
            ApiResult<String> warn3 = ApiResult.warn("test", throwable);
            assertNotNull(warn3);
            assertEquals("test", warn3.getInfo().getMessage());
            assertEquals(InfoType.WARNING, warn3.getInfo().getType());
            assertEquals(throwable, warn3.getStackTrace());
            assertNull(warn3.getPagination());
        }
    }

}
