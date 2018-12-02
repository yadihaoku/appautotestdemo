package logo.file;

import io.appium.java_client.AppiumDriver;//???
import io.appium.java_client.TouchAction;//????

import java.io.FileNotFoundException;//??
import java.io.IOException;//??
import jxl.read.biff.*;
import logo.module.ElementExist;
import logo.module.SlidePage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
	
public class AutoTestCaseID2 {
	ElementExist el = new ElementExist();
	static AutoTestExcelFile ft = new AutoTestExcelFile();//static??
	//static变量 静态变量，可以用类名直接访问
		//
	//static方法
		//可以直接通过类名调用，
	//static代码块
	SlidePage sp = new SlidePage();
	
	public void TestcaseId(AppiumDriver driver,String id) throws BiffException, IOException
	{
		int i,c1,c2,c3,c4,c5,c6,c7,c8;
		
		for(i = 0;i < ft.ReadContent().size();i++)
		{
			if(ft.ReadContent().get(i).contains(id))
			{
				for(c1 = 0; c1 < ft.ReadTitle().size(); c1++)//?
				{
					if(ft.ReadTitle().get(c1).contains("定位方式")){break;}
				}
				String caseidLocation = ft.ReadTitleContent(i+1, c1);
				
				for(c2 = 0; c2 < ft.ReadTitle().size(); c2++)
				{
					if(ft.ReadTitle().get(c2).contains("控件元素")){break;}
				}
				String caseidElement = ft.ReadTitleContent(i+1, c2);
				for(c3 = 0; c3 < ft.ReadTitle().size(); c3++){
					if(ft.ReadTitle().get(c3).contains("操作方法")){break;}
				}
				String caseidOperationMethod = ft.ReadTitleContent(i+1, c3);
				for (c4 = 0; c4 < ft.ReadTitle().size(); c4++)
				{
					if (ft.ReadTitle().get(c4).contains("测试数据")){break;}
				}
				String caseidTestData = ft.ReadTitleContent(i + 1, c4);
				for (c5 = 0; c5 < ft.ReadTitle().size(); c5++)
				{
					if (ft.ReadTitle().get(c5).contains("延迟时间")){break;}
				}
				String SleepTime = ft.ReadTitleContent(i + 1, c4);			
				if(caseidLocation.equals("By.xpath")){
					if(caseidOperationMethod.equals("sendkeys")){
						el.waitForElementByXpath(caseidElement, driver);
					}
					}
			}
			
		}
	}

}
