package com.eatco.fileservice.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.eatco.fileservice.response.dto.EnumsResponseDto;

@Service
public interface MiscService {

	/**
	 * Converting the Enumerator {@link com.lms.fileupload.enums.FileCategories
	 * FileCategories} into ArrayList for the UI
	 * 
	 * @return
	 */
	List<EnumsResponseDto> listOfFileCategories();

	/**
	 * Converting the Enumerator {@link com.lms.fileupload.enums.SubFolders
	 * SubFolders} into ArrayList for the UI
	 * 
	 * @return
	 */
	List<EnumsResponseDto> listOfSubFolders();

}
