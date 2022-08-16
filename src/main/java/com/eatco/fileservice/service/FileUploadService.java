package com.eatco.fileservice.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.eatco.fileservice.enums.FileCategories;
import com.eatco.fileservice.enums.SubFolders;

@Service
public interface FileUploadService {

	/**
	 * Upload the file to S3 bucket
	 * 
	 * Checking the micro-service category then creating a sub-folder by the name of
	 * service. Return the url of the file, with that url uploaded file can download.
	 * 
	 * @param category
	 * @param file
	 * @return
	 */
	String upload(FileCategories category, SubFolders subFolders, MultipartFile file);

	/**
	 * @param category
	 * @param files
	 * @return
	 */
	List<String> mulitpleUpload(FileCategories category, SubFolders subFolders, MultipartFile[] files);

	String delete(FileCategories category, SubFolders subFolders, String filename);

}
