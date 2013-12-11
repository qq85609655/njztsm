package com.sklay.controller.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.channels.Channels;
import java.nio.channels.WritableByteChannel;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sklay.api.SklayApi;
import com.sklay.core.util.Constants;
import com.sklay.mobile.Update;

@Controller
@RequestMapping("/apps")
public class ClientController {

	@Autowired
	private SklayApi sklayApi;

	@RequestMapping("/download/android")
	public void download(HttpServletRequest request,
			HttpServletResponse response) {
		String path = request.getSession().getServletContext().getRealPath("/");
		path = path + Constants.APP_PUBLISH_PATH;

		Update update = sklayApi.getMobileAndroidVer();

		if (null == update || StringUtils.isBlank(update.getVersionName()))
			return;

		String name = "jkgj." + update.getVersionName() + ".apk";
		path = path + File.separator + name;
		File file = new File(path);
		if (!file.exists())
			return;
		response.reset();
		response.setHeader("Accept-Ranges", "bytes");
		response.setHeader("Content-Disposition", "attachment; filename=\""
				+ name + "\"");
		response.addHeader("Content-Length", "" + file.length());
		response.setContentType("application/octet-stream; charset=UTF-8");

		String range = request.getHeader("Range");
		long start = 0;
		long size = file.length();
		if (range != null && range.startsWith("bytes=")) {
			int minus = range.indexOf('-');
			if (minus > -1)
				range = range.substring(6, minus);
			try {
				start = Integer.parseInt(range);
			} catch (NumberFormatException ignored) {
			}
		}
		if (start > 0) {
			response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);
			StringBuilder sb = new StringBuilder("bytes ");
			sb.append(start).append("-").append(size - 1).append("/")
					.append(size);
			response.setHeader("Content-Range", sb.toString());
		}
		response.setContentLength((int) (size - start));

		try {
			transferTo(file, start, size - start,
					Channels.newChannel(response.getOutputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void transferTo(File file, long position, long count,
			WritableByteChannel target) throws IOException {
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);
			fis.getChannel().transferTo(position, count, target);
		} catch (IOException e) {
			throw e;
		} finally {
			IOUtils.closeQuietly(fis);
		}
	}

	public static String getRandColorCode() {
		String r, g, b;
		Random random = new Random();
		r = Integer.toHexString(random.nextInt(256)).toUpperCase();
		g = Integer.toHexString(random.nextInt(256)).toUpperCase();
		b = Integer.toHexString(random.nextInt(256)).toUpperCase();

		r = r.length() == 1 ? "0" + r : r;
		g = g.length() == 1 ? "0" + g : g;
		b = b.length() == 1 ? "0" + b : b;

		return "#" + r + g + b;
	}
}
