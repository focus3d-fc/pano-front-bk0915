var userPicker;
$(function(){
	var mySwiper = new Swiper ('.swiper-container', {
		autoplay: 5000,//可选选项，自动滑动
		loop: true,
		pagination: '.swiper-pagination',
	})
	
	$(document).click(function(e){
		if(e.target.id != "showProjectPicker"){
			userPicker.hide();
		}
	});
});
(function($, doc, jquery) {
		$.init();
		$.ready(function() {
			var _getParam = function(obj, param) {
				return obj[param] || '';
			};
			//普通示例
			userPicker = new $.PopPicker({
				layer : 4
			});
			var projectSelectData = jquery('#projectSelectData').text();
			userPicker.setData(JSON.parse(projectSelectData));
			var showProjectPickerButton = doc.getElementById('showProjectPicker');
			//var userResult = doc.getElementById('sexResult');
			var projectResultValue = doc.getElementById('projectResultValue');
			var formObj = jquery("form");
			showProjectPickerButton.addEventListener('tap', function(event) {
				userPicker.show(function(items) {
					projectResultValue.value = _getParam(items[3], 'value');
					formObj.submit();
				});
			}, false);
		});
	})(mui, document, jQuery);
	