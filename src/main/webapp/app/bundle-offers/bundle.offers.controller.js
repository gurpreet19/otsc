(function() {
    'use strict';

    angular
    .module('otscApp')
        .controller('BundleOffersController', BundleOffersController);

        BundleOffersController.$inject = ['$state', '$scope', '$http', 'BundleOfferTemplate'];

    function BundleOffersController ($state, $scope, $http, BundleOfferTemplate) {
      console.log("hello, otsc");
      $scope.template_type = "Bundle Offer";

      $http.get("/api/templates/bundle-offers/").then(
        function(data, status, headers, config) {
          console.log("in bundle offers controller:");
          var templates = data.data;
          console.log(templates);

	      var template_select = {
	        "title": "Template Name",
	        "key": "template_name",
	        "type":"select",
	        "titleMap":templates,
	        "onChange":function(modelValue, form) {
	          if(modelValue !== null && modelValue !== undefined) {
	            update_form(modelValue);
	          }
	        }
	      };
      
	      var submit_btn = {
	        "type": "submit",
	        "style": "btn-info",
	        "title": "Next"
	      };

	      update_form();

	      function update_form(tmpl) {
	        console.log("inside update_form");
	
	        if(tmpl !== null && tmpl !== undefined) {
	        	BundleOfferTemplate.get_template_schema_form(tmpl).then(function(jsf) {
	            console.log("return value from get_tsf");
	            console.log(jsf);
	
	            $scope.bundleofferschema = { type: "object", properties: jsf[0] };
	            jsf[1].unshift(template_select);
	            jsf[1].push(submit_btn);
	            $scope.bundleofferform = jsf[1];
	            $scope.$broadcast('schemaFormRedraw');
	            $scope._type = jsf[2];
	            console.log("schema :" + JSON.stringify($scope.bundleofferschema));
	            console.log("form:" + JSON.stringify($scope.bundleofferform));
	          });
	        } else {
	          console.log("no template selected yet");
	          $scope.bundleofferschema = {
	            type: "object",
	            properties: { template_name : { "type": "string" } }
	          };
	
	          $scope.bundleofferform = [template_select,submit_btn];
	          $scope.bundleoffermodel = {};
	        }
	      }

	      $scope.onSubmit = function(form) {
	        // First we broadcast an event so all fields validate themselves
	        $scope.$broadcast('schemaFormValidate');
	
	        // Then we check if the form is valid
	        if (form.$valid) {
	          console.log("form validation here");
	          console.log("model:");
	          console.log(JSON.stringify($scope.bundleoffermodel));
	          console.log("_type is:");
	          console.log(JSON.stringify($scope._type));
	          // ... do whatever you need to do with your data.
	          $state.go('create-bundle-offer-step2', {bundleoffermodel: $scope.bundleoffermodel, template_type:$scope._type});
	        }
	      };
        }
      );
    }

})();
