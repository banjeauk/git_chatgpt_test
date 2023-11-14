package com.chatgpt_test.dto;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatForm {
	 	
	 	
	 	
	 	
	    private String msgdata;
	    
	 	
	    private LocalDateTime createDate;
}
