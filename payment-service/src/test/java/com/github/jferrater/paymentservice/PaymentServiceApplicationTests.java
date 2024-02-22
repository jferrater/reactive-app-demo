package com.github.jferrater.paymentservice;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PaymentServiceApplicationTests {

	// fixing bean creation in test with name 'embeddedMongoServer'
	static {
		System.setProperty("spring.mongodb.embedded.version","5.0.0");
	}

	@Test
	void contextLoads() {
	}

}
