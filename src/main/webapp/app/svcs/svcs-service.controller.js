(function() {
    'use strict';

    angular
    .module('otscApp')
        .controller('SvcServiceController', SvcServiceController);

    SvcServiceController.$inject = ['$state', '$scope', '$http', 'servicemodel', 'template_type'];

    function SvcServiceController ($state, $scope, $http, servicemodel, template_type) {
      console.log("in svc-service-controller");
      console.log(servicemodel);
      $scope.servicemodel = servicemodel;

      var saveServiceRequest = {};
      var vm = this;

      saveServiceRequest.name = servicemodel.General.service_name;
      saveServiceRequest.description=servicemodel.General.description;
      saveServiceRequest.lifecycleStatus=servicemodel.General.lifecycleStatus;
      saveServiceRequest.created_date = new Date();
      saveServiceRequest.type = template_type;
      saveServiceRequest.template = servicemodel.template_name;
      saveServiceRequest.externalId = servicemodel.General.externalId;
      saveServiceRequest.service_params = servicemodel["Service Parameters"];

      console.log("saveServiceRequest:");
      console.log(JSON.stringify(saveServiceRequest));

      $http.post("/api/services", saveServiceRequest).then(function(data, status, headers, config){
        console.log("after service post");
      });
    }

})();
