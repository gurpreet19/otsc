(function() {
    'use strict';

    angular
    .module('otscApp')
        .controller('OfferProductLinkController', OfferProductLinkController);

    OfferProductLinkController.$inject = ['$state', '$scope', '$http', 'savedoffer', 'oid', 'pid', 'OfferProductService'];

    function OfferProductLinkController ($state, $scope, $http, savedoffer, oid, pid, OfferProductService) {
      console.log("in offer-product-link-controller");
      console.log("selected offer id:");
      console.log(oid);
      console.log("selected product id:");
      console.log(pid);

      var updateOfferRequest = savedoffer;
      var vm = this;

      updateOfferRequest.product_id = pid;
      updateOfferRequest.updated_date = new Date();

      console.log("updateOfferRequest:");
      console.log(JSON.stringify(updateOfferRequest));

      $http.put("/api/offers", updateOfferRequest).then(function(data, status, headers, config){
        console.log("after offer put:" + JSON.stringify(data.data));

      });
    }

})();

