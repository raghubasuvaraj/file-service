package com.eatco.fileservice.enums;

/**
 * Add the sub folder's name here and update the array list in the class
 * {@link com.lms.fileupload.utils.MainAndSubFolders MainAndSubFolders}'s method
 * {@link com.lms.fileupload.utils.MainAndSubFolders
 * createMainOrSubFolder(String mainFolder, String subFolder)}
 * List name subFolders 
 * 
 * @author Arya C Achari
 * @since 1.0.0
 *
 */
public enum SubFolders {
	
	//sub-folders under the folder Content-Service
	BLUE_PRINT("BLUE_PRINT", "Blue-Print"),
	DOCUMENT_CATERGORY("DOCUMENT_CATERGORY", "Document-Category"),
	INTERACTIVE_CONTENT("INTERACTIVE_CONTENT", "Interactive-Content"), 
	COMMON_CONTENT("COMMON_CONTENT", "Common-Content"),
	CHAPTERS("CHAPTERS", "Chapters"),
	
	
	//sub-folders under the folder User-Service
	SCHOOL("SCHOOL", "School"),
	BRANCH("BRANCH", "Branch"),
	STAFF("STAFF", "Staff"),
	STUDENT("STUDENT", "Student");
	
	private String code;
	
	private String name;
	
	SubFolders(String code, String name) {
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
