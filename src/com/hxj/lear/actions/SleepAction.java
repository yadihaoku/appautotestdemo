package com.hxj.lear.actions;

import java.util.concurrent.TimeUnit;

import com.hxj.lear.utils.Finder;

public class SleepAction implements Action {
	
	long timeInMs;
	SleepAction(long times){
		this.timeInMs = times;
	}
	 
	@Override
	public String execute() {
		try {
			TimeUnit.MILLISECONDS.sleep(timeInMs);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return RESULT_PASS;
	}


	@Override
	public void setFinder(Finder finder) {
		
	}

	@Override
	public void addSubAction(Action action) {
		
	}

}
