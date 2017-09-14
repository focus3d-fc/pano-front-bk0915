$(function(){
	$.ajax({
		url:"http://app.3d-focus.com/wxjssdk/share/config",
		data:{
			url:location.href.split('#')[0]
		},
		type:"get",
		cache:false,
		dataType:"json",
		success:function(data){
			wx.config(data);
		},
		error:function(){
			
		}
	});
	
	wx.ready(function(){
		//朋友圈
		wx.onMenuShareTimeline({
    		title: $("#shareTitle").val(), // 分享标题
    		desc: $("#desc").val(), // 分享描述
    		link: location.href.split('#')[0], // 分享链接
    		imgUrl: $("#imgUrl").val(), // 分享图标
		    success: function () {
			},
		    cancel: function () {
		        // 用户取消分享后执行的回调函数
		    }
		});
		//好友
		wx.onMenuShareAppMessage({
		    title: $("#shareTitle").val(), // 分享标题
		    desc: $("#desc").val(), // 分享描述
		   	link: location.href.split('#')[0], // 分享链接
    		imgUrl: $("#imgUrl").val(), // 分享图标
		    type: '', // 分享类型,music、video或link，不填默认为link
		    dataUrl: '', // 如果type是music或video，则要提供数据链接，默认为空
		    success: function () {
			},
		    cancel: function () {
		        // 用户取消分享后执行的回调函数
		    }
		});
	});
	
});