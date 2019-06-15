(function() {
	'use strict';

	angular
	.module('otscApp')
	.controller('PublishBundleOffersController', PublishBundleOffersController);

	PublishBundleOffersController.$inject = ['$state', '$scope', '$http'];

	function PublishBundleOffersController ($state, $scope, $http) {
		console.log("in bundle.offers.publish");

		$http.get("/api/bundle-offers").then(
				function(data, status, headers, config) {
					console.log("got bundle offers:");
					console.log(data);
					console.log("length" + data.data.length);
					$scope.bundleOffers = data.data;
					console.log("bundle offers" + $scope.bundleOffers);
				}
		)

		function redrawForm(modelValue, form){
			$scope.$broadcast('schemaFormRedraw');
		}

		$scope.check= function(bundleOffers) {
			for(var i in bundleOffers){
				if(bundleOffers[i].SELECTED=='Y'){
					console.log(bundleOffers[i]);
					console.log("bundleOffer id" + bundleOffers[i].id);
					for (var j in bundleOffers[i].offer_ids){
						console.log("bundleOffer OfferId" + bundleOffers[i].offer_ids[j]);
						//get offers
						$http.get("/api/offers/"+bundleOffers[i].offer_ids[j]).then(
							function(offer, status, headers, config) {
								console.log("got offer:");
								console.log(offer);
								console.log("offer id" + offer.data.id);
								console.log("offer product id" + offer.data.product_id);
								
								//get product
								$http.get("/api/products/"+offer.data.product_id).then(
									function(product, status, headers, config) {
										console.log("got product:");
										console.log(product);
										console.log("product id" + product.data.id);
										console.log("product service id" + product.data.service_id);	
										
										if(product.data.service_id !== null){
											//get service
											$http.get("/api/services/"+product.data.service_id).then(
												function(service, status, headers, config) {
													console.log("got service:");
													console.log(service);
													console.log("service id" + service.data.id);
												}
											)
											$http.post("/api/publish/service/"+product.data.service_id).then(function(data, status, headers, config){
											  console.log("after service publish with id" + "/api/publish/service/"+data.data.id);
											});											
										}
									}
								)
								$http.post("/api/publish/product/"+offer.data.product_id).then(function(data, status, headers, config){
								  console.log("after product publish with id" + "/api/publish/product/"+data.data.id);
								});
							}
						)
						$http.post("/api/publish/offer/"+bundleOffers[i].offer_ids[j]).then(function(data, status, headers, config){
						  console.log("after offer publish with id" + "/api/publish/offer/"+data.data.id);
						});
					}
					$http.post("/api/publish/bundle-offer/"+bundleOffers[i].id).then(function(data, status, headers, config){
					  console.log("after bundle offer publish with id" + "/api/publish/bundle-offer/"+data.data.id);
					});
				}
			}
		}
	}

})();