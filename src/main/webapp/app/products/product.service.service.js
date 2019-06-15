(function() {
    'use strict';
    angular
    .module('otscApp')
    .factory('ProductServiceService', ProductServiceService);

    ProductServiceService.$inject = ['$http'];

    function ProductServiceService ($http) {
      var factory = {};

      factory.getServicesForType = function(product_type) {
        console.log("got request for services of product type: " + product_type);
        return $http.get("/api/services").then(
          function(data, status, headers, config) {
            console.log("got services:");
            console.log(data);            
            var filtered_services = data.data.filter(function(obj){
              return obj.type == product_type;
            });

            return filtered_services;
          }
        );
      };
      return factory;
    }
})();

