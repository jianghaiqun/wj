
/*
*@des:线下核保
*@maker:guodongqi
*@time:2017-6-14
*/
jQuery(document).ready(function($) {

    jQuery(".linedown_s,.linedown_text").each(function(index, el) {
         if(jQuery(el).val()!=""){
           jQuery(el).siblings(".mes_tip_p").hide();
          }
    });
    jQuery(".linedown_s,.linedown_text").bind("input propertychange click",function(){
        var text = jQuery(this).val();
        jQuery(this).siblings(".mes_tip_p").hide();
    })
    jQuery(".linedown_s,.linedown_text").bind("blur",function(){
            var text = jQuery(this).val();
            if(text==""){
                jQuery(this).siblings(".mes_tip_p").show();
            }
    })

    changeLength(jQuery('.linedown_text'),jQuery("#changeNums"),500);

});

// 提示可输入字数
function changeLength(obj,num,size){
    obj.on('keyup',function(){
    var txtval = obj.val().length;
        var str = parseInt(size-txtval);

        if(str > 0 ){
            num.html(str);
        }else {
            num.html('0');
            obj.val(obj.val().substring(0, size));
        }

    });
}


/** 
 * @des: 开心保Wap：上传图片
 * @maker: Songzairan
 * @date: 2016.5.16
 */
wxPhotoFile = function(opt) {
    
    this._opt = $.extend({
        contArea : ".sbt_list",
         fileElm : ".file_up",
      uploadArea : ".upload",
         delArea : ".upload > em",
          delElm : ".del_img > i",
         acptElm : "#accept_b",
          chkElm : ".btn_confirm .check",
         tipArea : "#tip_weixin",
          clsTip : ".close",
          sbtTip : ".btn_tip",
        filesize : true, //是否开启文件限制
            size : 5, //单位M
        callBack : function() {}
    }, opt);
    
    this.setInit();
    this.addEvent();
    
}
wxPhotoFile.prototype.setInit = function() {
    
    this.contArea = $(this._opt.contArea);
    this.chkBtn = $(this._opt.chkElm);
    this.acptChk = $(this._opt.acptElm);
    this.tipArea = $(this._opt.tipArea);
    this.btnClose = $(this._opt.clsTip);
    this.sbtTip = $(this._opt.sbtTip);
    this.imgList = $(this._opt.imglist);
    
}
wxPhotoFile.prototype.addEvent = function() {
    
    var _this = this;
    var isIE = /msie/i.test(navigator.userAgent) && !window.opera;
    //上传图片
    this.contArea.delegate(this._opt.fileElm, "change", function() {
        
        if( _this._opt.filesize){
            var fileSize = 0;
             if (isIE && !this.files) {
                var filePath = this.value;
                try{
                    var fileSystem = new ActiveXObject("Scripting.FileSystemObject");
                    var file = fileSystem.GetFile (filePath);
                    fileSize = file.Size;
                }catch(exp){
                    // alert("上传图片,必须将浏览器须设置为可使用“ActiveX控件”。如有疑问，请点击浏览器的“帮助”了解浏览器设置方法！");
                }
                
            } else {
                fileSize = this.files[0].size;
           }
            var size = fileSize / 1024 ;
            var optsize = parseInt(_this._opt.size)*1024;
            if(size>optsize){
                alert("附件不能大于"+_this._opt.size+"M");
                this.value="";
                return
            }
        }

            if(!/.(gif|jpg|jpeg|png|bmp)$/i.test(this.value)){
                alert("支持jpg、gif、png和bmp格式");
                return;
            }


            var src;
            var nThis = this;
            var oClone = jQuery(this).parent().clone();

            if(this && this.files && this.files[0]){
                 src = window.URL.createObjectURL(this.files[0]);

                    var Img = new Image();
                    Img.onload = function() {
                         jQuery(nThis).siblings("em")
                                    .html(this)
                                    .append("<b class='del_img'><i>删除</i></b>")
                                    .show()
                                    .parent()
                                    .after(oClone);
                         jQuery(this).parents("dd")
                                   .find(":file").each(function(i, v) {
                                        var _name = v.name.replace(/claimsFile\[\d+\]/g,"claimsFile["+i+"]");
                                        jQuery(v)[0].name = _name;
                                        jQuery(v).next(":hidden")[0].name = _name.slice(0, -5) + ".filePath";
                                        jQuery(v).next(":hidden").next(":hidden")[0].name = _name.slice(0, -5) + ".id";
                                    });

                     }
                    Img.src = src;

            } else {
                 
                 var srcie = "";
                 var obj = $(this).prev();
                 var div = obj.parent("p")[0];
                 this.select();
                 if (top != self) {
                    window.parent.document.body.focus()
                 } else {
                    this.blur()
                 }
                 srcie = document.selection.createRange().text;
                 document.selection.empty();
                 obj.hide();
                 obj.parent("p").css({
                            'filter': 'progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale)',
                            'width': 180 + 'px',
                            'height': 130 + 'px'
                        });
                 div.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = srcie

                 src = srcie;

                 jQuery(nThis).siblings("em")
                        .append("<b class='del_img'><i>删除</i></b>")
                        .show()
                        .parent()
                        .after(oClone);

                 jQuery(this).parents("dd")
                           .find(":file").each(function(i, v) {
                                var _name = v.name.replace(/claimsFile\[\d+\]/g,"claimsFile["+i+"]");
                                jQuery(v)[0].name = _name;
                                jQuery(v).next(":hidden")[0].name = _name.slice(0, -5) + ".filePath";
                                jQuery(v).next(":hidden").next(":hidden")[0].name = _name.slice(0, -5) + ".id";
                            });

         }

        _this._opt.callBack(this);

    });


    //鼠标以上显示图片删除操作
    this.contArea.delegate(this._opt.uploadArea, "hover", function() {
       
        if(jQuery(this).attr("name") != "1") {
            jQuery(this).attr("name", 1)
                   .find("b")
                   .fadeTo(150, 0.8)
                   .find("i").show();
        } else {
            jQuery(this).attr("name", 0)
                   .find("b")
                   .fadeOut(150)
                   .find("i").hide();
        }
        
    });
    

    //删除上传图片
    this.contArea.delegate(this._opt.delElm, "click", function() {
        
        var nThis = this;
        var jLi = jQuery(this).parents("dd");
        var jFile = jQuery(this).parents("em")
                           .siblings(":file");
        var nKey = jFile.attr("data-name");
        
        jQuery(this).parents(_this._opt.uploadArea)
               .remove();

        jLi.find(":file")
           .each(function(i, v) {
                var _name = v.name.replace(/claimsFile\[\d+\]/g, "claimsFile[" + i + "]");
                jQuery(v)[0].name = _name;
                jQuery(v).next(":hidden")[0].name = _name.slice(0, -5) + ".filePath";
                jQuery(v).next(":hidden").next(":hidden")[0].name = _name.slice(0, -5) + ".id";
            });
        
    });

    this.chkBtn.on("click", function(e) {

        e.preventDefault();

        _this.tipArea.fadeIn();

    });

    this.btnClose.on("click", function() {

        _this.tipArea.hide();

    });


    
}