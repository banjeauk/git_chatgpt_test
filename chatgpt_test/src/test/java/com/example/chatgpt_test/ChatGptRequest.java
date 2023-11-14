package com.example.chatgpt_test;

public record ChatGptRequest(String model, String prompt, int temperature, int max_tokens) {
}

