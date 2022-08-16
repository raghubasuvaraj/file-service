package com.eatco.fileservice.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.eatco.fileservice.component.Translator;
import com.eatco.fileservice.enums.ErrorCode;
import com.eatco.fileservice.enums.FileCategories;
import com.eatco.fileservice.enums.SubFolders;
import com.eatco.fileservice.exceptions.FUException;
import com.eatco.fileservice.s3.S3Service;
import com.eatco.fileservice.service.FileUploadService;
import com.eatco.fileservice.utils.MainAndSubFolders;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Arya C Achari
 * @since 0.0.1
 *
 */
@Slf4j
@Service("FileUploadService")
public class FileUploadServiceImpl implements FileUploadService {

	@Autowired
	private S3Service s3Service;

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
	@Override
	public String upload(FileCategories category, SubFolders subFolders, MultipartFile file) {
		log.info("File upload started.");
		try {
			String subFolder = this.getFolderPath(category, subFolders);
			String randomId = UUID.randomUUID().toString();
			String s3ObjectKey = s3Service.generateKey(randomId, file);
			log.info("File upload completed.");
			return s3Service.uploadFile(randomId, file, s3ObjectKey, subFolder);
		} catch (Exception e) {
			log.error(ExceptionUtils.getStackTrace(e));
			throw new FUException(ErrorCode.INTERNAL_SERVER_ERROR, Translator.toLocale("file.upload.fail", null));
		}
	}

	/**
	 * @param category
	 * @param files
	 * @return
	 */
	@Override
	public List<String> mulitpleUpload(FileCategories category, SubFolders subFolders, MultipartFile[] files) {
		log.info("Multiple file upload started.");
		try {
			List<String> filesUrl = new ArrayList<>();
			Arrays.asList(files).stream().forEach(file -> {
				String subFolder = this.getFolderPath(category, subFolders);
				String randomId = UUID.randomUUID().toString();
				String s3ObjectKey = s3Service.generateKey(randomId, file);
				log.info("File uploaded.");
				filesUrl.add(s3Service.uploadFile(randomId, file, s3ObjectKey, subFolder));
			});
			log.info("Multiple file upload completed.");
			return filesUrl;
		} catch (Exception e) {
			log.error(ExceptionUtils.getStackTrace(e));
			throw new FUException(ErrorCode.INTERNAL_SERVER_ERROR, Translator.toLocale("file.upload.fail", null));
		}
	}

	@Override
	public String delete(FileCategories category, SubFolders subFolders, String filename) {
		try {
			String subFolder = this.getFolderPath(category, subFolders);
			log.info("File deleted from the S3");
			return s3Service.deleteFile(filename, subFolder);
		} catch (Exception e) {
			log.error(ExceptionUtils.getStackTrace(e));
			throw new FUException(ErrorCode.INTERNAL_SERVER_ERROR, Translator.toLocale("file.delete.fail", null));
		}
	}
	
	/**
	 * Create the folder path by implementing sub-folder concept.
	 * @param category
	 * @param subFolders
	 * @return
	 */
	private String getFolderPath(FileCategories category, SubFolders subFolders) {
		if (StringUtils.isEmpty(category.toString()))
			throw new FUException(ErrorCode.BAD_REQUEST, Translator.toLocale("main.folder.name.mandatory", null));

		try {
			String mainFolder = category.getName();
			String subFolder = null;
			if (!StringUtils.isEmpty(subFolders.toString())) {
				subFolder = subFolders.getName();
			}
			return MainAndSubFolders.createMainOrSubFolder(mainFolder, subFolder);
		} catch (Exception e) {
			log.error(ExceptionUtils.getStackTrace(e));
			throw new FUException(ErrorCode.INTERNAL_SERVER_ERROR, Translator.toLocale("folder.create.failed", null));
		}
	}
}
