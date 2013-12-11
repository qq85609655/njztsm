package com.sklay.controller.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.channels.Channels;
import java.nio.channels.WritableByteChannel;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.sklay.api.SklayApi;
import com.sklay.core.enums.AuditStatus;
import com.sklay.core.enums.Level;
import com.sklay.core.ex.ErrorCode;
import com.sklay.core.ex.SklayException;
import com.sklay.core.util.Constants;
import com.sklay.core.util.DateTimeUtil;
import com.sklay.mobile.Line;
import com.sklay.mobile.LineChart;
import com.sklay.mobile.Update;
import com.sklay.model.ChartData;
import com.sklay.model.DeviceBinding;
import com.sklay.model.GatherData;
import com.sklay.model.HealthReport;
import com.sklay.model.MedicalReport;
import com.sklay.model.User;
import com.sklay.service.BindingService;
import com.sklay.service.MedicalReportService;
import com.sklay.vo.DataView;

@Controller
@RequestMapping("/android")
public class AndroidColtroller {

	@Autowired
	private MedicalReportService reportService;

	@Autowired
	private BindingService bindingService;

	@Autowired
	private SklayApi sklayApi;

	@RequestMapping("/mobile/android")
	public void android(HttpServletRequest request, HttpServletResponse response) {
		String strDirPath = request.getSession().getServletContext()
				.getRealPath("/");

		String name = "njzt-1.7.6.3.apk";
		String path = prePath(strDirPath) + "WEB-INF/version/" + name;
		File file = new File(path);

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

	@RequestMapping("/version")
	@ResponseBody
	public DataView lastVersion() {

		// 版本描述字符串
		// String versionName = "1.7.6.3";
		// // 新版本更新下载地址
		// String downloadUrl =
		// "http://10.0.2.2/android/download/njzt-1.7.6.3.apk";
		// // 更新描述信息
		// String updateLog = "版本信息：3G智能健康服务机 for Android v"
		// + versionName
		// +
		// " 更新日志：1、修复点击评论内容中链接时程序崩溃问题。请点击确定在线升级，如果升级失败，请到http://www.njztsm.net下载最新版本安装包大小：2.0MB";
		// // 版本号
		// int versionCode = 22;

		String versionName = "1.7.4";
		// 新版本更新下载地址
		String downloadUrl = "http://test.njztsm.net/android/download/njzt-1.7.6.3.apk";
		// 更新描述信息
		String updateLog = "版本信息：3G智能健康服务机 for Android v"
				+ versionName
				+ " 更新日志：1、修复点击评论内容中链接时程序崩溃问题。请点击确定在线升级，如果升级失败，请到http://www.njztsm.net下载最新版本安装包大小：2.0MB";
		// 版本号
		int versionCode = 18;

		Update data = sklayApi.getMobileAndroidVer();
		DataView dataView = new DataView(1, "success",
				JSONObject.toJSONString(data));

		return dataView;
	}

	@RequestMapping("/download/{ver}")
	public void download(@PathVariable String ver, HttpServletRequest request,
			HttpServletResponse response) {
		String strDirPath = request.getSession().getServletContext()
				.getRealPath("/");
		String name = "njzt-1.7.6.3.apk";
		String path = prePath(strDirPath) + "WEB-INF/version/" + name;
		File file = new File(path);

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

	@RequestMapping("/chart")
	public String initLineChart(String phone, ModelMap modelMap) {
		modelMap.addAttribute("copyright", "");

		if (StringUtils.isBlank(phone))
			throw new SklayException(ErrorCode.SMS_GATHER_SIMNO_EMPTY);
		/** 获取绑定主号 */
		List<DeviceBinding> list = bindingService.findTargetBinding(
				phone.trim(), Level.FIRST);

		if (CollectionUtils.isEmpty(list))
			throw new SklayException(ErrorCode.SMS_GATHER_ERROR);
		DeviceBinding deviceBinding = list.get(Constants.ZERO);

		if (null == deviceBinding.getTargetUser())
			throw new SklayException(ErrorCode.SMS_GATHER_NOBANDING, null,
					phone);
		if (AuditStatus.PASS != deviceBinding.getStatus())
			throw new SklayException(ErrorCode.AUTIT_ERROR, null, new Object[] {
					"设备绑定信息", "获取体检报告" });
		User member = deviceBinding.getTargetUser();

		if (AuditStatus.PASS != member.getStatus())
			throw new SklayException(ErrorCode.AUTIT_ERROR, null, new Object[] {
					"用户信息", "获取体检报告" });

		List<String> labels = Lists.newArrayList();
		List<Integer> systolic = Lists.newArrayList();
		List<Integer> diastolic = Lists.newArrayList();
		List<Integer> health = Lists.newArrayList();
		List<String> reports = Lists.newArrayList();
		Long userId = member.getId();

		MedicalReport lastReport = reportService.getMemberLastReports(userId);

		if (null == lastReport)
			throw new SklayException(ErrorCode.SMS_GATHER_ERROR);

		List<MedicalReport> lastReports = reportService.getMemberReports(
				lastReport, userId, -7);

		if (CollectionUtils.isEmpty(lastReports))
			return "mobile.android.error";

		long firstDate = lastReports.get(Constants.ZERO).getReportTime();

		Map<String, HealthReport> result4health = Maps.newHashMap();

		for (MedicalReport mReoprt : lastReports) {
			String x_time = DateTimeUtil.getDate(mReoprt.getReportTime());
			String text = mReoprt.getOriginalData();
			GatherData gatherData = JSONObject.parseObject(text,
					GatherData.class);

			Integer systolic_val = Integer
					.valueOf(gatherData.getHighPressure());
			Integer diastolic_val = Integer
					.valueOf(gatherData.getLowPressure());
			Integer health_val = gatherData.getIndexHealth();
			String reports_val = mReoprt.getResult();

			HealthReport healthReport = new HealthReport(systolic_val,
					diastolic_val, health_val, reports_val);

			result4health.put(x_time, healthReport);
		}

		for (int i = 0; i < 7; i++) {
			Integer systolic_val = 0;
			Integer diastolic_val = 0;
			Integer health_val = 0;
			String reports_val = "";

			long cutDate = firstDate - 1000 * 3600 * 24 * i;
			String x_time = DateTimeUtil.getDate(cutDate);
			labels.add(x_time);

			if (result4health.containsKey(x_time)) {

				HealthReport healthReport = result4health.get(x_time);

				systolic_val = healthReport.getSystolic();
				diastolic_val = healthReport.getDiastolic();
				health_val = healthReport.getHealth();
				reports_val = healthReport.getReport();
			}

			systolic.add(systolic_val);
			diastolic.add(diastolic_val);
			health.add(health_val);
			reports.add(reports_val);
		}
		Collections.reverse(labels);
		Collections.reverse(systolic);
		Collections.reverse(diastolic);
		Collections.reverse(health);
		Collections.reverse(reports);
		modelMap.addAttribute("member", member);
		modelMap.addAttribute("labels", JSON.toJSONString(labels));
		modelMap.addAttribute("systolic", JSON.toJSONString(systolic));
		modelMap.addAttribute("diastolic", JSON.toJSONString(diastolic));
		modelMap.addAttribute("health", JSON.toJSONString(health));
		modelMap.addAttribute("reports", JSON.toJSONString(reports));
		modelMap.addAttribute("vertical", 6);

		return "mobile.android.lineChart";
	}

	@RequestMapping("/chart/{phone}")
	@ResponseBody
	public DataView lineChart(@PathVariable String phone) {

		if (StringUtils.isBlank(phone))
			throw new SklayException(ErrorCode.SMS_GATHER_SIMNO_EMPTY);
		/** 获取绑定主号 */
		List<DeviceBinding> list = bindingService.findTargetBinding(
				phone.trim(), Level.FIRST);

		if (CollectionUtils.isEmpty(list))
			throw new SklayException(ErrorCode.SMS_GATHER_ERROR);
		DeviceBinding deviceBinding = list.get(Constants.ZERO);

		if (null == deviceBinding.getTargetUser())
			throw new SklayException(ErrorCode.SMS_GATHER_NOBANDING, null,
					phone);
		if (AuditStatus.PASS != deviceBinding.getStatus())
			throw new SklayException(ErrorCode.AUTIT_ERROR, null, new Object[] {
					"设备绑定信息", "获取体检报告" });
		User member = deviceBinding.getTargetUser();

		if (AuditStatus.PASS != member.getStatus())
			throw new SklayException(ErrorCode.AUTIT_ERROR, null, new Object[] {
					"用户信息", "获取体检报告" });

		List<String> labels = Lists.newArrayList();
		List<Integer> systolic = Lists.newArrayList();
		List<Integer> diastolic = Lists.newArrayList();
		List<Integer> health = Lists.newArrayList();
		List<String> reports = Lists.newArrayList();
		Long userId = member.getId();

		MedicalReport lastReport = reportService.getMemberLastReports(userId);

		if (null == lastReport)
			throw new SklayException(ErrorCode.SMS_GATHER_ERROR);

		List<MedicalReport> lastReports = reportService.getMemberReports(
				lastReport, userId, -7);

		if (CollectionUtils.isEmpty(lastReports))
			throw new SklayException("暂无体检报告");

		long firstDate = lastReports.get(Constants.ZERO).getReportTime();

		Map<String, HealthReport> result4health = Maps.newHashMap();
		String yeah = DateTimeUtil.getYear(firstDate);

		for (MedicalReport mReoprt : lastReports) {
			String x_time = DateTimeUtil.getDate(mReoprt.getReportTime());
			x_time = x_time.replaceFirst(yeah + "-", "");

			String text = mReoprt.getOriginalData();
			ChartData gatherData = JSONObject.parseObject(text,
					ChartData.class);

			Integer systolic_val = Integer
					.valueOf(gatherData.getHighPressure());
			Integer diastolic_val = Integer
					.valueOf(gatherData.getLowPressure());
			Integer health_val = gatherData.getIndexHealth();
			String reports_val = mReoprt.getResult();

			HealthReport healthReport = new HealthReport(systolic_val,
					diastolic_val, health_val, reports_val);

			result4health.put(x_time, healthReport);
		}

		for (int i = 0; i < 7; i++) {
			Integer systolic_val = 0;
			Integer diastolic_val = 0;
			Integer health_val = 0;
			String reports_val = "";

			long cutDate = firstDate - 1000 * 3600 * 24 * i;
			String x_time = DateTimeUtil.getDate(cutDate);
			x_time = x_time.replaceFirst(yeah + "-", "");

			labels.add(x_time);
			if (result4health.containsKey(x_time)) {

				HealthReport healthReport = result4health.get(x_time);

				systolic_val = healthReport.getSystolic();
				diastolic_val = healthReport.getDiastolic();
				health_val = healthReport.getHealth();
				reports_val = healthReport.getReport();
			}

			systolic.add(systolic_val);
			diastolic.add(diastolic_val);
			health.add(health_val);
			reports.add(reports_val);
		}
		Collections.reverse(labels);
		Collections.reverse(systolic);
		Collections.reverse(diastolic);
		Collections.reverse(health);
		Collections.reverse(reports);

		// modelMap.addAttribute("member", member);
		// modelMap.addAttribute("labels", JSON.toJSONString(labels));
		// modelMap.addAttribute("systolic", JSON.toJSONString(systolic));
		// modelMap.addAttribute("diastolic", JSON.toJSONString(diastolic));
		// modelMap.addAttribute("health", JSON.toJSONString(health));
		// modelMap.addAttribute("reports", JSON.toJSONString(reports));
		// modelMap.addAttribute("vertical", 6);
		Integer line_width = 2;

		Line line_systolic = new Line("收缩压（高压）", systolic, getRandColorCode(),
				line_width);

		Line line_diastolic = new Line("舒张压（低压）", diastolic,
				getRandColorCode(), line_width);

		Line line_health = new Line("健康指数", health, getRandColorCode(),
				line_width);

		List<Line> data = Lists.newArrayList(line_systolic, line_diastolic,
				line_health);

		LineChart chart = new LineChart(labels, data, reports, yeah, 6,
				member.getName());

		DataView dataView = new DataView(1, "成功", JSON.toJSONString(chart));

		return dataView;
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

	private String prePath(String path) {
		if (path.endsWith("/"))
			return path;
		else
			return path + "/";
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
