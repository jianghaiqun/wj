package weibo4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import weibo4j.http.HttpClient;

public class Weibo implements java.io.Serializable {
	public static final Logger logger = LoggerFactory.getLogger(Weibo.class);

	private static final long serialVersionUID = 4282616848978535016L;

	public HttpClient client = new HttpClient();

	public  void setToken(String token) {
		client.setToken(token);
	}

}