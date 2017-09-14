
$(function(){
	
	$("form").validate({
		onfocusout:function(element){$(element).valid();},
    	errorPlacement: function(error, element){
    		error.appendTo(element.parent().parent());
    	},
    	rules: {
    		USER_NAME:{
    			required: true
    		},
    		MOBILE:{
    			required: true,
    			isMobile: true
    		},
    		cityResult3:{
    			required: true
    		},
    		STREET:{
    			required: true
    		}
    	},
    	messages: {
    		USER_NAME:{
    			required: "请输入您的姓名"
    		},
    		MOBILE:{
				required: "请输入您的手机号码",
				isMobile: "请输入正确的手机号"
			},
			cityResult3:{
    			required: "请选择城市"
    		},
    		STREET:{
    			required: "请填写地址"
    		}
    	},
    	submitHandler:function(form){
    		form.submit();
    	}
    });
});