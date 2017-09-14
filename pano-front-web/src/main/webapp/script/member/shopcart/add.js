//全景里面点击套餐，添加套餐到购物车
function addToShopcar(packageSn){
	var panoDomain = $("#panoDomain").val();
	$.ajax({
	    url: panoDomain + "/shopcart/add",
	    type:'GET',
	    async:false,
	    timeout:5000,
	    dataType: "json",
	    data:{
	    	packageEncryptSn : packageSn
	    },
	    success:function(data){
	    	var status = data.status;
	    	if(status == 1){
	    		//alert("添加至购物车");
	    		mui.toast('添加至购物车');
	    	} else {
	    		//alert("从购物车删除");
	    		//mui.alert('', '从购物车删除');
	    	}
	    },
	    error:function(xhr,textStatus){
	    	if(xhr.getResponseHeader('sessionstatus') === "timeout"){
	    		window.location.href = "/member/login/nomal";
	    	}
	    }
	});
}

var totalPrice = 0.0;
$(function(){
	
	//默认记忆状态
	var rememberStatus = $("#rememberStatus").val();
	if(rememberStatus){
		var rememberStatusJo = JSON.parse(rememberStatus);
		var housePackageSn = rememberStatusJo.housePackageSn;
		//点击展开收起
		$("a[id^='closeOrOpen_']").each(function(){
			if(housePackageSn == $(this).attr("package_sn")){
				$(this).parent().prev().show(200);
				$(this).text("");
				$(this).append("点击收起 <i class='iconfont'>&#xe60c;</i>");
				$(this).attr("status", 1);
			}
		});
		window.location.hash = "#" + housePackageSn;
	}
	
	$("div[id^='choose_']").click(function(){
		var status = $(this).attr("status");
		var price = parseFloat($(this).attr("price"));
		if(!status || status == 0){
			$(this).attr("status", 1);
			totalPrice += price;
			$(this).addClass("imgChoose");
		} else {
			$(this).attr("status", 0);
			$(this).removeClass("imgChoose");
			$("#selectAll").attr("checked", false);
			$("#selectAll").attr("status", 0);
			totalPrice -= price;
		}
		$("#totalPrice").text(totalPrice);
	});
	
	$("#selectAll").click(function(){
		totalPrice = 0.0;
		var status = $(this).attr("status");
		if(!status || status == 0){
			$(this).attr("status", 1);
			$(this).addClass("imgChoose");
			$("div[id^='choose_']").attr("status", 1);
			$("div[id^='choose_']").addClass("imgChoose");
			$("div[id^='choose_']").each(function(){
				var price = parseFloat($(this).attr("price"));
				totalPrice += price;
			});
		} else {
			$(this).attr("status", 0);
			$(this).removeClass("imgChoose");
			$("div[id^='choose_']").attr("status", 0);
			$("div[id^='choose_']").removeClass("imgChoose");
		}
		$("#totalPrice").text(totalPrice);
	});
	//点击展开收起
	$("a[id^='closeOrOpen_']").click(function(){
		var status = $(this).attr("status");
		if(!status || status == 0){
			$(this).parent().prev().show(200);
			$(this).text("");
			$(this).append("点击收起 <i class='iconfont'>&#xe60c;</i>");
			$(this).attr("status", 1);
		} else {
			$(this).parent().prev().hide(200);
			$(this).text("");
			$(this).append("点击展开 <i class='iconfont'>&#xe612;</i>");
			$(this).attr("status", 0);
		}
	});
	
	//结算
	$("#addToOrder").click(function(){
		var packageSns = "";
		$("div[id^='choose_']").each(function(){
			if($(this).attr("Status") == 1){
				packageSns += $(this).attr("sn") + ",";
			}
		});
		if(!packageSns){
			alert("请选择一个套餐");
		} else {
			$("#packageSns").val(packageSns);
			$("form").submit();
		}
	});
	
	$("a[id^='productType_']").click(function(){
		var houseStyleSn = $(this).attr("house_style");
		var packageTypeSn = $(this).attr("package_type");
		var productSn = $(this).attr("product_sn");
		ValidatePerspective(houseStyleSn, packageTypeSn, productSn);
	});
});
