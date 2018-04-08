package com.data.encryption;

import com.data.encryption.util.DesUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
//@SpringBootTest
public class DataEncryptionApplicationTests {

	@Test
	public void contextLoads() throws Exception {
		System.out.println(DesUtil.decrypt("STiwDBReow7MG75V0iAlIV3y6P/TEgcIryrwwSor6YQ=","wow!@#$%"));
	}

}
