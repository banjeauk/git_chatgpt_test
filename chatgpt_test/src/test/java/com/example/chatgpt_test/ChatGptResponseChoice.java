package com.example.chatgpt_test;

public record ChatGptResponseChoice(
	    String text,
	    int index,
	    Object logprobs,
	    String finish_reason
	) {
}	