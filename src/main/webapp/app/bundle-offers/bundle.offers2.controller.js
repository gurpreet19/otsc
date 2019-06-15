(function() {
	'use strict';

	angular
	.module('otscApp')
	.controller('BundleOfferServiceControllerStep2', BundleOfferServiceControllerStep2);

	BundleOfferServiceControllerStep2.$inject = ['$state', '$scope', '$http', 'bundleoffermodel', 'template_type', 'BundleOfferTemplate', 'Offer'];

	function BundleOfferServiceControllerStep2 ($state, $scope, $http, bundleoffermodel, template_type, BundleOfferTemplate, Offer) {
		console.log("in bundle-offer-controller-step2");
		console.log("bundleoffermodel:" + JSON.stringify(bundleoffermodel));
		$scope.template_message = "Bundle Offer - Link Offers";

		$http.get("/api/offers").then(
				function(data, status, headers, config) {
					console.log("got offers:");
					console.log(data);
					console.log("length" + data.data.length);
		            var filtered_products = data.data.filter(function(obj){
		                return obj.type == template_type;
	            	});
					$scope.offers = filtered_products;
					console.log("offers" + $scope.offers);
				}
		)

		function redrawForm(modelValue, form){
			$scope.$broadcast('schemaFormRedraw');
		}

		$scope.check= function(offers) { 
			var selectedOffers = [];
			for(var i in offers){
				if(offers[i].SELECTED=='Y'){
					selectedOffers.push(offers[i].id);
				}
			}

			$scope.selectedOffers=selectedOffers;
			console.log(selectedOffers);
			console.log(JSON.stringify(bundleoffermodel));
			$state.go('save-bundle-offer', {bundleoffermodel: bundleoffermodel, selectedOffers: $scope.selectedOffers, template_type:template_type});
		}

	}

})();