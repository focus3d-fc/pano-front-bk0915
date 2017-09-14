
var prodDetailCache = new Array();
var mySwiper;
$(function(){
	$("#arrow_left").click(function(){
		var prodDetailLen = prodDetailCache.length;
		var index = $(this).attr("index");
		showProductDetail(index);
		index --;
		var prevIndex = index > 0 ? index : 0;
		$(this).attr("index", prevIndex);
		$("#arrow_right").attr("index", prevIndex < (prodDetailLen - 1) ? ++prevIndex : prevIndex);
		console.info("left:" + $(this).attr("index") + ",right:" + $("#arrow_right").attr("index"));
	});
	$("#arrow_right").click(function(){
		var prodDetailLen = prodDetailCache.length;
		var index = $(this).attr("index");
		showProductDetail(index);
		index ++;
		var nextIndex = index < prodDetailLen ? index : (prodDetailLen - 1);
		$(this).attr("index", nextIndex);
		$("#arrow_left").attr("index", nextIndex > 0 ? --nextIndex : nextIndex);
		console.info("left:" + $("#arrow_left").attr("index") + ",right:" + $(this).attr("index"));
	});
	
	$("#closeProductDetail").click(function(){
		$("#moreProd").hide();
		closeHotspotWin();
	});
	resizeSlider();
	
	$("#moreDetail").click(function(){
		var houseStyleSn = $("#moreDetail").attr("houseStyleSn");
		var packageTypeSn = $("#moreDetail").attr("packageTypeSn");
		var productSn = $("#moreDetail").attr("productSn");
		ValidatePerspective(houseStyleSn, packageTypeSn, productSn);
	});

});

function showProductDetail(index){
	var data = prodDetailCache[index];
	var houseStyleSn = data.houseStyleSn;
	var packageTypeSn = data.packageTypeSn;
	//产品信息
	var productSn = data.productSn;
	var nameP = data.nameP;
	var materialNameP = data.materialNameP;
	var materialColorP = data.materialColorP;
	var parameterP = data.parameterP;
	var modelP = data.modelP;
	var fullImgUrl = data.fullImgUrl;
	var leftImgUrl = data.leftImgUrl;
	var downImgUrl = data.downImgUrl;
	$("#nameP").text(nameP);
	$("#materialNameP").text(materialNameP);
	$("#materialColorP").text(materialColorP);
	$("#parameterP").text(parameterP);
	$("#modelP").text(modelP);
	$("#moreDetail").attr("houseStyleSn", houseStyleSn);
	$("#moreDetail").attr("packageTypeSn", packageTypeSn);
	$("#moreDetail").attr("productSn", productSn);
	$("#moreProd").show();
	//小图显示
	if(mySwiper == null){
		
	} else {
		mySwiper.destroy(true, false);
	}
	mySwiper = new Swiper ('#product-swiper-container', {
		loop: false,
		autoplay: 3000,
		autoplayDisableOnInteraction:false
	});
	mySwiper.removeAllSlides();
	mySwiper.appendSlide("<div class='swiper-slide'><img id='fullImgUrl' src='" + fullImgUrl + "'/></div>");
	//mySwiper.appendSlide("<div class='swiper-slide'><img id='fullImgUrl' src='" + leftImgUrl + "'/></div>");
	//mySwiper.appendSlide("<div class='swiper-slide'><img id='fullImgUrl' src='" + downImgUrl + "'/></div>");
	//mySwiper.startAutoplay();
	
}

function resizeSlider(){
	var winW = $(window).width();
	var winH = $(window).height();
	var factor;
	if(winW > winH){
		factor = winW * 0.7;
	} else {
		factor = winW;
	}
	var opoWinW = factor * 0.55 / 0.75;
	var opoWinH = factor * 0.9;
	var opoWinTop = (winH - opoWinH) / 2;
	var opoWinLeft = (winW - opoWinW) / 2;
	$("#moreProd").css("position", "absolute");
	$("#moreProd").height(opoWinH);
	$("#moreProd").width(opoWinW);
	$("#moreProd").css("left", opoWinLeft);
	var h = $("#moreProd").height();
	$("#moreProd").css("top", opoWinTop * 0.7);
	$("#moreProd").css("z-index", 1000);
	
	$(".prod_hotspot").css("position", "absolute");
	$(".prod_hotspot").height(opoWinH * 0.9);
	$(".prod_hotspot").width(opoWinW * 0.8);
	$(".prod_hotspot").css("left", 32);
	$(".prod_hotspot").css("top", 20);
}


$(window).resize(function() {
	setTimeout("resizeSlider()", 500);
});