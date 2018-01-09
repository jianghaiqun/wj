// JavaScript Document
  jQuery(function(){

			 jQuery('#tag_box_ste > ul:not(:first)').hide();
			  
            jQuery('#min_tag_ste li a').click(
			function()
            {
                jQuery(this).addClass('select_tab_ste').parent().siblings().find('a').removeClass('select_tab_ste');
                jQuery('#tag_box_ste > ul:eq('+ jQuery('#min_tag_ste li').index(jQuery(this).parent()) +')').show().siblings().hide();
            });
	
			
			

			
	});