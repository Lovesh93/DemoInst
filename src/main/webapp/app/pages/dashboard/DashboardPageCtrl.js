(function() {
	'use strict';

	angular.module('ECommerce.pages.dashboard').controller('DashboardPageCtrl',
			DashboardPageCtrl);

	/** @ngInject */
	function DashboardPageCtrl($timeout, baConfig, baUtil) {
		var self = this;
		
		self.init = function() {
			self.orders = [];
			self.item = {};
			
			self.item.names=[
				{"name":"Item1"},
				{"name":"Item2"},
				{"name":"Item3"},
			];
			self.item.codes=[
				{"code":"Code1"},
				{"code":"Code2"},
				{"code":"Code3"},
				
			];
			
		}
		
		self.refresh = function(){
			self.init();
		}
		
		self.deleteOrder = function(id){
			self.orders.splice(id,1);
		}
		
		self.addOrder = function() {
			self.orders.push({"index":1});
		}
		var pieColor = baUtil.hexToRGB(baConfig.colors.defaultText, 0.2);
	    self.charts = [{
	      color: pieColor,
	      description: 'Total sales',
	      stats: '57,820',
	      icon: 'person',
	    }, {
	      color: pieColor,
	      description: 'Pending',
	      stats: '$ 89,745',
	      icon: 'money',
	    }, {
	      color: pieColor,
	      description: 'On Rent',
	      stats: '178,391',
	      icon: 'face',
	    }, {
	      color: pieColor,
	      description: 'Booked',
	      stats: '32,592',
	      icon: 'refresh',
	    }
	    ];

	    function getRandomArbitrary(min, max) {
	      return Math.random() * (max - min) + min;
	    }

	    function loadPieCharts() {
	      $('.chart').each(function () {
	        var chart = $(this);
	        chart.easyPieChart({
	          easing: 'easeOutBounce',
	          onStep: function (from, to, percent) {
	            $(this.el).find('.percent').text(Math.round(percent));
	          },
	          barColor: chart.attr('rel'),
	          trackColor: 'rgba(0,0,0,0)',
	          size: 84,
	          scaleLength: 0,
	          animation: 2000,
	          lineWidth: 9,
	          lineCap: 'square',
	        });
	      });

	      $('.refresh-data').on('click', function () {
	        updatePieCharts();
	      });
	    }

	    function updatePieCharts() {
	      $('.pie-charts .chart').each(function(index, chart) {
	        $(chart).data('easyPieChart').update(getRandomArbitrary(55, 90));
	      });
	    }
	    
	    self.init();

	    $timeout(function () {
	      loadPieCharts();
	      updatePieCharts();
	    }, 1000);
	}
})();