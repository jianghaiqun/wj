package weibo4j.util;

/////////////////////////////////////////////////////////
//Bare Bones Browser Launch                            //
//Version 1.5 (December 10, 2005)                      //
//By Dem Pilafian                                      //
//Supports: Mac OS X, GNU/Linux, Unix, Windows XP      //
//Example Usage:                                       //
// String url = "http://www.centerkey.com/";           //
// BareBonesBrowserLaunch.openURL(url);                //
//Public Domain Software -- Free to Use as You Like    //
/////////////////////////////////////////////////////////

/**
 * @author Dem Pilafian
 * @author John Kristian
 */
import java.lang.reflect.Method;

import javax.swing.JOptionPane;

public class BareBonesBrowserLaunch {

    public static void openURL(String url) {
        try {
            browse(url);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error attempting to launch web browser:\n" + e.getLocalizedMessage());
        }
    }

    private static void browse(String url) throws Exception { 
        //获取操作系统的名字  
        String osName = System.getProperty("os.name", "");  
        if (osName.startsWith("Mac OS")) {  
            //苹果的打开方式  
            Class fileMgr = Class.forName("com.apple.eio.FileManager");  
            Method openURL = fileMgr.getDeclaredMethod("openURL", new Class[] { String.class });  
            openURL.invoke(null, new Object[] { url });  
        } else if (osName.startsWith("Windows")) {  
           //windows的打开方式。  
            Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + url);  
        } else {  
            // Unix or Linux的打开方式  
            String[] browsers = { "firefox", "opera", "konqueror", "epiphany", "mozilla", "netscape" };  
            String browser = null;  
            for (int count = 0; count < browsers.length && browser == null; count++)  
                //执行代码，在brower有值后跳出，  
                //这里是如果进程创建成功了，==0是表示正常结束。  
                if (Runtime.getRuntime().exec(new String[] { "which", browsers[count] }).waitFor() == 0)  
                    browser = browsers[count];  
            if (browser == null)  
                throw new Exception("Could not find web browser");  
            else  
                //这个值在上面已经成功的得到了一个进程。  
                Runtime.getRuntime().exec(new String[] { browser, url });  
        }  
    }  
   
}
