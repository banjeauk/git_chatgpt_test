package com.chatgpt_test;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;


@RequiredArgsConstructor
@Getter
@Setter
public class TestLombok {
	private final String hello;
    private final int lombok;

    public static void main(String[] args) {
    	TestLombok testLombok = new TestLombok("헬로",5);
    	//testLombok.setHello("헬로");
    	//testLombok.setLombok(5);

        System.out.println(testLombok.getHello());
        System.out.println(testLombok.getLombok());
    }
}
