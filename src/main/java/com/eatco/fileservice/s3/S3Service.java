package com.eatco.fileservice.s3;

import java.io.File;

import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.model.S3Object;

public interface S3Service {

	/**
	 * Upload the files
	 * @param mediaFileId
	 * @param multipart
	 * @param s3ObjectKey
	 * @param subfolder
	 * @return
	 */
	String uploadFile(String mediaFileId, MultipartFile multipart, String s3ObjectKey, String subfolder);

	/**
	 * Generate media file unique id
	 * @param mediaFileId
	 * @param multipart
	 * @return
	 */
	String generateKey(String mediaFileId, MultipartFile multipart);

	/**
	 * Making a temp directory then download the files from S3
	 * @param objectKey
	 * @return
	 */
	File downloadFile(String objectKey);

	/**
	 * @param fileName
	 * @param subFolder
	 * @return
	 */
	String deleteFile(String fileName, String subFolder);

	/**
	 * Represents an object stored in Amazon S3. This object contains
	 * the data content
	 */
	S3Object getDocumentasS3Object(String folderName, String documentName);


}
