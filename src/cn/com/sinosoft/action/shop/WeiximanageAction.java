package cn.com.sinosoft.action.shop;

import com.sinosoft.framework.Config;
import org.apache.struts2.convention.annotation.ParentPackage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * 
* @ClassName: WeiximanageAction 
* @Description: TODO(维析统计逻辑代码，禁止提交生产环境！！！！！) 
* @author zhangjing 
* @date 
*
 */
@ParentPackage("shop")
public class WeiximanageAction extends BaseShopAction {
	String path = "";
	String name = "";
	private static final long serialVersionUID = -8062028974045078820L;

	public String pathManage() {
		String str = "页面分组名称：("+name+")             URL路径为："+Config.getValue("ContextPath") + path;
		try {
			String path=Config.getContextRealPath()+"weixi.txt";
			File f = new File(path);
			logger.info("维析统计文件存储的路径为(禁止提交生产环境)："+path);
			if (!(f.exists())) {
				f.createNewFile();
			}
			Date date = new Date();
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String s = sf.format(date).toString();
			str = str + "             操作时间为：  " + s;
			FileOutputStream fos = new FileOutputStream(f, true);
			PrintWriter pw = new PrintWriter(fos, true);
			pw.println();
			pw.write(str);
			pw.flush();
			fos.close();
			pw.close();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
