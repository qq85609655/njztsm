package com.sklay.interceptor;

import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.sklay.core.annotation.AvoidDuplicateSubmission;
import com.sklay.model.User;
import com.sklay.util.LoginUserHelper;

/**
 * <p>
 * 防止重复提交过滤器
 * </p>
 * 
 * @author: chuanli
 * @date: 2013-6-27上午11:19:05
 */
public class AvoidDuplicateSubmissionInterceptor extends
		HandlerInterceptorAdapter {
	private static final Logger LOG = LoggerFactory
			.getLogger(AvoidDuplicateSubmissionInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {

		User user = LoginUserHelper.getLoginUser();
		if (user != null) {
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			Method method = handlerMethod.getMethod();

			AvoidDuplicateSubmission annotation = method
					.getAnnotation(AvoidDuplicateSubmission.class);
			if (annotation != null) {
				boolean needSaveSession = annotation.needSaveToken();
				if (needSaveSession) {
					request.getSession(false).setAttribute("token",
							TokenProcessor.getInstance().generateToken());
				}

				boolean needRemoveSession = annotation.needRemoveToken();
				if (needRemoveSession) {
					if (isRepeatSubmit(request)) {
						LOG.warn("please don't repeat submit,[user:"
								+ user.getName() + " , id :" + user.getId()
								+ ",url:" + request.getServletPath() + "]");
						return false;
					}
					request.getSession(false).removeAttribute("token");
				}
			}
		}
		return true;
	}

	private boolean isRepeatSubmit(HttpServletRequest request) {
		String serverToken = (String) request.getSession(false).getAttribute(
				"token");
		if (serverToken == null) {
			return true;
		}
		String clinetToken = request.getParameter("token");
		if (clinetToken == null) {
			return true;
		}
		if (!serverToken.equals(clinetToken)) {
			return true;
		}
		return false;
	}

	// 令牌，用于参数一个随机唯一的令牌值
	static class TokenProcessor {
		public TokenProcessor() {
		}

		public static final TokenProcessor token = new TokenProcessor();

		public static TokenProcessor getInstance() {
			return token;
		}

		public String generateToken() {
			String token = System.currentTimeMillis() + new Random().nextInt()
					+ ""; // 随机的值
			try {
				MessageDigest md = MessageDigest.getInstance("md5"); // 注意下面的处理方式
				byte[] md5 = md.digest(token.getBytes());
				return toHex(md5); // base64编码
			} catch (NoSuchAlgorithmException e) {
				throw new RuntimeException(e);
			}
		}

		private String toHex(byte buffer[]) {
			StringBuffer sb = new StringBuffer(buffer.length * 2);
			for (int i = 0; i < buffer.length; i++) {
				sb.append(Character.forDigit((buffer[i] & 240) >> 4, 16));
				sb.append(Character.forDigit(buffer[i] & 15, 16));
			}

			return sb.toString();
		}
	}
}