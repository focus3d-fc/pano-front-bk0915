$(function(){
	$("#submitOrder").click(function(){
		var isReadContract = $("#readContractTick").attr("status");
		if(isReadContract != 1){
			alert("请阅读并同意合同条款");
		} else {
			if($("#regMobile").val() == "true"){
				create_order();
			} else {
				$('.wrap1').show();
			}
		}
	});
	
	$("#readContract").click(function(){
		$('.wrap').hide();
		$("#readContractTick").attr("status", 1);
		$("#readContractTick").attr("src", "/images/choose3.png");
	});
	
	$("#readContractTick").click(function(){
		var status = $(this).attr("status");
		if(!status || status == 0){
			$(this).attr("status", 1);
			$(this).attr("src", "/images/choose3.png");
		} else {
			$(this).attr("status", 0);
			$(this).attr("src", "/images/choose4.png");
		}
	});
});