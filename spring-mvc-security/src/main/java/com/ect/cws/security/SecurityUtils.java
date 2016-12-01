package com.ect.cws.security;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.util.Base64;
import java.util.Properties;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.bind.DatatypeConverter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtils {

	private static final Logger logger = LoggerFactory.getLogger(SecurityUtils.class);

	private static final String cometWebAuthCallbackUrl = "/cometd/auth";
	private static final String cometWebAuthCallbackUrlForGuest = "/cometd/guest";

	@Autowired
	private Properties properties;

	public String buildWebAuthToken(HttpServletRequest request, boolean isForGuest) {
		
		String cometWebAuthSharedKey = properties.getProperty("shared.key");
		logger.debug("shared.key:" + cometWebAuthSharedKey +" "+request);
		
		String result = null;
		HttpSession session = request.getSession(false);
		if (session != null) {
			StringBuilder sb = new StringBuilder();
			sb.append(session.getId());
			sb.append(";");

			String host = request.getLocalAddr();
			if ("0:0:0:0:0:0:0:1".equals(host))
				host = "localhost";
			if (request.getLocalPort() != 443 && request.getLocalPort() != 80)
				host = host + ":" + request.getLocalPort();
			sb.append(request.getScheme());
			sb.append("://");
			sb.append(host);
			sb.append(request.getContextPath());

			if (isForGuest == true) {
				sb.append(cometWebAuthCallbackUrlForGuest);
				sb.append(";");
				sb.append("isForGuest");
				sb.append(";");
			} else {
				sb.append(cometWebAuthCallbackUrl);
				sb.append(";");
			}

			logger.info("notencoded:" + sb.toString());
			Cipher encryptCipher;
			try {
				encryptCipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
				encryptCipher.init(Cipher.ENCRYPT_MODE,
						new SecretKeySpec(DatatypeConverter.parseHexBinary(cometWebAuthSharedKey), "AES"));
				byte[] encrypted = encryptCipher.doFinal(sb.toString().getBytes("UTF-8"));
				result = new String(Base64.getEncoder().encode(encrypted), "UTF-8");
				logger.info("encoded:" + result);
			} catch (InvalidKeyException e) {
				logger.warn("Invalid key. Probably JDK is installed without JCE Unlimited Strength policy files.", e);
			} catch (GeneralSecurityException e) {
				logger.warn("Could not encrypt jsession token. Will result in invald credentials", e);
			} catch (UnsupportedEncodingException e) {
				// UTF-8 is always there
			}
		}

		return result;
	}
}
