package cn.com.sinosoft.action.shop.uw;

import com.sinosoft.schema.SDInformationSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * @author meijunfeng
 * 
 */
public interface UWCheckInterfaceNew {
	public static final Logger logger = LoggerFactory.getLogger(UWCheckInterfaceNew.class);

	public Map<String,String> dealData(SDInformationSchema sdinformation,String insuredSn);
}
