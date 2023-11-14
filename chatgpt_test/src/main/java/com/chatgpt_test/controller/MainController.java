package com.chatgpt_test.controller;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chatgpt_test.dto.ChatForm;
import com.chatgpt_test.dto.ChatResultForm;
import com.chatgpt_test.dto.ContentDataForm;
import com.chatgpt_test.entity.ContentData;
import com.chatgpt_test.repository.ContentDataRepsitory;
import com.chatgpt_test.service.ContentService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;

import io.github.flashvayne.chatgpt.dto.chat.MultiChatMessage;
import io.github.flashvayne.chatgpt.dto.image.ImageFormat;
import io.github.flashvayne.chatgpt.dto.image.ImageSize;
import io.github.flashvayne.chatgpt.service.ChatgptService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Controller
public class MainController {
	
	private final ContentService contentservice;
	
	@Autowired
	private ChatgptService chatgptService;
	
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
	
	@GetMapping("/gpt/chatform")
	public String chatform(@Valid ChatForm chatForm) {
		return "jgptchat";
	}
	
	
	@PostMapping("/gpt/reqchatgpt")
	@ResponseBody
	public String reqchatgpttxt(Model model, ChatForm chatForm) {
		String responseMessage = chatgptService.multiChat(Arrays.asList(new MultiChatMessage("user",chatForm.getMsgdata())));
		System.out.print(responseMessage);
		ChatResultForm resultform = new ChatResultForm();
		resultform.setMsgdata(responseMessage);
		resultform.setCreateDate(this.getCurrentDateTime());
		
		ObjectMapper mapper = new ObjectMapper(); 
		String jsonString = "";
		try {
			jsonString = mapper.writeValueAsString(resultform);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return jsonString;
	}
	
	
	
	@PostMapping("/gpt/reqchatgptimg")
	@ResponseBody
	public String reqchatgpttxtimg(Model model, ChatForm chatForm) {
		

		 String gptQ = chatForm.getMsgdata();
         String transEn = this.gptTransKrToEn(chatForm.getMsgdata());
         if (!transEn.equals("")) {
            gptQ = transEn;
         }
         
         List<String> listGptImage = chatgptService.imageGenerate(gptQ, 1, ImageSize.MEDIUM, ImageFormat.BASE64);
         String base64Image = listGptImage.toString().replaceAll("\\[", "").replaceAll("\\]", "");
		
		System.out.print(base64Image);
		ChatResultForm resultform = new ChatResultForm();
		resultform.setImgdata(base64Image);
		resultform.setCreateDate(this.getCurrentDateTime());
		
		ObjectMapper mapper = new ObjectMapper(); 
		String jsonString = "";
		try {
			jsonString = mapper.writeValueAsString(resultform);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return jsonString;
	}
	
	
	@PostMapping("/gpt/getcurrenttime")
	@ResponseBody
	public String getcurrenttime() {
		
		JsonObject jo = new JsonObject();
		
		jo.addProperty("ctime", this.getCurrentDateTime());
		
		
		
		
		
		return jo.toString();
	}
	
	
	
	@GetMapping("/")
	public String rootCall() {
		
		return "content_list";
	}
	
	public static String getCurrentDateTime() {
		Date today = new Date();
		Locale currentLocale = new Locale("KOREAN", "KOREA");
		String pattern = "YYYY/MM/DD hh:mm:ss"; //hhmmss로 시간,분,초만 뽑기도 가능
		SimpleDateFormat formatter = new SimpleDateFormat(pattern,
				currentLocale);
		return formatter.format(today);
	}
	
	public String gptTransKrToEn(String str) {
		   String strEn = "";

		   if (isLangEn(str)) {
		      System.out.printf("isLangEn Text:\n\t%s\n", isLangEn(str));
		   }else {
		       String gptResponse = chatgptService.sendMessage("'" + str + "' 영어로 번역해주세요.");
		       strEn = gptResponse.trim();
		   }
		   
		   return strEn;
		}

		public boolean isLangEn(String str) {
		   return Pattern.matches("^[0-9a-zA-Z]*$", str);
	}
	
}
