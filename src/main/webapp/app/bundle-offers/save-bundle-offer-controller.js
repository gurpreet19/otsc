(function() {
    'use strict';

    angular
    .module('otscApp')
        .controller('BundleOfferServiceController', BundleOfferServiceController);

    BundleOfferServiceController.$inject = ['$state', '$scope', '$http', 'bundleoffermodel', 'selectedOffers', 'template_type'];

    function BundleOfferServiceController ($state, $scope, $http, bundleoffermodel, selectedOffers, template_type) {
      console.log("in BundleOfferServiceController");
      console.log(bundleoffermodel);
      console.log(selectedOffers);
      console.log(template_type);
      $scope.bundleoffermodel = bundleoffermodel;

      var saveBundleOfferRequest = {};
      var vm = this;

      saveBundleOfferRequest.name = bundleoffermodel.General.bundle_offer_name;
      saveBundleOfferRequest.description = bundleoffermodel.General.description;
      saveBundleOfferRequest.isSellable = bundleoffermodel.General.isSellable;
      saveBundleOfferRequest.lifecycleStatus = bundleoffermodel.General.lifecycleStatus;
      saveBundleOfferRequest.validFor_startDateTime = bundleoffermodel.General.validFor_startDateTime;
      saveBundleOfferRequest.validFor_endDateTime = bundleoffermodel.General.validFor_endDateTime;
      saveBundleOfferRequest.created_date = new Date();
      saveBundleOfferRequest.type = template_type;
      saveBundleOfferRequest.template = bundleoffermodel.template_name;
      saveBundleOfferRequest.offer_ids = selectedOffers;

      console.log("saveBundleOfferRequest:");
      console.log(JSON.stringify(saveBundleOfferRequest));

      $http.post("/api/bundle-offers", saveBundleOfferRequest).then(function(data, status, headers, config){
        console.log("after bundle offer post");
      });
    }

})();
