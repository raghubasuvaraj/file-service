package com.eatco.fileservice.utils;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

import com.eatco.fileservice.component.Translator;
import com.eatco.fileservice.enums.ErrorCode;
import com.eatco.fileservice.exceptions.FUException;

import lombok.extern.slf4j.Slf4j;

/**
 * Implement the sub-folder concept
 * 
 * @author Arya C Achari
 * @since 1.0.0
 *
 */
@Slf4j
public class MainAndSubFolders {

	/**
	 * Add the needed folders in the Enums and below ArrayList
	 * <p>
	 * main folder enumerator {@link com.lms.fileupload.enums.FileCategories
	 * FileCategories}
	 * <p>
	 * sub folder enumerator {@link com.lms.fileupload.enums.SubFolders SubFolders}
	 * 
	 * @param mainFolder
	 * @param subFolder
	 * @return
	 */
	public static String createMainOrSubFolder(String mainFolder, String subFolder) {
		if (StringUtils.isEmpty(mainFolder))
			throw new FUException(ErrorCode.BAD_REQUEST, Translator.toLocale("main.folder.name.mandatory", null));
		try {
			String folderPath = null;

			// main folders: related with the service
			List<String> mainFolders = Arrays.asList("Misc-folder", "Certification-Service", "Character-Service",
					"Content-Service", "Dashboard-Service", "Do-list-Service", "Exam-Service", "Lesson-plan-Service",
					"Notifications-Service", "Report-Service", "Study-materials-Service", "User-Service");

			int mianFolderIndex = mainFolders.indexOf(mainFolder);
			String mainFolderPath = (mianFolderIndex <= 0) ? mainFolders.get(0) : mainFolders.get(mianFolderIndex);

			// main folders: related with the service's each controller
			List<String> subFolders = Arrays.asList("Misc-sub-folder", "Blue-Print", "Interactive-Content",
					"Common-Content", "Chapters", "Document-Category", "School", "Branch", "Staff", "Student");

			if (!StringUtils.isEmpty(subFolder)) {
				int subFolderIndex = subFolders.indexOf(subFolder);
				String subFolderPath = (subFolderIndex <= 0) ? subFolders.get(0) : subFolders.get(subFolderIndex);
				folderPath = mainFolderPath + "/" + subFolderPath;
			} else {
				folderPath = mainFolderPath;
			}
			return folderPath;
		} catch (Exception e) {
			log.error(ExceptionUtils.getStackTrace(e));
			throw new FUException(ErrorCode.INTERNAL_SERVER_ERROR, Translator.toLocale("folder.create.failed", null));
		}
	}
}
