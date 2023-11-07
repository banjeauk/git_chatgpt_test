package com.chatgpt_test.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chatgpt_test.entity.ContentData;

public interface ContentDataRepsitory extends JpaRepository<ContentData, Integer> {
		
	ContentData findBySubjectLike(String subject);
}
