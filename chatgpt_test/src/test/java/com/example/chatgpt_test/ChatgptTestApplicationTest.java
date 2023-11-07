package com.example.chatgpt_test;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import com.chatgpt_test.ChatgptTestApplication;
import com.chatgpt_test.entity.ContentData;
import com.chatgpt_test.repository.ContentDataRepsitory;


@SpringBootTest
@ContextConfiguration(classes = ChatgptTestApplication.class)
class ChatgptTestApplicationTest {
	 @Autowired
	 private ContentDataRepsitory contentRepository;
	 
	 @Test
	    void testJpa() {        
		 ContentData cd = new ContentData();
		 cd.setSubject("제목");
		 cd.setContent("내용");
		 cd.setCreateDate(LocalDateTime.now());
         this.contentRepository.save(cd);  // 첫번째 질문 저장

	     
	   }
	 
}
