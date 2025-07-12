package dnit.commons.api;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;


class ApiResponseJavaTest {


    @Nested
    class Success {

        @Test
        void testDefaultConstructor() {
            ApiResponse defaultConstructor = new ApiResponse();
            assertNotNull(defaultConstructor);
            assertNull(defaultConstructor.getResult());
            assertNull(defaultConstructor.getInfo());
            assertNull(defaultConstructor.getPagination());
        }


        @Test
        void testSuccessConstructorWithResult() {
            ApiResponse success1 = ApiResponse.success("test");
            assertNotNull(success1);
            assertEquals("test", success1.getResult());
            assertEquals(InfoType.SUCCESS, success1.getInfo().getType());
            assertNull(success1.getInfo().getMessage());
            assertNull(success1.getPagination());
        }


        @Test
        void testSuccessConstructorWithListAsResult() {
            List<String> list = List.of("test1", "test2");
            ApiResponse success1 = ApiResponse.success(list);
            assertNotNull(success1);
            assertEquals(list, success1.getResult());
            assertEquals(InfoType.SUCCESS, success1.getInfo().getType());
            assertNull(success1.getInfo().getMessage());
            assertNull(success1.getPagination());
        }


        @Test
        void testSuccessConstructorWithResultAndMessage() {
            ApiResponse success2 = ApiResponse.success("test", "message");
            assertNotNull(success2);
            assertEquals("test", success2.getResult());
            assertEquals(InfoType.SUCCESS, success2.getInfo().getType());
            assertEquals("message", success2.getInfo().getMessage());
            assertNull(success2.getPagination());
        }


        @Test
        void testSuccessConstructorWithResultAndPagination() {
            PageMetadata pagination = new PageMetadata();
            ApiResponse success3 = ApiResponse.success("test", pagination);
            assertNotNull(success3);
            assertEquals("test", success3.getResult());
            assertEquals(InfoType.SUCCESS, success3.getInfo().getType());
            assertNull(success3.getInfo().getMessage());
            assertEquals(pagination, success3.getPagination());
        }


        @Test
        void testSuccessConstructorWithAllParameters() {
            PageMetadata pagination = new PageMetadata();
            ApiResponse success4 = ApiResponse.success("test", "message", pagination);
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
            ApiResponse info1 = ApiResponse.info("test");
            assertNotNull(info1);
            assertEquals("test", info1.getResult());
            assertEquals(InfoType.INFO, info1.getInfo().getType());
            assertNull(info1.getInfo().getMessage());
            assertNull(info1.getStackTrace());
            assertNull(info1.getPagination());
        }


        @Test
        void testInfoConstructorWithResultAndMessage() {
            ApiResponse info2 = ApiResponse.info("test", "message");
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
            ApiResponse info2 = ApiResponse.info(list, "message");
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
            ApiResponse error1 = ApiResponse.error("test");
            assertNotNull(error1);
            assertNotNull(error1.getInfo());
            assertEquals("test", error1.getInfo().getMessage());
            assertEquals(InfoType.ERROR, error1.getInfo().getType());
            assertNull(error1.getPagination());
            assertNull(error1.getStackTrace());
        }

    }


    @Nested
    class Warn {

        @Test
        void testWarnConstructorWithResult() {
            ApiResponse warn1 = ApiResponse.warn("test");
            assertNotNull(warn1);
            assertEquals("test", warn1.getInfo().getMessage());
            assertEquals(InfoType.WARNING, warn1.getInfo().getType());
            assertNull(warn1.getStackTrace());
            assertNull(warn1.getPagination());
        }


        @Test
        void testWarnConstructorWithResultMessageAndStackTrace() {
            Throwable throwable = new Throwable("test throwable");
            ApiResponse warn3 = ApiResponse.warn("test", throwable);
            assertNotNull(warn3);
            assertEquals("test", warn3.getInfo().getMessage());
            assertEquals(InfoType.WARNING, warn3.getInfo().getType());
            assertEquals(throwable, warn3.getStackTrace());
            assertNull(warn3.getPagination());
        }
    }

}
