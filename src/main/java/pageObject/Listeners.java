package pageObject;

import bases.BaseClass;
import bases.Utilities;
import org.testng.*;

public class Listeners extends BaseClass implements ITestListener {
    Utilities utils=new Utilities();
    @Override
    public void onTestStart(ITestResult result) {
        String message="On test start "+result.getName();
        Reporter.log(message);
        utis1(message);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        String message="On test success "+result.getName();
        Reporter.log(message);
        utis1(message);
    }

    @Override
    public void onTestFailure(ITestResult result) {
        String message="On test failure "+result.getName();
        Reporter.log(message);
        utis1(message);
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        String message="On test skip "+result.getName();
        Reporter.log(message);
        utis1(message);
    }

    @Override
    public void onStart(ITestContext context) {
        String message="On start "+context.getName();
        Reporter.log(message);
        utis1(message);
    }

    @Override
    public void onFinish(ITestContext context) {

        String message="On Finish "+context.getName();
        Reporter.log(message);
        utis1(message);
    }
    public void utis1(String text)
    {
        try{
            utils.takeSnapShot(text+utils.timereturn()+".png");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
