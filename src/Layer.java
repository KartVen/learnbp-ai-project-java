public class Layer {

    int size;
    int inputs;
    Matrix weights;
    double bias;
    Matrix outputs;
    Matrix errorDelta;

    public Layer(int nSize, int nInputs) {
        this.size = nSize;
        this.inputs = nInputs;
        this.weights = new Matrix(nSize, nInputs,true);
        this.bias = Main.random.nextDouble() * 2 - 1;
        this.outputs = new Matrix(nSize, 1);
        this.errorDelta = new Matrix(nSize, 1);
    }

    public Matrix sigmoid() {
        Matrix newOutputs = this.outputs.clone();
        for (int iNeuron = 0; iNeuron < this.size; iNeuron++)
            newOutputs.matrix[iNeuron][0] = 1.0 / (1 + Math.exp(-1 * newOutputs.matrix[iNeuron][0]));
        return newOutputs;
    }

    public Matrix dsigmoid() {
        Matrix newOutputs = this.outputs.clone();
        for (int iNeuron = 0; iNeuron < this.size; iNeuron++)
            newOutputs.matrix[iNeuron][0] = newOutputs.matrix[iNeuron][0] * (1 - newOutputs.matrix[iNeuron][0]);
        return newOutputs;
    }

}
