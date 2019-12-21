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
var status=null;
var appInfo=null;
var a_logoPicPath=null;
var errorinfo=null;

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
    status=$("#status");
    appInfo=$("#appInfo");
    a_logoPicPath=$("#a_logoPicPath");

    errorinfo=$("#errorinfo");

    if(errorinfo.val() == null || errorinfo.val() == ""){
        a_logoPicPath.next().html("*");
    }else{
        a_logoPicPath.next().html(errorinfo.val());
    }


	//动态加载所属平台列表
	$.ajax({
		type:"GET",//请求类型
		url:"/data/list",//请求的url
		data:{tcode:"APP_FLATFORM"},//请求参数
		dataType:"json",//ajax接口（请求url）返回的数据类型
		success:function(data){//data：返回数据（json对象）
			$("#flatformId").html("");
			var options = "<option value=\"\">--请选择--</option>";
			for(var i = 0; i < data.length; i++){
				options += "<option value=\""+data[i].valueId+"\">"+data[i].valueName+"</option>";
			}
			$("#flatformId").html(options);
		},
		error:function(data){//当访问时候，404，500 等非200的错误状态码
			alert("加载平台列表失败！");
		}
	});  
	//动态加载一级分类列表
    if($("#categoryLevel1").val() != '' && $("#categoryLevel1").val()  != null){

    }else{
        $("#categoryLevel2").html("");
        var options = "<option value=\"\">--请选择--</option>";
        $("#categoryLevel2").html(options);

        $("#categoryLevel3").html("");
        var options = "<option value=\"\">--请选择--</option>";
        $("#categoryLevel3").html(options);
    }
	$.ajax({
		type:"GET",//请求类型
		url:"/catagory/levellist",//请求的url
		data:{pid:null},//请求参数
		dataType:"json",//ajax接口（请求url）返回的数据类型
		success:function(data){//data：返回数据（json对象）
			$("#categoryLevel1").html("");
			var options = "<option value=\"\">--请选择--</option>";
			for(var i = 0; i < data.length; i++){
				options += "<option value=\""+data[i].id+"\">"+data[i].categoryName+"</option>";
			}
			$("#categoryLevel1").html(options);
		},
		error:function(data){//当访问时候，404，500 等非200的错误状态码
			alert("加载一级分类列表失败！");
		}
	});  
	//动态加载二级分类列表
	$("#categoryLevel1").change(function(){
		var categoryLevel1 = $("#categoryLevel1").val();
		if(categoryLevel1 != '' && categoryLevel1 != null){
			$.ajax({
				type:"GET",//请求类型
				url:"/catagory/levellist",//请求的url
				data:{pid:categoryLevel1},//请求参数
				dataType:"json",//ajax接口（请求url）返回的数据类型
				success:function(data){//data：返回数据（json对象）
					$("#categoryLevel2").html("");
					var options = "<option value=\"\">--请选择--</option>";
					for(var i = 0; i < data.length; i++){
						options += "<option value=\""+data[i].id+"\">"+data[i].categoryName+"</option>";
					}
					$("#categoryLevel2").html(options);
				},
				error:function(data){//当访问时候，404，500 等非200的错误状态码
					alert("加载二级分类失败！");
				}
			});
		}else{
			$("#categoryLevel2").html("");
			var options = "<option value=\"\">--请选择--</option>";
			$("#categoryLevel2").html(options);
		}
		$("#categoryLevel3").html("");
		var options = "<option value=\"\">--请选择--</option>";
		$("#categoryLevel3").html(options);
	});
	//动态加载三级分类列表
	$("#categoryLevel2").change(function(){
		var categoryLevel2 = $("#categoryLevel2").val();
		if(categoryLevel2 != '' && categoryLevel2 != null){
			$.ajax({
				type:"GET",//请求类型
				url:"/catagory/levellist",//请求的url
				data:{pid:categoryLevel2},//请求参数
				dataType:"json",//ajax接口（请求url）返回的数据类型
				success:function(data){//data：返回数据（json对象）
					$("#categoryLevel3").html("");
					var options = "<option value=\"\">--请选择--</option>";
					for(var i = 0; i < data.length; i++){
						options += "<option value=\""+data[i].id+"\">"+data[i].categoryName+"</option>";
					}
					$("#categoryLevel3").html(options);
				},
				error:function(data){//当访问时候，404，500 等非200的错误状态码
					alert("加载三级分类失败！");
				}
			});
		}else{
			$("#categoryLevel3").html("");
			var options = "<option value=\"\">--请选择--</option>";
			$("#categoryLevel3").html(options);
		}
	});
	
	$("#back").on("click",function(){
		window.history.back();
	});
	
	$("#APKName").bind("blur",function(){
		//ajax后台验证--APKName是否已存在
		$.ajax({
			type:"GET",//请求类型
			url:"/developer/appinfo/apkexist",//请求的url
			data:{APKName:$("#APKName").val()},//请求参数
			dataType:"json",//ajax接口（请求url）返回的数据类型
			success:function(data){//data：返回数据（json对象）
				if(data.APKName == "empty"){//参数APKName为空，错误提示
					// alert("APKName为不能为空！");
                    validateTip(APKName.next(),{"color":"red"},imgNo+ " APKName为不能为空！",false);

				}else if(data.APKName == "exist"){//账号不可用，错误提示
                    // alert("该APKName已存在，不能使用！");
					validateTip(APKName.next(),{"color":"red"},imgNo+ " 该APKName已存在，不能使用！",false);

				}else if(data.APKName == "noexist"){//账号可用，正确提示
					// alert("该APKName可以使用！");
                    validateTip(APKName.next(),{"color":"green"},imgYes+" 该APKName可以使用！",true);
				}
			},
			error:function(data){//当访问时候，404，500 等非200的错误状态码
				// alert("请求错误！");
                validateTip(APKName.next(),{"color":"red"},imgNo+ " 请求错误！",false);
			}
		});
	});
    //提取公共方法
    function  bindBlur(element,text,num) {
        element.bind("blur",function () {
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

    a_logoPicPath.bind("change",function () {
		if(a_logoPicPath.val()!=null&&a_logoPicPath.val()!=''){
            validateTip(a_logoPicPath.next(),{"color":"green"},imgYes,true);
		}else{
            validateTip(a_logoPicPath.next(),{"color":"red"},imgNo+"未上传文件",false);
		}
    });

    $("#send").bind("click",function(){
		if(softwareName.attr("validateStatus") != "true"){
            softwareName.blur();
		}else if(APKName.attr("validateStatus") != "true"){
            APKName.blur();
        }else if(supportROM.attr("validateStatus") != "true"){
            supportROM.blur();
        }else if(interfaceLanguage.attr("validateStatus") != "true"){
            interfaceLanguage.blur();
        }else if(softwareSize.attr("validateStatus") != "true"){
            softwareSize.blur();
        }else if(downloads.attr("validateStatus") != "true"){
            downloads.blur();
        }else if(flatformId.attr("validateStatus") != "true"){
            flatformId.blur();
        }else if(categoryLevel1.attr("validateStatus") != "true"){
            categoryLevel1.blur();
        }else if(categoryLevel2.attr("validateStatus") != "true"){
            categoryLevel2.blur();
        }else if(categoryLevel3.attr("validateStatus") != "true"){
            categoryLevel3.blur();
        }else if(appInfo.attr("validateStatus") != "true"){
            appInfo.blur();
        }else if(a_logoPicPath.attr("validateStatus") != "true"){
            a_logoPicPath.change();
        }else{
            if(confirm("是否确认提交数据")){
                $("#userForm").submit();
            }
        }
    });
});
      
      
      