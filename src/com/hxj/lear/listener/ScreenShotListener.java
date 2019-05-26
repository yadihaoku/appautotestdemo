package com.hxj.lear.listener;

import org.testng.ITestResult;

import com.hxj.lear.utils.ScreenShot;

public class ScreenShotListener extends BaseListener {

	@Override
	public void onTestFailure(ITestResult tr) {
		ScreenShot.takeFailureScreenShot(tr.getMethod().getMethodName());
	}
	
}
