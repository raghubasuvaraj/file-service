package com.eatco.fileservice.config;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.InstanceProfileCredentialsProvider;
import com.amazonaws.services.workdocs.AmazonWorkDocs;
import com.amazonaws.services.workdocs.AmazonWorkDocsClient;
import com.amazonaws.services.workdocs.AmazonWorkDocsClientBuilder;
import com.amazonaws.services.workdocs.ContentManager;
import com.amazonaws.services.workdocs.ContentManagerBuilder;

/**
 * @author Arya C Achari
 * @since 0.0.1
 *
 */
@Configurable
public class AwsWorkdocsConfig {

	@Value("${aws.workdocs.accesskey}")
	private String accesskey;

	@Value("${aws.workdocs.accesssecret}")
	private String secretkey;

	@Value("${aws.workdocs.region}")
	private String region;

	@Value("${aws.fileupload.instanceaccess}")
	private boolean instanceAccess;

	/**
	 * Client for accessing Amazon WorkDocs.
	 * 
	 * @return
	 */
	@Bean
	public AmazonWorkDocsClient amazonWorkDocsClient() {
		AWSCredentialsProvider credentialsProvider = null;
		if (instanceAccess) {
			credentialsProvider = new InstanceProfileCredentialsProvider(false);
		} else {
			credentialsProvider = new AWSStaticCredentialsProvider(new BasicAWSCredentials(accesskey, secretkey));
		}
		return (AmazonWorkDocsClient) AmazonWorkDocsClientBuilder.standard().withRegion(region)
				.withCredentials(credentialsProvider).build();
	}

	/**
	 * High level synchronous utility for transferring content from and to Amazon
	 * WorkDocs. <code>ContentManager</code> provides a simple API for uploading and
	 * downloading documents to/from Amazon WorkDocs easily.
	 * 
	 * @return
	 */
	@Bean
	public ContentManager workDocsClient() {
		AWSCredentialsProvider credentialsProvider = null;
		if (instanceAccess) {
			credentialsProvider = new InstanceProfileCredentialsProvider(false);
		} else {
			credentialsProvider = new AWSStaticCredentialsProvider(new BasicAWSCredentials(accesskey, secretkey));
		}
		AmazonWorkDocs client = AmazonWorkDocsClient.builder().withCredentials(credentialsProvider).withRegion(region)
				.build();
		return ContentManagerBuilder.standard().withWorkDocsClient(client).build();
	}
}
