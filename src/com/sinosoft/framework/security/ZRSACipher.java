package com.sinosoft.framework.security;

import org.bouncycastle.crypto.encodings.PKCS1Encoding;
import org.bouncycastle.crypto.engines.RSABlindedEngine;
import org.bouncycastle.jce.provider.JCERSACipher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.SecureRandom;


public class ZRSACipher extends JCERSACipher {
	private static final Logger logger = LoggerFactory.getLogger(ZRSACipher.class);

	public ZRSACipher() {
		super(new PKCS1Encoding(new RSABlindedEngine()));
	}

	public void init(int mode, Key key) {
		try {
			engineInit(mode, key, new SecureRandom());
		} catch (InvalidKeyException e) {
			logger.error(e.getMessage(), e);
		}
	}

	public int doFinal(byte[] b1, int i1, int i2, byte[] b2, int i3) throws Exception {
		return engineDoFinal(b1, i1, i2, b2, i3);
	}
}
