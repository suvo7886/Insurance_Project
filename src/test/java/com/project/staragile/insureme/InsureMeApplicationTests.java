package com.project.staragile.insureme;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class InsureMeApplicationTests {
	
	@Test
	void testngTest() {
		Policy policy = new Policy(1, "Subhajit Saha", "Individual" , 10000, "11-Jun-1988", "11-Jul-2023");
		PolicyService pService = new PolicyService();
		//Policy outputPolicy = pService.CreatePolicy();
		assertEquals(policy.getPolicyId(), pService.generateDummyPolicy().getPolicyId());
		
	}
}
