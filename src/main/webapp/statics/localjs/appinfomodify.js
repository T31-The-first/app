var APKName=null;
var softwareName=null;
var supportROM=null;
var interfaceLanguage=null;
var softwareSize=null;
var downloads=null;
var flatformId=null;
var categoryLevel1=null;
var categoryLevel2=null;
var categoryLevel3=null;
var statusName=null;
var appInfo=null;
var attach=null;
var errorinfo=null;
var logoPicPath2=null;

function  loadCategoryLevel(pid,cl,categoryLevel){
    $.ajax({
        type:"GET",//请求类型
        url:"/catagory/levellist",//请求的url
        data:{pid:pid},//请求参数
        dataType:"json",//ajax接口（请求url）返回的数据类型
        success:function(data){//data：返回数据（json对象）

            $("#"+categoryLevel).html("");
            var options = "<option value=\"\">--请选择--</option>";
            for(var i = 0; i < data.length; i++){
                if(cl != null && cl != undefined && data[i].id == cl ){
                    options += "<option selected=\"selected\" value=\""+data[i].id+"\" >"+data[i].categoryName+"</option>";
                }else{
                    options += "<option value=\""+data[i].id+"\">"+data[i].categoryName+"</option>";
                }
            }
            $("#"+categoryLevel).html(options);
        },
        error:function(data){//当访问时候，404，500 等非200的错误状态码
            alert("加载分类列表失败！");
        }
    });
}

function delfile(id){
    $.ajax({
        type:"GET",//请求类型
        url:"/developer/appinfo/delFile",//请求的url
        data:{id:id,flag:'logo'},//请求参数
        dataType:"json",//ajax接口（请求url）返回的数据类型
        success:function(data){//data：返回数据（json对象）
            if(data.result == "success"){
                alert("删除成功！");
                $("#uploadfile").show();
                $("#logoFile").html('');
                $("#logoPicPath").val("");
                logoPicPath2.val("");
            }else if(data.result == "failed"){
                alert("删除失败！");
            }
        },
        error:function(data){//当访问时候，404，500 等非200的错误状态码
            alert("请求错误！");
        }
    });
}

$(function(){
    APKName=$("#APKName");
    softwareName=$("#softwareName");
    supportROM=$("#supportROM");
    interfaceLanguage=$("#interfaceLanguage");
    softwareSize=$("#softwareSize");
    downloads=$("#downloads");
    flatformId=$("#flatformId");
    categoryLevel1=$("#categoryLevel1");
    categoryLevel2=$("#categoryLevel2");
    categoryLevel3=$("#categoryLevel3");
    statusName=$("#statusName");
    appInfo=$("#appInfo");
    attach=$("#attach");

    errorinfo=$("#errorinfo");
    logoPicPath2=$("#logoPicPath");


    /*if(errorinfo.val() == null || errorinfo.val() == ""){
        logoPicPath2.next().html("*");
    }else{
        logoPicPath2.next().html(errorinfo.val());
    }*/

	//动态加载所属平台列表
	$.ajax({
		type:"GET",//请求类型
		url:"/data/list",//请求的url
		data:{tcode:"APP_FLATFORM"},//请求参数
		dataType:"json",//ajax接口（请求url）返回的数据类型
		success:function(data){//data：返回数据（json对象）
			var fid = $("#fid").val();
			$("#flatformId").html("");
			var options = "<option value=\"\">--请选择--</option>";
			for(var i = 0; i < data.length; i++){
				if(fid != null && fid != undefined && data[i].valueId == fid ){
					options += "<option selected=\"selected\" value=\""+data[i].valueId+"\" >"+data[i].valueName+"</option>";
				}else{
					options += "<option value=\""+data[i].valueId+"\">"+data[i].valueName+"</option>";
				}
			}
			$("#flatformId").html(options);
		},
		error:function(data){//当访问时候，404，500 等非200的错误状态码
			alert("加载平台列表失败！");
		}
	});  
	
	var cl1 = $("#cl1").val();
	var cl2 = $("#cl2").val();
	var cl3 = $("#cl3").val();
	//动态加载一级分类列表
	loadCategoryLevel(null,cl1,"categoryLevel1");
	//动态加载二级分类列表
	loadCategoryLevel(cl1,cl2,"categoryLevel2");
	//动态加载三级分类列表
	loadCategoryLevel(cl2,cl3,"categoryLevel3");
    /*if($("#categoryLevel1").val() != '' && $("#categoryLevel1").val()  != null){

    }else{
        $("#categoryLevel2").html("");
        var options = "<option value=\"\">--请选择--</option>";
        $("#categoryLevel2").html(options);

        $("#categoryLevel3").html("");
        var options = "<option value=\"\">--请选择--</option>";
        $("#categoryLevel3").html(options);
    }*/
	//联动效果：动态加载二级分类列表
	$("#categoryLevel1").change(function(){
        var cl2 = $("#cl2").val("");
        var cl3 = $("#cl3").val("");
		var categoryLevel1 = $("#categoryLevel1").val();
		if(categoryLevel1 != '' && categoryLevel1 != null){
			loadCategoryLevel(categoryLevel1,cl2,"categoryLevel2");
		}else{
			$("#categoryLevel2").html("");
			var options = "<option value=\"\">--请选择--</option>";
			$("#categoryLevel2").html(options);
		}
		$("#categoryLevel3").html("");
		var options = "<option value=\"\">--请选择--</option>";
		$("#categoryLevel3").html(options);
	});
	//联动效果：动态加载三级分类列表
	$("#categoryLevel2").change(function(){
        var cl2 = $("#cl2").val("");
        var cl3 = $("#cl3").val("");
		var categoryLevel2 = $("#categoryLevel2").val();
		if(categoryLevel2 != '' && categoryLevel2 != null){
			loadCategoryLevel(categoryLevel2,cl3,"categoryLevel3");
		}else{
			$("#categoryLevel3").html("");
			var options = "<option value=\"\">--请选择--</option>";
			$("#categoryLevel3").html(options);
		}
	});
	
	$("#back").on("click",function(){
		// window.location.href = "list";
		window.history.back();
	});
	
	
	//LOGO图片---------------------
	var logoPicPath = $("#logoPicPath").val();
	var id = $("#id").val();
	if(logoPicPath == null || logoPicPath == "" ){
		$("#uploadfile").show();
	}else{
		$("#logoFile").append("<p><img src=\""+logoPicPath+"?m="+Math.random()+"\" width=\"100px;\"/> &nbsp;&nbsp;"+
							"<a href=\"javascript:;\" onclick=\"delfile('"+id+"');\">删除</a></p>");
		
	}


    //提取公共方法
    function  bindBlur(element,text,num) {


        element.on("blur",function () {
            if(num==1){
                if(element.val()>0){
                    validateTip(element.next(),{"color":"green"},imgYes,true);
                }
                else{
                    validateTip(element.next(),{"color":"red"},imgNo+text,false);
                }
            }else if(num==2){
                if(element.val()!=null&&element.val()!=''){
                    validateTip(element.next(),{"color":"green"},imgYes,true);
                }
                else{
                    validateTip(element.next(),{"color":"red"},imgNo+text,false);
                }
            }
        });
    }

    var stringElem=new Array(softwareName,supportROM,interfaceLanguage,appInfo,downloads);
    var stringZhi=new Array("","","","","下载次数不能为负数");
    var elem=new Array(softwareSize,flatformId,categoryLevel1,categoryLevel2,categoryLevel3/*,a_logoPicPath*/);
    var zhi=new Array("软件大小不能为0和负数","请选择所属平台","请选择一级分类","请选择二级分类","请选择三级分类"/*,"未上传文件"*/);

    for(var i=0; i<elem.length; i++){
        bindBlur(elem[i],zhi[i],1);
        bindBlur(stringElem[i],stringZhi[i],2);
    }

  /*  if(logoPicPath2.val()!=null&&logoPicPath2.val()!=''){
        attach.attr("validateStatus",true);
    }else{*/
        attach.on("change",function () {
            if(attach.val()!=null&&attach.val()!=''){
                validateTip(attach.next(),{"color":"green"},imgYes,true);
            }else{
                validateTip(attach.next(),{"color":"red"},imgNo+"未上传文件",false);
            }
        });
    // }




    $("#send").on("click",function(){
        // userName.blur();
        // phone.blur();
        // birthday.blur();
        // userRole.blur();
        softwareName.blur();
        APKName.blur();
        supportROM.blur();
        interfaceLanguage.blur();
        softwareSize.blur();
        downloads.blur();
        flatformId.blur();
        categoryLevel1.blur();
        categoryLevel2.blur();
        categoryLevel3.blur();
        appInfo.blur();

        if(logoPicPath2.val()!=null&&logoPicPath2.val()!=''){
            attach.attr("validateStatus",true);
        }else{
            attach.change();
        }


        if(softwareName.attr("validateStatus") == "true"
            // &&APKName.attr("validateStatus") != "true"
            &&supportROM.attr("validateStatus") == "true"
            &&interfaceLanguage.attr("validateStatus") == "true"
            &&softwareSize.attr("validateStatus") == "true"
            &&downloads.attr("validateStatus") == "true"
            &&flatformId.attr("validateStatus") == "true"
            &&categoryLevel1.attr("validateStatus") == "true"
            &&categoryLevel2.attr("validateStatus") == "true"
            &&categoryLevel3.attr("validateStatus") == "true"
            &&appInfo.attr("validateStatus") == "true"
            &&attach.attr("validateStatus") == "true") {
				if(confirm("是否确认要提交数据？")){
					$("#userForm").submit();
				}
        	}
		});

});
      
      
      