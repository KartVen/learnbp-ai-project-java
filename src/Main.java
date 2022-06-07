import java.io.File;
import java.io.IOException;
import java.util.*;

public class Main {

    static Random random;

    public static void main(String[] args) throws Exception {
        random = new Random(1);

        Matrix data = importData("BreastTissueLight.csv", ";");
        Matrix dataTest = importData("BreastTissueTest.csv", ";");

        Matrix P = data.getCols(1, 10);
        Matrix T = data.getCols(0, 1);
        Matrix PNorm = normalizeMinMax(P.T(), 0, 1).T();
        Matrix TVector = createVector(T, 6);

        Matrix Ptest = dataTest.getCols(1, 10);
        Matrix TTest = dataTest.getCols(0, 1);
        Matrix PTestNorm = normalizeMinMax(Ptest.T(), 0, 1).T();
        Matrix TTestVector = createVector(TTest, 6);

        Network netBP = new Network(9,25,6);
        netBP.train(PNorm, TTestVector, 1, 0.01, PTestNorm, TTestVector);


    }
    private static Matrix importData(String fileName, String comma) throws Exception {
        List<List<Double>> data = new ArrayList<>();
        try {
            int n_row = 0;
            Scanner sc = new Scanner(new File(fileName));
            while (sc.hasNext()) {
                String row = sc.next();
                if (row.length() == 0) continue;
                String[] rowList = row.split(comma);
                data.add(new ArrayList<Double>());
                for (String col : rowList) data.get(n_row).add(Double.parseDouble(col));
                n_row++;
            }
            sc.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Matrix dataMatrix = new Matrix(data.size(), data.size() > 0 ? data.get(0).size() : 0);

        for (int i_row = 0; i_row < dataMatrix.rows; i_row++)
            for (int i_col = 0; i_col < dataMatrix.cols; i_col++)
                dataMatrix.matrix[i_row][i_col] = data.get(i_row).get(i_col);
        return dataMatrix;
    }

    private static Matrix normalizeMinMax(Matrix value, int y_min, int y_max) {
        Matrix normalized = new Matrix(value.rows, value.cols);
        for (int i_row = 0; i_row < value.rows; i_row++) {
            double[] row = value.matrix[i_row].clone();
            Arrays.sort(row);
            double min_value = row[0];
            double max_value = row[row.length - 1];
            for (int i_col = 0; i_col < row.length; i_col++) {
                value.matrix[i_row][i_col] = min_value != max_value ? (y_max - y_min) * (value.matrix[i_row][i_col] - min_value) / (max_value - min_value) + y_min: max_value;
            }
        }
        return value;
    }
    
    private static Matrix createVector(Matrix value, int cols){
        Matrix vector = new Matrix(value.rows, cols);
        for (int i_row = 0; i_row < vector.rows; i_row++)
            for (int i_col = 0; i_col < vector.cols; i_col++)
                if(value.matrix[i_row][0] - 1 == i_col)
                    vector.matrix[i_row][i_col] = 1;
        return vector;
    }

}
