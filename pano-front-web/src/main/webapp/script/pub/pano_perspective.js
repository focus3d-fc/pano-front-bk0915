
//产品透视图
function ValidatePerspective(houseStyleSn, packageTypeSn, productSn){
	var param = new Object();
	param.houseStyleSn = houseStyleSn;
	param.packageTypeSn = packageTypeSn;
	param.productSn = productSn;
    $.ajax({
        url:"/perspective/ValidatePerspective",
        type: "POST",
        data:param,
        dataType:"json",
        success:function(data){
            if(data){
                var num = parseInt(data.num);
                if(num!=0){
					QueryPerspectiveInfo(data.param);
				}
            }
        }
    })
}
//产品透视图
function QueryPerspectiveInfo(param) {
	
    window.location.href = "/perspective/QueryPerspective?houseStyleSn="+param.houseStyleSn+"&packageTypeSn="+param.packageTypeSn+"&productSn="+param.productSn;
}