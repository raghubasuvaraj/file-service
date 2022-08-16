package com.eatco.fileservice.service.impl;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Service;

import com.eatco.fileservice.component.Translator;
import com.eatco.fileservice.enums.ErrorCode;
import com.eatco.fileservice.enums.FileCategories;
import com.eatco.fileservice.enums.SubFolders;
import com.eatco.fileservice.exceptions.FUException;
import com.eatco.fileservice.response.dto.EnumsResponseDto;
import com.eatco.fileservice.service.MiscService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("miscService")
public class MiscServiceImpl implements MiscService {

	/**
	 * Converting the Enumerator {@link com.lms.fileupload.enums.FileCategories
	 * FileCategories} into ArrayList for the UI
	 * 
	 * @return
	 */
	@Override
	public List<EnumsResponseDto> listOfFileCategories() {
		try {
			List<EnumsResponseDto> response = new ArrayList<>();
			// converting enumerator into list
			List<FileCategories> eumList = new ArrayList<>(EnumSet.allOf(FileCategories.class));
			eumList.forEach(item -> response.add(new EnumsResponseDto(item.getCode(), item.getName())));
			return response;
		} catch (Exception e) {
			log.error(ExceptionUtils.getStackTrace(e));
			throw new FUException(ErrorCode.NOT_FOUND, Translator.toLocale("files.category.fail", null));
		}
	}
	
	/**
	 * Converting the Enumerator {@link com.lms.fileupload.enums.SubFolders
	 * SubFolders} into ArrayList for the UI
	 * 
	 * @return
	 */
	@Override
	public List<EnumsResponseDto> listOfSubFolders() {
		try {
			List<EnumsResponseDto> response = new ArrayList<>();
			// converting enumerator into list
			List<SubFolders> eumList = new ArrayList<>(EnumSet.allOf(SubFolders.class));
			eumList.forEach(item -> response.add(new EnumsResponseDto(item.getCode(), item.getName())));
			return response;
		} catch (Exception e) {
			log.error(ExceptionUtils.getStackTrace(e));
			throw new FUException(ErrorCode.NOT_FOUND, Translator.toLocale("sub.folder.enum.fail", null));
		}
	}
}
