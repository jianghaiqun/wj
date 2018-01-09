<#assign s=JspTaglibs["/WEB-INF/struts-tags.tld"] />
<!DOCTYPE html >
<!--<%@ taglib prefix="s" uri="/struts-tags" %>-->
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>填写投保信息</title>
<meta name="Author" content="SINOSOFT Team" />
<meta name="Copyright" content="SINOSOFT" />
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<#include "/WEB-INF/template/common/include.ftl">
<link href="${base}/template/shop/css/login.css" rel="stylesheet" type="text/css" />

<link href="${base}/template/admin/css/input.css" rel="stylesheet" type="text/css" />

<link href="${base}/template/shop/css/register.css" rel="stylesheet" type="text/css" />

<link href="${base}/template/shop/css/product.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/shop/css/product_content.css" rel="stylesheet" type="text/css" />

<link href="${base}/template/shop/css/order.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/shop/js/login.js"></script>
<script type="text/javascript" src="${base}/template/shop/js/register.js"></script>
<script type="text/javascript" src="${base}/template/shop/js/product.js"></script>
<script type="text/javascript" src="${base}/template/shop/js/calendar.js"></script>


<SCRIPT LANGUAGE="JavaScript"> 
function Dsy() 
{ 
this.Items = {}; 
} 
Dsy.prototype.add = function(id,iArray) 
{ 
this.Items[id] = iArray; 
} 
Dsy.prototype.Exists = function(id) 
{ 
if(typeof(this.Items[id]) == "undefined") return false; 
return true; 
}

function change(v){ 
var str="0"; 
for(i=0;i<v;i++){ str+=("_"+(document.getElementById(s[i]).selectedIndex-1));}; 
var ss=document.getElementById(s[v]); 
with(ss){ 
length = 0; 
options[0]=new Option(opt0[v],opt0[v]); 
if(v && document.getElementById(s[v-1]).selectedIndex>0 || !v) 
{ 
if(dsy.Exists(str)){ 
ar = dsy.Items[str]; 
for(i=0;i<ar.length;i++)options[length]=new Option(ar[i],ar[i]); 
if(v)options[1].selected = true; 
} 
} 
if(++v<s.length){change(v);} 
} 
}

var dsy = new Dsy();

dsy.add("0",["一般职业","农牧业","渔业","木材森林业","矿业采掘业","交通运输业","餐饮旅游业","建筑工程","制造加工维修业","出版广告业","医药卫生保健","娱乐业","文教机构","宗教机构","公共事业","一般买卖（零售批发业）","服务业","家庭管理","公检法等执法检查机关","军人","IT业（软、硬件开发制作）","职业运动","其他"]);
dsy.add("0_0",["机关团体公司","工厂"]); 
dsy.add("0_0_0",["机关内勤(不从事凶险工作)","机关外勤（不属于本表下列职业分类所列者)"]); 
dsy.add("0_0_1",["工厂负责人(不亲自作业)","工厂厂长(不亲自作业)"]);
dsy.add("0_1",["农业","牧业"]); 
dsy.add("0_1_0",["农场经营者（不亲自作业）","农夫","长短工","果农","苗圃栽培人员","花圃栽培人员","饲养家禽家畜人员","农业技师、指导员","农业机械之操作或修理人员","农具商","糖厂技工","昆虫(蜜蜂)饲养人员"]);
dsy.add("0_1_1",["畜牧场经营者(不亲自作业)","畜牧工作人员","兽医","动物养殖人员","驯养人员"]);
dsy.add("0_2",["内陆渔业","海上渔业"]); 
dsy.add("0_2_0",["渔场经营者（不亲自作业）","渔场经营者(亲自作业)","养殖工人(内陆)","热带鱼养殖者、水族馆经营者","捕鱼人(内陆)","捕鱼人(沿海)","水产实验人员(室内)","养殖工人(沿海)","水产加工工人"]);
dsy.add("0_2_1",["远洋渔船船员","近海渔船船员"]); 
dsy.add("0_3",["森林砍伐业","木材加工业","造林业","农林牧渔服务业"]); 
dsy.add("0_3_0",["领班、监工","伐木工人","锯木工人","运材车辆之司机及押运人员","起重机之操作人员","装运工人、挂钩工人"]); 
dsy.add("0_3_1",["木材工厂现场之职员","领班","分级员","检查员","标记员","磅秤员","锯木工人","防腐剂工人","木材储藏槽工人","木材搬运工人","吊车操作人员","合板制造人员"]); 
dsy.add("0_3_2",["领班","山地造林人员","山林管理人员","森林防火人员","平地育苗人员","实验室育苗栽培人员"]); 
dsy.add("0_3_3",["技术服务咨询人员","拖拉机驾驶员","联合收割机驾驶员","农用运输车驾驶员","农用机械操作及修理人员","沼气工程施工人员","能源设备安装、调试、检修人员","沼气生产管理人员"]); 
dsy.add("0_4",["坑道内作业","坑道外作业","海上作业","采矿石业","陆上油矿开采业"]); 
dsy.add("0_4_0",["矿工、采掘工、爆破工"]);
dsy.add("0_4_1",["经营者(不到现场者)","经营者(现场监督者)","经理人员","矿寻工程师、技师、领班","工人","工矿安全人员"]);
dsy.add("0_4_2",["海上所有作业人员(潜水人员拒保)"]);
dsy.add("0_4_3",["采石业工人","采砂业工人"]);
dsy.add("0_4_4",["行政人员","工程师","技术员","油气井清洁保养修护工","钻勘设备安装换修保养工","钻油井工人"]);
dsy.add("0_5",["陆运","铁路","航运","空运"]);
dsy.add("0_5_0",["出租车企业、物流企业负责人","外务员（无驾照人员）","内勤工作人员","自用小客车司机","自用大客车司机","出租车、救护车司机","游览车司机及服务员","客运车司机及服务员（市区内）","小型客货两用车司机","自用货车司机、随车工人、搬家工","人力三轮车夫","铁牛车驾驶、混凝土预拌车驾驶员`","机动三轮车夫","柜台售票员","客运车稽核人员","营业用货车司机、随车工人","搬运工人、装卸工人","矿石车司机、随车工人","工程卡车司机、随车人员","液化、氧化油罐车司机、随车工人","货柜车司机、随车人员","缆车操纵员","公路收费及监控人员","加油站工作人员","客运车司机及服务员（中长途、高速路段）"]);
dsy.add("0_5_1",["铁路站长","铁路票房工作人员","铁路播音员","铁路一般内勤人员","铁路车站检票员","铁路服务台人员","铁路月台上工作人员","车站治安巡视人员","铁路行李搬运工人","铁路车站清洁工人","铁路随车人员(技术人员除外)","铁路机车驾驶员","铁路机车燃料填充员","铁路机工","铁路电工","铁路修护厂厂长","铁路修护厂内勤","铁路修护厂工程师","铁路修护厂技工","铁路修路工","铁路维护员","铁路平交道看守人员","铁路货运领班","铁路货运、搬运工人","铁路通信工","铁路押运员"]);
dsy.add("0_5_2",["船长","轮机长","高级船员","大副","二副","三副","大管轮","二管轮","三管轮","报务员","事务长","医务人员","水手长","水手","铜匠","木匠","泵匠","电机师","厨师","服务生","实习生","游览船之驾驶及工作人员","小汽艇之驾驶及工作人员","码头工人及领班","堆高机操作员","仓库管理人、理货员","领航员","引水员","关务人员","稽查人员","缉私人员","拖船驾驶员及工作人员","渡船驾驶员及工作人员","救难船员","船长","轮机长","高级船员","大副","二副","三副","大管轮","二管轮","三管轮","报务员","事务长","医务人员","水手长","水手","铜匠","木匠","泵匠","电机师","厨师","服务生","实习生","船长","轮机长","高级船员","大副","二副","三副","大管轮","二管轮","三管轮","报务员","事务长","医务人员","水手长","水手","铜匠","木匠","泵匠","电机师","厨师","服务生","实习生"]);
dsy.add("0_5_3",["站长","播音员","服务台人员","一般内勤人员","塔台工作人员","关务人员","检查人员","运务人员","缉私人员","站内清洁工人(航空大厦内)","机场内交通车司机","行李货运搬运工人","加添燃料人员","飞机洗刷人员","清洁工(站外、航空大厦外)","跑道维护工","机械员","飞机修护人员","办事处人员","票务人员","机场柜台工作人员","清仓工","航空一般内勤人员","外务员","报关人员","理货员","民航机飞行人员","机上服务员","直升机飞行人员"]);
dsy.add("0_6",["旅游业","旅馆业","餐饮业"]); 
dsy.add("0_6_0",["一般内勤人员","外务员","导游、领队"]);
dsy.add("0_6_1",["负责人","一般内勤工作人员","外务员","收帐员","技工"]);
dsy.add("0_6_2",["经理人员","一般内勤服务人员","柜台人员","收帐员","采购人员","厨师","服务人员"]);
dsy.add("0_7",["建筑公司","铁路公路铺设","造修船业","电梯、升降机","装璜业","安装及其他","测绘工程"]); 
dsy.add("0_7_0",["建筑设计人员","制图员","测量员","工程监理","监工","建筑公司负责人、业务员","引导参观工地服务人员","模板工","木工","泥水工","油漆工、喷漆工","水电工","钢骨结构工人","鹰架架设工人、铁工","焊工","建筑工程车辆驾驶员","建筑工程机械操作员","承包商（土木建筑）","磨石工人","洗石工人","石棉瓦或浪板安装人员","金属门窗装修工人","排水工程人员","防水工程人员","拆屋、迁屋工人","凿岩工","砌筑、砌砖工","混凝土工","混凝土制品模具工","混凝土搅拌机械操作工","装饰装修工（室内）（基础装修至毛坯）","装饰装修工（室外）（基础装修至毛坯）","室内成套设施装饰工","古建筑结构施工工","古建筑装饰工","房屋维修工人（室内）","房屋维修工人（室外）","轻钢彩板安装和维修人员"]);
dsy.add("0_7_1",["工程设计人员","现场勘测人员（山区）","现场勘测人员（非山区）","监工","工程机械操作员","工程车辆驾驶员","铺设工人(山地)","铺设工人(平地)","维护工人","电线架设及维护工人","管道铺设及维护工人","高速公路工程人员(含美化人员)","筑路、养护工","铁道线路工","铁路舟桥工","道岔制修工","枕木处理工","铁路平交道看守人员","铁路修护厂技工"]);
dsy.add("0_7_2",["设计人员","监工","工人","拆船工人"]);
dsy.add("0_7_3",["安装工人","操作员(不含矿场使用者)","电梯、升降机修理及维护工人"]);
dsy.add("0_7_4",["设计制图人员","地毯之装设人员","非住宅室内装璜人员(不含木工、油漆工)","住宅室内装璜人员","室外装璜人员","承包商、监工","PVC材质制造、装修工人","金属门窗制造、装修工人","木工、油漆工"]);
dsy.add("0_7_5",["安装玻璃幕墙工人","钢结构安装工","机械设备安装工","电气设备安装工","中央空调系统安装及维护人员","管工","防火系统、警报器安装人员","地质探测员（山区）","地质探测员（海上）","工地看守员(平地)","海湾港口工程人员","水坝工程人员、挖井工程人员","桥梁工程人员","隧道工程人员","潜水工作人员","爆破工作人员","挖泥船工人","中小型施工机械操作工"]);
dsy.add("0_7_6",["大地、工程测量工程技术人员","摄影测量与遥感工程技术人员","地图制图与印刷工程技术人员","海洋测绘工程技术人员（非海上作业）","海洋测绘工程技术人员（海上作业）","地质勘探工程技术人员"]);
dsy.add("0_8",["冶金业","机械制造维修业","电子器械产品业","电机业","水泥业(包括水泥、石膏、石灰、陶器)","化工业","火药、炸药、烟花业","机动车、自行车制造业与修理业","纺织印染及成衣业","造纸工业","家具制造业","工艺品生产加工业","电线电缆光缆制造业","食品饮料烟草加工业","家电制造维修业","玻璃制造业"]); 
dsy.add("0_8_0",["工程师","领班、监工","高炉原料工","高炉炉前工","高炉运转工","炼钢原料工","炼钢工","炼钢浇铸工","练钢准备工","铁合金原料工","铁合金电炉冶炼工","重冶备料工","焙烧工","火法冶炼工","电解精炼工","烟气制酸工","铝电解工","多晶制取工","轧制原料工","酸洗工","金属材料涂层工","金属材热处理工","焊管工","金属挤压工","铸轧工","铸管备品工","铸管工","碳素石墨加工工","硬质合金成型工","硬质合金精加工工","冶炼风机工","有色金属冶炼工"]);
dsy.add("0_8_1",["企业负责人","领班、监工","技工","车床工","车床工(全自动)","车工","铣工","磨工","镗工","钻床工","组合机床工","加工中心操作工","拉床、锯床工","弹性元件制造工","铸造、锻造工","冲压工","剪切工","焊工","金属热处理工","粉末冶金处理工","冷作钣金工","涂装工","电切削工","电镀工","磨具制造工","仪器仪表元件制造工","装配工,品管人员","基础件、部件装配工","装配钳工","汽轮机、内燃机装配工","锅炉设备装配工","电机装配工","有关高压电之工作人员","铁心叠装工","变压器,互感器装配工","高低压电器装配工","电焊机装配工","电炉装配工"]);
dsy.add("0_8_2",["工程师","领班、监工","修理工","制造工","仪器仪表装调工","包装工人"]);
dsy.add("0_8_3",["工程师","领班、监工","有关高压电之工作人员","装配修理工、冷冻修理厂工人"]);
dsy.add("0_8_4",["工程师","领班","水泥生产制造工","采掘工","爆破工","石灰焙烧工","陶瓷、木炭、砖块制造工","砖、瓦生产工","加气混凝土制品工","保温、吸音材料制造工","装饰石材生产工","石棉制品工","金刚石制品工","人工合成制品工","耐火制品制造工","古建琉璃工","搪瓷胚体制做工"]);
dsy.add("0_8_5",["工程师","技术人员","领班、监工","电池制造(技师)","电池制造(工人)","液化气体制造工","化工原料准备工","压缩机工","气体净化工","过滤工","油加热工","制冷工","蒸发工","蒸馏工","萃取工","吸收工","吸附工","干燥工","结晶工","造粒工","防腐蚀工","化工工艺试验工","化工总控工","燃料油生产工","润滑油,脂生产工","石油产品精制工","油制气工","备煤筛焦工","焦炉调温工","炼焦工,焦炉机车司机","煤制气工","煤气储运工","合成氨生产工","尿素生产工","硝酸铵生产工","碳酸氢铵生产工","硫酸铵生产工","过磷酸铵生产工","复合磷肥生产工","钙镁磷肥生产工","氯化钾生产工","微量元素混肥生产工","硫酸生产工","硝酸生产工","盐酸生产工","磷酸生产工","纯碱生产工","烧碱生产工","氟化盐生产工","缩聚磷酸盐生产工","无机化学生产工","高频等离子工","气体深冷分离工,制氧工","工业气体液化工","炭黑制造工","二氧化硫制造工","脂肪烃生产工","环烃生产工","烃类衍生物生产工","聚乙烯生产工","聚丙烯生产工","聚苯乙烯生产工","聚丁二烯生产工","聚氯乙烯生产工","酚醛树脂生产工","环氧树脂生产工","丙烯睛-丁二烯-苯乙烯共聚物生产工","橡胶生产工","橡胶半成品制造者","橡胶成型工","橡胶硫化工","废胶再生工","塑料制品配料工","塑料制品成型工（自动）","塑料制品成型人员（其他）","化纤聚合工","湿纺远液制造工","纺丝工","化纤后处理工","纺丝凝固浴液配制工","无纺布制造工","化纤纺丝精密组件工","合成革制造工","有机合成工","农药生物测试试验工","染料标准工","染料应用试验工","染料拼混工","研磨分散工","催化剂制造工","催化剂试验工","涂料合成树脂工","制漆配色调制工","溶剂制造工","化学试剂制造工","化工添加剂制造工","片基制造工","感光材料制造工","感光材料试验工","暗盒制造工","废片,白银回收工","磁粉制造工","磁记录材料制造工","磁记录材料试验工","感光鼓涂敷工","其他有毒物品生产工"]);
dsy.add("0_8_6",["火药炸药业人员","烟花爆竹业人员"]);
dsy.add("0_8_7",["工程师","修理保养工人(汽车、摩托车)","领班、监工","试车人员","汽车(拖拉机)装配工","铁路车辆制造装修工","电机车装配工","摩托车装配工","助动车、自行车装配修理工"]);
dsy.add("0_8_8",["工程师","设计师","纤维验配工","开清棉工","纤维染色工","加湿软麻工","选剥煮茧工","纤维梳理工","并条工","粗纱工","绢纺精炼工","细纱工","简并摇工","捻线工","制线工","缫丝工","整经工","浆纱工","穿经工","织布工","织物验修工","意匠纹版工","织造工人","纬编工","经编工","横机工","织袜工","铸、钳针工","坯布检查修理工","印染烧毛工","煮炼漂工","印染洗涤工","印染烘干工","印染丝光工","印染定型工","纺织针织染色工","印花工","印染雕刻制版工","印染后处理工","印染成品定等装潢工","印染染化料配制工","工艺染织制作工","染整工人","裁剪工","缝纫工","缝纫品整型工","裁缝","剧装工","制鞋工","制帽工","皮革加工工","毛皮加工工","缝纫制品充填处理工","胶制服装上胶工","服装水洗工","纺织纤维检验工","针纺织品检验工","印染工艺检验工","服装鞋帽检验工"]);
dsy.add("0_8_9",["领班、监工","制浆备料工","制浆设备操作工","制浆废液回收利用工","造纸工","纸张整饰工","宣纸书画纸制作工","纸箱制作工","纸盒制作工"]);
dsy.add("0_8_10",["技术人员","领班、监工","木制家具制造工人","木制家具修理工人","金属家具制造工人","金属家具修理工人","木材及家具检验工"]);
dsy.add("0_8_11",["竹木制手工艺品加工工人","竹木制手工艺品雕刻工人","金属手工艺品加工工人","金属手工艺品雕刻工人","布类纸品工艺品加工工人","矿石手工艺品加工人员","地毯制作工","金属、塑料、木制玩具装配工","布绒玩具制作工","搪塑玩具制作工","漆器制胎工","彩绘雕填制作工","漆器镶嵌工","机绣工","手绣制作工","抽纱调编工","景泰蓝制作工","金属摆件工","装饰美工","雕塑翻制工","壁画制作工","油画外框制作工","装裱工","版画制作工","民间工艺品制作工","人造花制作工","工艺画制作工","墨制作工","墨水制作工","墨汁制作工","绘图仪器制作工","静电复印机消耗材制造工","笔类制作工","印泥制作工","制球工","球拍、球网制作工","健身器材制作工","乐器制作工"]);
dsy.add("0_8_12",["技术人员","工人"]);
dsy.add("0_8_13",["领班、监工","制米工","制粉工","制油工","食糖制造工","糖果、巧克力制造工","乳品预处理工","乳品加工工","速冻食品制作工","食品罐头加工工","饮料制作工","酒类酿造工","酶制剂制造工","酱油酱醋类制作工","酱腌菜制作工","食用调料制作工","味精制作工","糕点、面包烘焙工","糕点装饰工","米面主食制作工","油脂制品工","植物蛋白制作工","豆制品制作工","猪屠宰加工工","牛羊屠宰加工工","肠衣工","禽类屠宰加工工","熟肉制品加工工","蛋品及再制蛋品加工工","饲料原料清理上料工","饲料粉碎工","饲料配料混合工","饲料添加剂预混工","冰块制造工人","装罐工人","烟叶调制工","烟叶制丝工","烟叶分级工","挂杆复烤工","打烟复烤工","烟叶回潮工","烟叶发酵工","烟用二醋片制造工","烟用丝束制造工","滤棒工"]);
dsy.add("0_8_14",["工程师","空调机装配工","电冰箱、电冰柜制造装配工","洗衣机制造装配工","小型家用电器装配工","缝纫机制造装配工","家用空调安装与维修","其他家用电器安装与维修","电极丝制造工","真空电子器件金属零件制造工","液晶显示器件制造工","半导体芯片制造工","电阻器制造者","电容器制造者","微波铁氧体元器件制造工","石英晶体元器件加工制造工","电声器件制造工","专用继电器制造工","高频电感器件制造者","磁头制造者","印制电路制作工","薄膜加热制造工","真空电子器件装配工","真空电子器件装调工","电子设备装接工","电子真空镀膜工","石英晶体生长设备操作工","焊接工","电子产品制版工","半导体分立器件、集成电路装调工","包装工","冲床工","剪床工","铣床工","铸造工","车床工(全自动)","车床工(其他)"]);
dsy.add("0_8_15",["监工","玻璃配料工","玻璃溶化工","玻璃制板及玻璃成型工","玻璃加工工","玻璃制品装饰加工工","玻璃纤维制品工","玻璃钢制品工","石英玻璃制品加工工","玻璃搬运工"]);
dsy.add("0_9",["出版印刷业","广告业"]);
dsy.add("0_9_0",["内勤人员","外勤记者","文字记者","摄影记者","战地记者","编辑","校对员","翻译","推销员","排版工","装订工","印刷工","送货员","送报员"]);
dsy.add("0_9_1",["撰稿员、一般内勤","广告设计人员","广告业务员","广告招牌绘制人员（地面）","广告片拍摄录制人员","广告招牌架设人员","霓虹光管安装及维修人员"]);
dsy.add("0_10",["医疗卫生","制药制剂业"]);
dsy.add("0_10_0",["医务行政人员","一般医师","精神科医师、看护、护士","急诊科医师","乡村医师","一般护士","急诊护士","手术室护士","监狱、看守所医生护理人员","护理员","药剂检验人员","放射线之技术人员","放射线之修理人员","配膳员","卫生检查员","医用气体工","卫生防疫、妇幼保健员","医院炊事","医院勤杂工、清洁工"]);
dsy.add("0_10_1",["化学合成制药工","生化药品制造工","发酵工程制药工","疫苗制品工","血液制品工","基因工程产品工","药物制剂工","淀粉、葡萄糖制造工","医药代表"]);
dsy.add("0_11",["广播电影电视业","高尔夫球馆","保龄球馆","撞球馆","游泳池","海水浴场","其他游乐园","艺术及演艺人员","特种营业"]);
dsy.add("0_11_0",["制片人","影片商","编剧","一般演员、导演","武打演员","特技演员","武术指导","配音演员","播音员","节目主持人","道具师","剪辑师","美工师","化妆师","场记","跑片员","摄影工作人员","灯光及音响效果工作人员","冲片工作人员","洗片工作人员","影视舞台烟火特效员","电视记者","机械工、电工","影视设备机械员","广播电视天线工","有线广播电视机线员","广播电视编播工程技术人员","广播电视传输覆盖工程技术人员","电影工程技术人员","布景搭设人员","电影院售票员","电影院放映人员、服务人员"]);
dsy.add("0_11_1",["教练","球场保养人员","维护工人","球童","职业高尔夫球员"]);
dsy.add("0_11_2",["记分员","柜台人员","机械保护员","清洁工人"]);
dsy.add("0_11_3",["负责人","记分员"]);
dsy.add("0_11_4",["负责人","管理员","教练","售票员","救生员"]);
dsy.add("0_11_5",["负责人","管理员","售票员","救生员"]);
dsy.add("0_11_6",["负责人","售票员","电动玩具操作员","一般清洁工","兽栏清洁工","水电机械工","动物园驯兽师","饲养人员","兽医(动物园)"]);
dsy.add("0_11_7",["作曲人员","编曲人员","演奏人员","音乐指挥","绘画人员","舞蹈演艺人员、歌星","服装模特","戏曲演员","皮影戏演员","木偶戏演员","杂技魔术演员","舞台监督","雕塑人员","戏剧演员","高空杂技、飞车、飞人演员"]);
dsy.add("0_12",["教育机构","考古、文物保护及其他"]);
dsy.add("0_12_0",["教育、教学单位行政人员","教师","学生","校工","军训教官、体育教师","汽车驾驶训练班教练及学员","教育、教学单位司机","体校、技校教师、学生","戏曲舞蹈教师、学生","杂技教师、学生","飞行训练教官及学员","警校、军校学生"]);
dsy.add("0_12_1",["负责人(出版商、书店、文具店)","店员","外务员","送货员","图书馆工作人员","博物馆工作人员","考古工作者","文物鉴定和保管人员","文物保护专业人员","考古发掘工","文物修复工","文物拓印工","古旧书画修复工"]);
dsy.add("0_13",["宗教人士"]);
dsy.add("0_13_0",["寺庙及教堂管理人员","宗教团体工作人员","僧尼、道士、传教人员","乩童"]);
dsy.add("0_14",["邮政与通信","电力","自来水(水利)","燃气"]);
dsy.add("0_14_0",["内勤人员","投递员","邮政设备安装、维护人员","包裹邮物人员","包裹搬运人员","通信设备维护人员","通信系统供电设备、空调设备安装维护人员","电信装置维护修理人员","电信工程设施架设人员","电台天线维护人员","光缆铺设人员"]);
dsy.add("0_14_1",["内勤人员","抄表员、收费员","电力拖动与自动控制工程技术人员","电线电缆与电工材料工程技术人员","发电工程技术人员","输变电工程技术人员","供用电工程技术人员","发电厂电动机检修工","水轮机检修工","水电站水利机械试验工","水电自动装置检修工","高压线路带电检修工","变压器检修工","变电设备检修工","电气试验员","继电保护员","电力装置维护修理工","电力负荷控制员","用电监察员","装表核算收费员","装表接电工","电能计量装置检修工","变点设备安装工","变配电室值班电工","常用电机检修工","牵引电力线路安装维护工","维修电工","电力设施架设人员","电力高压电工程设施人员"]);
dsy.add("0_14_2",["工程师","水坝、水库管理人员","水利工程设施人员","自来水管装修人员","抄表员,收费员","自来水厂水质分析员(实地)","水泵或提水站的管理人员","水泵或提水站的维修人员","河道清淤的工人"]);
dsy.add("0_14_3",["工程师","管线装修工","收费员、抄表员","检查员","燃气器具制造工","燃气储气槽,分装厂之工作人员"]);
dsy.add("0_15",["买卖"]);
dsy.add("0_15_0",["厨具商","陶瓷器商","古董商","花卉商","米商","杂货商","玻璃商（不含搬运和加工）","果菜商","石材商（不含搬运和加工）","建材商（不含搬运和加工）","铁材商（不含搬运和加工）","木材商（不含搬运和加工）","五金商","电器商","水电卫生器材商","家具商（不含搬运和加工）","自行车买卖商","机车买卖商(不含修理)","汽车买卖商(不含修理)","车辆器材商(不含矿物油)","矿物油、香烛买卖商","眼镜商","食品商","文具商","布商","服饰买卖商","农具商","售货商","碾米商","鱼贩","肉贩","屠宰","屠牛 ","药品买卖商","医疗器械仪器商","化学原料商、农药买卖商","手工艺品买卖商","银楼珠宝、当铺负责人及工作人员","负责人","店员","送货员","装饰工","负责人及工作人员","送货员","液化燃气分装工"]);
dsy.add("0_16",["金融、保险、证券业","自由业","其他"]);
dsy.add("0_16_0",["金融一般内勤人员","金融外务员","保险收费员","保险调查员","征信人员","现金运送车司机、点钞员、押送员"]);
dsy.add("0_16_1",["律师","会计师","代书(内勤)","经纪人(内勤)","鉴定估价师","速录师","土地房屋买卖介绍人"]);
dsy.add("0_16_2",["公证员","报关行外务员","理发师","美容师","保健按摩师","修脚师","钟表匠","鞋匠、伞匠","洗衣店工人","勘查师","警卫人员(工厂、公司行号、大楼)","大楼管理员","摄影师","道路清洁工,垃圾车司机及随车工人","下水道清洁工","清洁打腊工人","高楼外部清洁工、烟囱清洁工","金属、化工废物回收人员","一般废旧物资回收人员","收费站、停车场收费人员","加油站工作人员","地磅场工作人员","洗车工人"]);
dsy.add("0_17",["家政管理"]);
dsy.add("0_17_0",["家政服务员（小时工）","保姆（全日制）"]);
dsy.add("0_18",["法院、检察院工作人员","公安警务工作人员","其他执法、治安及特种工作人员"]);
dsy.add("0_18_0",["人民法院负责人","人民检察院负责人","法官","检查官","法医","法警","书记员","商业犯罪调查处理人员"]);
dsy.add("0_18_1",["警务行政及内勤人员","警察（负有巡逻任务者）","监狱看守所管理人员","交通警察","刑警","警务特警","防暴警察、武警"]);
dsy.add("0_18_2",["港口机场警卫及安全人员","工商、税务、海关、城管等特定国家行政执法人员","缉私人员","违禁品检查员","治安调查人员","保安人员（办公楼、物业）","保安人员（工厂、银行）","防毒防化防核抢险员","一般事故抢险员","抢险救援器材工具调配工","火险监督员、防火审核员","可燃气体（毒气）检测员、危险物品监督员","消防队队员","火灾嘹望观察员（嘹望塔）","火灾嘹望观察员（直升机）"]);
dsy.add("0_19",["现役军人"]);
dsy.add("0_19_0",["一般军人(空中、海中服役者拒保)","特种兵(伞兵、水中爆破兵、化学兵、负有布雷爆破任务之工兵)","行政及内勤人员","宪兵"]);
dsy.add("0_20",["IT业（软、硬件开发制作）"]);
dsy.add("0_20_0",["维护工程师","系统工程师（软、硬件）","销售人员"]);
dsy.add("0_21",["高尔夫球","保龄球","桌球","乒乓球、羽毛球","游泳","射箭","网球","垒球","溜冰","射击","民族体育活动(不含竞技性)","举重","篮球","排球","棒球","田径","体操","滑雪","帆船","划船","泛舟","巧固球","手球","风浪板","水上摩托车","足球","曲棍球","冰上曲棍球","橄榄球","摔跤","击剑","拳击"]);
dsy.add("0_21_0",["教练","高尔夫球球员","球童"]);
dsy.add("0_21_1",["教练","保龄球球员"]);
dsy.add("0_21_2",["教练","桌球球员"]);
dsy.add("0_21_3",["教练","球员"]);
dsy.add("0_21_4",["教练","游泳人员"]);
dsy.add("0_21_5",["教练","射箭人员"]);
dsy.add("0_21_6",["教练","网球球员"]);
dsy.add("0_21_7",["教练","垒球球员"]);
dsy.add("0_21_8",["教练","溜冰人员"]);
dsy.add("0_21_9",["教练","射击人员"]);
dsy.add("0_21_10",["教练","民族体育活动人员"]);
dsy.add("0_21_11",["教练","举重人员"]);
dsy.add("0_21_12",["教练","篮球球员"]);
dsy.add("0_21_13",["教练","排球球员"]);
dsy.add("0_21_14",["教练","棒球球员"]);
dsy.add("0_21_15",["教练","参赛人员"]);
dsy.add("0_21_16",["教练","体操人员"]);
dsy.add("0_21_17",["教练","滑雪人员"]);
dsy.add("0_21_18",["教练","驾乘人员"]);
dsy.add("0_21_19",["教练","驾乘人员"]);
dsy.add("0_21_20",["教练","驾乘人员"]);
dsy.add("0_21_21",["教练","巧固球球员"]);
dsy.add("0_21_22",["教练","手球球员"]);
dsy.add("0_21_23",["教练","驾乘人员"]);
dsy.add("0_21_24",["教练","驾乘人员"]);
dsy.add("0_21_25",["教练","足球球员"]);
dsy.add("0_21_26",["教练","曲棍球球员"]);
dsy.add("0_21_27",["教练","冰上曲棍球球员"]);
dsy.add("0_21_28",["教练","橄榄球球员"]);
dsy.add("0_21_29",["教练","摔跤运动员"]);
dsy.add("0_21_30",["教练","击剑运动员"]);
dsy.add("0_21_31",["教练","职业拳击运动员","业余拳击运动员"]);
dsy.add("0_22",["无业人员"]);
dsy.add("0_22_0",["退休人员","无业人员"]);
</SCRIPT> 
<SCRIPT LANGUAGE = JavaScript>


var s=["s1","s2","s3"]; 
var opt0 = ["请选择","请选择","请选择"]; 
function setup() 
{ 
for(i=0;i<s.length-1;i++) 
document.getElementById(s[i]).onchange=new Function("change("+(i+1)+")"); 
change(0); 
} 

</SCRIPT>



<script type="text/javascript">
$().ready( function() {

	var productTotalPrice = ${productTotalPrice};// 产品总价格
	var deliveryFee = 0;// 配送费用
	var paymentFee = 0;// 支付费用
	
	var $orderInfoForm = $("#orderInfoForm");
	var $areaSelect = $(".areaSelect");
	var $occupationSelect = $(".occupationSelect");
	var $receiverInput = $("input[name='receiver.id']");
	var $otherReceiverTable = $("#otherReceiverTable");
	var $otherReceiverTableInput = $(".otherReceiverTable :input");
	var $deliveryTypeInput = $("input[name='deliveryType.id']");
	var $paymentConfigTr = $(".paymentConfigTr");
	var $paymentConfigInput = $("input[name='paymentConfig.id']");
	var $productTotalPrice = $("#productTotalPrice");
	var $deliveryFee = $("#deliveryFee");
	var $paymentFee = $("#paymentFee");
	var $orderAmount = $("#orderAmount");
	// 地区选择菜单
	$areaSelect.lSelect({
		url: "${base}/shop/area!ajaxChildrenArea.action"// Json数据获取url
	});
	
	// 职业朱勇选择菜单
//	$occupationSelect.lSelect({
//		url: "${base}/shop/occupation!ajaxChildrenOcc.action"// Json数据获取url
//	});
	
	// 如果默认选择“其它收货地址”，则显示“其它收货地址输入框”
	if ($receiverInput.val() == "") {
		$otherReceiverTable.fadeIn();
		$otherReceiverTableInput.removeClass("ignoreValidate");
	} else {
		$otherReceiverTableInput.addClass("ignoreValidate");
	}
	
	// 显示“其它收货地址输入框”
	$receiverInput.click( function() {
		$this = $(this);
		if ($this.val() == "") {
			$otherReceiverTable.fadeIn();
			$otherReceiverTableInput.removeClass("ignoreValidate");
		} else {
			$otherReceiverTable.fadeOut();
			$otherReceiverTableInput.addClass("ignoreValidate");
		}
	});
	
	// 根据配送方式修改配送费用、订单总金额，并显示/隐藏支付方式
	$deliveryTypeInput.click( function() {
		var $this = $(this);
		var $parent = $this.parent();
		$paymentConfigInput.attr("checked", false);
		paymentFee = 0;
		var deliveryMethod = $parent.metadata().deliveryMethod;
		if (deliveryMethod == "deliveryAgainstPayment") {
			$paymentConfigInput.removeClass("ignoreValidate");
			$paymentConfigTr.show();
		} else {
			$paymentConfigInput.addClass("ignoreValidate");
			$paymentConfigTr.hide();
		}
		deliveryFee = $parent.metadata().deliveryFee;
		$deliveryFee.text(orderUnitCurrencyFormat(deliveryFee));
		$paymentFee.text(orderUnitCurrencyFormat(paymentFee));
		$orderAmount.text(orderUnitCurrencyFormat(floatAdd(floatAdd(productTotalPrice, deliveryFee), paymentFee)));
	});
	
	// 根据支付方式修改订单总金额
	$paymentConfigInput.click( function() {
		var $this = $(this);
		var $parent = $this.parent();
		var paymentFeeTypeChecked = $parent.metadata().paymentFeeType;
		var paymentFeeChecked = $parent.metadata().paymentFee;
		if (paymentFeeTypeChecked == "scale") {
			paymentFee = floatMul(floatAdd(productTotalPrice, deliveryFee), floatDiv(paymentFeeChecked, 100));
		} else {
			paymentFee = paymentFeeChecked;
		}
		$paymentFee.text(orderUnitCurrencyFormat(paymentFee));
		$orderAmount.text(orderUnitCurrencyFormat(floatAdd(productTotalPrice, floatAdd(deliveryFee, paymentFee))));
	});
	
	// 表单验证
	$orderInfoForm.validate({
		ignore: ".ignoreValidate",
		invalidHandler: function(form, validator) {
			$.each(validator.invalid, function(key, value){
				$.tip(value);
				return false;
			});
		},
		errorPlacement:function(error, element) {},
		submitHandler: function(form) {
			$orderInfoForm.find(":submit").attr("disabled", true);
			form.submit();
		}
	});
	
});

//身份证号码验证开始
//alert(isIdCardNo("370784198810174818"));
//--身份证号码验证-支持新的带x身份证
function isIdCardNo(num) 
{
    var factorArr = new Array(7,9,10,5,8,4,2,1,6,3,7,9,10,5,8,4,2,1);
    var error;
    var varArray = new Array();
    var intValue;
    var lngProduct = 0;
    var intCheckDigit;
    var intStrLen = num.length;
    var idNumber = num;    
    // initialize
    if ((intStrLen != 15) && (intStrLen != 18)) {
        error = "输入身份证号码长度不对！";
        //alert(error);
        //frmAddUser.txtIDCard.focus();
        return error;
    }    
    // check and set value
    for(i=0;i<intStrLen;i++) {
        varArray[i] = idNumber.charAt(i);
        if ((varArray[i] < '0' || varArray[i] > '9') && (i != 17)) {
            error = "错误的身份证号码！.";
            //alert(error);
            //frmAddUser.txtIDCard.focus();
            return error;
        } else if (i < 17) {
            varArray[i] = varArray[i]*factorArr[i];
        }
    }
    if (intStrLen == 18) {
        //check date
        var date8 = idNumber.substring(6,14);
        if (checkDate(date8) == false) {
            error = "身份证中日期信息不正确！.";
         //  alert(error);
           return "error";
        }        
        // calculate the sum of the products
        for(i=0;i<17;i++) {
            lngProduct = lngProduct + varArray[i];
        }        
        // calculate the check digit
        intCheckDigit = 12 - lngProduct % 11;
        switch (intCheckDigit) {
            case 10:
                intCheckDigit = 'X';
                break;
            case 11:
                intCheckDigit = 0;
                break;
            case 12:
                intCheckDigit = 1;
                break;
        }        
        // check last digit
        if (varArray[17].toUpperCase() != intCheckDigit) {
            //error = "身份证效验位错误!...正确为： " + intCheckDigit + ".";
            //alert(error);
            return "身份证效验位错误!";
        }
    } 
    else{        //length is 15
        //check date
        var date6 = idNumber.substring(6,12);
        if (checkDate(date6) == false) {
            //alert("身份证日期信息有误！.");
            return "身份证日期信息有误！";
        }
    }
    //alert ("Correct.");
    return "身份证号码正确";
}

function checkDate(date)
{
    return true;
}

//身份证号码验证结束


</script>
<script type="text/javascript">
$().ready( function() {

	var $listTable = $(".listTable");
	var $quantity = $("input.quantity");
	var $changeQuantityTip = $("#changeQuantityTip");
	var $changeQuantityTipClose = $("#changeQuantityTipClose");
	var $changeQuantityTipTopMessage = $("#changeQuantityTipTopMessage");
	var $changeQuantityTipBottomMessage = $("#changeQuantityTipBottomMessage");
	var $deleteCartItem = $(".deleteCartItem");
	var $clearCartItem = $("#clearCartItem");
	var $totalQuantity = $("#totalQuantity");
	var $totalPoint = $("#totalPoint");
	var $totalPrice = $("#totalPrice");
	var $orderInfoButton = $("#orderInfoButton");
	
	
	// 记录初始商品购买数
	$quantity.each(function(){
		$this = $(this);
		$this.data("previousQuantity", $this.val());
	});
	
	// 修改商品数量
	$quantity.change( function() {
		$this = $(this);
		var id = $this.metadata().id;
		var quantity = $this.val();
		
		var x = $this.offset().left - 63;
		var y = $this.offset().top + 25;
		$changeQuantityTip.css({"left" :x, "top" :y});
		
		var reg = /^[0-9]*[1-9][0-9]*$/;
		if (!reg.test(quantity)) {
			var previousQuantity = $this.data("previousQuantity");
			$this.val(previousQuantity);
			$changeQuantityTipTopMessage.text("产品数量修改失败！");
			$changeQuantityTipBottomMessage.text("产品数量必须为正整数！");
			$changeQuantityTip.fadeIn();
			setTimeout(function() {$changeQuantityTip.fadeOut()}, 3000);
			return false;
		}
		$.ajax({
			url: "cart_item!ajaxEdit.action",
			data: {"id": id, "quantity": quantity},
			dataType: "json",
			async: false,
			beforeSend: function() {
				$quantity.attr("disabled", true);
			},
			success: function(data) {
				if (data.status == "success") {
					$this.data("previousQuantity", quantity);
					$this.parent().parent().find(".subtotalPrice").text(data.subtotalPrice);
					$this.next(".storeInfo").empty();
					$.flushCartItemList();
					$totalQuantity.text(data.totalQuantity);
					$totalPoint.text(data.totalPoint);
					$totalPrice.text(data.totalPrice);
					$changeQuantityTipTopMessage.text("产品数量修改成功！");
					$changeQuantityTipBottomMessage.text("产品总金额：" + data.totalPrice);
					$changeQuantityTip.fadeIn();
					setTimeout(function() {$changeQuantityTip.fadeOut()}, 3000);
				} else {
					var previousQuantity = $this.data("previousQuantity");
					$this.val(previousQuantity);
					$changeQuantityTipTopMessage.text("产品数量修改失败！");
					$changeQuantityTipBottomMessage.text(data.message);
					$changeQuantityTip.fadeIn();
					setTimeout(function() {$changeQuantityTip.fadeOut()}, 3000);
				}
				$quantity.attr("disabled", false);
			}
		});
	});
	
	// 修改商品数量提示框隐藏
	$changeQuantityTipClose.click( function() {
		$changeQuantityTip.fadeOut();
		return false;
	});
	
	// 删除购物车项
	$deleteCartItem.click( function() {
		$this = $(this);
		var id = $this.metadata().id;
		if (confirm("您确定要移除此产品吗？") == true) {
			$.ajax({
				url: "cart_item!ajaxDelete.action",
				data: {"id": id},
				dataType: "json",
				async: false,
				success: function(data) {
					if (data.status == "success") {
						$.flushCartItemList();
						$this.parent().parent().remove();
						$totalQuantity.text(data.totalQuantity);
						$totalPoint.text(data.totalPoint);
						$totalPrice.text(data.totalPrice);
					}
					$.tip(data.status, data.message);
				}
			});
		}
	});
	
	// 清空购物车项
	$clearCartItem.click( function() {
		if (confirm("您确定要清空购物车吗？") == true) {
			$.ajax({
				url: "cart_item!ajaxClear.action",
				dataType: "json",
				async: false,
				success: function(data) {
					if (data.status == "success") {
						$.flushCartItemList();
						$(".listTable tr:gt(0)").remove();
						$listTable.append('<tr><td class="noRecord" colspan="5">购物车目前没有加入任何产品！</td></tr>');
						$orderInfoButton.remove();
						$clearCartItem.remove();
					}
					$.tip(data.status, data.message);
				}
			});
		}
	});
	
	// 结算前检测购物车状态
	$orderInfoButton.click( function() {
		var $this = $(this);
		if (parseInt($totalQuantity.text()) < 1) {
			$.message("error", "购物车目前没有加入任何产品！");
			return false;
		}
		if ($.cookie("loginMemberUsername") == null) {
			$.cookie("redirectionUrl", $(this).attr("href"), {path: "/"});
			$("#loginWindow").jqmShow();
			return false; 
		}
	});

});
</script>
</head>
<body class="orderInfo">
<!---=========增加填写保单信息的功能======------>
	<div id="addCartItemTip" class="addCartItemTip">
		<div class="top">
			<div class="tipClose addCartItemTipClose"></div>
		</div>
		<div class="middle">
			<p>
				<span id="addCartItemTipMessageIcon"> </span>
				<span id="addCartItemTipMessage"></span>
			</p>
			<p id="addCartItemTipInfo" class="red"></p>
			<input type="button" class="formButton tipClose" value="继续购物" hidefocus="true" />
			<input type="button" class="formButton" onclick="location.href='${base}/shop/order!info.action'" value="进入购物车" hidefocus="true" />
		</div>
		<div class="bottom"></div>
	</div>
<!---=========增加填写保单信息的功能======------>
	<#include "/WEB-INF/template/shop/header.ftl">
	<div class="body">
		<div class="blank"></div>
		<div class="orderInfoDetail">
			<form id="orderInfoForm" action="order!save.action" method="post">
				<@s.token />
				<table class="orderInfoTable">
<!--投保人信息  开始1-->
				<!--
					<tr>
						<th>电子保单邮寄信息</th>
						<td>
							<ul>
								<#list loginMember.receiverSet as list>
									<li>
										<label>
											<input type="radio" name="receiver.id" class="{required: true, messages: {required: '请选择地址！'}}" value="${list.id}"<#if list.isDefault> checked</#if> />
											<strong>投保人姓名：</strong>${list.name}    
											<#if (list.mobile != null)!>
												<strong>手机：</strong>${list.mobile}    
											<#else>
												<strong>电话：</strong>${list.phone}    
											</#if>
											<strong>地址：</strong>${list.address}
										</label>
									</li>
								</#list>
									<div class="blank"></div>
									<table id="otherReceiverTable" class="otherReceiverTable">
												<input type="hidden" name="receiver.name" value="sinosoft" />
												<input type="hidden" name="receiver.address" value="sinosoft" />
												<input type="hidden" name="receiver.phone" value="sinosoft" />
												<input type="hidden" name="receiver.mobile" value="sinosoft" />
												<input type="hidden" name="receiver.zipCode" value="sinosoft" />
									</table>
								</li>
							</ul>
					</td>
					</tr>
				-->
<!--投保人信息 结束1-->			
								<#list allDeliveryType as list>
												<input type="hidden" name="deliveryType.id" value="${list.id}" />
								</#list>
								
								<#list allPaymentConfig as list>
												<input type="hidden" name="paymentConfig.id" value="${list.id}" />
								</#list>
							</table>
						</td>
					</tr>
				</table>
				<div>
				</td>
            	</tr>
           <tbody id="TableData" class="dataContainer">
        	<s:iterator value="allProductAttr">
                <td>${name}&nbsp;</td>
            </s:iterator>
            
        </tbody>
            	
            	
				</div>
				<div class="blank"></div>
				<!--
				<a class="backCartItem" href="${base}/shop/cart_item!list.action"><span class="icon"> </span>编辑购物车</a>
				-->
				<a class="backCartItem" href="${base}/"><span class="icon"> </span>继续投保</a>
				<table class="cartItemTable">
					<tr>
						<th>产品</th>
						<th>产品名称</th>
						<th>销售价格</th>
						<#if (loginMember.memberRank.preferentialScale != 100)!>
							<th>优惠价格</th>
						</#if>
						<th>数量</th>
						<th>操作</th>
					</tr>
					<#list cartItemSet as list>
						<tr>
							<!--	
							<td class="productName">
							-->		
							<td>
								<a href="${base}${list.product.htmlFilePath}" target="_blank">
									<img src="${base}${(list.product.productImageList[0].thumbnailProductImagePath)!systemConfig.defaultThumbnailProductImagePath}" /> 
									${list.name}
								</a>
								<!-------------product属性获取测试------------------
								<a href="${base}/shop/order!info.action?id=${list.product.id}">${list.product.name}</a>
								--------product属性获取测试-------------------------->
							</td>
							
							<td>
								${list.product.name}
							</td>
							<#if (loginMember.memberRank.preferentialScale != 100)!>
								<td class="priceTd">
									<span class="lineThrough">${list.product.price?string(priceCurrencyFormat)}</span>
								</td>
								<td class="priceTd">
									${list.preferentialPrice?string(priceCurrencyFormat)}
								</td>
							<#else>
								<td class="priceTd">
									${list.product.price?string(priceCurrencyFormat)}
								</td>
							</#if>
							<!--
								<span class="subtotalPrice">${list.subtotalPrice?string(orderCurrencyFormat)}</span>
							-->
							</td>
							<td>
								${list.quantity}
							</td>
							<!--
							<td>
								<#assign index = 1 />
								<#list (list.product.productInsType.enabledProductInsAttributeList)! as list>
			                    		<li>
			                    			<strong>${list.name}:</strong>
			                				<#list (product.productInsAttributeMap.get(list))! as attributeOptionList>
			                            		${attributeOptionList} 
			                            		<#if (attributeOptionList_index == 3) >
													<#break>
												</#if>
			                            	</#list>
			                            </li>
			                            <#if index == 5 >
											<#break>
										</#if>
										<#assign index = index + 1 />
								</#list>
							</td>
							-->
							<td>
								<a class="deleteCartItem {id: '${list.product.id}'}" href="javascript: void(0);">填写投保信息</a>
								<a class="deleteCartItem {id: '${list.product.id}'}" href="javascript: void(0);">删除</a>
							</td>
						</tr>
					</#list>
					<tr>
						<td class="info" colspan="<#if (loginMember.memberRank.preferentialScale != 100)!>6<#else>5</#if>">
							产品共计：<span class="red">${totalQuantity}</span> 件 
						   
							产品总金额：<span id="productTotalPrice" class="red">${productTotalPrice?string(orderUnitCurrencyFormat)}</span>    
						</td>
					</tr>
				</table>
				<div class="blank"></div>
				<input type="submit" class="formButton" value="填写保单" />
				<div class="clearfix"></div>
			</form>
		</div>
		<div class="blank"></div>
		<#include "/WEB-INF/template/shop/friend_link.ftl">
	</div>
	<div class="blank"></div>
	<#include "/WEB-INF/template/shop/footer.ftl">
</body>
</html>