package at.fhooe.mc.wifipositioning.model.graphics

import java.awt.Point

/**
 * This class is a representation of affine transformations with the usage of matrices.
 */
class Matrix {
    private val matrix = Array(3) { DoubleArray(3) }

    constructor() {
        matrix[0][0] = 1.0
        matrix[0][1] = 0.0
        matrix[0][2] = 0.0
        matrix[1][0] = 0.0
        matrix[1][1] = 1.0
        matrix[1][2] = 0.0
        matrix[2][0] = 0.0
        matrix[2][1] = 0.0
        matrix[2][2] = 1.0
    }

    constructor(m11: Double, m12: Double, m13: Double, m21: Double, m22: Double, m23: Double,
                m31: Double, m32: Double, m33: Double) {

        matrix[0][0] = m11
        matrix[0][1] = m12
        matrix[0][2] = m13
        matrix[1][0] = m21
        matrix[1][1] = m22
        matrix[1][2] = m23
        matrix[2][0] = m31
        matrix[2][1] = m32
        matrix[2][2] = m33

    }

    /**
     * Prints a String representation of the matrix.
     *
     * @return - a String of the matrix.
     */
    override fun toString(): String {
        var i = 0
        var j = 0
        val output = StringBuilder()
        while (i < matrix.size) {
            output.append("[")
            while (j < matrix[i].size) {
                output.append(matrix[i][j])
                j++
                if (j < matrix[i].size) {
                    output.append(", ")
                }
            }
            output.append("]")
            i++
            j = 0

            output.append("\n")

        }
        return output.toString()
    }

    /**
     * Multiplies this matrix with another one with the correct
     * matrix-multiplication rules.
     *
     * @param other - the other matrix for the multiplication
     * @return the product of the matrixmultiplication
     */
    fun multiply(other: Matrix): Matrix {
        val multiplied = Matrix()
        var i = 0
        var j = 0
        var k = 0
        var tmp = 0.0

        while (i < matrix.size) {
            while (j < matrix[i].size) {
                tmp = 0.0
                while (k < matrix.size) {
                    tmp += matrix[i][k] * other.matrix[k][j]
                    k++
                }
                multiplied.matrix[i][j] = tmp
                j++
                k = 0
            }
            j = 0
            i++
        }

        return multiplied

    }

    /**
     * Multiplies this matrix with a point with the correct
     * matrix-multiplication rules.
     *
     * @param point - the point for the multiplication
     * @return the product of the multiplication
     */
    fun multiply(point: java.awt.Point): java.awt.Point {

        return Point((matrix[0][0] * point.x + matrix[0][1] * point.y + matrix[0][2]).toInt(), (matrix[1][0] * point.x + matrix[1][1] * point.y + matrix[1][2]).toInt())

    }

    /**
     * Multiplies this matrix with a rectangle with the correct
     * matrix-multiplication rules.
     *
     * @param rectangle - the rectangle for the multiplication
     * @return the product of the multiplication
     */
    fun multiply(rectangle: java.awt.Rectangle): java.awt.Rectangle {
        var point1 = java.awt.Point(rectangle.x, rectangle.y)
        var point2 = java.awt.Point(rectangle.x + rectangle.width, rectangle.y + rectangle.height)

        point1 = multiply(point1)
        point2 = multiply(point2)

        val r_out = java.awt.Rectangle(point1)
        r_out.add(point2)

        return r_out

    }

    /**
     * Multiplies this matrix with a polygon with the correct
     * matrix-multiplication rules.
     *
     * @param polygon - the polygon for the multiplication
     * @return the product of the multiplication
     */
    fun multiply(polygon: java.awt.Polygon): java.awt.Polygon {


        val poly = java.awt.Polygon(polygon.xpoints, polygon.ypoints, polygon.npoints)
        val x = poly.xpoints
        val y = poly.ypoints

        var i = 0

        while (i < x.size) {
            val tmp = multiply(java.awt.Point(x[i], y[i]))
            x[i] = tmp.x
            y[i] = tmp.y
            i++
        }

        return poly

    }

    /**
     * Calculates the inverse matrix of this matrix.
     *
     * @return the inverse matrix
     */
    fun inverse(): Matrix {
        val inv = Matrix()
        val abs = matrixAbs()
        inv.matrix[0][0] = det(matrix[1][1], matrix[1][2], matrix[2][1], matrix[2][2]) / abs
        inv.matrix[0][1] = det(matrix[0][2], matrix[0][1], matrix[2][2], matrix[2][1]) / abs
        inv.matrix[0][2] = det(matrix[0][1], matrix[0][2], matrix[1][1], matrix[1][2]) / abs

        inv.matrix[1][0] = det(matrix[1][2], matrix[1][0], matrix[2][2], matrix[2][0]) / abs
        inv.matrix[1][1] = det(matrix[0][0], matrix[0][2], matrix[2][0], matrix[2][2]) / abs
        inv.matrix[1][2] = det(matrix[0][2], matrix[0][0], matrix[1][2], matrix[1][0]) / abs

        inv.matrix[2][0] = det(matrix[1][0], matrix[1][1], matrix[2][0], matrix[2][1]) / abs
        inv.matrix[2][1] = det(matrix[0][1], matrix[0][0], matrix[2][1], matrix[2][0]) / abs
        inv.matrix[2][2] = det(matrix[0][0], matrix[0][1], matrix[1][0], matrix[1][1]) / abs

        return inv
    }

    /**
     * Calculates the determinant of a 2x2 matrix.
     *
     * @param a - upper left value of the 2x2 matrix
     * @param b - upper right value of the 2x2 matrix
     * @param c - lower left value of the 2x2 matrix
     * @param d - lower right value of the 2x2 matrix
     * @return the determinant
     */
    private fun det(a: Double, b: Double, c: Double, d: Double): Double {

        return a * d - b * c
    }

    /**
     * Calculates the absolute value of a 3x3 matrix.
     *
     * @return the absolute value
     */
    private fun matrixAbs(): Double {
        val d1 = matrix[0][0] * matrix[1][1] * matrix[2][2]
        val d2 = matrix[0][1] * matrix[1][2] * matrix[2][0]
        val d3 = matrix[0][2] * matrix[1][0] * matrix[2][1]
        val d4 = matrix[0][0] * matrix[1][2] * matrix[2][1]
        val d5 = matrix[0][1] * matrix[1][0] * matrix[2][2]
        val d6 = matrix[0][2] * matrix[1][1] * matrix[2][0]

        return d1 + d2 + d3 - d4 - d5 - d6
    }

    /**
     * Multiplies a GeoDoublePoint with this matrix.
     *
     * @param point the GeoDublePoint which is multiplied
     * @return the result of the multiplication
     */
    fun multiply(point: GeoDoublePoint): GeoDoublePoint {
        val srcx = point.x
        val srcy = point.y
        val destx = matrix[0][0] * srcx + matrix[0][1] * srcy
        val desty = matrix[1][0] * srcx + matrix[1][1] * srcy
        return GeoDoublePoint(destx, desty)
    }

    companion object {
        /**
         * Calculates a Translation for the matrix with x and y values.
         *
         * @param x - the value which tells the distance on the x axis
         * @param y - the value which tells the distance on the y axis
         * @return the matrix with the included translation
         */
        fun translate(x: Double, y: Double): Matrix {
            return Matrix(1.0, 0.0, x, 0.0, 1.0, y, 0.0, 0.0, 1.0)

        }

        /**
         * Calculates a Translation for the matrix with a point.
         *
         * @param point - the point tells the distance on the x and y axis
         * @return the matrix with the included translation
         */
        fun translate(point: java.awt.Point): Matrix {
            return Matrix(1.0, 0.0, point.x.toDouble(), 0.0, 1.0, point.y.toDouble(), 0.0, 0.0, 1.0)

        }

        /**
         * Calculates the scaling for the matrix.
         *
         * @param by - the factor for which the matrix is scaled on the
         * x and y axis
         * @return the matrix with the included scaling
         */
        fun scale(by: Double): Matrix {

            return Matrix(by, 0.0, 0.0, 0.0, by, 0.0, 0.0, 0.0, 1.0)
        }

        /**
         * Mirrors this matrix.
         *
         * @return - the mirrored matrix.
         */
        fun mirrorX(): Matrix {
            return Matrix(1.0, 0.0, 0.0, 0.0, -1.0, 0.0, 0.0, 0.0, 1.0)

        }

        /**
         * Mirrors this matrix on the y axis.
         *
         * @return - the mirrored matrix
         */
        fun mirrorY(): Matrix {
            return Matrix(-1.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 1.0)
        }

        /**
         * Rotates the matrix for a specific value.
         *
         * @param by - the angle in degrees
         * @return the matrix with the included rotation
         */
        fun rotate(by: Double): Matrix {
            val angle = Math.toRadians(by)
            return Matrix(Math.cos(angle), Math.sin(-angle), 0.0, Math.sin(angle), Math.cos(angle), 0.0, 0.0, 0.0, 1.0)
        }

        /**
         * Calculates the zoom factor for the x value which is necessary to fit the world rectangle into the screen.
         *
         * @param world - the world rectangle
         * @param window   - the window rectangle
         * @return - the factor which is necessary to resize the world
         */
        fun getZoomFactorX(world: java.awt.Rectangle, window: java.awt.Rectangle): Double {
            return window.width.toDouble() / world.width.toDouble()
        }

        /**
         * Calculates the zoom factor for the y value which is necessary to fit the world rectangle into the screen.
         *
         * @param world - the world rectangle
         * @param window   - the window rectangle
         * @return - the factor which is necessary to resize the world
         */
        fun getZoomFactorY(world: java.awt.Rectangle, window: java.awt.Rectangle): Double {
            return window.height.toDouble() / world.height.toDouble()

        }

        /**
         * Calculates the zoom factor for the x value, the zoom factor for the y value which are necessary to fit the
         * world rectangle into the screen and moves the world rectangle to the right position that it is entirely
         * displayed.
         *
         * @param world - the world rectangle
         * @param window   - the window rectangle
         * @return the matrix which fits the world into the screen
         */
        fun zoomToFit(world: java.awt.Rectangle, window: java.awt.Rectangle): Matrix {
            val middleWorld = java.awt.Point(world.centerX.toInt(), world.centerY.toInt())
            val middleWin = java.awt.Point(window.centerX.toInt(), window.centerY.toInt())

            middleWorld.x *= -1
            middleWorld.y *= -1

            val m1 = translate(middleWorld)
            val zoomX = getZoomFactorX(world, window)
            val zoomY = getZoomFactorY(world, window)

            val m2: Matrix

            if (zoomX <= zoomY) {
                m2 = scale(zoomX)
            } else {
                m2 = scale(zoomY)
            }

            val m3 = mirrorX()

            val m4 = translate(middleWin)

            var output = m4
            output = output.multiply(m2)
            output = output.multiply(m1)

            return output

        }

        /**
         * Calculates a new transformation matrix to zoom at a specific point on the screen for a specific
         * factor.
         *
         * @param old       - the matrix which is modified for the zooming
         * @param zoomPoint    - the point on the screen where the zoom happens
         * @param zoomScale - the factor for the zoom
         * @return the new matrix which the correct zoom values
         */
        fun zoomPoint(old: Matrix, zoomPoint: java.awt.Point, zoomScale: Double): Matrix {
            val zoom = java.awt.Point(zoomPoint.x * -1, zoomPoint.y * -1)
            val m1 = translate(zoom)
            val m2 = scale(zoomScale)
            val m3 = translate(zoomPoint)

            var output = m3.multiply(m2)
            output = output.multiply(m1)
            output = output.multiply(old)

            return output

        }
    }
}
