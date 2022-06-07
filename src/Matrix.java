
class ShapeMismatchException extends Exception {
    public ShapeMismatchException() {
        super("Matrix shape mismatch");
    }
}

public class Matrix {

    double[][] matrix;
    int rows;
    int cols;


    public Matrix(int rows, int cols, boolean randoms) {
        this.rows = rows;
        this.cols = cols;
        this.matrix = new double[rows][cols];

        if (randoms)
            for (int i_row = 0; i_row < rows; i_row++) {
                for (int i_col = 0; i_col < cols; i_col++)
                    this.matrix[i_row][i_col] = Math.random() * 2 - 1;
            }
        else
            for (int i_row = 0; i_row < rows; i_row++) {
                for (int i_col = 0; i_col < cols; i_col++)
                    this.matrix[i_row][i_col] = 0;
            }
    }

    public Matrix(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.matrix = new double[rows][cols];

        for (int i_row = 0; i_row < rows; i_row++)
            for (int i_col = 0; i_col < cols; i_col++)
                this.matrix[i_row][i_col] = 0;
    }

    public Matrix() {
        this.rows = this.cols = 0;
        this.matrix = new double[rows][cols];
    }

    public void print() {
        var matrixRowStr = new StringBuilder();
        for (int i_row = 0; i_row < this.rows; i_row++) {
            for (int i_col = 0; i_col < this.cols; i_col++)
                matrixRowStr.append(this.matrix[i_row][i_col]).append(" ");
            System.out.println(matrixRowStr);
            matrixRowStr.setLength(0);
        }
    }

    public Matrix getCols(int begin_cols, int end_cols) {
        Matrix splitted = new Matrix(this.rows, end_cols - begin_cols);
        for (int i_row = 0; i_row < this.rows; i_row++)
            for (int i_col = begin_cols; i_col < end_cols; i_col++)
                splitted.matrix[i_row][i_col - begin_cols] = this.matrix[i_row][i_col];
        return splitted;
    }

    public Matrix T() {
        Matrix transposed = new Matrix(this.cols, this.rows, false);

        for (int i_row = 0; i_row < this.rows; i_row++)
            for (int i_col = 0; i_col < this.cols; i_col++)
                transposed.matrix[i_col][i_row] = this.matrix[i_row][i_col];

        return transposed;
    }

    public Matrix add(Matrix other) throws ShapeMismatchException {
        if (this.rows != other.rows || this.cols != other.cols) throw new ShapeMismatchException();
        Matrix added = new Matrix(this.rows, this.cols);

        for (int i_row = 0; i_row < rows; i_row++)
            for (int i_col = 0; i_col < cols; i_col++)
                added.matrix[i_row][i_col] = this.matrix[i_row][i_col] + other.matrix[i_row][i_col];
        return added;
    }

    public Matrix add(double value){
        Matrix added = new Matrix(this.rows, this.cols);
        for (int i_row = 0; i_row < rows; i_row++)
            for (int i_col = 0; i_col < cols; i_col++)
                added.matrix[i_row][i_col] = this.matrix[i_row][i_col] + value;
        return added;
    }

    public Matrix subtract(Matrix other) throws ShapeMismatchException {
        if (this.rows != other.rows || this.cols != other.cols) throw new ShapeMismatchException();
        Matrix substracted = new Matrix(this.rows, this.cols);

        for (int i_row = 0; i_row < this.rows; i_row++)
            for (int i_col = 0; i_col < this.cols; i_col++)
                substracted.matrix[i_row][i_col] = this.matrix[i_row][i_col] - other.matrix[i_row][i_col];
        return substracted;
    }

    public Matrix multiply(Matrix other) throws ShapeMismatchException {
        if (this.cols != other.rows) throw new ShapeMismatchException();
        Matrix multiplied = new Matrix(this.rows, other.cols);
        for (int i = 0; i < multiplied.rows; i++)
            for (int j = 0; j < multiplied.cols; j++) {
                double sum = 0;
                for (int k = 0; k < this.cols; k++)
                    sum += this.matrix[i][k] * other.matrix[k][j];
                multiplied.matrix[i][j] = sum;
            }
        return multiplied;
    }

    public Matrix multiply(double value) {
        Matrix multiplied = new Matrix(this.rows, this.cols);
        for (int i_row = 0; i_row < rows; i_row++)
            for (int i_col = 0; i_col < cols; i_col++)
                multiplied.matrix[i_row][i_col] = this.matrix[i_row][i_col] * value;
        return multiplied;
    }

    public Matrix clone(){
        Matrix cloned = new Matrix();
        cloned.rows = this.rows;
        cloned.cols = this.cols;
        cloned.matrix = this.matrix.clone();
        return cloned;
    }

}
