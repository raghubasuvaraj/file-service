package com.eatco.fileservice.enums;

/**
 * Give the file name according to the each micro-services, which need to
 * utilise the S3
 * 
 * @author Arya C Achari
 * @since 0.0.1
 *
 */
public enum FileCategories {

	CERTIFICATION_SERVICE("CERTIFICATION_SERVICE", "Certification-Service"), 
	CHARACTER_SERVICE("CHARACTER_SERVICE", "Character-Service"),
	CONTENT_SERVICE("CONTENT_SERVICE", "Content-Service"), 
	DASHBOARD_SERVICE("DASHBOARD_SERVICE", "Dashboard-Service"), 
	DO_LIST_SERVICE("DO_LIST_SERVICE", "Do-list-Service"),
	EXAM_SERVICE("EXAM_SERVICE", "Exam-Service"), 
	LESSON_PLAN_SERVICE("LESSON_PLAN_SERVICE", "Lesson-plan-Service"),
	NOTIFICATIONS_SERVICE("NOTIFICATIONS_SERVICE", "Notifications-Service"), 
	REPORT_SERVICE("REPORT_SERVICE", "Report-Service"),
	STUDY_MATERIALS_SERVICE("STUDY_MATERIALS_SERVICE", "Study-materials-Service"), 
	USER_SERVICE("USER_SERVICE", "User-Service");
	
	private String code;
	
	private String name;

	FileCategories(String code, String name) {
		this.code = code;
		this.name = name;
	}
	
	public String getCode() {
		return code;
	}

	public String getName() {
		return name;
	}
}
