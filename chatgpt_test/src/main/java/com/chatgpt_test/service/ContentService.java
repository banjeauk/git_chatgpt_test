package com.chatgpt_test.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.chatgpt_test.repository.ContentDataRepsitory;
import com.chatgpt_test.entity.ContentData;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ContentService {
	private final ContentDataRepsitory contantRepository;
	
	public void insert(String subject, String content) {
        ContentData q = new ContentData();
        q.setSubject(subject);
        q.setContent(content);
        q.setCreateDate(LocalDateTime.now());
        this.contantRepository.save(q);
    }
	
	public ContentData getContentData(Integer id) {  
        Optional<ContentData> q = this.contantRepository.findById(id);
        if (q.isPresent()) {
        return q.get();
        }else {
        return new ContentData();
        }
        	
        
    }
	
	
    public List<ContentData> getList() {
        return this.contantRepository.findAll();
    }
}
