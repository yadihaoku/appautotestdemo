package com.hxj.lear.actions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
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
	ArrayList<Action> mNextActions;

	public AbstractAction(AppiumDriver driver, Action... nextActions) {
		this.mDriver = driver;
		this.mNextActions = new ArrayList<>(Arrays.asList( nextActions  ));
	}
 

	public void addSubAction(Action action){
		if(this.mNextActions == null) {
			this.mNextActions = new ArrayList<>();
		}
		this.mNextActions.add( action );
	}
	/**
	 * 执行动作
	 */
	@Override
	// 接口的实现
	public String execute() {
		if(mFinder == Finder.EMPTY) {
			return run(mDriver, null);
		}
		WebElement element = mFinder.getByDriver(mDriver);
		if (element != null) {
			final String result = run(mDriver, element);
			if(result != RESULT_PASS)
				return result;
		
			if (mNextActions != null) {
				final List<Action> actions = Collections.unmodifiableList(mNextActions);
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
