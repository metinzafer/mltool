package com.gnsmind.springBoot.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.tomcat.jdbc.pool.ClassLoaderUtil;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.gnsmind.springBoot.constant.Constants;
import com.gnsmind.springBoot.fileStore.FileStore;
import com.gnsmind.springBoot.model.Ads;

@RestController
@RequestMapping(value = "/file")
public class FileUploadController {

	@PostMapping(value = "/upload", headers = ("content-type=multipart/*"))
	public ModelAndView upload(@RequestParam("fileselect") MultipartFile[] inputFiles,
							   ModelAndView modelAndView, 
							   HttpServletRequest request,
							   @RequestParam("memberAdPhoto") Optional<String> memberAdPhoto
							  ) {
		
		FileStore fileInfo = new FileStore();
		String path = "", originalFilename = "";
		boolean checkUpload = false;
		String line = "";
        String cvsSplitBy = ",", cp = "";
        int indexCounter = 0;
        File destinationFile = null;
        
		modelAndView.setViewName("index");
		if (inputFiles != null) {

			try {
				HttpSession session = request.getSession(false);
				if(session==null){
					
					session = request.getSession();
				}
				session.setAttribute("indexCounter", indexCounter);
				//member ad photos
				if(memberAdPhoto.isPresent() && memberAdPhoto.get().equals("true")){
					
					if (inputFiles.length < 2) {
						
						for (MultipartFile uploadedFile : inputFiles) {
							
							originalFilename = uploadedFile.getOriginalFilename();
							path = Constants.UPLOADED_FOLDER + originalFilename;
							destinationFile = new File(path);
							if (!destinationFile.exists()) {
								destinationFile.createNewFile();
							}
							fileInfo.setFileName(originalFilename);
							fileInfo.setFileSize(uploadedFile.getSize());
							
							destinationFile = new File(path);
							cp = destinationFile.getCanonicalPath();
							cp = cp.replaceAll("\\\\", "/");
							destinationFile = new File(cp);
							uploadedFile.transferTo(destinationFile);
							session.setAttribute("fileName", destinationFile);
							
							checkUpload = true;
						}
						if (checkUpload) {
							
							if(destinationFile.exists()) {
								
								
								BufferedReader br = new BufferedReader(new FileReader(cp));
								
								int counter = 0;
								List<String> headerList = new ArrayList<String>(); 
								List<String> headerListForMainTab = new ArrayList<String>(); 
								List<String> elementList = new ArrayList<String>();
								while ((line = br.readLine()) != null) {
	
					                // use comma as separator
					                String[] data = line.split(cvsSplitBy);
	
					                if(counter<1){
						                
					                	for(int i=0;i!=data.length;i++){
						                
					                		headerList.add(data[i] + " (" + i + ")");
					                		headerListForMainTab.add(data[i]);
					                	}
						                session.setAttribute("csvHeaders", headerList);
						                session.setAttribute("csvHeadersForMainTab", headerListForMainTab);
					                }else if(counter<2){
					                	
					                	for(int i=0;i!=data.length;i++){
							                
					                		elementList.add(data[i]);
					                	}
						                session.setAttribute("csvElements", elementList);
					                }
					                counter++;	
					            }
								modelAndView.addObject("fileUploadMsg", "Filled");
								br.close();
							}else {
								
								throw new Exception("resource not found: " + destinationFile.getCanonicalPath());
							}
						}
					}else{
						
						modelAndView.addObject("fileUploadTotalNumMsg", "Filled");
						
					}
				//member photo	
				}
			} catch (Exception e) {
				
				modelAndView.addObject("errorMessage", e.getMessage());
				modelAndView.setViewName("error");
			}
		} else {
		
			modelAndView.addObject("errorMessageFileIsEmpty", "filled");
		}
		return modelAndView;
	}

}