package com.sinosoft.framework.security;

import org.bouncycastle.crypto.engines.DESedeEngine;
import org.bouncycastle.jce.provider.JCEBlockCipher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.SecureRandom;

public class Z3DESCipher extends JCEBlockCipher {
	private static final Logger logger = LoggerFactory.getLogger(Z3DESCipher.class);

	public Z3DESCipher() {
		super(new DESedeEngine());
	}

	public void init(int mode, Key key) {
		try {
			engineInit(mode, key, new SecureRandom());
		} catch (InvalidKeyException e) {
			logger.error(e.getMessage(), e);
		}
	}

	public byte[] doFinal(byte[] str) throws Exception {
		return engineDoFinal(str, 0, str.length);
	}
}
