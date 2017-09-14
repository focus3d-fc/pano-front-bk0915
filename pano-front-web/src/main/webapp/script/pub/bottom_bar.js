    $(function(){
       $('.rd').click(function () {
           $(this).hide();
           $('.rd-show').show();
       });
       
       var Swiper1 = new Swiper('.swiper1',{
           autoplayStopOnLast:true,
           slidesPerView : 5,
           slidesPerGroup : 1,
       })
       var Swiper2 = new Swiper('.swiper2',{
           lazyLoading:true,
           autoplayStopOnLast:true,
           slidesPerView : 4,
           slidesPerGroup : 1,
           spaceBetween:10,
       })
       var Swiper3 = new Swiper('.swiper3',{
           autoplayStopOnLast:true,
           slidesPerView : 4,
           slidesPerGroup : 1,
           spaceBetween:10,
       })
       var Swiper4 = new Swiper('.swiper4',{
           spaceBetween : 20,
              loop : true,
          })
       

       $("a[id='hxshow']").click(function () {
       	var status = $(this).attr("status");
	        $(this).parent().find("a").removeClass("active");
	        $(this).parent().find("a").attr("status", 0);
   		if(!status || status == 0){
   			$('#hx').show();
   			$(this).attr("status", 1);
   			$(".location3").css('bottom','3.05rem');
   			$("#tc").hide();
	            $(this).addClass('active');
	            $(".swiper-container").addClass("swiper-container2");
   		} else {
   			$('#hx').hide();
   			$(this).attr("status", 0);
   			$(".location3").css('bottom','1.05rem');
   			$(".swiper-container").removeClass("swiper-container2");
   		}
           getHouse();
       });
       
       $('#hx').find('a').click(function () {
           $(this).toggleClass('active');
       });
   
       $("a[id='fjshow']").click(function () {
       	var status = $(this).attr("status");
   		$(this).parent().find("a").removeClass("active");
   		$(this).parent().find("a").attr("status", 0);
   		if(!status || status == 0){
   			$('#hx').show();
   			$(this).attr("status", 1);
   			$(".location3").css('bottom','3.05rem')
   			$("#tc").hide();
	            $(this).addClass('active');
	            $(".swiper-container").addClass("swiper-container2");
   		} else {
   			$('#hx').hide();
   			$(this).attr("status", 0);
   			$(".location3").css('bottom','1.05rem');
   			$(".swiper-container").removeClass("swiper-container2");
   		}
           //全景id
       	var panoId = $("#panoId").val();
       	console.info("套餐ID"+houseId);
           getFjshow(panoId);
       });
       $('#hx').find('a').click(function () {
           $(this).toggleClass('active');
       })
   

       $("a[id='tcshow']").click(function () {
       	var status = $(this).attr("status");
   		$(this).parent().find("a").removeClass("active");
   		$(this).parent().find("a").attr("status", 0);
   		if(!status || status == 0){
   			$('#tc').show();
   			$(this).attr("status", 1);
   			$(".location3").css('bottom','2.05rem')
   			$('#hx').hide();
   			$('#fj')
            	$(this).addClass('active');
   			$(".swiper-container").addClass("swiper-container2");
   		} else {
   			$('#tc').hide();
   			$(this).attr("status", 0);
   			$(".location3").css('bottom','1.05rem');
   			$(".swiper-container").removeClass("swiper-container2");
   		}
           var houseId = $("#houseId").val();
           console.info("户型ID"+houseId);
           getTcshow(houseId);
       });
       $('#tc').find('a').click(function () {
           $(this).toggleClass('active');
       })
       $("#toHousePackage").click(function(){
    	   var projectSn = $("#projectId").val();
    	   var houseSn = $("#houseId").val();
    	   var styleSn = $("#styleId").val();
    	   var url = "/shopcart/list?projectSn=" + projectSn + "&styleId=" + styleSn + "&houseId=" + houseSn;
    	   window.location.href = url;
       });
    });