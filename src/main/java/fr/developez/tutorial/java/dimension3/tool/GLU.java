package fr.developez.tutorial.java.dimension3.tool;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.DoubleBuffer;
import java.nio.IntBuffer;
import org.lwjgl.opengl.GL11;

public class GLU
{
    public static final int    GLU_BEGIN                      = 100100;
    public static final int    GLU_CCW                        = 100121;
    public static final int    GLU_CW                         = 100120;
    public static final int    GLU_EDGE_FLAG                  = 100104;
    public static final int    GLU_END                        = 100102;
    public static final int    GLU_ERROR                      = 100103;
    public static final int    GLU_EXTENSIONS                 = 100801;
    public static final int    GLU_EXTERIOR                   = 100123;
    public static final int    GLU_FALSE                      = 0;
    public static final int    GLU_FILL                       = 100012;
    public static final int    GLU_FLAT                       = 100001;
    public static final int    GLU_INSIDE                     = 100021;
    public static final int    GLU_INTERIOR                   = 100122;
    public static final int    GLU_INVALID_ENUM               = 100900;
    public static final int    GLU_INVALID_OPERATION          = 100904;
    public static final int    GLU_INVALID_VALUE              = 100901;
    public static final int    GLU_LINE                       = 100011;
    public static final int    GLU_NONE                       = 100002;
    public static final int    GLU_OUTSIDE                    = 100020;
    public static final int    GLU_OUT_OF_MEMORY              = 100902;
    public static final int    GLU_POINT                      = 100010;
    public static final int    GLU_SILHOUETTE                 = 100013;
    public static final int    GLU_SMOOTH                     = 100000;
    public static final int    GLU_TESS_BEGIN                 = 100100;
    public static final int    GLU_TESS_BEGIN_DATA            = 100106;
    public static final int    GLU_TESS_BOUNDARY_ONLY         = 100141;
    public static final int    GLU_TESS_COMBINE               = 100105;
    public static final int    GLU_TESS_COMBINE_DATA          = 100111;
    public static final int    GLU_TESS_COORD_TOO_LARGE       = 100155;
    public static final int    GLU_TESS_EDGE_FLAG             = 100104;
    public static final int    GLU_TESS_EDGE_FLAG_DATA        = 100110;
    public static final int    GLU_TESS_END                   = 100102;
    public static final int    GLU_TESS_END_DATA              = 100108;
    public static final int    GLU_TESS_ERROR                 = 100103;
    public static final int    GLU_TESS_ERROR1                = 100151;
    public static final int    GLU_TESS_ERROR2                = 100152;
    public static final int    GLU_TESS_ERROR3                = 100153;
    public static final int    GLU_TESS_ERROR4                = 100154;
    public static final int    GLU_TESS_ERROR5                = 100155;
    public static final int    GLU_TESS_ERROR6                = 100156;
    public static final int    GLU_TESS_ERROR7                = 100157;
    public static final int    GLU_TESS_ERROR8                = 100158;
    public static final int    GLU_TESS_ERROR_DATA            = 100109;
    public static final double GLU_TESS_MAX_COORD             = 1.0E150;
    public static final int    GLU_TESS_MISSING_BEGIN_CONTOUR = 100152;
    public static final int    GLU_TESS_MISSING_BEGIN_POLYGON = 100151;
    public static final int    GLU_TESS_MISSING_END_CONTOUR   = 100154;
    public static final int    GLU_TESS_MISSING_END_POLYGON   = 100153;
    public static final int    GLU_TESS_NEED_COMBINE_CALLBACK = 100156;
    public static final int    GLU_TESS_TOLERANCE             = 100142;
    public static final int    GLU_TESS_VERTEX                = 100101;
    public static final int    GLU_TESS_VERTEX_DATA           = 100107;
    public static final int    GLU_TESS_WINDING_ABS_GEQ_TWO   = 100134;
    public static final int    GLU_TESS_WINDING_NEGATIVE      = 100133;
    public static final int    GLU_TESS_WINDING_NONZERO       = 100131;
    public static final int    GLU_TESS_WINDING_ODD           = 100130;
    public static final int    GLU_TESS_WINDING_POSITIVE      = 100132;
    public static final int    GLU_TESS_WINDING_RULE          = 100140;
    public static final int    GLU_TRUE                       = 1;
    public static final int    GLU_UNKNOWN                    = 100124;
    public static final int    GLU_VERSION                    = 100800;
    public static final int    GLU_VERTEX                     = 100101;
    public static final String extensionString                = "GLU_EXT_nurbs_tessellator GLU_EXT_object_space_tess ";
    public static final String versionString                  = "1.3";


    private static final double[]     forward    = new double[3];
    private static       DoubleBuffer forwardBuf;
    private static final double[]     in         = new double[4];
    private static       DoubleBuffer inBuf;
    private static final double[]     matrix     = new double[16];
    private static       DoubleBuffer matrixBuf;
    private static final double[]     out        = new double[4];
    private static       DoubleBuffer outBuf;
    private static final double[]     side       = new double[3];
    private static       DoubleBuffer sideBuf;
    private static final double[][]   tempMatrix = new double[4][4];
    private static       DoubleBuffer tempMatrixBuf;
    private static final double[]     up         = new double[3];
    private static       DoubleBuffer upBuf;

    static
    {
        DoubleBuffer buffer = ByteBuffer.allocateDirect(128 * 8)
                                        .order(ByteOrder.nativeOrder())
                                        .asDoubleBuffer();
        final int position = 0;
        int       size     = 16;
        GLU.matrixBuf = GLU.slice(buffer, position, size);
        int index = position + size;
        GLU.tempMatrixBuf = GLU.slice(buffer, index, size);
        index += size;
        size              = 4;
        GLU.inBuf         = GLU.slice(buffer, index, size);
        index += size;
        GLU.outBuf        = GLU.slice(buffer, index, size);
        index += size;
        size              = 3;
        GLU.forwardBuf    = GLU.slice(buffer, index, size);
        index += size;
        GLU.sideBuf       = GLU.slice(buffer, index, size);
        index += size;
        GLU.upBuf         = GLU.slice(buffer, index, size);
    }

    private static final double[] IDENTITY_MATRIX = new double[]{
            1.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0,
            0.0, 0.0, 1.0};

    private static void cross(double[] vector1, double[] vector2, double[] vectorResult)
    {
        vectorResult[0] = vector1[1] * vector2[2] - vector1[2] * vector2[1];
        vectorResult[1] = vector1[2] * vector2[0] - vector1[0] * vector2[2];
        vectorResult[2] = vector1[0] * vector2[1] - vector1[1] * vector2[0];
    }

    private static void cross(DoubleBuffer vector1, DoubleBuffer vector2, DoubleBuffer vectorResult)
    {
        final int vector1Position      = vector1.position();
        final int vector2Position      = vector2.position();
        final int vectorResultPosition = vectorResult.position();
        vectorResult.put(0 + vectorResultPosition,
                         vector1.get(1 + vector1Position) * vector2.get(2 + vector2Position) - vector1.get(
                                 2 + vector1Position) * vector2.get(1 + vector2Position));
        vectorResult.put(1 + vectorResultPosition,
                         vector1.get(2 + vector1Position) * vector2.get(0 + vector2Position) - vector1.get(
                                 0 + vector1Position) * vector2.get(2 + vector2Position));
        vectorResult.put(2 + vectorResultPosition,
                         vector1.get(0 + vector1Position) * vector2.get(1 + vector2Position) - vector1.get(
                                 1 + vector1Position) * vector2.get(0 + vector2Position));
    }

    private static void normalize(double[] vector)
    {
        double norm = Math.sqrt(vector[0] * vector[0] + vector[1] * vector[1] + vector[2] * vector[2]);

        if (norm != 0.0)
        {
                         norm = 1.0 / norm;
            vector[0] *= norm;
            vector[1] *= norm;
            vector[2] *= norm;
        }
    }

    private static void normalize(DoubleBuffer vector)
    {
        int vectorPosition = vector.position();
        double norm = Math.sqrt(
                vector.get(0 + vectorPosition) * vector.get(0 + vectorPosition) +
                        vector.get(1 + vectorPosition) * vector.get(1 + vectorPosition) +
                        vector.get(2 + vectorPosition) * vector.get(2 + vectorPosition));
        if (norm != 0.0)
        {
            norm = 1.0 / norm;
            vector.put(0 + vectorPosition, vector.get(0 + vectorPosition) * norm);
            vector.put(1 + vectorPosition, vector.get(1 + vectorPosition) * norm);
            vector.put(2 + vectorPosition, vector.get(2 + vectorPosition) * norm);
        }
    }

    private static DoubleBuffer slice(DoubleBuffer buffer, int startIndex, int size)
    {
        buffer.position(startIndex);
        buffer.limit(startIndex + size);
        return buffer.slice();
    }

    private static boolean gluInvertMatrixd(double[] matrix, double[] matrixResult)
    {
        double[][] matrixTemp = GLU.tempMatrix;
        int        x          = 0;
        int        y;

        while (x < 4)
        {
            y = 0;

            while (y < 4)
            {
                matrixTemp[x][y] = matrix[x * 4 + y];
                y++;
            }

            x++;
        }

        GLU.gluMakeIdentityd(matrixResult);
        x = 0;

        while (x < 4)
        {
            int pivot = x;
            y = x + 1;

            while (y < 4)
            {
                if (Math.abs(matrixTemp[y][x]) > Math.abs(matrixTemp[x][x]))
                {
                    pivot = y;
                }

                ++y;
            }

            int    yy;
            double value;

            if (pivot != x)
            {
                yy = 0;

                while (yy < 4)
                {
                    value                        = matrixTemp[x][yy];
                    matrixTemp[x][yy]            = matrixTemp[pivot][yy];
                    matrixTemp[pivot][yy]        = value;
                    value                        = matrixResult[x * 4 + yy];
                    matrixResult[x * 4 + yy]     = matrixResult[pivot * 4 + yy];
                    matrixResult[pivot * 4 + yy] = value;
                    ++yy;
                }
            }

            if (matrixTemp[x][x] == 0.0)
            {
                return false;
            }

            value = matrixTemp[x][x];
            yy    = 0;

            while (yy < 4)
            {
                matrixTemp[x][yy] /=        value;
                matrixResult[x * 4 + yy] /= value;
                ++yy;
            }

            y = 0;

            while (y < 4)
            {
                if (y != x)
                {
                    value = matrixTemp[y][x];
                    yy    = 0;

                    while (yy < 4)
                    {
                        matrixTemp[y][yy] -= matrixTemp[x][yy] * value;
                        matrixResult[y * 4 + yy] -= matrixResult[x * 4 + yy] * value;
                        ++yy;
                    }
                }

                ++y;
            }

            ++x;
        }

        return true;
    }

    private static boolean gluInvertMatrixd(DoubleBuffer matrix, DoubleBuffer matrixResult)
    {
        final int    matrixPosition = matrix.position();
        final int    resultPosition = matrixResult.position();
        DoubleBuffer matrixTemp     = GLU.tempMatrixBuf;

        int x = 0;
        int y;

        while (x < 4)
        {
            y = 0;

            while (y < 4)
            {
                matrixTemp.put(x * 4 + y, matrix.get(x * 4 + y + matrixPosition));
                ++y;
            }

            ++x;
        }

        GLU.gluMakeIdentityd(matrixResult);
        x = 0;

        while (x < 4)
        {
            int pivot = x;
            y = x + 1;

            while (y < 4)
            {
                if (Math.abs(matrixTemp.get(y * 4 + x)) > Math.abs(matrixTemp.get(x * 4 + x)))
                {
                    pivot = y;
                }

                ++y;
            }

            int    yy;
            double value;

            if (pivot != x)
            {
                yy = 0;

                while (yy < 4)
                {
                    value = matrixTemp.get(x * 4 + yy);
                    matrixTemp.put(x * 4 + yy, matrixTemp.get(pivot * 4 + yy));
                    matrixTemp.put(pivot * 4 + yy, value);
                    value = matrixResult.get(x * 4 + yy + resultPosition);
                    matrixResult.put(x * 4 + yy + resultPosition, matrixResult.get(pivot * 4 + yy + resultPosition));
                    matrixResult.put(pivot * 4 + yy + resultPosition, value);
                    ++yy;
                }
            }

            if (matrixTemp.get(x * 4 + x) == 0.0)
            {
                return false;
            }

            value = matrixTemp.get(x * 4 + x);
            yy    = 0;

            while (yy < 4)
            {
                matrixTemp.put(x * 4 + yy, matrixTemp.get(x * 4 + yy) / value);
                matrixResult.put(x * 4 + yy + resultPosition, matrixResult.get(x * 4 + yy + resultPosition) / value);
                ++yy;
            }

            y = 0;

            while (y < 4)
            {
                if (y != x)
                {
                    value = matrixTemp.get(y * 4 + x);
                    yy    = 0;

                    while (yy < 4)
                    {
                        matrixTemp.put(y * 4 + yy, matrixTemp.get(y * 4 + yy) - matrixTemp.get(x * 4 + yy) * value);
                        matrixResult.put(y * 4 + yy + resultPosition,
                                         matrixResult.get(y * 4 + yy + resultPosition) - matrixResult.get(
                                                 x * 4 + yy + resultPosition) * value);
                        ++yy;
                    }
                }

                ++y;
            }

            ++x;
        }

        return true;
    }

    private static void gluMakeIdentityd(DoubleBuffer matrix)
    {
        int position = matrix.position();
        matrix.put(GLU.IDENTITY_MATRIX);
        matrix.position(position);
    }

    private static void gluMakeIdentityd(double[] matrix)
    {
        System.arraycopy(GLU.IDENTITY_MATRIX, 0, matrix, 0, 16);
    }

    private static void gluMultMatricesd(double[] matrix1, int matrix1Offset,
                                         double[] matrix2, int matrix2Offset,
                                         double[] matrixResult)
    {
        for (int x = 0; x <= 3; x++)
        {
            for (int y = 0; y <= 3; y++)
            {
                matrixResult[x * 4 + y] = matrix1[x * 4 + matrix1Offset] * matrix2[y + matrix2Offset] +
                        matrix1[x * 4 + 1 + matrix1Offset] * matrix2[4 + y + matrix2Offset] +
                        matrix1[x * 4 + 2 + matrix1Offset] * matrix2[8 + y + matrix2Offset] +
                        matrix1[x * 4 + 3 + matrix1Offset] * matrix2[12 + y + matrix2Offset];
            }
        }
    }

    private static void gluMultMatricesd(DoubleBuffer matrix1, DoubleBuffer matrix2, DoubleBuffer matrixResult)
    {
        final int position1      = matrix1.position();
        final int position2      = matrix2.position();
        final int positionResult = matrixResult.position();

        for (int x = 0; x <= 3; x++)
        {
            for (int y = 0; y <= 3; y++)
            {
                matrixResult.put(x * 4 + y + positionResult,
                                 matrix1.get(x * 4 + position1) * matrix2.get(y + position2) +
                                         matrix1.get(x * 4 + 1 + position1) * matrix2.get(4 + y + position2) +
                                         matrix1.get(x * 4 + 2 + position1) * matrix2.get(8 + y + position2) +
                                         matrix1.get(x * 4 + 3 + position1) * matrix2.get(12 + y + position2));
            }
        }
    }

    private static void gluMultMatrixVecd(double[] matrix, int matrixOffset, double[] vector, double[] vectorResult)
    {
        for (int index = 0; index <= 3; index++)
        {
            vectorResult[index] = vector[0] * matrix[index + matrixOffset] + vector[1] * matrix[4 + index + matrixOffset] +
                    vector[2] * matrix[8 + index + matrixOffset] + vector[3] * matrix[12 + index + matrixOffset];
        }

    }

    private static void gluMultMatrixVecd(DoubleBuffer matrix, DoubleBuffer vector, DoubleBuffer vectorResult)
    {
        final int positionVector = vector.position();
        final int positionResult = vectorResult.position();
        final int positionMatrix = matrix.position();

        for (int index = 0; index <= 3; index++)
        {
            vectorResult.put(index + positionResult, vector.get(positionVector) * matrix.get(index + positionMatrix) +
                    vector.get(1 + positionVector) * matrix.get(4 + index + positionMatrix) +
                    vector.get(2 + positionVector) * matrix.get(8 + index + positionMatrix) +
                    vector.get(3 + positionVector) * matrix.get(12 + index + positionMatrix));
        }

    }

    @ThreadOpenGL
    public static void gluLookAt(double x, double y, double z,
                                 double destinationX, double destinationY, double destinationZ,
                                 double upX, double upY, double upZ)
    {
        DoubleBuffer forwardVector = GLU.forwardBuf;
        DoubleBuffer sideVector    = GLU.sideBuf;
        DoubleBuffer upVector      = GLU.upBuf;
        forwardVector.put(0, destinationX - x);
        forwardVector.put(1, destinationY - y);
        forwardVector.put(2, destinationZ - z);
        upVector.put(0, upX);
        upVector.put(1, upY);
        upVector.put(2, upZ);
        GLU.normalize(forwardVector);
        GLU.cross(forwardVector, upVector, sideVector);
        GLU.normalize(sideVector);
        GLU.cross(sideVector, forwardVector, upVector);
        GLU.gluMakeIdentityd(GLU.matrixBuf);
        GLU.matrixBuf.put(0, sideVector.get(0));
        GLU.matrixBuf.put(4, sideVector.get(1));
        GLU.matrixBuf.put(8, sideVector.get(2));
        GLU.matrixBuf.put(1, upVector.get(0));
        GLU.matrixBuf.put(5, upVector.get(1));
        GLU.matrixBuf.put(9, upVector.get(2));
        GLU.matrixBuf.put(2, -forwardVector.get(0));
        GLU.matrixBuf.put(6, -forwardVector.get(1));
        GLU.matrixBuf.put(10, -forwardVector.get(2));
        GL11.glMultMatrixd(GLU.matrixBuf);
        GL11.glTranslated(-x, -y, -z);
    }

    @ThreadOpenGL
    public static void gluOrtho2D(double lefFrustumPlane, double rightFrustumPlane, double bottomFrustumPlane,
                                  double topFrustumPlane)
    {
        /*
         * @param l the left frustum plane
         * @param r the right frustum plane
         * @param b the bottom frustum plane
         * @param t the top frustum plane
         * @param n the near frustum plane
         * @param f the far frustum plane
         */
        GL11.glOrtho(lefFrustumPlane, rightFrustumPlane, bottomFrustumPlane, topFrustumPlane, -1.0, 1.0);
    }

    /**
     * Compute and apply a matrix to have a perspective view of the scene
     *
     * @param angleInDegree Angle (in degree) for vanishing point
     * @param ratio         View ratio (usually width/height)
     * @param nearZ         Value of Z near the screen
     * @param farZ          Value of Z in depth
     */
    @ThreadOpenGL
    public static void gluPerspective(double angleInDegree, double ratio, double nearZ, double farZ)
    {
        double angleInRadian = (angleInDegree * Math.PI) / 180.0;
        double distance      = farZ - nearZ;
        double sinusAngle    = Math.sin(angleInRadian);

        if (distance != 0.0 && sinusAngle != 0.0 && ratio != 0.0)
        {
            double angleRatio = Math.cos(angleInRadian) / sinusAngle;
            GLU.gluMakeIdentityd(GLU.matrixBuf);
            GLU.matrixBuf.put(0, angleRatio / ratio);
            GLU.matrixBuf.put(5, angleRatio);
            GLU.matrixBuf.put(10, -(farZ + nearZ) / distance);
            GLU.matrixBuf.put(11, -1.0);
            GLU.matrixBuf.put(14, -2.0 * nearZ * farZ / distance);
            GLU.matrixBuf.put(15, 0.0);
            GL11.glMultMatrixd(GLU.matrixBuf);
        }
    }

    @ThreadOpenGL
    public static void gluPickMatrix(double x, double y, double width, double height, IntBuffer rectangle2D)
    {
        if (width > 0.0 && height > 0.0)
        {
            int var11 = rectangle2D.position();
            GL11.glTranslated(
                    ((double) rectangle2D.get(2 + var11) - 2.0 * (x - (double) rectangle2D.get(var11))) / width,
                    ((double) rectangle2D.get(3 + var11) - 2.0 * (y - (double) rectangle2D.get(1 + var11))) / height,
                    0.0);
            GL11.glScaled((double) rectangle2D.get(2 + var11) / width, (double) rectangle2D.get(3 + var11) / height,
                          1.0);
        }
    }

    @ThreadOpenGL
    public static void gluPickMatrix(double x, double y, double width, double height, int[] rectangle2D,
                                     int rectangleOffset)
    {
        if (width > 0.0 && height > 0.0)
        {
            GL11.glTranslated(
                    ((double) rectangle2D[2 + rectangleOffset] - 2.0 * (x - (double) rectangle2D[rectangleOffset])) / width,
                    ((double) rectangle2D[3 + rectangleOffset] - 2.0 * (y - (double) rectangle2D[1 + rectangleOffset])) / height,
                    0.0);
            GL11.glScaled((double) rectangle2D[2 + rectangleOffset] / width,
                          (double) rectangle2D[3 + rectangleOffset] / height, 1.0);
        }
    }

    public static boolean gluProject(double x, double y, double z,
                                     double[] modelViewMatrix, int modelViewOffset,
                                     double[] projectionMatrix, int projectionOffset,
                                     int[] viewPort, int viewPortOffset,
                                     double[] point3D, int point3DOffset)
    {
        final double[] vectorIn  = GLU.in;
        final double[] vectorOut = GLU.out;
        vectorIn[0] = x;
        vectorIn[1] = y;
        vectorIn[2] = z;
        vectorIn[3] = 1.0;
        GLU.gluMultMatrixVecd(modelViewMatrix, modelViewOffset, vectorIn, vectorOut);
        GLU.gluMultMatrixVecd(projectionMatrix, projectionOffset, vectorOut, vectorIn);

        if (vectorIn[3] == 0.0)
        {
            return false;
        }

        vectorIn[3]                = 1.0 / vectorIn[3] * 0.5;
        vectorIn[0]                = vectorIn[0] * vectorIn[3] + 0.5;
        vectorIn[1]                = vectorIn[1] * vectorIn[3] + 0.5;
        vectorIn[2]                = vectorIn[2] * vectorIn[3] + 0.5;
        point3D[point3DOffset]     = vectorIn[0] * (double) viewPort[2 + viewPortOffset] + (double) viewPort[viewPortOffset];
        point3D[1 + point3DOffset] = vectorIn[1] * (double) viewPort[3 + viewPortOffset] + (double) viewPort[1 + viewPortOffset];
        point3D[2 + point3DOffset] = vectorIn[2];
        return true;
    }

    public static boolean gluProject(double x, double y, double z,
                                     DoubleBuffer modelView, DoubleBuffer projection, IntBuffer viewPort,
                                     DoubleBuffer point3D)
    {
        DoubleBuffer vectorIn  = GLU.inBuf;
        DoubleBuffer vectorOut = GLU.outBuf;
        vectorIn.put(0, x);
        vectorIn.put(1, y);
        vectorIn.put(2, z);
        vectorIn.put(3, 1.0);
        GLU.gluMultMatrixVecd(modelView, vectorIn, vectorOut);
        GLU.gluMultMatrixVecd(projection, vectorOut, vectorIn);

        if (vectorIn.get(3) == 0.0)
        {
            return false;
        }

        vectorIn.put(3, 1.0 / vectorIn.get(3) * 0.5);
        vectorIn.put(0, vectorIn.get(0) * vectorIn.get(3) + 0.5);
        vectorIn.put(1, vectorIn.get(1) * vectorIn.get(3) + 0.5);
        vectorIn.put(2, vectorIn.get(2) * vectorIn.get(3) + 0.5);
        int viewPortPosition = viewPort.position();
        int point3DPosition  = point3D.position();
        point3D.put(point3DPosition,
                    vectorIn.get(0) * (double) viewPort.get(2 + viewPortPosition) + (double) viewPort.get(
                            viewPortPosition));
        point3D.put(1 + point3DPosition,
                    vectorIn.get(1) * (double) viewPort.get(3 + viewPortPosition) + (double) viewPort.get(
                            1 + viewPortPosition));
        point3D.put(2 + point3DPosition, vectorIn.get(2));
        return true;
    }

    public static boolean gluUnProject(double x, double y, double z,
                                       double[] modelView, int modelViewOffset,
                                       double[] projection, int projectionOffset,
                                       int[] viewPort, int viewPortOffset,
                                       double[] point3D, int point3DOffset)
    {
        double[] vectorIn  = GLU.in;
        double[] vectorOut = GLU.out;
        GLU.gluMultMatricesd(modelView, modelViewOffset, projection, projectionOffset, GLU.matrix);

        if (!GLU.gluInvertMatrixd(GLU.matrix, GLU.matrix))
        {
            return false;
        }

        vectorIn[0] = x;
        vectorIn[1] = y;
        vectorIn[2] = z;
        vectorIn[3] = 1.0;
        vectorIn[0] = (vectorIn[0] - (double) viewPort[viewPortOffset]) / (double) viewPort[2 + viewPortOffset];
        vectorIn[1] = (vectorIn[1] - (double) viewPort[1 + viewPortOffset]) / (double) viewPort[3 + viewPortOffset];
        vectorIn[0] = vectorIn[0] * 2.0 - 1.0;
        vectorIn[1] = vectorIn[1] * 2.0 - 1.0;
        vectorIn[2] = vectorIn[2] * 2.0 - 1.0;
        GLU.gluMultMatrixVecd(GLU.matrix, 0, vectorIn, vectorOut);

        if (vectorOut[3] == 0.0)
        {
            return false;
        }

        vectorOut[3]               = 1.0 / vectorOut[3];
        point3D[point3DOffset]     = vectorOut[0] * vectorOut[3];
        point3D[1 + point3DOffset] = vectorOut[1] * vectorOut[3];
        point3D[2 + point3DOffset] = vectorOut[2] * vectorOut[3];
        return true;
    }

    public static boolean gluUnProject(double x, double y, double z,
                                       DoubleBuffer modelView, DoubleBuffer projection, IntBuffer viewPort,
                                       DoubleBuffer point3D)
    {
        DoubleBuffer vectorIn  = GLU.inBuf;
        DoubleBuffer vectorOut = GLU.outBuf;

        GLU.gluMultMatricesd(modelView, projection, GLU.matrixBuf);

        if (!GLU.gluInvertMatrixd(GLU.matrixBuf, GLU.matrixBuf))
        {
            return false;
        }

        vectorIn.put(0, x);
        vectorIn.put(1, y);
        vectorIn.put(2, z);
        vectorIn.put(3, 1.0);
        int viewPortPosition = viewPort.position();
        int point3DPosition  = point3D.position();
        vectorIn.put(0, (vectorIn.get(0) - (double) viewPort.get(viewPortPosition)) / (double) viewPort.get(
                2 + viewPortPosition));
        vectorIn.put(1, (vectorIn.get(1) - (double) viewPort.get(1 + viewPortPosition)) / (double) viewPort.get(
                3 + viewPortPosition));
        vectorIn.put(0, vectorIn.get(0) * 2.0 - 1.0);
        vectorIn.put(1, vectorIn.get(1) * 2.0 - 1.0);
        vectorIn.put(2, vectorIn.get(2) * 2.0 - 1.0);
        GLU.gluMultMatrixVecd(GLU.matrixBuf, vectorIn, vectorOut);

        if (vectorOut.get(3) == 0.0)
        {
            return false;
        }

        vectorOut.put(3, 1.0 / vectorOut.get(3));
        point3D.put(point3DPosition, vectorOut.get(0) * vectorOut.get(3));
        point3D.put(1 + point3DPosition, vectorOut.get(1) * vectorOut.get(3));
        point3D.put(2 + point3DPosition, vectorOut.get(2) * vectorOut.get(3));
        return true;
    }

    public static boolean gluUnProject4(double x, double y, double z, double w,
                                        double[] modelView, int modelViewOffset,
                                        double[] projection, int projectionOffset,
                                        int[] viewPort, int viewPortOffset,
                                        double var15, double var17,
                                        double[] point4D, int point4DOffset)
    {
        double[] var21 = GLU.in;
        double[] var22 = GLU.out;
        GLU.gluMultMatricesd(modelView, modelViewOffset, projection, projectionOffset, GLU.matrix);

        if (!GLU.gluInvertMatrixd(GLU.matrix, GLU.matrix))
        {
            return false;
        }

        var21[0] = x;
        var21[1] = y;
        var21[2] = z;
        var21[3] = w;
        var21[0] = (var21[0] - (double) viewPort[viewPortOffset]) / (double) viewPort[2 + viewPortOffset];
        var21[1] = (var21[1] - (double) viewPort[1 + viewPortOffset]) / (double) viewPort[3 + viewPortOffset];
        var21[2] = (var21[2] - var15) / (var17 - var15);
        var21[0] = var21[0] * 2.0 - 1.0;
        var21[1] = var21[1] * 2.0 - 1.0;
        var21[2] = var21[2] * 2.0 - 1.0;
        GLU.gluMultMatrixVecd(GLU.matrix, 0, var21, var22);

        if (var22[3] == 0.0)
        {
            return false;
        }

        point4D[point4DOffset]     = var22[0];
        point4D[1 + point4DOffset] = var22[1];
        point4D[2 + point4DOffset] = var22[2];
        point4D[3 + point4DOffset] = var22[3];
        return true;
    }

    public static boolean gluUnProject4(double x, double y, double z, double w,
                                        DoubleBuffer modelView, DoubleBuffer projection, IntBuffer viewPort,
                                        double var12, double var14,
                                        DoubleBuffer point4D)
    {
        DoubleBuffer var17 = GLU.inBuf;
        DoubleBuffer var18 = GLU.outBuf;
        GLU.gluMultMatricesd(modelView, projection, GLU.matrixBuf);

        if (!GLU.gluInvertMatrixd(GLU.matrixBuf, GLU.matrixBuf))
        {
            return false;
        }

        var17.put(0, x);
        var17.put(1, y);
        var17.put(2, z);
        var17.put(3, w);
        int var19 = viewPort.position();
        var17.put(0, (var17.get(0) - (double) viewPort.get(var19)) / (double) viewPort.get(2 + var19));
        var17.put(1, (var17.get(1) - (double) viewPort.get(1 + var19)) / (double) viewPort.get(3 + var19));
        var17.put(2, (var17.get(2) - var12) / (var14 - var12));
        var17.put(0, var17.get(0) * 2.0 - 1.0);
        var17.put(1, var17.get(1) * 2.0 - 1.0);
        var17.put(2, var17.get(2) * 2.0 - 1.0);
        GLU.gluMultMatrixVecd(GLU.matrixBuf, var17, var18);

        if (var18.get(3) == 0.0)
        {
            return false;
        }

        int var20 = point4D.position();
        point4D.put(var20, var18.get(0));
        point4D.put(1 + var20, var18.get(1));
        point4D.put(2 + var20, var18.get(2));
        point4D.put(3 + var20, var18.get(3));
        return true;
    }
}
