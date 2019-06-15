(function() {
    'use strict';

    angular
    .module('otscApp')
        .controller('OfferProductController', OfferProductController);

    OfferProductController.$inject = ['$state', '$scope', '$http', 'offermodel', 'template_type', 'OfferProductService'];

    function OfferProductController ($state, $scope, $http, offermodel, template_type, OfferProductService) {
      console.log("in offer-product-controller");
      console.log(offermodel);
      $scope.offermodel = offermodel;

      var saveOfferRequest = {};
      var vm = this;

      saveOfferRequest.name = offermodel.General.offer_name;
      saveOfferRequest.description = offermodel.General.description;
      saveOfferRequest.isSellable = offermodel.General.isSellable;
      saveOfferRequest.lifecycleStatus = offermodel.General.lifecycleStatus;
      saveOfferRequest.validFor_startDateTime = offermodel.General.validFor_startDateTime;
      saveOfferRequest.validFor_endDateTime = offermodel.General.validFor_endDateTime;
      saveOfferRequest.category = offermodel.General.category;
      saveOfferRequest.created_date = new Date();
      saveOfferRequest.type = template_type;
      saveOfferRequest.template = offermodel.template_name;
      saveOfferRequest.basic_pricing_params = offermodel.basic_pricing_parameters;
      saveOfferRequest.addon_pricing_params = offermodel.addon_pricing_parameters;
      delete offermodel.basic_pricing_parameters;
      delete offermodel.addon_pricing_parameters;
      delete offermodel.template_name;
      delete offermodel.General;
      saveOfferRequest.offer_params = offermodel;

      console.log("saveOfferRequest:");
      console.log(JSON.stringify(saveOfferRequest));

      $http.post("/api/offers", saveOfferRequest).then(function(data, status, headers, config){
        console.log("after offer post:" + JSON.stringify(data.data));

        OfferProductService.getProductsForType(template_type).then(function(products) {
          console.log("got products for type:");
          console.log(products);

          $scope.products = products;
          $scope.offer = data.data;
        });
      });
    }

})();
