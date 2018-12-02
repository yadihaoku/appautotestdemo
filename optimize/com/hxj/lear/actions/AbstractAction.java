package com.hxj.lear.actions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebElement;

import com.hxj.lear.utils.Finder;

import io.appium.java_client.AppiumDriver;

/**
 * extends继承 对方法的继承
 * 
 * @author YanYadi implements对接口进行实现
 *         AbstractAction对Action接口进行实现；Action只有两个动作，真正实现是AbstractAction
 */
public abstract class AbstractAction implements Action {

	AppiumDriver mDriver;
	Finder mFinder;
	Action[] mNextActions;

	public AbstractAction(AppiumDriver driver, Action... nextActions) {
		this.mDriver = driver;
		this.mNextActions = nextActions;
	}

	// 重载
	public AbstractAction(AppiumDriver driver) {
		this(driver, null);
	}

	public void setNextActions(Action[] actions){
		this.mNextActions = actions;
	}
	/**
	 * 执行动作
	 */
	@Override
	// 重写
	public String execute() {
		WebElement element = mFinder.getByDriver(mDriver);
		if (element != null) {
			final String result = run(mDriver, element);
			if(result != RESULT_PASS)
				return result;
		
			if (mNextActions != null) {
					ArrayList<Action> actions = new ArrayList<>();
					actions.addAll(Arrays.asList(mNextActions));

				for (Action action : actions) {
						// 如果有一个 动作，执行失败。则全部认为失败。
						if (!RESULT_PASS.equals(action.execute()))
							return RESULT_FAILURE;
				}

				return RESULT_PASS;
			}
			return result;
		} else {
			return RESULT_FAILURE;
		}
	}

	public abstract String run(AppiumDriver driver, WebElement element);

	@Override
	public void setFinder(Finder finder) {
		mFinder = finder;
	}
	protected void sleep(long ms){
		if(ms <= 0)return;
		try {
			TimeUnit.MILLISECONDS.sleep(ms);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
