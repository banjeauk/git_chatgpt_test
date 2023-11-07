package com.chatgpt_test.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chatgpt_test.dto.ContentDataForm;
import com.chatgpt_test.entity.ContentData;
import com.chatgpt_test.repository.ContentDataRepsitory;
import com.chatgpt_test.service.ContentService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Controller
public class MainController {
	
	private final ContentService contentservice;
	
	@GetMapping("/contents/list")
	public String list(Model model) {
		
		List<ContentData> contentList = this.contentservice.getList();
		model.addAttribute("contentList", contentList);
        return "content_list";
    }
	
	
	@PostMapping("/contents/insert")
	public String insert(@Valid ContentDataForm contentForm, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
            return "content_insert";
        }
		
		this.contentservice.insert(contentForm.getSubject(),contentForm.getContent());
        return "redirect:/content_list";
    }
	
	@GetMapping("/contents/detail/{id}")
	public String detail(Model model, @PathVariable("id") Integer id) {
		ContentData contentdata = this.contentservice.getContentData(id);
        model.addAttribute("contentDataForm", contentdata);
		
		return "content_insert";
	}
	
	@GetMapping("/contents/insertform")
	public String insertform(@Valid ContentDataForm contentForm) {
		return "content_insert";
	}
	
	
	@GetMapping("/")
	public String rootCall() {
		
		return "content_list";
	}
}
