<%@page contentType="text/html;charset=UTF-8" import="java.util.*,com.sinosoft.framework.User" isErrorPage="true"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>中国农业银行北京市分行业务报表</title>
		<link rel="stylesheet" href="css/treeview.css" />
		<link rel="stylesheet" href="css/screen.css" />
		<script src="js/jquery.js" type="text/javascript"></script>
		<script src="js/jquery.cookie.js" type="text/javascript"></script>
		<script src="js/jquery.treeview.js" type="text/javascript"></script>

		<script src="js/demo.js" type="text/javascript"></script>

	</head>
	<body
		style="margin: 0; padding: 0; height: 100%; background-color: #FBFDFC;">
<%
List ldapGroups = (List)User.getValue("ldapGroups");
%>
		<div id="main">

			<ul id="browser" class="filetree">
				<li>
					<span class="folder">业务报表</span>
					<ul style="background-color: #FBFDFC;">
						<li>
							<span class="file"> <a
								href="http://10.2.0.6/cognos8/cgi-bin/cognos.cgi?b_action=cognosViewer&ui.action=run&ui.object=%2fcontent%2ffolder%5b%40name%3d%27%e5%a4%b4%e5%af%b8%27%5d%2freport%5b%40name%3d%27%e6%94%af%e8%a1%8c%e5%a4%b4%e5%af%b8%e6%8a%a5%e8%a1%a8%27%5d&ui.name=%e6%94%af%e8%a1%8c%e5%a4%b4%e5%af%b8%e6%8a%a5%e8%a1%a8&run.outputFormat=&run.prompt=true&CAMUsername=ywbbbj&CAMPassword=12345&CAMNamespace=test&cv.header=false&cv.toolbar=false"
								target="report_frame">支行头寸</a> </span>
						</li>
						<li>
							<span class="file"> <a
								href="http://10.2.0.6/cognos8/cgi-bin/cognos.cgi?b_action=cognosViewer&ui.action=run&ui.object=%2fcontent%2ffolder%5b%40name%3d%27%e5%a4%b4%e5%af%b8%27%5d%2freport%5b%40name%3d%27%e7%bd%91%e7%82%b9%e5%a4%b4%e5%af%b8%e6%8a%a5%e8%a1%a8%27%5d&ui.name=%e7%bd%91%e7%82%b9%e5%a4%b4%e5%af%b8%e6%8a%a5%e8%a1%a8&run.outputFormat=&run.prompt=true&CAMUsername=ywbbbj&CAMPassword=12345&CAMNamespace=test&cv.header=false&cv.toolbar=false"
								target="report_frame">网点头寸</a> </span>
						</li>
						<li>
							<span class="file"> <a
								href="http://10.2.0.6/cognos8/cgi-bin/cognos.cgi?b_action=cognosViewer&ui.action=run&ui.object=%2fcontent%2ffolder%5b%40name%3d%27%e5%a4%b4%e5%af%b8%27%5d%2freport%5b%40name%3d%27%e4%b8%89%e5%86%9c%e5%a4%b4%e5%af%b8%e6%8a%a5%e8%a1%a8%27%5d&ui.name=%e4%b8%89%e5%86%9c%e5%a4%b4%e5%af%b8%e6%8a%a5%e8%a1%a8&run.outputFormat=&run.prompt=true&CAMUsername=ywbbbj&CAMPassword=12345&CAMNamespace=test&cv.header=false&cv.toolbar=false"
								target="report_frame">三农头寸</a> </span>
						</li>

						<li class="closed">
							<span class="folder">其他报表</span>
							<ul style="background-color: #FBFDFC;">
								<li class="closed">
									<span class="folder">头寸历史数据查询</span>
									<ul style="background-color: #FBFDFC;">
										<li>
											<span class="file"> <a
												href="http://10.2.0.6/cognos8/cgi-bin/cognos.cgi?b_action=cognosViewer&ui.action=run&ui.object=%2fcontent%2ffolder%5b%40name%3d%27%e5%a4%b4%e5%af%b8%27%5d%2freport%5b%40name%3d%27%e5%a4%b4%e5%af%b8%e6%8a%a5%e8%a1%a8%27%5d&ui.name=%e5%a4%b4%e5%af%b8%e6%8a%a5%e8%a1%a8&run.outputFormat=&run.prompt=true&CAMUsername=ywbbbj&CAMPassword=12345&CAMNamespace=test"
												target="report_frame">头寸</a> </span>
										</li>
										<li>
											<span class="file"> <a
												href="http://10.2.0.6/cognos8/cgi-bin/cognos.cgi?b_action=cognosViewer&ui.action=run&ui.object=%2fcontent%2ffolder%5b%40name%3d%27%e5%a4%b4%e5%af%b8%27%5d%2freport%5b%40name%3d%27%e6%97%a5%e5%9d%87%e6%8a%a5%e8%a1%a8%27%5d&ui.name=%e6%97%a5%e5%9d%87%e6%8a%a5%e8%a1%a8&run.outputFormat=&run.prompt=true&CAMUsername=ywbbbj&CAMPassword=12345&CAMNamespace=test"
												target="report_frame">日均</a> </span>
										</li>
										<li>
											<span class="file"> <a
												href="http://10.2.0.6/cognos8/cgi-bin/cognos.cgi?b_action=cognosViewer&ui.action=run&ui.object=%2fcontent%2ffolder%5b%40name%3d%27%e5%a4%b4%e5%af%b8%27%5d%2freport%5b%40name%3d%27%e5%a4%b4%e5%af%b8%e6%8a%a5%e8%a1%a8-%e5%85%a8%e7%bd%91%e7%82%b9%27%5d&ui.name=%e5%a4%b4%e5%af%b8%e6%8a%a5%e8%a1%a8-%e5%85%a8%e7%bd%91%e7%82%b9&run.outputFormat=&run.prompt=true&CAMUsername=ywbbbj&CAMPassword=12345&CAMNamespace=test"
												target="report_frame">全网点头寸</a> </span>
										</li>
										<li>
											<span class="file"> <a
												href="http://10.2.0.6/cognos8/cgi-bin/cognos.cgi?b_action=cognosViewer&ui.action=run&ui.object=%2fcontent%2ffolder%5b%40name%3d%27%e5%a4%b4%e5%af%b8%27%5d%2freport%5b%40name%3d%27%e6%97%a5%e5%9d%87%e6%97%b6%e9%97%b4%e6%ae%b5%27%5d&ui.name=%e6%97%a5%e5%9d%87%e6%97%b6%e9%97%b4%e6%ae%b5&run.outputFormat=&run.prompt=true&CAMUsername=ywbbbj&CAMPassword=12345&CAMNamespace=test"
												target="report_frame">时间段日均</a> </span>
										</li>
									</ul>
								</li>
								
								<%
									if(User.getUserName().equals("admin") || ldapGroups.contains("EBANK")){
								%>
								<li>
									<span class="file"><a href="ftp://10.2.38.18/jcjs/" target="report_frame">企业网银每周新增客户明细统计表</a></span>
								</li>
								<%
									}
								%>
								<li>
									<span class="file"><a href="http://10.2.0.6/cognos8/cgi-bin/cognos.cgi?b_action=cognosViewer&ui.action=run&ui.object=%2fcontent%2ffolder%5b%40name%3d%27%e6%94%af%e7%a5%a8%e6%88%b7%e6%97%a5%e5%9d%87%e5%ad%98%e6%ac%be%e6%9c%88%e5%ba%95%e7%bb%9f%e8%ae%a1%e8%a1%a8%27%5d%2fpackage%5b%40name%3d%27%e6%94%af%e7%a5%a8%e6%88%b7%e6%97%a5%e5%9d%87%e5%ad%98%e6%ac%be%e6%9c%88%e5%ba%95%e7%bb%9f%e8%ae%a1FM%27%5d%2freport%5b%40name%3d%27%e6%94%af%e7%a5%a8%e6%88%b7%e6%97%a5%e5%9d%87%e5%ad%98%e6%ac%be%e6%9c%88%e5%ba%95%e7%bb%9f%e8%ae%a1%e8%a1%a8%27%5d&ui.name=%e6%94%af%e7%a5%a8%e6%88%b7%e6%97%a5%e5%9d%87%e5%ad%98%e6%ac%be%e6%9c%88%e5%ba%95%e7%bb%9f%e8%ae%a1%e8%a1%a8&run.outputFormat=&run.prompt=true&CAMUsername=ywbbbj&CAMPassword=12345&CAMNamespace=test" 
									target="report_frame">人民币支票户日均存款月底统计表</a></span>
								</li>

								<li class="closed">
									<span class="folder">渠道分析</span>
									<ul style="background-color: #FBFDFC;">
										<li>
											<span class="file"> <a
												href="http://10.2.0.6/cognos8/cgi-bin/cognos.cgi?b_action=cognosViewer&ui.action=run&ui.object=%2fcontent%2ffolder%5b%40name%3d%27%e6%b8%a0%e9%81%93%e5%88%86%e6%9e%90%27%5d%2freport%5b%40name%3d%27%e7%94%9f%e4%ba%a7%e8%bf%90%e8%a1%8c%e5%88%86%e6%9e%90%27%5d&ui.name=%e7%94%9f%e4%ba%a7%e8%bf%90%e8%a1%8c%e5%88%86%e6%9e%90&run.outputFormat=&run.prompt=true&CAMUsername=ywbbbj&CAMPassword=12345&CAMNamespace=test"
												target="report_frame">生产运行情况分析</a> </span>
										</li>
										<li>
											<span class="file"> <a
												href="http://10.2.0.6/cognos8/cgi-bin/cognos.cgi?b_action=cognosViewer&ui.action=run&ui.object=%2fcontent%2ffolder%5b%40name%3d%27%e6%b8%a0%e9%81%93%e5%88%86%e6%9e%90%27%5d%2freport%5b%40name%3d%27%e5%8c%97%e4%ba%ac%e5%88%86%e8%a1%8c%e4%ba%a4%e6%98%93%e5%88%86%e5%ba%94%e7%94%a820110301%27%5d&ui.name=%e5%8c%97%e4%ba%ac%e5%88%86%e8%a1%8c%e4%ba%a4%e6%98%93%e5%88%86%e5%ba%94%e7%94%a820110301&run.outputFormat=&run.prompt=true&CAMUsername=ywbbbj&CAMPassword=12345&CAMNamespace=test"
												target="report_frame">金融性交易按应用统计分析</a> </span>
										</li>
										<li>
											<span class="file"> <a
												href="http://10.2.0.6/cognos8/cgi-bin/cognos.cgi?b_action=cognosViewer&ui.action=run&ui.object=%2fcontent%2ffolder%5b%40name%3d%27%e6%b8%a0%e9%81%93%e5%88%86%e6%9e%90%27%5d%2freport%5b%40name%3d%27%e5%8c%97%e4%ba%ac%e5%88%86%e8%a1%8c%e4%ba%a4%e6%98%93%e5%88%86%e6%b8%a0%e9%81%9320110221%27%5d&ui.name=%e5%8c%97%e4%ba%ac%e5%88%86%e8%a1%8c%e4%ba%a4%e6%98%93%e5%88%86%e6%b8%a0%e9%81%9320110221&run.outputFormat=&run.prompt=true&CAMUsername=ywbbbj&CAMPassword=12345&CAMNamespace=test"
												target="report_frame">交易分渠道统计分析</a> </span>
										</li>
										<li>
											<span class="file"> <a
												href="http://10.2.0.6/cognos8/cgi-bin/cognos.cgi?b_action=cognosViewer&ui.action=run&ui.object=%2fcontent%2ffolder%5b%40name%3d%27%e6%b8%a0%e9%81%93%e5%88%86%e6%9e%90%27%5d%2freport%5b%40name%3d%27%e5%8c%97%e4%ba%ac%e5%88%86%e8%a1%8c%e4%ba%a4%e6%98%93%e5%88%86%e6%b8%a0%e9%81%93-%e7%bd%91%e7%82%b9%27%5d&ui.name=%e5%8c%97%e4%ba%ac%e5%88%86%e8%a1%8c%e4%ba%a4%e6%98%93%e5%88%86%e6%b8%a0%e9%81%93-%e7%bd%91%e7%82%b9&run.outputFormat=&run.prompt=true&CAMUsername=ywbbbj&CAMPassword=12345&CAMNamespace=test&cv.header=false"
												target="report_frame">交易分渠道统计分析(按网点)</a> </span>
										</li>
									</ul>
								</li>
								<%
									if(User.getUserName().equals("admin") || ldapGroups.contains("ATM_KH")){
								%>
								<li class="closed">
									<span class="folder">ATM考核分析</span>
									<ul style="background-color: #FBFDFC;">
										<li>
											<span class="file"> <a
												href="http://10.2.0.6/cognos8/cgi-bin/cognos.cgi?b_action=cognosViewer&ui.action=run&ui.object=%2fcontent%2ffolder%5b%40name%3d%27ATM%27%5d%2freport%5b%40name%3d%27%e6%8c%89%e8%ae%be%e5%a4%87%e5%88%86%e6%9e%90%27%5d&ui.name=%e6%8c%89%e8%ae%be%e5%a4%87%e5%88%86%e6%9e%90&run.outputFormat=&run.prompt=true&CAMUsername=ywbbbj&CAMPassword=12345&CAMNamespace=test"
												target="report_frame">按设备分析</a> </span>
										</li>
										<li>
											<span class="file"> <a
												href="http://10.2.0.6/cognos8/cgi-bin/cognos.cgi?b_action=cognosViewer&ui.action=run&ui.object=%2fcontent%2ffolder%5b%40name%3d%27ATM%27%5d%2freport%5b%40name%3d%27%e6%8c%89%e7%bd%91%e7%82%b9%e5%88%86%e6%9e%90%27%5d&ui.name=%e6%8c%89%e7%bd%91%e7%82%b9%e5%88%86%e6%9e%90&run.outputFormat=&run.prompt=true&CAMUsername=ywbbbj&CAMPassword=12345&CAMNamespace=test"
												target="report_frame">按支行分析</a> </span>
										</li>
									</ul>
								</li>

								<li class="closed">
									<span class="folder">ATM业务分析</span>
									<ul style="background-color: #FBFDFC;">
										<li>
											<span class="file"> <a
												href="http://10.2.0.6/cognos8/cgi-bin/cognos.cgi?b_action=cognosViewer&ui.action=run&ui.object=%2fcontent%2ffolder%5b%40name%3d%27ATM%27%5d%2freport%5b%40name%3d%27ATM%e4%ba%a4%e6%98%93%e6%98%8e%e7%bb%86%e5%88%86%e6%9e%90%27%5d&ui.name=ATM%e4%ba%a4%e6%98%93%e6%98%8e%e7%bb%86%e5%88%86%e6%9e%90&run.outputFormat=&run.prompt=true&CAMUsername=ywbbbj&CAMPassword=12345&CAMNamespace=test"
												target="report_frame">ATM交易明细分析</a> </span>
										</li>
										<li>
											<span class="file"> <a
												href="http://10.2.0.6/cognos8/cgi-bin/cognos.cgi?b_action=cognosViewer&ui.action=run&ui.object=%2fcontent%2ffolder%5b%40name%3d%27ATM%27%5d%2freport%5b%40name%3d%27%e6%94%af%e8%a1%8cATM%e4%ba%a4%e6%98%93%e6%9c%88%e7%bb%9f%e8%ae%a1%e5%88%86%e6%9e%90%27%5d&ui.name=%e6%94%af%e8%a1%8cATM%e4%ba%a4%e6%98%93%e6%9c%88%e7%bb%9f%e8%ae%a1%e5%88%86%e6%9e%90&run.outputFormat=&run.prompt=true&CAMUsername=ywbbbj&CAMPassword=12345&CAMNamespace=test"
												target="report_frame">支行ATM交易月统计分析</a> </span>
										</li>
										<li>
											<span class="file"> <a
												href="http://10.2.0.6/cognos8/cgi-bin/cognos.cgi?b_action=cognosViewer&ui.action=run&ui.object=%2fcontent%2ffolder%5b%40name%3d%27ATM%27%5d%2freport%5b%40name%3d%27%e5%88%86%e8%a1%8cATM%e4%ba%a4%e6%98%93%e6%9c%88%e7%bb%9f%e8%ae%a1%e5%88%86%e6%9e%90%27%5d&ui.name=%e5%88%86%e8%a1%8cATM%e4%ba%a4%e6%98%93%e6%9c%88%e7%bb%9f%e8%ae%a1%e5%88%86%e6%9e%90&run.outputFormat=&run.prompt=true&CAMUsername=ywbbbj&CAMPassword=12345&CAMNamespace=test"
												target="report_frame">分行ATM交易月统计分析</a> </span>
										</li>
									</ul>
								</li>

								<li class="closed">
									<span class="folder">POS业务分析</span>
									<ul style="background-color: #FBFDFC;">
										<li>
											<span class="file"> <a
												href="http://10.2.0.6/cognos8/cgi-bin/cognos.cgi?b_action=cognosViewer&ui.action=run&ui.object=%2fcontent%2ffolder%5b%40name%3d%27POS%e4%b8%9a%e5%8a%a1%e5%88%86%e6%9e%90%27%5d%2freport%5b%40name%3d%27POS%e4%ba%a4%e6%98%93%e9%87%8f%e7%bb%9f%e8%ae%a1%e5%88%86%e6%9e%90%27%5d&ui.name=POS%e4%ba%a4%e6%98%93%e9%87%8f%e7%bb%9f%e8%ae%a1%e5%88%86%e6%9e%90&run.outputFormat=&run.prompt=true&CAMUsername=ywbbbj&CAMPassword=12345&CAMNamespace=test"
												target="report_frame">POS交易量统计分析</a> </span>
										</li>
										<li>
											<span class="file"> <a
												href="http://10.2.0.6/cognos8/cgi-bin/cognos.cgi?b_action=cognosViewer&ui.action=run&ui.object=%2fcontent%2ffolder%5b%40name%3d%27POS%e4%b8%9a%e5%8a%a1%e5%88%86%e6%9e%90%27%5d%2freport%5b%40name%3d%27POS%e4%ba%a4%e6%98%93%e6%98%8e%e7%bb%86%e8%a1%a8_%e6%8c%89%e8%ae%be%e5%a4%87%27%5d&ui.name=POS%e4%ba%a4%e6%98%93%e6%98%8e%e7%bb%86%e8%a1%a8_%e6%8c%89%e8%ae%be%e5%a4%87&run.outputFormat=&run.prompt=true&CAMUsername=ywbbbj&CAMPassword=12345&CAMNamespace=test"
												target="report_frame">POS交易明细分析（按设备）</a> </span>
										</li>
										<li>
											<span class="file"> <a
												href="http://10.2.0.6/cognos8/cgi-bin/cognos.cgi?b_action=cognosViewer&ui.action=run&ui.object=%2fcontent%2ffolder%5b%40name%3d%27POS%e4%b8%9a%e5%8a%a1%e5%88%86%e6%9e%90%27%5d%2freport%5b%40name%3d%27POS%e4%ba%a4%e6%98%93%e6%98%8e%e7%bb%86%e8%a1%a8_%e6%8c%89%e5%95%86%e6%88%b7%27%5d&ui.name=POS%e4%ba%a4%e6%98%93%e6%98%8e%e7%bb%86%e8%a1%a8_%e6%8c%89%e5%95%86%e6%88%b7&run.outputFormat=&run.prompt=true&CAMUsername=ywbbbj&CAMPassword=12345&CAMNamespace=test"
												target="report_frame">POS交易明细分析（按商户）</a> </span>
										</li>
									</ul>
								</li>
								<%
									}
								
								if(User.getUserName().equals("admin") || ldapGroups.contains("SELF_KH")){
								
								%>
								<li class="closed">
									<span class="folder">自助终端</span>
									<ul style="background-color: #FBFDFC;">
										<li>
											<span class="file"> <a
												href="http://10.2.0.6/cognos8/cgi-bin/cognos.cgi?b_action=cognosViewer&ui.action=run&ui.object=%2fcontent%2ffolder%5b%40name%3d%27%e8%87%aa%e5%8a%a8%e7%bb%88%e7%ab%af%27%5d%2freport%5b%40name%3d%27%e5%85%a8%e8%a1%8c%e8%87%aa%e5%8a%a8%e7%bb%88%e7%ab%af%e4%ba%a4%e6%98%93%e8%80%83%e6%a0%b8%e6%b1%87%e6%80%bb%e8%a1%a8(%e6%8c%89%e7%bd%91%e7%82%b9)2010%27%5d&ui.name=%e5%85%a8%e8%a1%8c%e8%87%aa%e5%8a%a8%e7%bb%88%e7%ab%af%e4%ba%a4%e6%98%93%e8%80%83%e6%a0%b8%e6%b1%87%e6%80%bb%e8%a1%a8(%e6%8c%89%e7%bd%91%e7%82%b9)2010&run.outputFormat=&run.prompt=true&CAMUsername=ywbbbj&CAMPassword=12345&CAMNamespace=test"
												target="report_frame">按网点</a> </span>
										</li>
										<li>
											<span class="file"> <a
												href="http://10.2.0.6/cognos8/cgi-bin/cognos.cgi?b_action=cognosViewer&ui.action=run&ui.object=%2fcontent%2ffolder%5b%40name%3d%27%e8%87%aa%e5%8a%a8%e7%bb%88%e7%ab%af%27%5d%2freport%5b%40name%3d%27%e5%85%a8%e8%a1%8c%e8%87%aa%e5%8a%a9%e7%bb%88%e7%ab%af%e4%ba%a4%e6%98%93%e8%80%83%e6%a0%b8%e6%b1%87%e6%80%bb%e8%a1%a8(%e6%8c%89%e6%94%af%e8%a1%8c)2010%27%5d&ui.name=%e5%85%a8%e8%a1%8c%e8%87%aa%e5%8a%a9%e7%bb%88%e7%ab%af%e4%ba%a4%e6%98%93%e8%80%83%e6%a0%b8%e6%b1%87%e6%80%bb%e8%a1%a8(%e6%8c%89%e6%94%af%e8%a1%8c)2010&run.outputFormat=&run.prompt=true&CAMUsername=ywbbbj&CAMPassword=12345&CAMNamespace=test"
												target="report_frame">按支行</a> </span>
										</li>
									</ul>
								</li>

								<%
									}
								
								if(User.getUserName().equals("admin") || ldapGroups.contains("JJGZ")){
								
								%>
								<li class="closed">
									<span class="folder"><a
										href="http://10.2.0.6/cognos8/cgi-bin/cognos.cgi?b_action=cognosViewer&ui.action=run&ui.object=%2fcontent%2ffolder%5b%40name%3d%27%e8%ae%a1%e4%bb%b6%e5%b7%a5%e8%b5%84%e5%88%86%e6%9e%90%27%5d%2freport%5b%40name%3d%27%e8%ae%a1%e4%bb%b7%e5%b7%a5%e8%b5%84%e4%b8%9a%e5%8a%a1%e6%8a%a5%e8%a1%a8%27%5d&ui.name=%e8%ae%a1%e4%bb%b7%e5%b7%a5%e8%b5%84%e4%b8%9a%e5%8a%a1%e6%8a%a5%e8%a1%a8&run.outputFormat=&run.prompt=true&CAMUsername=ywbbbj&CAMPassword=12345&CAMNamespace=test"
										target="report_frame">计件工资分析</a>
									</span>
								</li>
								<%
									}
								
								if(User.getUserName().equals("admin") || ldapGroups.contains("CMAA1") || ldapGroups.contains("CMAA2") || ldapGroups.contains("CMAA3") || ldapGroups.contains("CMAA4") || ldapGroups.contains("CMAA5") || ldapGroups.contains("CMAA6") || ldapGroups.contains("CMAA7") || ldapGroups.contains("CMAA8") || ldapGroups.contains("CMAA9")){
								
								%>
								<li class="closed">
									<span class="folder">机构处统计月报表</span>
									<ul style="background-color: #FBFDFC;">
										<li>
											<span class="file"> <a href="http://10.2.0.6/cognos8/cgi-bin/cognos.cgi?b_action=cognosViewer&ui.action=run&ui.object=%2fcontent%2ffolder%5b%40name%3d%27%e6%97%a0%e5%bf%a7%e7%90%86%e8%b4%a2%27%5d%2freport%5b%40name%3d%27%e6%97%a0%e5%bf%a7%e7%90%86%e8%b4%a2%e4%b8%9a%e5%8a%a1%e9%87%8f%e7%bb%9f%e8%ae%a1%e8%a1%a8%27%5d&ui.name=%e6%97%a0%e5%bf%a7%e7%90%86%e8%b4%a2%e4%b8%9a%e5%8a%a1%e9%87%8f%e7%bb%9f%e8%ae%a1%e8%a1%a8&run.outputFormat=&run.prompt=true&cv.header=false&CAMUsername=ywbbbj&CAMPassword=12345&CAMNamespace=test" target="report_frame">无忧理财业务量统计表</a>
											</span>
										</li>
										<li>
											<span class="file"> <a href="http://10.2.0.6/cognos8/cgi-bin/cognos.cgi?b_action=cognosViewer&ui.action=run&ui.object=%2fcontent%2ffolder%5b%40name%3d%27%e6%9c%ba%e6%9e%84%e5%a4%84%e7%bb%9f%e8%ae%a1%27%5d%2freport%5b%40name%3d%27CMAA2%27%5d&ui.name=CMAA2&run.outputFormat=&run.prompt=true&CAMUsername=ywbbbj&CAMPassword=12345&CAMNamespace=test" target="report_frame">机构处统计月报表</a>
											</span>
										</li>
										<li>
											<span class="file"> <a href="http://10.2.0.6/cognos8/cgi-bin/cognos.cgi?b_action=cognosViewer&ui.action=run&ui.object=%2fcontent%2ffolder%5b%40name%3d%27%e7%ac%ac%e4%b8%89%e6%96%b9%e5%ad%98%e7%ae%a1%e4%b8%9a%e5%8a%a1%e7%bb%9f%e8%ae%a1%27%5d%2freport%5b%40name%3d%27%e7%ac%ac%e4%b8%89%e6%96%b9%e5%ad%98%e7%ae%a1%e4%b8%9a%e5%8a%a1%e7%bb%9f%e8%ae%a1%e6%9c%88%e6%8a%a5%27%5d&ui.name=%e7%ac%ac%e4%b8%89%e6%96%b9%e5%ad%98%e7%ae%a1%e4%b8%9a%e5%8a%a1%e7%bb%9f%e8%ae%a1%e6%9c%88%e6%8a%a5&run.outputFormat=&run.prompt=true&CAMUsername=ywbbbj&CAMPassword=12345&CAMNamespace=test" target="report_frame">第三方存管业务统计表</a>
											</span>
										</li>
									</ul>
								</li>
								<%
									}
								
								if(User.getUserName().equals("admin") || ldapGroups.contains("DKHTJ")){
								
								%>
								<li class="closed">
									<span class="folder">客户大额动账统计分析</span>
									<ul style="background-color: #FBFDFC;">
										<li>
											<span class="file"> <a href="http://10.2.0.6/cognos8/cgi-bin/cognos.cgi?b_action=cognosViewer&ui.action=run&ui.object=%2fcontent%2ffolder%5b%40name%3d%27GRED_RS%27%5d%2freport%5b%40name%3d%27%e4%b8%aa%e4%ba%ba%e5%a4%a7%e9%a2%9d%e5%8a%a8%e5%b8%90%e6%8a%a5%e8%a1%a8%27%5d&ui.name=%e4%b8%aa%e4%ba%ba%e5%a4%a7%e9%a2%9d%e5%8a%a8%e5%b8%90%e6%8a%a5%e8%a1%a8&run.outputFormat=&run.prompt=true&CAMUsername=ywbbbj&CAMPassword=12345&CAMNamespace=test" target="report_frame">个人大额动账统计分析</a>
											</span>
										</li>
										<li>
											<span class="file"> <a href="http://10.2.0.6/cognos8/cgi-bin/cognos.cgi?b_action=cognosViewer&ui.action=run&ui.object=%2fcontent%2ffolder%5b%40name%3d%27GRED_RS%27%5d%2freport%5b%40name%3d%27%e5%af%b9%e5%85%ac%e5%a4%a7%e9%a2%9d%e5%8a%a8%e5%b8%90%e7%bb%9f%e8%ae%a1%e6%8a%a5%e8%a1%a8%27%5d&ui.name=%e5%af%b9%e5%85%ac%e5%a4%a7%e9%a2%9d%e5%8a%a8%e5%b8%90%e7%bb%9f%e8%ae%a1%e6%8a%a5%e8%a1%a8&run.outputFormat=&run.prompt=true&CAMUsername=ywbbbj&CAMPassword=12345&CAMNamespace=test" target="report_frame">对公大额动账统计分析</a>
											</span>
										</li>
										<li>
											<span class="file"> <a href="http://10.2.0.6/cognos8/cgi-bin/cognos.cgi?b_action=cognosViewer&ui.action=run&ui.object=%2fcontent%2ffolder%5b%40name%3d%27GRED_RS%27%5d%2freport%5b%40name%3d%27%e5%af%b9%e5%85%ac%e5%a4%a7%e9%a2%9d%e5%8a%a8%e5%b8%90%e7%bb%9f%e8%ae%a1%e6%9c%88%e6%8a%a5%e8%a1%a8%27%5d&ui.name=%e5%af%b9%e5%85%ac%e5%a4%a7%e9%a2%9d%e5%8a%a8%e5%b8%90%e7%bb%9f%e8%ae%a1%e6%9c%88%e6%8a%a5%e8%a1%a8&run.outputFormat=&run.prompt=true&CAMUsername=ywbbbj&CAMPassword=12345&CAMNamespace=test&cv.header=false" target="report_frame">对公大额动帐统计月报表</a></span>
										</li>
									</ul>
								</li>
								<%
									}
								
								if(User.getUserName().equals("admin") || ldapGroups.contains("GJJDK")){
								
								%>

								<li class="closed">
									<span class="folder">公积金托收新增对公开户统计专题</span>
									<ul style="background-color: #FBFDFC;">
										<li>
											<span class="file"> <a href="http://10.2.0.6/cognos8/cgi-bin/cognos.cgi?b_action=cognosViewer&ui.action=run&ui.object=%2fcontent%2ffolder%5b%40name%3d%27%e5%85%ac%e7%a7%af%e9%87%91%27%5d%2freport%5b%40name%3d%27%e6%96%b0%e5%a2%9e%e5%bc%80%e6%88%b7%e8%90%a5%e9%94%80%e5%90%88%e8%ae%a1%e7%bb%9f%e8%ae%a1%27%5d&ui.name=%e6%96%b0%e5%a2%9e%e5%bc%80%e6%88%b7%e8%90%a5%e9%94%80%e5%90%88%e8%ae%a1%e7%bb%9f%e8%ae%a1&run.outputFormat=&run.prompt=true&CAMUsername=ywbbbj&CAMPassword=12345&CAMNamespace=test" target="report_frame">新增开户营销合计统计</a>
											</span>
										</li>
										<li>
											<span class="file"> <a href="http://10.2.0.6/cognos8/cgi-bin/cognos.cgi?b_action=cognosViewer&ui.action=run&ui.object=%2fcontent%2ffolder%5b%40name%3d%27%e5%85%ac%e7%a7%af%e9%87%91%27%5d%2freport%5b%40name%3d%27%e6%96%b0%e5%a2%9e%e5%bc%80%e6%88%b7%e8%90%a5%e9%94%80%e6%98%8e%e7%bb%86%e7%bb%9f%e8%ae%a1%27%5d&ui.name=%e6%96%b0%e5%a2%9e%e5%bc%80%e6%88%b7%e8%90%a5%e9%94%80%e6%98%8e%e7%bb%86%e7%bb%9f%e8%ae%a1&run.outputFormat=&run.prompt=true&CAMUsername=ywbbbj&CAMPassword=12345&CAMNamespace=test" target="report_frame">新增开户营销明细统计</a>
											</span>
										</li>
										<li>
											<span class="file"> <a href="http://10.2.0.6/cognos8/cgi-bin/cognos.cgi?b_action=cognosViewer&ui.action=run&ui.object=%2fcontent%2ffolder%5b%40name%3d%27%e5%85%ac%e7%a7%af%e9%87%91%27%5d%2freport%5b%40name%3d%27%e6%96%b0%e5%a2%9e%e5%bc%80%e6%88%b7%e7%b4%af%e8%ae%a1%e4%ba%a4%e6%98%93%e9%a2%9d%e7%bb%9f%e8%ae%a1%27%5d&ui.name=%e6%96%b0%e5%a2%9e%e5%bc%80%e6%88%b7%e7%b4%af%e8%ae%a1%e4%ba%a4%e6%98%93%e9%a2%9d%e7%bb%9f%e8%ae%a1&run.outputFormat=&run.prompt=true&CAMUsername=ywbbbj&CAMPassword=12345&CAMNamespace=test" target="report_frame">新增开户累计交易额统计</a>
											</span>
										</li>
									</ul>
								</li>
								<%
									}
								if(User.getUserName().equals("admin") || ldapGroups.contains("Real_Estate")){
								%>
								<li>
									<span class="file"><a href="http://10.2.0.6/cognos8/cgi-bin/cognos.cgi?b_action=cognosViewer&ui.action=run&ui.object=%2fcontent%2ffolder%5b%40name%3d%27%e6%88%bf%e5%9c%b0%e4%ba%a7%e4%b8%9a%e5%8a%a1%27%5d%2freport%5b%40name%3d%27%e6%88%bf%e5%9c%b0%e4%ba%a7%e4%b8%9a%e5%8a%a1%27%5d&ui.name=%e6%88%bf%e5%9c%b0%e4%ba%a7%e4%b8%9a%e5%8a%a1&run.outputFormat=&run.prompt=true&CAMUsername=ywbbbj&CAMPassword=12345&CAMNamespace=test&cv.header=false" 
									target="report_frame">房地产业务</a></span>
								</li>
								<%
									}
								%>
							</ul>
						</li>

					</ul>
				</li>

			</ul>
		</div>
	</body>
</html>