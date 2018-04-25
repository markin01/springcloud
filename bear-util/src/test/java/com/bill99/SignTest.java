package com.bill99;
import java.nio.charset.Charset;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.bill99.sign.DigestUtil;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SignTest.class)
public class SignTest {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private static final String publicKeyBase64 = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCFBo/mcJPAAWXm1XqNeDIosW9mGw9315r0b0BY00x1i534iG0qwidm77IUm1ZyE3KV0nizlWBa7CpDeS6AKGhXffPNu4JHOaOu2YTuMhHNH7uLpU3Tr6DmTxdrQrfMPLBMCVKKmDs5rGvmI5lR8Tf1EbdlvMHH/sBmJ5OUZNNB+QIDAQAB";
	private static final String privateKeyBase64 = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAIUGj+Zwk8ABZebVeo14Miixb2YbD3fXmvRvQFjTTHWLnfiIbSrCJ2bvshSbVnITcpXSeLOVYFrsKkN5LoAoaFd98827gkc5o67ZhO4yEc0fu4ulTdOvoOZPF2tCt8w8sEwJUoqYOzmsa+YjmVHxN/URt2W8wcf+wGYnk5Rk00H5AgMBAAECgYB5FqgWk5Nqy9r+bITKEgdWk21b/HXwGQqsRjjEolKPDTAW89ire2Byiu4HY/8kiB9d0eCdV/QFz8QI914kuqN+8Geatw3PACQobPQv6jjNhe4TAMjASE2PNdvFpqFAQsfImG+q0PqRFzS1Jfw7YaidICsXyMtCQDilgh2eW3wagQJBAML0dPJzyDSGQkPDpQ3RGaprDPs52wzpuBj6mNCqmaDnkGHdYS2LUFIJx05RRdVoPsKmoIttyOh6bD4RsFHiH20CQQCurd4yuPzzqWLDBEb8UtzdqaVtknkpx5eosaDUAA3S2Xi+Y5ro08/fWnr/gGlbwFZjFDzioV0Ra5ygD89MrLk9AkARSseYvcZTSllUU2UvfY2OPSKQoggY9B4dMRUe5RLtX7zBwXYeKQ49yJpCCxlrnWx7rXXuCBXa8Qjq1HzUNn9dAkA4xEQ7k6aQwPFZkyBGi98/IgyiyUEa9MQyjFdJziKv7QCyTshe9cbOPSdPtTmIGb95P3Bx7iLCsIgb9EdYEaG5AkEAom/D7XaxxnhsqH94nreJKCmXW/Fjp4IShvZoeJK7WxCvNJJlD+N4Bsuupp6azJ73QL0wM+y7l8ER5NRVA+zciQ==";
	
	private static final String charset = "UTF-8";
	private static final String text = "id=100,name=1000";
	
	
	@Test
	public void sign() throws Exception {
		String rsaSign = DigestUtil.RSA.sign(text, privateKeyBase64, Charset.forName(charset));
		logger.info("@@rsaSign=={}", rsaSign);
		
		boolean isVerify = DigestUtil.RSA.verify(text, rsaSign, publicKeyBase64, Charset.forName(charset));
		logger.info("@@isVerify=={}", isVerify);
	}
	
	

}
