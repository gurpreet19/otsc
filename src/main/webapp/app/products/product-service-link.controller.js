(function() {
    'use strict';

    angular
    .module('otscApp')
        .controller('ProductServiceLinkController', ProductServiceLinkController);

    ProductServiceLinkController.$inject = ['$state', '$scope', '$http', 'savedproduct', 'pid', 'sid', 'ProductServiceService'];

    function ProductServiceLinkController ($state, $scope, $http, savedproduct, pid, sid, ProductServiceService) {
      console.log("in product-service-link-controller");
      console.log("selected product id:");
      console.log(pid);
      console.log("selected service id:");
      console.log(sid);

      var updateProductRequest = savedproduct;
      var vm = this;

      updateProductRequest.service_id = sid;
      updateProductRequest.updated_date = new Date();

      console.log("updateProductRequest:");
      console.log(JSON.stringify(updateProductRequest));

      $http.put("/api/products", updateProductRequest).then(function(data, status, headers, config){
        console.log("after product put:" + JSON.stringify(data.data));

      });
    }

})();

