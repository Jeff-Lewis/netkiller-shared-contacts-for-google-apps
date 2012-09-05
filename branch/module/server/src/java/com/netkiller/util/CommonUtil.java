package com.netkiller.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.logging.Level;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

import com.netkiller.core.AppException;
import com.netkiller.service.conversion.impl.GoogleConversionService;
import com.netkiller.vo.Customer;

public class CommonUtil {

	private static final AppLogger log = AppLogger
			.getLogger(GoogleConversionService.class);

	public static void dispatchRequest(HttpServletRequest request,
			HttpServletResponse response, String path) throws AppException {
		try {
			RequestDispatcher requestDispatcher = request
					.getRequestDispatcher(path);
			requestDispatcher.forward(request, response);
			// response.sendRedirect(path);
		} catch (IOException ioException) {
			throw new AppException("Unable to redirect to " + path, ioException);
		} catch (ServletException servletException) {
			throw new AppException("Unable to dispatch redirect to " + path,
					servletException);
		}
	}

	public static String getCurrentUrl(String path) {
		String hostUrl = null;
		String environment = System
				.getProperty("com.google.appengine.runtime.environment");
		if (StringUtils.equals("Production", environment)) {
			String applicationId = System
					.getProperty("com.google.appengine.application.id");
			String version = System
					.getProperty("com.google.appengine.application.version");
			hostUrl = "http://" + applicationId + ".appspot.com/";
		} else {
			hostUrl = "";
		}
		return hostUrl + path;

	}

	public static String getImageUrl(String blobKeyString) {
		String imageUrl = null;
		String environment = System
				.getProperty("com.google.appengine.runtime.environment");
		if (StringUtils.equals("Production", environment)) {
			if (blobKeyString != null) {
				imageUrl = getCurrentUrl("resource/showImage.do?blobKey="
						+ blobKeyString);
			} else {
				imageUrl = getCurrentUrl("sample.png");
			}
		} else {
			imageUrl = getCurrentUrl("sample.png");
		}
		return imageUrl;

	}

	public static byte[] getByteArray(InputStream is) {

		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		try {
			int nRead;
			byte[] data = new byte[16384];
			while ((nRead = is.read(data, 0, data.length)) != -1) {
				buffer.write(data, 0, nRead);
			}

			buffer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return buffer.toByteArray();
	}

	public static byte[] getByteArrayfromUrl(String url) throws AppException {
		byte[] data = null;

		try {
			data = IOUtils.toByteArray(new URL(url).openStream());
		} catch (MalformedURLException e) {
			throw new AppException("Unable to get data from url " + url, e);
		} catch (IOException e) {
			throw new AppException("Unable to get data from url " + url, e);
		}
		return data;
	}

	public static String convertEncodingType(String orgStr, String orgStrType,
			String newStrType) throws UnsupportedEncodingException {
		return new String(orgStr.getBytes(orgStrType), newStrType);
	}

	public static String convertEncodingType(String orgStr)
			throws UnsupportedEncodingException {
		return convertEncodingType(orgStr, "8859_1", "UTF-8");
	}

	public static String getNotNullString(String param) {
		if (param == null) {
			param = "";
		}
		return param;
	}

	public static String getNotNullValue(Object value) {
		if (value == null) {
			value = "";
		}
		return value.toString();
	}

	public static String getString(String param, String replace) {
		if (param == null || param.equals("")) {
			param = replace;
		}
		return param;
	}

	public static String getStringWithSpace(String param) {
		return getString(param, " ");
	}

	public static String getCurrentDate() {

		// String DATE_FORMAT = "MMM dd, yyyy HH:mm:ss z";
		String DATE_FORMAT = "MMM dd, yyyy HH:mm:ss";
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		sdf.setTimeZone(TimeZone.getTimeZone("GMT-4:00"));

		return sdf.format(new Date());

		// TimeZone theTz = TimeZone.getTimeZone("GMT-4:00");
		// DateFormat df = DateFormat.getDateTimeInstance( DateFormat.MEDIUM,
		// DateFormat.MEDIUM );
		// df.setTimeZone( theTz );
		// return df.format( new Date()) ;

		// Date date = new Date();
		// SimpleDateFormat sdf = new SimpleDateFormat( format );
		// return sdf.format( date );
	}

	public static String toWon(Long param) {
		// NumberFormat nf = new DecimalFormat("#,###");
		NumberFormat nf = new DecimalFormat("#,##0");
		return nf.format(param);
	}

	public static String getEmailLink(String email) {
		if (email.trim().isEmpty()) {
			return email;
		}
		return "<a style='text-decoration:none;' href='https://mail.google.com/mail/?view=cm&fs=1&tf=1&to="
				+ email + "' target='_blank'>" + email + "</a>";
	}

	public static String getMapLink(String address) {
		if (address.trim().isEmpty()) {
			return address;
		}
		String linkAddress = address.replaceAll(" ", "+");
		return "<a style='text-decoration:none;' href='http://maps.google.com/maps?q="
				+ linkAddress + "' target='_blank'>" + address + "</a>";
	}

	// Kentucky,+United+States

	public static boolean isTheSecondTypeCustomer(Customer customer) {
		boolean isTheSecondTypeCustomer = false;
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			long registeredTimestamp = customer.getRegisteredDate() == null ? 0
					: customer.getRegisteredDate().getTime();
			long april1;
			april1 = format.parse("2012-04-01 00:00").getTime();

			if (registeredTimestamp == 0 || registeredTimestamp > april1) {
				isTheSecondTypeCustomer = true;
			}

			log.warn("customer.getAccountType(): " + customer.getAccountType()
					+ "april1: " + april1 + ", " + "registeredTimestamp("
					+ customer.getRegisteredDate() + "): "
					+ registeredTimestamp + ", isTheSecondTypeCustomer:"
					+ isTheSecondTypeCustomer);

		} catch (ParseException e) {
			log.error(e.getMessage(), e);
		}

		return isTheSecondTypeCustomer;
	}
}
