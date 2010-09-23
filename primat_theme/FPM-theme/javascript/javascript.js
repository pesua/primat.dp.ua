var FPM = function () {
	var $ = jQuery;
	return {
		init: function() {
			var instance = this;
			instance.handleSearchForm();
		},
		
		handleSearchForm: function() {
			var searchForm = $('#banner .search');	
			
			var searchInput = searchForm.find('input[type=image]');
			var searchLink = $('<a class="search-input-link" href="javascript:;"></a>');
			
			searchLink.click(
				function() {
					$(this).parents('form')[0].submit();
				}
			);
			
			searchInput.hide();
			searchInput.before(searchLink);
		}
	};
}();

AUI().ready(

	/*
	This function gets loaded when all the HTML, not including the portlets, is
	loaded.
	*/

	function() {
		FPM.init();
	}
);

Liferay.Portlet.ready(

	/*
	This function gets loaded after each and every portlet on the page.

	portletId: the current portlet's id
	node: the Alloy Node object of the current portlet
	*/

	function(portletId, node) {
	}
);

Liferay.on(
	'allPortletsReady',
	/*
	This function gets loaded when everything, including the portlets, is on
	the page.
	*/

	function() {
	  	var _gaq = _gaq || [];
	  	_gaq.push(['_setAccount', 'UA-18689287-1']);
	  	_gaq.push(['_trackPageview']);

 	 	(function() {
 	   		var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
    			ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
    			var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
  		})();
	}
);