(function() {
    'use strict';

    angular
    .module('otscApp')
        .controller('ProductServiceController', ProductServiceController);

    ProductServiceController.$inject = ['$state', '$scope', '$http', 'productmodel', 'template_type', 'ProductServiceService'];

    function ProductServiceController ($state, $scope, $http, productmodel, template_type, ProductServiceService) {
      console.log("in product-service-controller");
      console.log(productmodel);
      $scope.productmodel = productmodel;

      var saveProductRequest = {};
      var vm = this;

      saveProductRequest.name = productmodel.General.name;
      saveProductRequest.description=productmodel.General.description;
      saveProductRequest.lifecycleStatus=productmodel.General.lifecycleStatus;
      saveProductRequest.created_date = new Date();
      saveProductRequest.type = template_type;
      saveProductRequest.template = productmodel.template_name;
      saveProductRequest.product_params = productmodel["Product Specification Parameters"];

      console.log("saveProductRequest:");
      console.log(JSON.stringify(saveProductRequest));

      $http.post("/api/products", saveProductRequest).then(function(data, status, headers, config){
        console.log("after product post:" + JSON.stringify(data.data));

        ProductServiceService.getServicesForType(template_type).then(function(services) {
            console.log("got services for type:");
            console.log(services);

            $scope.services = services;
            $scope.product = data.data;
          });
      });
    }

})();
