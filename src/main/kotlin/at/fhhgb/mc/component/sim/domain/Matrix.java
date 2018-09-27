package at.fhhgb.mc.component.sim.domain;

import at.fhhgb.mc.component.sim.model.GeoDoublePoint;

/**
 * This class is a representation of a 3x3 Matrix. It can do
 * most of the basic Matrix calculations and it is used as 
 * Translationmatrix for the coordinates.
 * 
 * @author Michael Nigl
 */
public class Matrix {
	
	
	private double[][] m_matrix = new double[3][3];
	
	/**
	 * default constructor which uses a default matrix
	 */
	public Matrix(){
		m_matrix[0][0] = 1;
		m_matrix[0][1] = 0;
		m_matrix[0][2] = 0;
		m_matrix[1][0] = 0;
		m_matrix[1][1] = 1;
		m_matrix[1][2] = 0;
		m_matrix[2][0] = 0;
		m_matrix[2][1] = 0;
		m_matrix[2][2] = 1;
	}
	
	/**
	 * Constructor which initializes a Matrix with custom values.
	 * 
	 * @param _m11 - the value of the matrix at the position 11
	 * @param _m12 - the value of the matrix at the position 12
	 * @param _m13 - the value of the matrix at the position 13
	 * @param _m21 - the value of the matrix at the position 21
	 * @param _m22 - the value of the matrix at the position 22
	 * @param _m23 - the value of the matrix at the position 23
	 * @param _m31 - the value of the matrix at the position 31
	 * @param _m32 - the value of the matrix at the position 32
	 * @param _m33 - the value of the matrix at the position 33
	 */
	public Matrix(double _m11, double _m12, double _m13, double _m21, double _m22, double _m23,
	              double _m31, double _m32, double _m33){
		
		m_matrix[0][0] = _m11;
		m_matrix[0][1] = _m12;
		m_matrix[0][2] = _m13;
		m_matrix[1][0] = _m21;
		m_matrix[1][1] = _m22;
		m_matrix[1][2] = _m23;
		m_matrix[2][0] = _m31;
		m_matrix[2][1] = _m32;
		m_matrix[2][2] = _m33;
		
	}

    /**
     * Calculates a Translation for the matrix with x and y values.
     *
     * @param _x - the value which tells the distance on the x axis
     * @param _y - the value which tells the distance on the y axis
     * @return the matrix with the included translation
     */
    public static Matrix translate(double _x, double _y) {
        Matrix translate = new Matrix(1, 0, _x, 0, 1, _y, 0, 0, 1);
        return translate;

    }

    /**
     * Calculates a Translation for the matrix with a point.
     *
     * @param _pt - the point tells the distance on the x and y axis
     * @return the matrix with the included translation
     */
    public static Matrix translate(java.awt.Point _pt) {
        Matrix translate = new Matrix(1, 0, _pt.x, 0, 1, _pt.y, 0, 0, 1);
        return translate;

    }

    /**
     * Calculates the scaling for the matrix.
     *
     * @param _scaleVal - the factor for which the matrix is scaled on the
     *                  x and y axis
     * @return the matrix with the included scaling
     */
    public static Matrix scale(double _scaleVal) {
        Matrix scale = new Matrix(_scaleVal, 0, 0, 0, _scaleVal, 0, 0, 0, 1);

        return scale;
    }

    /**
     * Mirrors this matrix.
     *
     * @return - the mirrored matrix.
     */
    public static Matrix mirrorX() {
        Matrix xMirror = new Matrix(1, 0, 0, 0, -1, 0, 0, 0, 1);
        return xMirror;

    }

    /**
     * Mirrors this matrix on the y axis.
     *
     * @return - the mirrored matrix
     */
    public static Matrix mirrorY() {
        Matrix yMirror = new Matrix(-1, 0, 0, 0, 1, 0, 0, 0, 1);
        return yMirror;
    }

    /**
     * Rotates the matrix for a specific value.
     *
     * @param _alpha - the angle in degrees
     * @return the matrix with the included rotation
     */
    public static Matrix rotate(double _alpha) {
        double angle = Math.toRadians(_alpha);
        Matrix rotate = new Matrix(Math.cos(angle), Math.sin(-angle), 0, Math.sin(angle), Math.cos(angle), 0, 0, 0, 1);
        return rotate;
    }

    /**
     * Calculates the zoom factor for the x value which is necessary to fit the world rectangle into the screen.
     *
     * @param _world - the world rectangle
     * @param _win   - the window rectangle
     * @return - the factor which is necessary to resize the world
     */
    public static double getZoomFactorX(java.awt.Rectangle _world, java.awt.Rectangle _win) {

        double result = (double) _win.width / (double) _world.width;
        return result;

    }

    /**
     * Calculates the zoom factor for the y value which is necessary to fit the world rectangle into the screen.
     *
     * @param _world - the world rectangle
     * @param _win   - the window rectangle
     * @return - the factor which is necessary to resize the world
     */
    public static double getZoomFactorY(java.awt.Rectangle _world, java.awt.Rectangle _win) {
        return (double) _win.height / (double) _world.height;

    }

    /**
     * Calculates the zoom factor for the x value, the zoom factor for the y value which are necessary to fit the
     * world rectangle into the screen and moves the world rectangle to the right position that it is entirely
     * displayed.
     *
     * @param _world - the world rectangle
     * @param _win   - the window rectangle
     * @return the matrix which fits the world into the screen
     */
    public static Matrix zoomToFit(java.awt.Rectangle _world, java.awt.Rectangle _win) {
        java.awt.Point middleWorld = new java.awt.Point((int) _world.getCenterX(), (int) _world.getCenterY());
        java.awt.Point middleWin = new java.awt.Point((int) _win.getCenterX(), (int) _win.getCenterY());

        middleWorld.x *= -1;
        middleWorld.y *= -1;

        Matrix m1 = translate(middleWorld);
        double zoomX = getZoomFactorX(_world, _win);
        double zoomY = getZoomFactorY(_world, _win);

        Matrix m2;

        if (zoomX <= zoomY) {
            m2 = scale(zoomX);
        } else {
            m2 = scale(zoomY);
        }

        Matrix m3 = mirrorX();

        Matrix m4 = translate(middleWin);

        Matrix output = m4;
        output = output.multiply(m2);
        output = output.multiply(m1);

        return output;

    }

    /**
     * Calculates a new transformation matrix to zoom at a specific point on the screen for a specific
     * factor.
     *
     * @param _old       - the matrix which is modified for the zooming
     * @param _zoomPt    - the point on the screen where the zoom happens
     * @param _zoomScale - the factor for the zoom
     * @return the new matrix which the correct zoom values
     */
    public static Matrix zoomPoint(Matrix _old, java.awt.Point _zoomPt, double _zoomScale) {
        java.awt.Point zoom = new java.awt.Point(_zoomPt.x * -1, _zoomPt.y * -1);
        Matrix m1 = translate(zoom);
        Matrix m2 = scale(_zoomScale);
        Matrix m3 = translate(_zoomPt);

        Matrix output = m3.multiply(m2);
        output = output.multiply(m1);
        output = output.multiply(_old);

        return output;

    }

    /**
	 * Prints a String representation of the matrix.
     *
	 * @return - a String of the matrix.
	 */
	public String toString(){
		int i = 0;
		int j = 0;
		StringBuilder output = new StringBuilder();
		while(i < m_matrix.length){
			output.append("[");
			while(j < m_matrix[i].length){
				output.append(m_matrix[i][j]);
				j++;
				if(j < m_matrix[i].length){
					output.append(", ");
                }
			}
			output.append("]");
			i++;
			j=0;

			output.append("\n");

		}
		return output.toString();
	}
	
	/**
	 * Multiplies this matrix with another one with the correct
	 * matrix-multiplication rules.
     *
	 * @param _other - the other matrix for the multiplication
	 * @return the product of the matrixmultiplication
	 */
	public Matrix multiply(Matrix _other){

		Matrix multiplied = new Matrix();
		int i = 0;
		int j = 0;
		int k = 0;
		double tmp = 0.0;

		while(i < m_matrix.length){
			while(j < m_matrix[i].length){
				tmp = 0.0;
				while(k < m_matrix.length){
					tmp += m_matrix[i][k] * _other.m_matrix[k][j];
					k++;
                }
				multiplied.m_matrix[i][j] = tmp;
				j++;
				k=0;
			}
			j=0;
			i++;
		}

		return multiplied;

	}
	
	/**
	 * Multiplies this matrix with a point with the correct
	 * matrix-multiplication rules.
     *
	 * @param _pt - the point for the multiplication
	 * @return the product of the multiplication
	 */
	public java.awt.Point multiply(java.awt.Point _pt){

		java.awt.Point point = new java.awt.Point((int)(m_matrix[0][0]*_pt.x+m_matrix[0][1]* _pt.y+m_matrix[0][2]),(int)(m_matrix[1][0]*_pt.x+m_matrix[1][1]*_pt.y+m_matrix[1][2]));

		return point;

	}
	
	/**
	 * Multiplies this matrix with a rectangle with the correct
	 * matrix-multiplication rules.
     *
	 * @param _rect - the rectangle for the multiplication
	 * @return the product of the multiplication
	 */
	public java.awt.Rectangle multiply(java.awt.Rectangle _rect){
        java.awt.Point point1 = new java.awt.Point(_rect.x,_rect.y);
		java.awt.Point point2 = new java.awt.Point(_rect.x+_rect.width,_rect.y+_rect.height);

		point1 = multiply(point1);
		point2 = multiply(point2);

		java.awt.Rectangle r_out = new java.awt.Rectangle(point1);
		r_out.add(point2);

		return r_out;

	}
	
	/**
	 * Multiplies this matrix with a polygon with the correct
	 * matrix-multiplication rules.
     *
	 * @param _poly - the polygon for the multiplication
	 * @return the product of the multiplication
	 */
	public java.awt.Polygon multiply(java.awt.Polygon _poly){


		java.awt.Polygon poly = new java.awt.Polygon(_poly.xpoints,_poly.ypoints,_poly.npoints);
		int[] x = poly.xpoints;
		int[] y = poly.ypoints;

		int i = 0;

        while(i < x.length){
			java.awt.Point tmp = multiply(new java.awt.Point(x[i],y[i]));
			x[i] = tmp.x;
			y[i] = tmp.y;
			i++;
		}

		return poly;

	}
	
	/**
	 * Calculates the inverse matrix of this matrix.
     *
	 * @return the inverse matrix
	 */
	public Matrix invers(){
		Matrix inv = new Matrix();
		double abs = matrixAbs();
		inv.m_matrix[0][0]=det(m_matrix[1][1],m_matrix[1][2],m_matrix[2][1],m_matrix[2][2])/abs;
		inv.m_matrix[0][1]=det(m_matrix[0][2],m_matrix[0][1],m_matrix[2][2],m_matrix[2][1])/abs;
		inv.m_matrix[0][2]=det(m_matrix[0][1],m_matrix[0][2],m_matrix[1][1],m_matrix[1][2])/abs;

		inv.m_matrix[1][0]=det(m_matrix[1][2],m_matrix[1][0],m_matrix[2][2],m_matrix[2][0])/abs;
		inv.m_matrix[1][1]=det(m_matrix[0][0],m_matrix[0][2],m_matrix[2][0],m_matrix[2][2])/abs;
		inv.m_matrix[1][2]=det(m_matrix[0][2],m_matrix[0][0],m_matrix[1][2],m_matrix[1][0])/abs;

		inv.m_matrix[2][0]=det(m_matrix[1][0],m_matrix[1][1],m_matrix[2][0],m_matrix[2][1])/abs;
		inv.m_matrix[2][1]=det(m_matrix[0][1],m_matrix[0][0],m_matrix[2][1],m_matrix[2][0])/abs;
		inv.m_matrix[2][2]=det(m_matrix[0][0],m_matrix[0][1],m_matrix[1][0],m_matrix[1][1])/abs;

		return inv;
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
	private double det(double a, double b,double c, double d){

		return a*d-b*c;
	}
	
	/**
	 * Calculates the absolute value of a 3x3 matrix.
     *
	 * @return the absolute value
	 */
	private double matrixAbs(){
		double d1 = m_matrix[0][0]*m_matrix[1][1]*m_matrix[2][2];
		double d2 = m_matrix[0][1]*m_matrix[1][2]*m_matrix[2][0];
		double d3 = m_matrix[0][2]*m_matrix[1][0]*m_matrix[2][1];
		double d4 = m_matrix[0][0]*m_matrix[1][2]*m_matrix[2][1];
		double d5 = m_matrix[0][1]*m_matrix[1][0]*m_matrix[2][2];
		double d6 = m_matrix[0][2]*m_matrix[1][1]*m_matrix[2][0];

		return d1+d2+d3-d4-d5-d6;
	}
	
	/**
	 * Multiplies a GeoDoublePoint with this matrix.
	 * 
	 * @param _pt the GeoDublePoint which is multiplied
	 * @return the result of the multiplication
	 */
	public GeoDoublePoint multiply(GeoDoublePoint _pt) {
		double srcx = _pt.getX();
		double srcy = _pt.getY();
		double destx = m_matrix[0][0] * srcx + m_matrix[0][1] * srcy;
		double desty = m_matrix[1][0] * srcx + m_matrix[1][1] * srcy;
		return new GeoDoublePoint(destx,desty);
	}
}
