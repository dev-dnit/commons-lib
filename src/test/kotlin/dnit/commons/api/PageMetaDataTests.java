package dnit.commons.api;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


class PageMetaDataTests {

    @Test
    void testDefaultValues() {
        PageMetadata metadata = new PageMetadata();
        assertEquals(0, metadata.getCount());
        assertEquals(0, metadata.getTotal());
        assertEquals(0, metadata.getPages());
        assertEquals(0, metadata.getCurrentPage());
        assertFalse(metadata.getHasNextPage());
        assertFalse(metadata.getHasPreviousPage());
    }


    @Test
    void testSetAndGetCount() {
        PageMetadata metadata = new PageMetadata();
        metadata.setCount(10);
        assertEquals(10, metadata.getCount());
    }


    @Test
    void testSetAndGetTotal() {
        PageMetadata metadata = new PageMetadata();
        metadata.setTotal(100);
        assertEquals(100, metadata.getTotal());
    }


    @Test
    void testSetAndGetPages() {
        PageMetadata metadata = new PageMetadata();
        metadata.setPages(5);
        assertEquals(5, metadata.getPages());
    }


    @Test
    void testSetAndGetCurrentPage() {
        PageMetadata metadata = new PageMetadata();
        metadata.setCurrentPage(2);
        assertEquals(2, metadata.getCurrentPage());
    }


    @Test
    void testHasNextPageWhenTrue() {
        PageMetadata metadata = new PageMetadata();
        metadata.setCurrentPage(1);
        metadata.setTotal(3);
        assertTrue(metadata.getHasNextPage());
    }


    @Test
    void testHasNextPageWhenFalse() {
        PageMetadata metadata = new PageMetadata();
        metadata.setCurrentPage(3);
        metadata.setTotal(3);
        assertFalse(metadata.getHasNextPage());
    }


    @Test
    void testHasPreviousPageWhenTrue() {
        PageMetadata metadata = new PageMetadata();
        metadata.setCurrentPage(1);
        assertTrue(metadata.getHasPreviousPage());
    }


    @Test
    void testHasPreviousPageWhenFalse() {
        PageMetadata metadata = new PageMetadata();
        metadata.setCurrentPage(0);
        assertFalse(metadata.getHasPreviousPage());
    }

}
