/*
 * author：邹辉 ，功能：APP功能自动化测试框架代码，时间：2016年10月 ，书籍：《软件自动化测试开发》
 */
package logo.file;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;

import java.io.FileNotFoundException;
import java.io.IOException;
import jxl.read.biff.BiffException;
import logo.module.ElementExist;
import logo.module.SlidePage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class AutoTestCaseID   
{
	ElementExist el = new ElementExist(); //判断元素是否存在--创建类的对象及初始化                 
	static AutoTestExcelFile ft = new AutoTestExcelFile(); //获取excel文件数据 
	SlidePage sp = new SlidePage();  //页面滑动                       


	public void TestcaseId(AppiumDriver driver, String id) throws InterruptedException, BiffException, IOException
	{                                                      
		int i, j, k, l, m, n, o, p, q; 
		for (i = 0; i < ft.ReadContent().size(); i++)
			// ft.ReadContent().size()获得excel文件中行数（不包括标题行）
		{
			if (ft.ReadContent().get(i).contains(id))
				//id代表EXCEL文件中测试用例标号：TestcaseId(driver, "My_Login_001")中的"My_Login_001"
				//ft.ReadContent()是List类型
			{
				for (j = 0; j < ft.ReadTitle().size(); j++)
					//ft.ReadTitle().size()获取列的个数
				{
					if (ft.ReadTitle().get(j).contains("定位方式"))
					{
						break;
					}
				}
				String caseidLocation = ft.ReadTitleContent(i + 1, j);//caseidLocation存储定位方式的内容

				for (k = 0; k < ft.ReadTitle().size(); k++)
				{
					if (ft.ReadTitle().get(k).contains("控件元素"))
					{
						break;
					}
				}
				String caseidElement = ft.ReadTitleContent(i + 1, k);//caseidElement存储定位到的控件元素的内容

				for (l = 0; l < ft.ReadTitle().size(); l++)
				{
					if (ft.ReadTitle().get(l).contains("操作方法"))
					{
						break;
					}
				}
				String caseidOperationMethod = ft.ReadTitleContent(i + 1, l);//caseidOperationMethod存储操作方法的内容

				for (m = 0; m < ft.ReadTitle().size(); m++)
				{
					if (ft.ReadTitle().get(m).contains("测试数据"))
					{
						break;
					}
				}
				String caseidTestData = ft.ReadTitleContent(i + 1, m);//caseidTestData存储测试数据的内容

				for (n = 0; n < ft.ReadTitle().size(); n++)
				{
					if (ft.ReadTitle().get(n).contains("验证数据"))
					{
						break;
					}
				}
				String caseidVerifyData = ft.ReadTitleContent(i + 1, n);

				for (o = 0; o < ft.ReadTitle().size(); o++)
				{
					if (ft.ReadTitle().get(o).contains("延迟时间"))
					{
						break;
					}
				}
				String SleepTime = ft.ReadTitleContent(i + 1, o);//SleepTime存储延迟时间的内容

				if (caseidLocation.equals("By.xpath"))//xpath定位
				{
					if (caseidOperationMethod.equals("sendkeys"))//输入操作
					{
						el.waitForElementByXpath(caseidElement, driver);
						driver.findElement(By.xpath(caseidElement)).sendKeys(caseidTestData);
						if (SleepTime != null && SleepTime.length() != 0)
						{
							String StepTime = SleepTime.substring(0, SleepTime.indexOf("."));
							Thread.sleep(Integer.parseInt(StepTime));
						}
					} else if (caseidOperationMethod.equals("click"))//点击操作
					{
						el.waitForElementByXpath(caseidElement, driver);
						driver.findElement(By.xpath(caseidElement)).click();
						if (SleepTime != null && SleepTime.length() != 0)
						{
							String StepTime = SleepTime.substring(0, SleepTime.indexOf("."));
							Thread.sleep(Integer.parseInt(StepTime));
						}
					} else if (caseidOperationMethod.equals("swipedown"))
					{
						el.waitForElementByXpath(caseidElement, driver);
						sp.Down_Page(caseidElement, driver);
						if (SleepTime != null && SleepTime.length() != 0)
						{
							String StepTime = SleepTime.substring(0, SleepTime.indexOf("."));
							Thread.sleep(Integer.parseInt(StepTime));
						}
					}

				} else if (caseidLocation.equals("By.id"))//id定位
				{
					if (caseidOperationMethod.equals("sendkeys"))//输入操作
					{
						el.waitForElementById(caseidElement, driver);
						driver.findElement(By.id(caseidElement)).sendKeys(caseidTestData);
						if (SleepTime != null && SleepTime.length() != 0)
						{
							// String StepTime =
							// SleepTime.substring(0,SleepTime.indexOf("."));
							Thread.sleep(Integer.parseInt(SleepTime));
						}
					} else if (caseidOperationMethod.equals("click"))//点击操作
					{
						el.waitForElementById(caseidElement, driver);
						driver.findElement(By.id(caseidElement)).click();
						if (SleepTime != null && SleepTime.length() != 0)
						{
							// String StepTime =
							// SleepTime.substring(0,SleepTime.indexOf("."));
							Thread.sleep(Integer.parseInt(SleepTime));
						}
					} else if (caseidOperationMethod.equals("swipedown"))
					{
						el.waitForElementById(caseidElement, driver);
						sp.Down_Page(caseidElement, driver);
						if (SleepTime != null && SleepTime.length() != 0)
						{
							String StepTime = SleepTime.substring(0, SleepTime.indexOf("."));
							Thread.sleep(Integer.parseInt(StepTime));
						}
					} else if (caseidOperationMethod.equals("DownPage"))
					{
						el.waitForElementById(caseidElement, driver);
						sp.DownPage(caseidElement, driver);
						if (SleepTime != null && SleepTime.length() != 0)
						{
							// String StepTime =
							// SleepTime.substring(0,SleepTime.indexOf("."));
							Thread.sleep(Integer.parseInt(SleepTime));
						}
					}
				} else if (caseidLocation.equals("By.name"))//name定位
				{
					if (caseidOperationMethod.equals("sendkeys"))//输入操作
					{
						el.waitForElementByName(caseidElement, driver);
						driver.findElement(By.name(caseidElement)).sendKeys(caseidTestData);
						if (SleepTime != null && SleepTime.length() != 0)
						{
							String StepTime = SleepTime.substring(0, SleepTime.indexOf("."));
							Thread.sleep(Integer.parseInt(StepTime));
						}
					} else if (caseidOperationMethod.equals("click"))//点击操作
					{
						el.waitForElementByName(caseidElement, driver);
						driver.findElement(By.name(caseidElement)).click();
						if (SleepTime != null && SleepTime.length() != 0)
						{
							// String StepTime =
							// SleepTime.substring(0,SleepTime.indexOf("."));
							Thread.sleep(Integer.parseInt(SleepTime));
						}
					} else if (caseidOperationMethod.equals("press"))
					{
						el.waitForElementByName(caseidElement, driver);
						WebElement e = driver.findElement(By.name(caseidElement)); // 长按
																						// 拍视频
						TouchAction action = new TouchAction(driver);
						action.press(e).waitAction(5000);
						action.perform();
						if (SleepTime != null && SleepTime.length() != 0)
						{
							String StepTime = SleepTime.substring(0, SleepTime.indexOf("."));
							Thread.sleep(Integer.parseInt(StepTime));
						}
					}
				} else if (caseidLocation.equals("By.driver"))
				{
					if (caseidOperationMethod.equals("swipetoup"))
					{
						// el.waitForElementById(caseidObjectEntity, driver);
						sp.swipeToUp(Integer.parseInt(caseidElement), driver);
						if (SleepTime != null && SleepTime.length() != 0)
						{
							String StepTime = SleepTime.substring(0, SleepTime.indexOf("."));
							Thread.sleep(Integer.parseInt(StepTime));
						}
					}
				} else if (caseidLocation.equals("By.name"))
				{
					if ("pass".equals(el.waitForElementByNameSkip(caseidElement, driver)))//??????
					{
						if (caseidOperationMethod.equals("click"))
						{
							// el.waitForElementByName(caseidObjectEntity,
							// driver);
							driver.findElement(By.name(caseidElement)).click();
							if (SleepTime != null && SleepTime.length() != 0)
							{
								String StepTime = SleepTime.substring(0, SleepTime.indexOf("."));
								Thread.sleep(Integer.parseInt(StepTime));
							}
						}
					} else if ("failed".equals(el.waitForElementByNameSkip(caseidElement, driver)))
					{
						continue;
					}
				}

				if (caseidVerifyData != null && caseidVerifyData.length() != 0)
				{
					el.waitForElementByName(caseidVerifyData, driver);
				}

				for (p = 0; p < ft.ReadTitle().size(); p++)
				{
					if (ft.ReadTitle().get(p).contains("测试结果"))
					{
						break;
					}
				}
				ft.WriteTitleContent(i + 1, (short) p);
			} else
			{

			}
		}
		Thread.sleep(3000);
	}
}
