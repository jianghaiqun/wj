function submitData() {

	if (flag == 1 || flag == 2) {
		var fm = document.getElementById('fm');
		
		var dataArray = getData();
		if (!dataArray)
			return;
		var Json=dataArrayToJson(dataArray);

		var realJson={"rows":Json};
				
		fm.DTData.value = Ext.util.JSON.encode(realJson);	

		//fm.SQLStatement.value =SQLStatement;
	
		//fm.CreateTable.value =CreateTable;

		//fm.ViewPara.value =ViewPara;

		//alert(fm.ViewPara.value);

		//fm.BOMS.value = BOMSArray;

		//fm.SQLPara.value = SQLParaArray;

		//fm.RuleCh.value = RuleDesInCh;
		
		//alert(fm.RuleCh.value);

		//fm.ColumnDataType.value=ColumnDataTypeArray;

		//fm.TableColumnName.value=TableColumnNameArray;

		//fm.Types.value = ColumnDataTypeArray;		
		
		//fm.submit();
		//var dc = new DataCollection();
		var dc = Form.getData($F("fm"));
		//alert(fc.size());
		//for ( var i = 0; i < fc.size(); i++) {
		//	alert(dc.getKey(i)+"---"+dc.get(i));
		//	dc.add(dc.getKey(i), dc.get(i));
		//}
		//return;
		Dialog.confirm("确认提交吗？",function(){
			Server.sendRequest("com.sinosoft.ibrms.RuleMakeUI.submitData",dc,function(response){
				Dialog.alert(response.Message,function(){
					document.location.href="../ibrms/RuleMain.jsp";
				});
			});		
		});
	}
	if (flag == 4) {
		var fm = document.getElementById('fm');
		var op = fm.Operation.value;

		if (op != "Logic_Modification") {
			var dataArray = getData();
			if (!dataArray)
				return;
			
			var Json=dataArrayToJson(dataArray);
			
			var realJson={"rows":Json};
					
			fm.DTData.value = Ext.util.JSON.encode(realJson);
			
			fm.ColumnDataType.value = ColumnDataTypeArray;
			fm.TableColumnName.value=TableColumnNameArray;
			var dc = Form.getData($F("fm"));
			Dialog.confirm("确认提交吗？",function(){
				Server.sendRequest("com.sinosoft.ibrms.RuleMakeUI.submitData",dc,function(response){
					Dialog.alert(response.Message,function(){
						document.location.href="../ibrms/RuleMain.jsp";
					});
				});		
			});
			//fm.submit();
		} else {
			var fm = document.getElementById('fm');
			var dataArray = getData();
			if (!dataArray)
				return;
			
			var Json=dataArrayToJson(dataArray);
			
			var realJson={"rows":Json};
					
			fm.DTData.value = Ext.util.JSON.encode(realJson);

			fm.SQLStatement.value =SQLStatement;
			fm.CreateTable.value =CreateTable;
			fm.ViewPara.value =ViewPara;
			fm.BOMS.value = BOMSArray;
			fm.SQLPara.value = SQLParaArray;
			fm.RuleCh.value = RuleDesInCh;
			fm.ColumnDataType.value=ColumnDataTypeArray;
			fm.TableColumnName.value=TableColumnNameArray;
			
			var dc = Form.getData($F("fm"));
			Dialog.confirm("确认提交吗？",function(){
				Server.sendRequest("com.sinosoft.ibrms.RuleMakeUI.submitData",dc,function(response){
					Dialog.alert(response.Message,function(){
						document.location.href="../ibrms/RuleMain.jsp";
					});
				});		
			});
			//fm.submit();
		}
	}
}

function modifyLogic() {
	showButtons();
	showButtonsR();
	enableSpanNodes();
	enableSpanNodesR();
	disableInputNodes();
	disableInputNodesR();
	
	var fm = document.getElementById('fm');
	fm.Operation.value = 'Logic_Modification';
	
	var displayDisicionTable=document.getElementById('displayDisicionTable');
	displayDisicionTable.disabled=false;
	var submitData=document.getElementById('submitData');
	submitData.disabled=true;
	var logicToTable=document.getElementById('logicToTable');
	logicToTable.disabled=true;
	var modifyLogic=document.getElementById('modifyLogic');
	modifyLogic.disabled=true;
	
}