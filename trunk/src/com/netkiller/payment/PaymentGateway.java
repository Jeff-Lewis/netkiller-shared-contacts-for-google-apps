package com.netkiller.payment;

import java.io.IOException;
import java.net.InetAddress;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.netkiller.payment.paypal.InvoiceData;
import com.netkiller.payment.paypal.PaymentOptions;
import com.netkiller.payment.paypal.ReceiverIdentifier;
import com.netkiller.payment.paypal.ReceiverOptions;
import com.netkiller.payment.paypal.SetPaymentOptionsRequest;
import com.netkiller.vo.AppProperties;
import com.paypal.adaptive.api.requests.fnapi.CreateSimplePreapproval;
import com.paypal.adaptive.api.requests.fnapi.SimplePay;
import com.paypal.adaptive.api.responses.PayResponse;
import com.paypal.adaptive.api.responses.PreapprovalResponse;
import com.paypal.adaptive.api.responses.SetPaymentOptionsResponse;
import com.paypal.adaptive.core.APICredential;
import com.paypal.adaptive.core.CurrencyCodes;
import com.paypal.adaptive.core.DisplayOptions;
import com.paypal.adaptive.core.InitiatingEntity;
import com.paypal.adaptive.core.InvoiceItem;
import com.paypal.adaptive.core.PayError;
import com.paypal.adaptive.core.PaymentType;
import com.paypal.adaptive.core.Receiver;
import com.paypal.adaptive.core.SenderOptions;
import com.paypal.adaptive.core.ServiceEnvironment;
import com.paypal.adaptive.exceptions.AuthorizationRequiredException;
import com.paypal.adaptive.exceptions.InvalidResponseDataException;
import com.paypal.adaptive.exceptions.MissingParameterException;
import com.paypal.adaptive.exceptions.PayPalErrorException;
import com.paypal.adaptive.exceptions.RequestFailureException;

public class PaymentGateway {

	private String appID;

	private String apiUsername;

	private String apiPassword;

	private String apiSignature;
	private String accountEmail;
	private APICredential apiCredential;

	private AppProperties appProperties;

	public void setAppProperties(AppProperties appProperties) {
		this.appProperties = appProperties;
	}

	public PaymentGateway() {

	}

	public PaymentGateway(String appID, String apiUsername, String apiPassword, String apiSignature, String accountEmail) {

		this.accountEmail = accountEmail;
		this.appID = appID;
		this.apiUsername = apiUsername;
		this.apiPassword = apiPassword;
		this.apiSignature = apiSignature;
		setup();

	}

	private void setup() {
		this.apiCredential = new APICredential();
		apiCredential.setAPIUsername(this.apiUsername);
		apiCredential.setAPIPassword(this.apiPassword);
		apiCredential.setSignature(this.apiSignature);

		// setup your AppID from X.com
		apiCredential.setAppId(this.appID);

		// setup your Test Business account email
		// in most cases this would be associated with API Credentials
		apiCredential.setAccountEmail(this.accountEmail);
	}

	public String makeRequest(String requestUri, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		CreateSimplePreapproval simplePreapproval = new CreateSimplePreapproval();

		PreapprovalResponse preapprovalResponse = null;
		try {
			String returnURL = requestUri.toString() + "?cmd=upgrade&return=1&action=pay&paykey=${payKey}";
			String cancelURL = requestUri.toString() + "?action=pay&cancel=1";
			String ipnURL = requestUri.toString() + "?action=ipn";

			SimplePay simplePay = new SimplePay();
			simplePay.setCancelUrl(cancelURL);
			simplePay.setReturnUrl(returnURL);
			simplePay.setCredentialObj(this.apiCredential);
			InetAddress thisIp = InetAddress.getLocalHost();
			System.out.println(thisIp.getHostAddress());
			simplePay.setUserIp(thisIp.getHostAddress());
			simplePay.setApplicationName("Netkiller Shared Contacts Application");
			simplePay.setCurrencyCode(CurrencyCodes.USD);
			simplePay.setEnv(ServiceEnvironment.PRODUCTION);
			// simplePay.setIpnURL(ipnURL);
			simplePay.setLanguage("en_US");
			simplePay.setMemo("NetKiller Shared Contacts Paid Account");
			Receiver receiver = new Receiver();
			receiver.setAmount(199);
			receiver.setEmail("harryj@netkiller.com");
			//receiver.setInvoiceId("NetKiller Shared Contacts");
			receiver.setPaymentType(PaymentType.SERVICE);
			simplePay.setReceiver(receiver);

			PayResponse payResponse = simplePay.makeRequest();

		} catch (PayPalErrorException e) {
			// handle exceptions as necessary
			System.out.println(e.getPayErrorList());
			for (PayError ob : e.getPayErrorList()) {
				System.out.println(ob.getError().getMessage());
			}
			System.out.println(e.getResponseEnvelope());

			System.out.println("Exception caught" + preapprovalResponse);
			throw e;

		} catch (AuthorizationRequiredException e) {
			System.out.println(e.getMessage());
			requestUri = e.getAuthorizationUrl(ServiceEnvironment.PRODUCTION);
			System.out.println("paypal url" + e.getAuthorizationUrl(ServiceEnvironment.PRODUCTION));
			request.getSession().setAttribute("paykey", e.getPayKey());
			setPaymentOptions(e.getPayKey(), response);
			response.sendRedirect(requestUri);
			System.out.println("Success");
		}
		return requestUri;
	}

	public void setPaymentOptions(String payKey, HttpServletResponse response) {
		SetPaymentOptionsRequest setPaymentOptionsReq = new SetPaymentOptionsRequest("en_US",
				ServiceEnvironment.PRODUCTION);
		String pkey = payKey;
		if (pkey != null && pkey.length() > 0) {
			setPaymentOptionsReq.setPayKey(pkey);
		}
		PaymentOptions paymentOptions = new PaymentOptions();
		String headerImageUrl = "http://netkillersc1.appspot.com/img/sharedcontacts_sm.jpg";
		if (headerImageUrl != null && headerImageUrl.length() > 0) {
			DisplayOptions displayOptions = new DisplayOptions();
			displayOptions.setHeaderImageUrl(headerImageUrl);
			displayOptions.setBusinessName("Netkiller Shared Contacts");
			paymentOptions.setDisplayOptions(displayOptions);
		}
		String requireShippingAddressSelection = "";

		if (requireShippingAddressSelection != null && requireShippingAddressSelection.length() > 0) {
			SenderOptions senderOptions = new SenderOptions();
			senderOptions.setRequireShippingAddressSelection("true".equals(requireShippingAddressSelection));
			paymentOptions.setSenderOptions(senderOptions);
		}
		String itemName = "Netkiller Shared Contacts";
		ReceiverOptions receiverOptions = new ReceiverOptions();
		if (itemName != null) {
			InvoiceItem invoiceItem1 = new InvoiceItem();
			invoiceItem1.setName(itemName);
			invoiceItem1.setPrice("199");
			invoiceItem1.setItemCount(1);
			invoiceItem1.setItemPrice("199");

			InvoiceData invoiceData = new InvoiceData();
			invoiceData.addToItem(invoiceItem1);
			receiverOptions.setInvoiceData(invoiceData);
		}
		ReceiverIdentifier receiver = new ReceiverIdentifier();
		receiver.setEmail("harryj@netkiller.com");
		receiverOptions.setReceiver(receiver);
		paymentOptions.addToReceiverOptions(receiverOptions);
		setPaymentOptionsReq.setPaymentOptions(paymentOptions);

		try {
			response.getWriter().println(setPaymentOptionsReq.toString());
			System.out.println(setPaymentOptionsReq);
			System.out.println(receiverOptions);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			SetPaymentOptionsResponse response1 = setPaymentOptionsReq.execute(apiCredential);
			System.out.println("Response");

		} catch (MissingParameterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidResponseDataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RequestFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	

}
