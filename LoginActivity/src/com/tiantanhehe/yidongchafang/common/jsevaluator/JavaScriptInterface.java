package com.tiantanhehe.yidongchafang.common.jsevaluator;

import com.tiantanhehe.yidongchafang.common.jsevaluator.interfaces.CallJavaResultInterface;

import android.webkit.JavascriptInterface;

/**
 * Passed in addJavascriptInterface of WebView to allow web views's JS execute
 * Java code
 */
public class JavaScriptInterface {
	private final CallJavaResultInterface mCallJavaResultInterface;

	public JavaScriptInterface(CallJavaResultInterface callJavaResult) {
		mCallJavaResultInterface = callJavaResult;
	}

	@JavascriptInterface
	public void returnResultToJava(String value, int callIndex) {
		mCallJavaResultInterface.jsCallFinished(value, callIndex);
	}


}