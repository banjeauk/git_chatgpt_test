package com.example.chatgpt_test;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import com.chatgpt_test.ChatgptTestApplication;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@ContextConfiguration(classes = ChatgptTestApplication.class)
public class GptTest {
	
	private static String apikey = "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";

	
	@JsonIgnoreProperties(ignoreUnknown = true)
    public static void main(String[] args) throws IOException, InterruptedException {
        String prompt;
        if (args.length > 0) {
            prompt = args[0];
        } else {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter a string to search for: ");
            prompt = scanner.nextLine();
        }

        ObjectMapper mapper = new ObjectMapper();
        ChatGptRequest chatGptRequest = new ChatGptRequest("text-davinci-001", prompt, 1, 100);
        String input = mapper.writeValueAsString(chatGptRequest);

        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("https://api.openai.com/v1/completions"))
            .header("Content-Type", "application/json")
            .header("Authorization", "Bearer "+apikey+"")
            .POST(HttpRequest.BodyPublishers.ofString(input))
            .build();

        HttpClient client = HttpClient.newHttpClient();
        var response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            ChatGptResponse chatGptResponse = mapper.readValue(response.body(), ChatGptResponse.class);
            String answer = chatGptResponse.choices()[chatGptResponse.choices().length-1].text();
            if (!answer.isEmpty()) {
                System.out.println(answer.replace("\n", "").trim());
            }
        } else {
            System.out.println(response.statusCode());
            System.out.println(response.body());
        }
    }
}
