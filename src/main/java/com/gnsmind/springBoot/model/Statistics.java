package com.gnsmind.springBoot.model;

import java.util.List;

public class Statistics {
	
	private StringBuilder predResult;
	private String algorithm;
	private List<String> result;
	private double trainingError = 0.0;
	private double validationError = 0.0;
	private String finalModel = "";
	private double totalRatio = 0.0;
	private String helper;
	private double rmse;
	
	public Statistics() {
		super();
	}
	
	public Statistics(StringBuilder predResult, String algorithm, List<String> result, double trainingError,
			double validationError, String finalModel, double totalRatio, String helper) {
		super();
		this.predResult = predResult;
		this.algorithm = algorithm;
		this.result = result;
		this.trainingError = trainingError;
		this.validationError = validationError;
		this.finalModel = finalModel;
		this.totalRatio = totalRatio;
		this.helper = helper;
	}

	public StringBuilder getPredResult() {
		
		return predResult;
	}

	public void setPredResult(StringBuilder predResult) {
		this.predResult = predResult;
	}

	public String getAlgorithm() {
		return algorithm;
	}

	public void setAlgorithm(String algorithm) {
		this.algorithm = algorithm;
	}

	public List<String> getResult() {
		
		return result;
	}

	public void setResult(List<String> result) {
		this.result = result;
	}
	
	public double getTrainingError() {
		return trainingError;
	}

	public void setTrainingError(double trainingError) {
		this.trainingError = trainingError;
	}

	public double getValidationError() {
		return validationError;
	}

	public void setValidationError(double validationError) {
		this.validationError = validationError;
	}

	public String getFinalModel() {
		return finalModel;
	}

	public void setFinalModel(String finalModel) {
		this.finalModel = finalModel;
	}
	
	public double getTotalRatio() {
		return totalRatio;
	}

	public void setTotalRatio(double totalRatio) {
		this.totalRatio = totalRatio;
	}
	
	public String getHelper() {
		return helper;
	}

	public void setHelper(String helper) {
		this.helper = helper;
	}
	
	
	public double getRmse() {
		return rmse;
	}

	public void setRmse(double rmse) {
		this.rmse = rmse;
	}

	@Override
	public String toString() {
		return "Statistics [predResult=" + predResult + ", algorithm=" + algorithm + ", result=" + result
				+ ", trainingError=" + trainingError + ", validationError=" + validationError + ", finalModel="
				+ finalModel + ", totalRatio=" + totalRatio + ", helper=" + helper + "]";
	}

	
}
