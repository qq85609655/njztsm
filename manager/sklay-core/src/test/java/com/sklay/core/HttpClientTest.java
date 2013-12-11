package com.sklay.core;

import java.io.IOException;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.sklay.core.sdk.exceptions.JSONException;
import com.sklay.core.sdk.model.vo.Reduced;
import com.sklay.core.sdk.utils.JSONArray;
import com.sklay.core.sdk.utils.JSONML;
import com.sklay.core.sdk.utils.JSONObject;
import com.sklay.core.sdk.utils.XML;

public class HttpClientTest {

	// 调用接口DirectGetStockDetails
	public String directGetStockDetails(String url) {
		return excute(url);
	}

	// 调用接口DirectFetchSMS.
	public String directFetchSMS(String url) {
		return excute(url);
	}

	// 调用接口DirectSend,没有参数为中文的url时可调用如下方法.
	private String excute(String url) {
		HttpClient httpClient = new HttpClient();
		GetMethod getMethod = new GetMethod(url);
		try {
			getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
					new DefaultHttpMethodRetryHandler());
			int statusCode = httpClient.executeMethod(getMethod);
			if (statusCode != HttpStatus.SC_OK) {
				System.err.println("Method failed: "
						+ getMethod.getStatusLine());
			}
			byte[] responseBody = getMethod.getResponseBody();
			String str = new String(responseBody, "utf-8");
			if (str.contains("GBK")) {
				str = str.replaceAll("GBK", "utf-8");
			}
			String result = "";
			try {
				result = new Helper().readXMLString4TagName(str, "RetCode");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return "RetCode=" + result;

		} catch (HttpException e) {
			return "1";
		} catch (IOException e) {
			return "2";
		}

		finally {
			getMethod.releaseConnection();
		}
	}

	// 调用接口DirectSend,对于参数为中文的调用采用以下方法,为防止中文参数为乱码.
	public String directSend(String url, DirectSendDTO directSendDTO) {
		// String response = "";
		HttpClient httpClient = new HttpClient();
		httpClient.getParams().setBooleanParameter(
				"http.protocol.expect-continue", false);
		// String responseValue;
		PostMethod getMethod = new UTF8PostMethod(url);
		NameValuePair[] data = {
				new NameValuePair("UserID", directSendDTO.getUserID()),
				new NameValuePair("Account", directSendDTO.getAccountID()),
				new NameValuePair("Password", directSendDTO.getPassword()),
				new NameValuePair("Phones", directSendDTO.getPhones()),
				new NameValuePair("SendType", directSendDTO.getSendType()),
				new NameValuePair("SendTime", directSendDTO.getSendTime()),
				new NameValuePair("PostFixNumber",
						directSendDTO.getPostFixNumber()),
				new NameValuePair("Content", directSendDTO.getContent()) };
		getMethod.setRequestBody(data);
		getMethod.addRequestHeader("Connection", "close");
		try {
			int statusCode = httpClient.executeMethod(getMethod);
			if (statusCode != HttpStatus.SC_OK) {
				System.err.println("Method failed: "
						+ getMethod.getStatusLine());
			}
			byte[] responseBody = getMethod.getResponseBody();
			String str = new String(responseBody, "utf-8");

			JSONObject jsonObject = XML.toJSONObject(str);

			System.out.println("jsonObject1=>" + jsonObject);
			Reduced reduced = com.alibaba.fastjson.JSONObject.parseObject(
					jsonObject.getString("ROOT"), Reduced.class);
			System.out.println("jsonObject=>" + reduced);
			return null;
		} catch (HttpException e) {
			return "1";
		} catch (IOException e) {
			return "2";
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "2";
		} finally {
			getMethod.releaseConnection();
		}

	}

	public static class UTF8PostMethod extends PostMethod {
		public UTF8PostMethod(String url) {
			super(url);
		}

		@Override
		public String getRequestCharSet() {
			// return Charset.defaultCharset().name();
			// return super.getRequestCharSet();
			return "UTF-8";
		}
	}

	public static void main(String[] args) throws JSONException {
		HttpClientTest httpClientTest = new HttpClientTest();
		// 调用DirectGetStockDetails和DirectFetchSMS接口传入的参数
		String userID = "960396";
		String accountID = "admin";
		String password = "BYUEHZ";
		String phone = "15077827845;13962162124;";
		String content = "hello 短信测试";
		String type = "1";
		// 调用DirectSend接口传入的参数
		DirectSendDTO directSendDTO = new DirectSendDTO();
		directSendDTO.setUserID(userID);
		directSendDTO.setAccountID(accountID);
		directSendDTO.setPassword(password);
		directSendDTO.setPhones(phone);// 13823317233
		directSendDTO.setContent(content);
		directSendDTO.setSendType(type);
		// 调用接口DirectSend
		System.out.println("-----DirectSend begin-----");
		System.out.println(httpClientTest.directSend(
				"http://www.mxtong.net.cn/GateWay/Services.asmx/DirectSend?",
				directSendDTO));
		// TODO Auto-generated catch block
		System.out.println("-----DirectSend end-----");
		// 调用接口DirectGetStockDetails
		// System.out.println("-----DirectGetStockDetails begin-----");
		// System.out
		// .println(httpClientTest
		// .directGetStockDetails("http://www.mxtong.net.cn/GateWay/Services.asmx/DirectGetStockDetails?UserID="
		// + userID
		// + "&Account="
		// + accountID
		// + "&Password=" + password));
		// System.out.println("-----DirectGetStockDetails end-----");
		// 调用接口DirectFetchSMS
		// System.out.println("-----DirectFetchSMS begin-----");
		// System.out
		// .println(httpClientTest
		// .directFetchSMS("http://www.mxtong.net.cn/GateWay/Services.asmx/DirectFetchSMS?UserID="
		// + userID
		// + "&Account="
		// + accountID
		// + "&Password=" + password));
		// System.out.println("-----DirectFetchSMS end-----");
	}
}
