package com.eatco.fileservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eatco.fileservice.component.Translator;
import com.eatco.fileservice.response.dto.EnumsResponseDto;
import com.eatco.fileservice.response.formate.EatcoResponse;
import com.eatco.fileservice.response.formate.ResponseHelper;
import com.eatco.fileservice.service.MiscService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/v1/api/file/misc")
public class MiscController {

	@Autowired
	private MiscService miscService;

	@Lazy
	@SuppressWarnings("unchecked")
	@GetMapping("/file-categories")
	@ApiOperation(notes = "To use while upload/delete a file.", value = "Whereever upload/delete of the file needed")
	public EatcoResponse<List<EnumsResponseDto>> listOfFileCategories() {
		List<EnumsResponseDto> response = miscService.listOfFileCategories();
		return ResponseHelper.createResponse(new EatcoResponse<List<EnumsResponseDto>>(), response,
				Translator.toLocale("files.category.success", null), Translator.toLocale("files.category.fail", null));
	}

	@Lazy
	@SuppressWarnings("unchecked")
	@GetMapping("/sub-folders")
	@ApiOperation(notes = "To use while upload/delete a file.", value = "Whereever upload/delete of the file needed")
	public EatcoResponse<List<EnumsResponseDto>> listOfSubFolders() {
		List<EnumsResponseDto> response = miscService.listOfSubFolders();
		return ResponseHelper.createResponse(new EatcoResponse<List<EnumsResponseDto>>(), response,
				Translator.toLocale("sub.folder.enum.success", null),
				Translator.toLocale("sub.folder.enum.fail", null));
	}
}
