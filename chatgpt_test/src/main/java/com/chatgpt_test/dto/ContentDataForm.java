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
@Entity
public class ContentDataForm {
	 	@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Integer id;
	 	
	 	
	 	@NotEmpty(message="제목은 필수항목입니다.")
	    @Column(length = 200)
	    private String subject;
	 	
	 	@NotEmpty(message="내용은 필수항목입니다.")
	    @Column(columnDefinition = "TEXT")
	    private String content;

	    private LocalDateTime createDate;
}
