package com.duzon.lulu.testcase;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.junit.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TestJdbcEnc {
	
	protected Logger logger = LogManager.getLogger(this.getClass());
		
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		
		StandardPBEStringEncryptor pbeEnc = new StandardPBEStringEncryptor();
		pbeEnc.setPassword("tmzkdlvmffotvha"); // PBE 값(XML PASSWORD설정)
		
	    String url = pbeEnc.encrypt("jdbc:postgresql://10.72.113.11:5432/clinic");
	    String username = pbeEnc.encrypt("service_app");
	    String password = pbeEnc.encrypt("DBP!app123$");
	 
	    System.out.println("암호화 url : " + url);
	    System.out.println("암호화 username : " + username);
	    System.out.println("암호화 password : " + password);

		System.out.println("복호화 url : " + pbeEnc.decrypt(url));
		System.out.println("복호화 username : " + pbeEnc.decrypt(username));
		System.out.println("복호화 password : " + pbeEnc.decrypt(password));

		//임의 작성
		/*
	    System.out.println("복호화 url : " + pbeEnc.decrypt("Gx4xhXIvtGS2tqo5ZUH1qZkwRawuvr/tQVeZt+v4O4OFR9Wgoc2ncAec9BDU8QTBBzlPGnp0buA="));
	    System.out.println("복호화 username : " + pbeEnc.decrypt("Guit1z2UdPZc6m8X33OsFIbII74/Ct+N"));
	    System.out.println("복호화 password : " + pbeEnc.decrypt("qgzz9HWOjrppiEqAWKdFB0srYsGZl3TU"));
		*/
	}

}
