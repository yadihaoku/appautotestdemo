package com.hxj.lear.actions;

import org.openqa.selenium.WebElement;

import com.hxj.lear.utils.Finder;

import io.appium.java_client.AppiumDriver;

/**
 * extends继承         对方法的继承
 * @author YanYadi
 *implements对接口进行实现
 *AbstractAction对Action接口进行实现；Action只有两个动作，真正实现是AbstractAction
 */
public abstract class AbstractAction implements Action {

	AppiumDriver mDriver;
	Finder mFinder;
	Action[] mNextActions;
	public AbstractAction(AppiumDriver driver, Action...nextActions) {
		this.mDriver = driver;
		this.mNextActions = nextActions;
	}
	//重载
	public AbstractAction(AppiumDriver driver) {
		this(driver, null);
	}
	/**
	 * 执行动作
	 */
	@Override
	//重写
	public String execute() {
		WebElement element = mFinder.getByDriver(mDriver);
		if(element != null){
			
			return run(mDriver, element);
		}else{
			return RESULT_FAILURE;
		}
	}
	
	public abstract String run(AppiumDriver driver, WebElement element);

	@Override
	public void setFinder(Finder finder) {
		mFinder = finder;
	}

}
