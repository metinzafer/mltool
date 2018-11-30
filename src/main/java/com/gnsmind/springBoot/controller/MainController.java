/**
 * 
 */
package com.gnsmind.springBoot.controller;


import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.gnsmind.springBoot.tools.EncogProcessor;
import com.opencsv.CSVReader;

import java.nio.file.Files;
import java.nio.file.Paths;

import com.opencsv.CSVWriter;

import java.io.Writer;
/**
 * @author DEVELOPMENT
 * 
 */
@EnableAutoConfiguration
@Controller
public class MainController {

	final static Logger logger = Logger.getLogger(MainController.class);
	Map<String, String> propMap;
	
	@GetMapping(value = "/")
	public ModelAndView index(HttpServletRequest request, ModelAndView modelAndView) {
		
		
		try {
			
			HttpSession session = request.getSession(false);
			if(session==null){
			
				session = request.getSession();
			}
		} catch (Exception e) {
			
			modelAndView.addObject("errorMessage", e.getMessage());
			modelAndView.setViewName("error");
		}
		
		modelAndView.setViewName("index");
		return modelAndView;
	}
	
	@PostMapping(value = "/processMLTool")
	public ModelAndView processMLTool(HttpServletRequest request, ModelAndView modelAndView) {
		
		HttpSession session = request.getSession(false);
		modelAndView.setViewName("index");
		if(session==null){
		
			session = request.getSession();
		}
		String[] predictors = null, response = null, outputs = null, algrthm = null, repeated = null, repeatedValues = null;
		String fileName = "";
		int foldNo = 0, predictionType = 0;
		predictors = request.getParameterValues("predictors"); 
		response = request.getParameterValues("response");
		algrthm = request.getParameterValues("checkbox");
		foldNo = Integer.valueOf(request.getParameter("foldNo"));
		predictionType = Integer.valueOf(request.getParameter("predictionType"));
		
		if(request.getParameter("outputs")!=null && !request.getParameter("outputs").isEmpty()){
		
			outputs = request.getParameter("outputs").split(",");
		}
		if(request.getParameter("repeatedValues")!=null && !request.getParameter("repeatedValues").isEmpty()){
			
			
			repeatedValues = request.getParameter("repeatedValues").trim().split("#");
		}
		if(request.getParameter("repeated")!=null && !request.getParameter("repeated").isEmpty()){
			
			repeated = request.getParameterValues("repeated");
			for(int i=0;i!=repeated.length;i++) {
				
				repeated[i] = repeated[i].substring(0, repeated[i].indexOf("(")).trim();
			}
		}
		if(session.getAttribute("fileName")!=null){
		
			fileName = session.getAttribute("fileName").toString();
		}
		try {
			
			if(!fileName.equals("")){
				
				EncogProcessor ep = new EncogProcessor();
				ep.getMapping(fileName, predictors, response, algrthm, session, outputs, modelAndView, foldNo, predictionType, repeated, repeatedValues);
			}else{
				
				session.setAttribute("processMsg", "No CSV file found!");
			}
		} catch (Exception e) {
			
			modelAndView.addObject("errorMessage", e.getMessage());
			modelAndView.setViewName("error");
		}
		
		return modelAndView;
	}
	
	@GetMapping(value = "/transform")
	public ModelAndView transform(HttpServletRequest request, ModelAndView modelAndView) {
		
		
		try {
			//String[] attr = request.getParameterValues("attribute");
			HttpSession session = request.getSession(false);
			
			try (
		            Reader reader = Files.newBufferedReader(Paths.get(session.getAttribute("fileName").toString()));
		            CSVReader csvReader = new CSVReader(reader);
		        ) {
		            // Reading Records One by One in a String array
		            String[] nextRecord;
		            List<String> content = new ArrayList<String>();
		            while ((nextRecord = csvReader.readNext()) != null) {
		            	
		            	String line = "";
		            	System.out.println(nextRecord.length);
		            	for(int i=0;i!=nextRecord.length;i++){
		            		
		            		if(i!=(nextRecord.length-1)){
		            			
		            			if(nextRecord[i].length()<=1 ){
		            				
		            				line = line + "0" + ","; 
		            			}else{
		            				
		            				line = line + nextRecord[i] + ","; 
		            			}
		            		}else if(i==(nextRecord.length-1)){
		            			
		            			line = line + nextRecord[i].trim(); 
		            		}
		            	}
		            	content.add(line);
		            	
		            }
		            
		            Writer writer = Files.newBufferedWriter(Paths.get(session.getAttribute("fileName").toString()));

		            CSVWriter csvWriter = new CSVWriter(writer,
		                    CSVWriter.DEFAULT_SEPARATOR,
		                    CSVWriter.NO_QUOTE_CHARACTER,
		                    CSVWriter.DEFAULT_ESCAPE_CHARACTER,
		                    CSVWriter.DEFAULT_LINE_END);
		            for(int i=0;i!=content.size();i++){
		            	
		            	String[] line = content.get(i).split(",");
		                csvWriter.writeNext(line);
				    }
		            
		            csvWriter.close();
		        }
			modelAndView.addObject("attrMsg", "attrFilled");
			modelAndView.setViewName("index");
		} catch (Exception e) {
			
			modelAndView.addObject("errorMessage", e.getMessage());
			modelAndView.setViewName("error");
		}
		
		return modelAndView;
	}
	
	
	@ExceptionHandler
	void handleIllegalArgumentException(IllegalArgumentException e,
			HttpServletResponse response) throws IOException {
		
		response.sendError(HttpStatus.BAD_REQUEST.value());

	}
	
}
