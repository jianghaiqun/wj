package com.sinosoft.framework.utility.weixin.bean;

/** 
 * 复杂按钮（父按钮） 
 *  
 * @author  
 * @date 2014-06-11 
 */  
public class ComplexButton extends Button {  
    private Button[] sub_button = new Button[]{};  
  
    public Button[] getSub_button() {  
        return sub_button;  
    }  
  
    public void setSub_button(Button[] sub_button) {  
        this.sub_button = sub_button;  
    }  
}  
