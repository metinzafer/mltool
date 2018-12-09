package com.gnsmind.springBoot.tools;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.encog.ConsoleStatusReportable;
import org.encog.Encog;
import org.encog.ml.MLRegression;
import org.encog.ml.data.MLData;
import org.encog.ml.data.versatile.NormalizationHelper;
import org.encog.ml.data.versatile.VersatileMLDataSet;
import org.encog.ml.data.versatile.columns.ColumnDefinition;
import org.encog.ml.data.versatile.columns.ColumnType;
import org.encog.ml.data.versatile.sources.CSVDataSource;
import org.encog.ml.data.versatile.sources.VersatileDataSource;
import org.encog.ml.factory.MLMethodFactory;
import org.encog.ml.model.EncogModel;
import org.encog.util.csv.CSVFormat;
import org.encog.util.csv.ReadCSV;
import org.encog.util.simple.EncogUtility;
import org.springframework.web.servlet.ModelAndView;

import com.gnsmind.springBoot.model.Statistics;

public class EncogProcessor {

	public EncogProcessor() {
		// TODO Auto-generated constructor stub
	}

	public synchronized void getMapping(String fileName, String[] predictors, String[] response, String[] algrthm,
			HttpSession session, String[] outputs, ModelAndView modelAndView, int foldNo, int predictionType, String[] repeated, String[] repeatedValues) {
		try {
			File file = new File(fileName);

			VersatileDataSource source = null;
			String[] predictor = null, responseVal = null;
			int index = 0, respIndex = 0, repeatedValuesArrayIndex = 0;
			double tE = 0.0, vE = 0.0, max = 0.0, maxForReg = 10.0, rmse = 0.0, rmseTotal = 0.0;
			String modelName = "";
			CSVFormat format = new CSVFormat('.',','); // decimal point and comma separator
			
			if (file.exists()) {
				
				// Define the format of the data file.
				// This area will change, depending on the columns and
				// format of the file that you are trying to model.
				
				VersatileMLDataSet data = null;
				List<Integer> indexList = new ArrayList<Integer>();
				
				switch (predictionType) {
				
				//classification
				case 0:
					
					source = new CSVDataSource(file, true, CSVFormat.DECIMAL_POINT);
					data = new VersatileMLDataSet(source);
					break;
					
				//regression	
				case 1:

					source = new CSVDataSource(file, true, format);
					data = new VersatileMLDataSet(source);
					data.getNormHelper().setFormat(format);
					break;
				default:
					break;
				}
				List<ColumnDefinition> columns = new ArrayList<ColumnDefinition>();
				
				for (int i = 0; i != predictors.length; i++) {
					
					predictor = predictors[i].split(" ");
					if (predictor.length == 2) {

						index = Integer.valueOf((predictor[1].replace("(", "")).replace(")", "").trim());
						
						if(repeated!=null && repeated.length>0 && Arrays.asList(repeated).contains(predictor[0].trim())) {
							
							String[] repeatArray = repeatedValues[repeatedValuesArrayIndex].split(",");
							
							ColumnDefinition col = data.defineSourceColumn(predictor[0].trim(), index, ColumnType.ordinal);
							col.defineClass(repeatArray);
							columns.add(col);
							repeatedValuesArrayIndex++;
						}else {
							
							data.defineSourceColumn(predictor[0].trim(), index, ColumnType.continuous);
						}
						indexList.add(index);
					} else if (predictor.length > 2) {

						index = Integer.valueOf((predictor[2].replace("(", "")).replace(")", "").trim());
						
						data.defineSourceColumn((predictor[0] + " " + predictor[1]).trim(), index,
								ColumnType.continuous);
						
						indexList.add(index);
					}
				}
				
				responseVal = response[0].split(" ");
				respIndex = Integer.valueOf((responseVal[1].replace("(", "")).replace(")", "").trim());
				ColumnDefinition outputColumn = null;
				// Define the column that we are trying to predict.
				if(predictionType==0) {
					
					outputColumn = data.defineSourceColumn(responseVal[0].trim(), respIndex,
						ColumnType.nominal);
				}else {
					
					outputColumn = data.defineSourceColumn(responseVal[0].trim(), respIndex,
							ColumnType.continuous);
				}

				
				// Analyze the data, determine the min/max/mean/sd of every
				// column.

				data.analyze();

				// Map the prediction column to the output of the model, and all
				// other columns to the input.
				data.defineSingleOutputOthersInput(outputColumn);

				// Create feedforward neural network as the model type.
				// MLMethodFactory.TYPE_FEEDFORWARD.
				// You could also other model types, such as:
				// MLMethodFactory.TYPE_SVM: Support Vector Machine (SVM)
				// MLMethodFactory.TYPE_RBFNETWORK: RBF Neural Network
				// MLMethodFactor.TYPE_NEAT: NEAT Neural Network
				EncogModel model = new EncogModel(data);
				List<List<String>> resultsList = new ArrayList<List<String>>();
				List<Statistics> stList = new ArrayList<Statistics>();
				Statistics st = null;
				session.setAttribute("FNN", 0);
				session.setAttribute("SVM", 0);
				session.setAttribute("RBF", 0);
				session.setAttribute("NEAT", 0);
				for (int k = 0; k != algrthm.length; k++) {
					
					st = new Statistics();
					
					switch (algrthm[k]) {

					case "FNN":

						model.selectMethod(data, MLMethodFactory.TYPE_FEEDFORWARD);
						break;

					case "SVM":

						model.selectMethod(data, MLMethodFactory.TYPE_SVM);
						break;
					case "RBF":

						model.selectMethod(data, MLMethodFactory.TYPE_RBFNETWORK);
						break;
					case "NEAT":

						model.selectMethod(data, MLMethodFactory.TYPE_NEAT);
						break;
					case "PNN":

						model.selectMethod(data, MLMethodFactory.TYPE_PNN);
						break;
					case "SOM":

						model.selectMethod(data, MLMethodFactory.TYPE_SOM);
						break;
					default:
						break;
					}
						
					// Send any output to the console.
					//model.setReport(new ConsoleStatusReportable());

					// Now normalize the data. Encog will automatically determine
					// the correct normalization
					// type based on the model you chose in the last step.
					data.normalize();
					// Hold back some data for a final validation.
					// Shuffle the data into a random ordering.
					// Use a seed of 1001 so that we always use the same holdback
					// and will get more consistent results.
					model.holdBackValidation(0.3, true, 1001);

					// Choose whatever is the default training type for this model.
					model.selectTrainingType(data);

					// Use a 5-fold cross-validated train. Return the best method
					// found.
					MLRegression bestMethod = (MLRegression) model.crossvalidate(foldNo, true);

					// Display the training and validation errors.
					//System.out.println("Training error: " + EncogUtility.calculateRegressionError(bestMethod, model.getTrainingDataset()));
					//System.out.println("Validation error: " + EncogUtility.calculateRegressionError(bestMethod, model.getValidationDataset()));
					tE = EncogUtility.calculateRegressionError(bestMethod, model.getTrainingDataset());
					vE = EncogUtility.calculateRegressionError(bestMethod, model.getValidationDataset());
					/*
					session.setAttribute("TrainingError",
							EncogUtility.calculateRegressionError(bestMethod, model.getTrainingDataset()));
					session.setAttribute("ValidationError",
							EncogUtility.calculateRegressionError(bestMethod, model.getValidationDataset()));
					*/
					// Display our normalization parameters.
					NormalizationHelper helper = data.getNormHelper();
					//System.out.println(helper.toString());
					session.setAttribute("Helper", helper.toString());
					// Display the final model.
					//System.out.println("Final model: " + bestMethod);
					//session.setAttribute("Finalmodel", bestMethod);
					modelName = bestMethod.toString();
					// Loop over the entire, original, dataset and feed it through
					// the model.
					// This also shows how you would process new data, that was not
					// part of your
					// training set. You do not need to retrain, simply use the
					// NormalizationHelper
					// class. After you train, you can save the NormalizationHelper
					// to later
					// normalize and denormalize your data.
					ReadCSV csv = null;
					if(predictionType==0) {
						
						csv = new ReadCSV(file, true, CSVFormat.DECIMAL_POINT);
					}else {
						
						csv = new ReadCSV(file, true, format);
					}
					String[] line = new String[indexList.size()];
					MLData input = helper.allocateInputVector();
					double correctPredictionNumTotal = 0.0, correctPredictionRatioTotal = 0.0, numTotal = 0.0;

					HashMap<String, Double> predictionsTable = new HashMap<String, Double>();
					HashMap<String, Double> correctPredictionNumOutput = new HashMap<String, Double>();
					HashMap<String, Double> correctPredictionNumOutputTotal = new HashMap<String, Double>();
					HashMap<String, Double> correctPredictionNumOutputRatioTotal = new HashMap<String, Double>();
					
					if(outputs!=null && outputs.length>0) {
						
						for (int i = 0; i != outputs.length; i++) {
	
							String output = outputs[i].trim();
							predictionsTable.put(output, 0.0);
							correctPredictionNumOutput.put(output, 0.0);
							correctPredictionNumOutputTotal.put(output, 0.0);
							correctPredictionNumOutputRatioTotal.put(output, 0.0);
						}
					}
					int lineCounter = 0;
					StringBuilder predResult = new StringBuilder();
					while (csv.next()) {

						for (int i = 0; i != indexList.size(); i++) {

							line[i] = csv.get(indexList.get(i));
						}
						String correct = csv.get(respIndex);
						helper.normalizeInputVector(line, input.getData(), false);
						MLData output = bestMethod.compute(input);
						String elementChosen = helper.denormalizeOutputVectorToString(output)[0];

						predResult.append(Arrays.toString(line));
						predResult.append(" -> predicted: ");
						predResult.append("<strong>" + elementChosen + "</strong>");
						predResult.append("  ");
						predResult.append(" -> correct: ");
						predResult.append("<strong>" + correct + "</strong>");
						predResult.append("<br>");
						
						if(predictionType==0) {

							if (elementChosen.equals(correct)) {
	
								correctPredictionNumTotal++;
							}
						}else {
							
							if ((((Double.valueOf(elementChosen) + 2.0) >= Double.valueOf(correct)) && ((Double.valueOf(elementChosen) - 2.0) <= Double.valueOf(correct))) || (Double.valueOf(elementChosen) == Double.valueOf(correct))) {
								
								correctPredictionNumTotal++;
								rmseTotal = Math.pow((Double.valueOf(elementChosen) - Double.valueOf(correct)), 2);
							}
						}
						if(outputs!=null && outputs.length>0) {
							
							for (int i = 0; i != outputs.length; i++) {
	
								String out = outputs[i].trim();
	
								if (elementChosen.equals(out)) {
									if (elementChosen.equals(correct)) {
	
										Double num = correctPredictionNumOutput.get(out);
										num++;
										correctPredictionNumOutput.put(out, num);
									}
									numTotal = correctPredictionNumOutputTotal.get(out);
									numTotal++;
									correctPredictionNumOutputTotal.put(out, numTotal);
								}
							}
						}

						lineCounter++;
					}
					if(predictionType==1) 
						rmse = Math.sqrt(rmseTotal/lineCounter);
					else
						rmse = 0.0;
					
					if(outputs!=null && outputs.length>0) {

						for (int i = 0; i != outputs.length; i++) {
	
							String out = outputs[i].trim();
	
							Double res = (double) Math
									.round((correctPredictionNumOutput.get(out) / correctPredictionNumOutputTotal.get(out))
											* 100.0);
							correctPredictionNumOutputRatioTotal.put(out, res);
						}
					}
					correctPredictionRatioTotal = Math.round((correctPredictionNumTotal / lineCounter) * 100.0);

					List<String> result = new ArrayList<String>();
					// System.out.println("==================================================");
					// System.out.println("The ratio of correct prediction Total : " +
					// correctPredictionRatioTotal + "%");
					result.add("(" + algrthm[k] + ") The ratio of correct prediction TOTAL      : " + correctPredictionRatioTotal + "%");
					if(outputs!=null && outputs.length>0) {

						for (int i = 0; i != outputs.length; i++) {
	
							String out = outputs[i].trim();
							// System.out.println("The ratio of correct prediction " + out + " : " +
							// correctPredictionNumOutputRatioTotal.get(out) + "%");
							result.add("The ratio of correct prediction " + out + "     : "
									+ correctPredictionNumOutputRatioTotal.get(out) + "%");
						}
					}
					// System.out.println("==================================================");
					resultsList.add(result);
					st.setAlgorithm(algrthm[k]);
					st.setPredResult(predResult);
					st.setResult(result);
					st.setTrainingError(tE);
					st.setValidationError(vE);
					st.setFinalModel(modelName);
					st.setTotalRatio(correctPredictionRatioTotal);
					st.setHelper(helper.toString());
					st.setRmse(rmse);
					stList.add(st);
					st = null;
					
					if(predictionType == 0 && correctPredictionRatioTotal > max) {
					
						max = correctPredictionRatioTotal;
						session.setAttribute("bestRatio", max);
					}else if(predictionType == 1 && rmse < maxForReg){
						
						maxForReg = rmse;
						session.setAttribute("bestRatio", maxForReg);
					}
					session.setAttribute(algrthm[k], correctPredictionRatioTotal);
				}
				
				session.setAttribute("statistics", stList);
				stList = null;
				// Delete data file ande shut down.
				// file.delete();
				Encog.getInstance().shutdown();
			}

		} catch (Exception e) {

			modelAndView.addObject("errorMessage", e.getMessage());
			modelAndView.setViewName("error");
		}

	}
/*
	private BasicNetwork buildNetwork(String[] predictors, String[] response) {

		BasicNetwork network = new BasicNetwork();
		// input
		network.addLayer(new BasicLayer(new ActivationSigmoid(), false, predictors.length));
		// hidden levels
		network.addLayer(new BasicLayer(new ActivationSigmoid(), false, 1000));
		network.addLayer(new BasicLayer(new ActivationSigmoid(), false, 1000));
		// output
		network.addLayer(new BasicLayer(new ActivationLinear(), false, response.length));
		network.getStructure().finalizeStructure();
		network.reset();

		return network;
	}
*/
}
