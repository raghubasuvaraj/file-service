package com.eatco.fileservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.eatco.fileservice.component.Translator;
import com.eatco.fileservice.enums.FileCategories;
import com.eatco.fileservice.enums.SubFolders;
import com.eatco.fileservice.response.formate.EatcoResponse;
import com.eatco.fileservice.response.formate.ResponseHelper;
import com.eatco.fileservice.service.FileUploadService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/v1/api/file")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class FileUploadController {

	@Autowired
	private FileUploadService fileUploadService;

	@Lazy
	@SuppressWarnings("unchecked")
	@PostMapping("/upload")
	@ApiOperation(notes = "To use while upload a single file.", value = "screen where all the upload functionality enabled")
	public EatcoResponse<String> upload(@RequestParam(name = "fileCategory") FileCategories category,
			@RequestParam(name = "subFolders") SubFolders subFolders, @RequestParam(name = "file") MultipartFile file) {
		String response = fileUploadService.upload(category, subFolders, file);
		return ResponseHelper.createResponse(new EatcoResponse<String>(), response,
				Translator.toLocale("file.upload.success", null), Translator.toLocale("file.upload.fail", null));
	}

	@Lazy
	@SuppressWarnings("unchecked")
	@PostMapping("/multiple-upload")
	@ApiOperation(notes = "To use while upload a multiple file.", value = "screen where all the multiple upload functionality enabled", hidden = true)
	public EatcoResponse<List<String>> mulitpleUpload(@RequestParam(name = "fileCategory") FileCategories category,
			@RequestParam(name = "subFolders") SubFolders subFolders,
			@RequestParam(name = "files") MultipartFile[] files) {
		List<String> response = fileUploadService.mulitpleUpload(category, subFolders, files);
		return ResponseHelper.createResponse(new EatcoResponse<List<String>>(), response,
				Translator.toLocale("files.upload.success", null), Translator.toLocale("files.upload.fail", null));
	}

	@Lazy
	@SuppressWarnings("unchecked")
	@DeleteMapping("/delete")
	@ApiOperation(notes = "To use while delete a multiple file.", value = "screen where all the upload functionality enabled", hidden = true)
	public EatcoResponse<String> delete(@RequestParam(name = "fileCategory") FileCategories category,
			@RequestParam(name = "subFolders") SubFolders subFolders,
			@RequestParam(name = "fileName") String fileName) {
		String response = fileUploadService.delete(category, subFolders, fileName);
		return ResponseHelper.createResponse(new EatcoResponse<String>(), response,
				Translator.toLocale("file.delete.success", null), Translator.toLocale("file.delete.fail", null));
	}
}
