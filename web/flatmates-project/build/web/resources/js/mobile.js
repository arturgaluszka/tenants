	$(document).ready(function () {
			$( ".mobile-menu" ).click(function() {
	  		if($(".mobile-list").hasClass('non-active')){
	  			$(".mobile-list").removeClass('non-active');
	  		}
	  		else{
	  			$(".mobile-list").addClass('non-active');
	  		}
			});
		});
		$(window).resize(function() {
			if($(window).width()>=680){
				$(".mobile-list").addClass('non-active');
			};
		});