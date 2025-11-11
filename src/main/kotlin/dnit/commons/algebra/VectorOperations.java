package dnit.commons.algebra;

/**
 * Classe utilitária para operações com vetores tridimensionais.
 *
 * Esta classe fornece métodos estáticos para realizar operações matemáticas
 * comuns com vetores no espaço tridimensional (3D), incluindo produto escalar,
 * produto vetorial e cálculo de norma (magnitude).
 *
 * Todos os vetores são representados como arrays de doubles com exatamente
 * 3 elementos, correspondendo às coordenadas (x, y, z).
 */
public class VectorOperations {


    private static final int VECTOR_3D_SIZE = 3;


    private VectorOperations() {}



    /**
     * Calcula o produto escalar (dot product) entre dois vetores tridimensionais. <br>
     *
     * O resultado é um escalar que representa a projeção de um vetor sobre o outro,
     * multiplicada pela magnitude do segundo vetor.                                  <br>
     * Quando o produto escalar é zero, os vetores são ortogonais (perpendiculares).  <br>
     *
     * Fórmula: a · b = a₁b₁ + a₂b₂ + a₃b₃
     */
    public static double dot(double[] a, double[] b) {
        validateVector(a, "primeiro vetor");
        validateVector(b, "segundo vetor");

        return a[0] * b[0] + a[1] * b[1] + a[2] * b[2];
    }



    /**
     * Calcula o produto vetorial (cross product) entre dois vetores tridimensionais. <br>
     *
     * O produto vetorial resulta num novo vetor perpendicular aos dois vetores
     * originais, seguindo a regra da mão direita. A magnitude do vetor resultante
     * é igual à área do paralelogramo formado pelos dois vetores. <br>
     *
     * Fórmula: a × b = (a₂b₃ - a₃b₂, a₃b₁ - a₁b₃, a₁b₂ - a₂b₁)
     */
    public static double[] cross(double[] a, double[] b) {
        validateVector(a, "primeiro vetor");
        validateVector(b, "segundo vetor");

        return new double[] {
                a[1] * b[2] - a[2] * b[1],
                a[2] * b[0] - a[0] * b[2],
                a[0] * b[1] - a[1] * b[0]
        };
    }



    /**
     * Calcula a norma euclidiana (magnitude ou comprimento) de um vetor tridimensional.
     *
     * A norma representa a distância do vetor da origem até o ponto (v₁, v₂, v₃)
     * no espaço tridimensional. <br>
     *
     * A norma é calculada como a raiz quadrada da soma dos quadrados de cada componente:
     * Fórmula: ||v|| = √(v₁² + v₂² + v₃²)
     */
    public static double norm(double[] v) {
        validateVector(v, "vetor");
        return Math.sqrt(dot(v, v));
    }



    /**
     * Valida se um vetor é não-nulo e possui exatamente 3 elementos.
     */
    private static void validateVector(double[] vector, String paramName) {
        if (vector == null) {
            throw new IllegalArgumentException("O " + paramName + " não pode ser nulo");
        }

        if (vector.length != VECTOR_3D_SIZE) {
            throw new IllegalArgumentException("O " + paramName + " deve ter exatamente "
                    + VECTOR_3D_SIZE +" elementos, mas tem " + vector.length);
        }
    }

}