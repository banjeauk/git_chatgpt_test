package com.example.chatgpt_test;

public record ChatGptResponse(
	    String id,
	    String object,
	    int created,
	    String model,
	    String warning,
	    ChatGptResponseChoice[] choices,
	    ChatGptResponseUsage usage
	) {
}
	