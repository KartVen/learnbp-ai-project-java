import java.io.*;

public class Network {

    private Layer[] layers;

    public Network(int nInputs, int nHidden, int nOutputs) {
        layers = new Layer[2];
        layers[0] = new Layer(nHidden, nInputs);
        layers[1] = new Layer(nOutputs, nHidden);

    }
    private void forward_pass(double[] inputRecord) throws ShapeMismatchException {
        Layer hiddenLayer = layers[0];
        Layer outputLayer = layers[1];

        Matrix inputLayer = new Matrix(inputRecord.length, 1);
        for (int iNeuron = 0; iNeuron < inputLayer.rows; iNeuron++)
            inputLayer.matrix[iNeuron][0] = inputRecord[iNeuron];

        hiddenLayer.outputs = hiddenLayer.weights.multiply(inputLayer);
        hiddenLayer.outputs = hiddenLayer.outputs.add(hiddenLayer.bias);
        hiddenLayer.outputs = hiddenLayer.sigmoid();

        outputLayer.outputs = outputLayer.weights.multiply(hiddenLayer.outputs);
        outputLayer.outputs = outputLayer.outputs.add(outputLayer.bias);
        outputLayer.outputs = outputLayer.sigmoid();
    }


    public void train(Matrix trainDataInput, Matrix trainDataOutput, int epochs, double eta, Matrix testDataInput, Matrix testDataOutput) throws ShapeMismatchException {
        Layer hiddenLayer = layers[0];
        Layer outputLayer = layers[1];
        for (int iEpoch = 0; iEpoch < epochs; iEpoch++) {
            for (int iRecord = 0; iRecord < trainDataInput.rows; iRecord++) {
                forward_pass(trainDataInput.matrix[iRecord]);

                Matrix targetLayer = new Matrix(trainDataOutput.cols, 1);
                for (int iNeuron = 0; iNeuron < targetLayer.rows; iNeuron++)
                    targetLayer.matrix[iNeuron][0] = trainDataOutput.matrix[iRecord][iNeuron];

                Matrix outputLayerError = targetLayer.subtract(outputLayer.outputs);
                Matrix gradientOutputs = outputLayer.dsigmoid();
                gradientOutputs = gradientOutputs.multiply(outputLayerError).multiply(eta);


            }
        }
    }

}
