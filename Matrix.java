import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;


/**
 * Created by kelseyedge on 2/12/17.
 */

@SuppressWarnings("DefaultFileTemplate")
public class Matrix {
    private int rows;
    private int columns;
    private double[][] data;

    public Matrix(double[][] data) {
        this.rows = data.length;
        this.columns = data[0].length;
        this.data = data;
    }

    public Matrix(String filename) { readItIn(filename); }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    /**
     * Compares two matrices
     *
     * @param otherMatrix
     * @return
     */
    public boolean equals(Matrix otherMatrix) {
//        boolean equal = Boolean.TRUE;
        double epsilon = .001;
        if (!isSameSize(otherMatrix)) {
            return Boolean.FALSE;
        }
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (Math.abs((this.data[i][j]) - otherMatrix.data[i][j]) > epsilon) {
                    return Boolean.FALSE;
                }
            }
        }
        return true;
    }

    /**
     * Determines if the dimensions of two matrices are equivalent
     * @param otherMatrix
     * @return True or False
     */
    private boolean isSameSize(Matrix otherMatrix) {
        if (rows == otherMatrix.rows && columns == otherMatrix.columns) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    /**
     * Transposes a given matrix
     * @return new Matrix
     */
    public Matrix transpose() {
        double[][] newMatrix = new double[columns][rows];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                newMatrix[j][i] = data[i][j];
            }
        }
        return new Matrix(newMatrix);
    }

    /**
     * Reads in a matrix from txt file. Determines size of rows and columns. Creates
     * new double[][] with the dimensions from txt file. Splits tokens by whitespace.
     * iterates though  array, returns new double initialized from given String.
     * Sets matrix data to value.
     *
     * @param filename
     */
    private void readItIn(String filename) {
        ArrayList<String> lines; // = new ArrayList<>();
        int e = 0;
        lines = readData(filename);
        rows = lines.size();
        columns = (lines.get(0).split(" ")).length;
        data = new double[rows][columns];
        for (String ln : lines) {
            String[] x = ln.split(" ");
            int m = 0;
            for (String element : x) {
                String token = element.trim();
                double value = Double.parseDouble(token);
                data[e][m] = value;
                m++;
            }
            e++;
        }
    }

    /**
     * Adds two matrices together
     * @param otherMatrix
     * @return new Matrix
     */
    public Matrix add(Matrix otherMatrix) {
        double[][] k = new double[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                k[i][j] = this.data[i][j] + otherMatrix.data[i][j];
            }
        }
        return new Matrix(k);
    }

    /**
     * Mulitiplies two matrices. Checks the dimensions of the matrices to see if they
     * can be multiplied. Separately sends the rows of this.data and otherMatrix to multHelper.
     * @param otherMatrix
     * @return new Matrix
     */
    public Matrix mult(Matrix otherMatrix) {
        double[][] k = new double[this.rows][otherMatrix.columns];
        if (canBeMultiplied(otherMatrix))
            for (int i = 0; i < rows; i++) {
                k[i] = multHelper(this.data[i], otherMatrix.data);
            }
        return new Matrix(k);
    }

    /**
     * calculates the dot product for matrix multiplication and returns
     * a double[] to mult() method that contains the rows of the final matrix
     * @param row
     * @param otherDouble
     * @return double[]
     */
    private double[] multHelper(double[] row, double[][] otherDouble) {
        double[] k = new double[otherDouble[0].length];
        for (int i = 0; i < otherDouble[0].length; i++) {
            double total = 0;
            for (int j = 0; j < otherDouble.length; j++) {
                total += (row[j] * otherDouble[j][i]);
            }
            k[i] = total;
        }
        return k;
    }

    /**
     * multiplies a matrix by a constant
     * @param value
     * @return new Matrix
     */
    public Matrix mult(double value) {
        double[][] k = new double[rows][columns];
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[0].length; j++) {
                k[i][j] = value * data[i][j];
            }
        }
        return new Matrix(k);
    }

    /**
     * Determines if two matrices have dimensions that allow them to be multiplied
     * @param otherMatrix
     * @return True or False
     */
    private Boolean canBeMultiplied(Matrix otherMatrix) {
        if (this.columns == otherMatrix.rows) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    /**
     * load file-helper
     *
     * @param filename
     * @return ArayList of Strings
     */
    private static ArrayList<String> readData(String filename) {
        ArrayList<String> lines = new ArrayList<>();
        Scanner s = null;
        File infile = new File(filename);
        try {
            s = new Scanner(infile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        while (s != null ? s.hasNext() : false)
            lines.add(s.nextLine());

        return lines;
    }


    @Override
    public String toString() {
        String x = "";
        for (int i = 0; i < rows; i++) {
            x += Arrays.toString(data[i]) + "\n";

        }
        return x;
    }
}
