package com.eatco.fileservice.s3;

import java.io.File;
import java.io.IOException;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.InstanceProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.transfer.Download;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;
import com.amazonaws.services.s3.transfer.Upload;
import com.eatco.fileservice.component.Translator;
import com.eatco.fileservice.enums.ErrorCode;
import com.eatco.fileservice.exceptions.FUException;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Arya C Achari
 * @since 0.0.1
 *
 */
@Slf4j
@Component
@Configurable
public class S3ServiceImpl implements S3Service {

	@Value("${aws.fileupload.accesskey}")
	private String accessKey;

	@Value("${aws.fileupload.accesssecret}")
	private String accessSecret;

	@Value("${aws.fileupload.bucketname}")
	private String bucketName;

	@Value("${aws.fileupload.baseurl}")
	private String baseUrl;

	@Value("${aws.fileupload.region}")
	private String region;

	@Value("${aws.fileupload.instanceaccess}")
	private boolean instanceAccess;

	private TransferManager transferManager;

	private AmazonS3 s3Client;
	
	@PostConstruct
	public void initilaze() {
		AWSCredentialsProvider credentialsProvider = null;

		if (instanceAccess) {
			credentialsProvider = new InstanceProfileCredentialsProvider(false);
		} else {
			credentialsProvider = new AWSStaticCredentialsProvider(
					new BasicAWSCredentials(this.accessKey, this.accessSecret));
		}
		this.s3Client = AmazonS3ClientBuilder.standard().withRegion(region).withCredentials(credentialsProvider)
				.build();
		this.transferManager = TransferManagerBuilder.standard().withS3Client(this.s3Client).build();
	}
	
	/**
	 * Upload the files
	 * @param mediaFileId
	 * @param multipart
	 * @param s3ObjectKey
	 * @param subfolder
	 * @return
	 */
	@Override
	public String uploadFile(String mediaFileId, MultipartFile multipart, String s3ObjectKey, String subfolder) {
		try {
			ObjectMetadata metadata = new ObjectMetadata();
			metadata.setContentType(multipart.getContentType());
			metadata.setContentLength(multipart.getSize());

			String folderPath;
			if (subfolder != null && !subfolder.isEmpty()) {
				folderPath = bucketName + "/" + subfolder;
			} else {
				folderPath = bucketName + "/";
			}
			Upload upload = this.transferManager
					.upload(new PutObjectRequest(folderPath, s3ObjectKey, multipart.getInputStream(), metadata)
							.withCannedAcl(CannedAccessControlList.PublicRead));

			upload.waitForCompletion();

			return baseUrl + folderPath + "/" + s3ObjectKey;
		} catch (AmazonServiceException | InterruptedException e) {
			log.error(Translator.toLocale("media.file.id", null), mediaFileId);
			log.error(ExceptionUtils.getStackTrace(e));
			Thread.currentThread().interrupt();
			throw new FUException(ErrorCode.INTERNAL_SERVER_ERROR, Translator.toLocale("file.upload.fail", null));
		} catch (AmazonClientException e) {
			log.error(Translator.toLocale("media.file.id", null), mediaFileId);
			log.error(ExceptionUtils.getStackTrace(e));
			throw new FUException(ErrorCode.INTERNAL_SERVER_ERROR, Translator.toLocale("file.upload.fail", null));
		} catch (IOException e) {
			log.error(Translator.toLocale("media.file.id", null), mediaFileId);
			log.error(ExceptionUtils.getStackTrace(e));
			throw new FUException(ErrorCode.INTERNAL_SERVER_ERROR, Translator.toLocale("file.upload.fail", null));
		} catch (Exception e) {
			log.error(ExceptionUtils.getStackTrace(e));
			throw new FUException(ErrorCode.INTERNAL_SERVER_ERROR, Translator.toLocale("file.download.fail", null));
		}
	}	
	
	/**
	 * Generate media file unique id
	 * @param mediaFileId
	 * @param multipart
	 * @return
	 */
	@Override
	public String generateKey(String mediaFileId, MultipartFile multipart) {
		return mediaFileId + "_" + multipart.getOriginalFilename();
	}
	
	/**
	 * Making a temp directory then download the files from S3
	 * @param objectKey
	 * @return
	 */
	@Override
	public File downloadFile(String objectKey) {
		try {
			String fileName = objectKey.substring(objectKey.lastIndexOf("/"));

			File tempDir = new File("temp");
			if (!tempDir.exists()) {
				tempDir.mkdir();
			}

			File file = new File(tempDir, fileName);

			Download download = this.transferManager.download(bucketName, objectKey, file);
			
			download.waitForCompletion();
			
			return file;
		} catch (AmazonClientException | InterruptedException e) {
			log.error(ExceptionUtils.getStackTrace(e));
			Thread.currentThread().interrupt();
			throw new FUException(ErrorCode.INTERNAL_SERVER_ERROR, Translator.toLocale("file.download.fail", null));
		} catch (Exception e) {
			log.error(ExceptionUtils.getStackTrace(e));
			throw new FUException(ErrorCode.INTERNAL_SERVER_ERROR, Translator.toLocale("file.download.fail", null));
		}	
	}
	
	
	/**
	 * @param fileName
	 * @param subFolder
	 * @return
	 */
	@Override
	public String deleteFile(String fileName, String subFolder) {
		try {
			String folderPath;
			if (subFolder != null && !subFolder.isEmpty()) {
				folderPath = bucketName + "/" + subFolder;
			} else {
				folderPath = bucketName + "/";
			}

			s3Client.deleteObject(folderPath, fileName);
			return "Deleted File: " + fileName;
		}catch (Exception e) {
			log.error(ExceptionUtils.getStackTrace(e));
			throw new FUException(ErrorCode.INTERNAL_SERVER_ERROR, Translator.toLocale("file.delete.fail", null));
		}
	}
	
	/**
	 * Represents an object stored in Amazon S3. This object contains
	 * the data content
	 */
	@Override
	public S3Object getDocumentasS3Object(String folderName, String documentName) {
		return s3Client.getObject(new GetObjectRequest(folderName, documentName));
	}
	
	//To-Do multiple file upload
}
