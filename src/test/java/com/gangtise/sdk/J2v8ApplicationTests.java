package com.gangtise.sdk;

import com.gangtise.sdk.callback.CallbackHashMap;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class J2v8ApplicationTests {

	@Test
	void contextLoads() {
		CallbackHashMap callbackHashMap = new CallbackHashMap();
		Object ooo = callbackHashMap.get(2);
		System.out.println(ooo);
	}

}
