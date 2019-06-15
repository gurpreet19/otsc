(function() {
    'use strict';
    angular
    .module('otscApp')
    .factory('OfferProductService', OfferProductService);

    OfferProductService.$inject = ['$http'];

    function OfferProductService ($http) {
      var factory = {};

      factory.getProductsForType = function(offer_type) {
        console.log("got request for products of offer type: " + offer_type);
        return $http.get("/api/products").then(
          function(data, status, headers, config) {
            console.log("got products:");
            console.log(data);
            var filtered_products = data.data.filter(function(obj){
              return obj.type == offer_type;
            });

            return filtered_products;
          }
        );
      };
      return factory;
    }
})();

