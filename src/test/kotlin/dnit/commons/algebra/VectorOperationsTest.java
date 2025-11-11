package dnit.commons.algebra;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VectorOperationsTest {

    private static final double EPS = 1e-9;


    // --- dot ---
    @Test
    void dot_deveCalcularProdutoEscalarCorretamente() {
        assertEquals(3.0, VectorOperations.dot(new double[]{-3, 7, 1}, new double[]{5, 2, 4}), EPS);
        assertEquals(-9.0, VectorOperations.dot(new double[]{3, -2, 4}, new double[]{1, 2, -2}), EPS);
        assertEquals(-23.0, VectorOperations.dot(new double[]{7, 1, -3}, new double[]{-2, 6, 5}), EPS);
        assertEquals(-1.0, VectorOperations.dot(new double[]{1, 1, 0}, new double[]{0, -1, 1}), EPS);
        assertEquals(-9.0, VectorOperations.dot(new double[]{3, -4, 0}, new double[]{5, 6, 0}), EPS);
        assertEquals(12.0, VectorOperations.dot(new double[]{2, 3, 1}, new double[]{-1, 4, 2}), EPS);
        assertEquals(-9.0, VectorOperations.dot(new double[]{5, 4, 2}, new double[]{-3, 0, 3}), EPS);
        assertEquals(0.0, VectorOperations.dot(new double[]{0, 0, 0}, new double[]{1, 2, 3}), EPS);
        assertEquals(14.0, VectorOperations.dot(new double[]{1, 2, 3}, new double[]{1, 2, 3}), EPS); // 1+4+9
        assertEquals(-6.0, VectorOperations.dot(new double[]{2, -1, 0}, new double[]{-2, 2, 1}), EPS);
        // ortogonal
        assertEquals(0.0, VectorOperations.dot(new double[]{2, 5, 6}, new double[]{5, -2, 0}), EPS);
        assertEquals(0.0, VectorOperations.dot(new double[]{1, 0, 0}, new double[]{0, 1, 0}), EPS);
    }


    @Test
    void dot_deveLancarExcecaoParaVetoresInvalidos() {
        // null
        assertThrows(IllegalArgumentException.class, () -> VectorOperations.dot(null, new double[]{1, 2, 3}));
        assertThrows(IllegalArgumentException.class, () -> VectorOperations.dot(new double[]{1, 2, 3}, null));
        // tamanhos incorretos
        assertThrows(IllegalArgumentException.class, () -> VectorOperations.dot(new double[]{1, 2}, new double[]{1, 2, 3}));
        assertThrows(IllegalArgumentException.class, () -> VectorOperations.dot(new double[]{1, 2, 3}, new double[]{1, 2, 3, 4}));
    }


    // --- cross
    @Test
    void cross_deveCalcularCorretamenteProdutoVetorial() {
        double[] a = {1, 2, 4};
        double[] b = {3, 5, 2};
        double[] ab = VectorOperations.cross(a, b);
        assertArrayEquals(new double[]{-16, 10, -1}, ab, EPS);

        double[] ba = VectorOperations.cross(b, a);
        assertArrayEquals(new double[]{-ab[0], -ab[1], -ab[2]}, ba, EPS);
    }


    @Test
    void cross_deveCalcularCorretamenteProdutoVetorial2() {
        double[] a = {1, 2, 0};
        double[] b = {3, 1, 2};
        double[] ab = VectorOperations.cross(a, b);
        assertArrayEquals(new double[]{4, -2, -5}, ab, EPS);

        double[] ba = VectorOperations.cross(b, a);
        assertArrayEquals(new double[]{-ab[0], -ab[1], -ab[2]}, ba, EPS);
    }


    @Test
    void cross_deveCalcularCorretamenteProdutoVetorial3() {
        double[] a = {2, -3, 4};
        double[] b = {1, 5, -2};
        double[] ab = VectorOperations.cross(a, b);
        assertArrayEquals(new double[]{-14, 8, 13}, ab, EPS);

        double[] ba = VectorOperations.cross(b, a);
        assertArrayEquals(new double[]{-ab[0], -ab[1], -ab[2]}, ba, EPS);
    }


    @Test
    void cross_deveCalcularCorretamenteProdutoVetorial4() {
        double[] a = {5, -2, 3};
        double[] b = {1, 4, -1};
        double[] ab = VectorOperations.cross(a, b);
        assertArrayEquals(new double[]{-10, 8, 22}, ab, EPS);

        double[] ba = VectorOperations.cross(b, a);
        assertArrayEquals(new double[]{-ab[0], -ab[1], -ab[2]}, ba, EPS);
    }


    @Test
    void cross_deveCalcularCorretamenteProdutoVetorial5() {
        double[] a = {6, -3, 1};
        double[] b = {2, 4, -5};
        double[] ab = VectorOperations.cross(a, b);
        assertArrayEquals(new double[]{11, 32, 30}, ab, EPS);

        double[] ba = VectorOperations.cross(b, a);
        assertArrayEquals(new double[]{-ab[0], -ab[1], -ab[2]}, ba, EPS);
    }


    @Test
    void cross_deveCalcularCorretamenteProdutoVetorial6() {
        double[] a = {2, -1, 3};
        double[] b = {4, 2, 1};
        double[] ab = VectorOperations.cross(a, b);
        assertArrayEquals(new double[]{-7, 10, 8}, ab, EPS);

        double[] ba = VectorOperations.cross(b, a);
        assertArrayEquals(new double[]{-ab[0], -ab[1], -ab[2]}, ba, EPS);
    }


    @Test
    void cross_deveCalcularCorretamenteProdutoVetorial7() {
        double[] a = {7, 0, -3};
        double[] b = {-1, 5, 2};
        double[] ab = VectorOperations.cross(a, b);
        assertArrayEquals(new double[]{15, -11, 35}, ab, EPS);

        double[] ba = VectorOperations.cross(b, a);
        assertArrayEquals(new double[]{-ab[0], -ab[1], -ab[2]}, ba, EPS);
    }


    @Test
    void cross_deveCalcularProdutoVetorialBasico() {
        // i x j = k
        assertArrayEquals(new double[]{0, 0, 1}, VectorOperations.cross(new double[]{1, 0, 0}, new double[]{0, 1, 0}), EPS);
        // j x k = i
        assertArrayEquals(new double[]{1, 0, 0}, VectorOperations.cross(new double[]{0, 1, 0}, new double[]{0, 0, 1}), EPS);
        // k x i = j
        assertArrayEquals(new double[]{0, 1, 0}, VectorOperations.cross(new double[]{0, 0, 1}, new double[]{1, 0, 0}), EPS);
    }


    @Test
    void cross_deveSerAntiComutativo() {
        double[] a = {2, -1, 3};
        double[] b = {0, 4, -2};
        double[] ab = VectorOperations.cross(a, b);
        double[] ba = VectorOperations.cross(b, a);
        assertArrayEquals(new double[]{-ab[0], -ab[1], -ab[2]}, ba, EPS);
    }


    @Test
    void cross_resultadoDeveSerOrtogonalAosOperandos() {
        double[] a = {3, 5, 2};
        double[] b = {1, 0, 4};
        double[] c = VectorOperations.cross(a, b);
        assertEquals(0.0, VectorOperations.dot(a, c), EPS);
        assertEquals(0.0, VectorOperations.dot(b, c), EPS);
    }


    @Test
    void cross_deveRetornarZeroParaVetoresParalelos() {
        double[] a = {1, 2, 3};
        double[] b = {2, 4, 6}; // paralelo a a
        assertArrayEquals(new double[]{0, 0, 0}, VectorOperations.cross(a, b), EPS);
    }


    @Test
    void cross_deveLancarExcecaoParaVetoresInvalidos() {
        assertThrows(IllegalArgumentException.class, () -> VectorOperations.cross(null, new double[]{1, 2, 3}));
        assertThrows(IllegalArgumentException.class, () -> VectorOperations.cross(new double[]{1, 2, 3}, null));
        assertThrows(IllegalArgumentException.class, () -> VectorOperations.cross(new double[]{1, 2}, new double[]{1, 2, 3}));
        assertThrows(IllegalArgumentException.class, () -> VectorOperations.cross(new double[]{1, 2, 3}, new double[]{1, 2, 3, 4}));
    }


    // --- norm ---
    @Test
    void norm_deveCalcularNormaCorretamente() {
        assertEquals(5.0, VectorOperations.norm(new double[]{3, 4, 0}), EPS);
        assertEquals(Math.sqrt(14.0), VectorOperations.norm(new double[]{3, 2, -1}), EPS);
        assertEquals(0.0, VectorOperations.norm(new double[]{0, 0, 0}), EPS);
        assertEquals(1.0, VectorOperations.norm(new double[]{1, 0, 0}), EPS);
        assertEquals(3.0, VectorOperations.norm(new double[]{1, 2, 2}), EPS); // sqrt(1+4+4)
        assertEquals(13.0, VectorOperations.norm(new double[]{3, 4, 12}), EPS); // sqrt(9+16+144)
    }


    @Test
    void norm_deveLancarExcecaoParaVetorInvalido() {
        assertThrows(IllegalArgumentException.class, () -> VectorOperations.norm(null));
        assertThrows(IllegalArgumentException.class, () -> VectorOperations.norm(new double[]{1, 2}));
        assertThrows(IllegalArgumentException.class, () -> VectorOperations.norm(new double[]{1, 2, 3, 4}));
    }

}
