(function() {
    'use strict';

    angular
    .module('otscApp')
        .controller('SvcsController', SvcsController);

    SvcsController.$inject = ['$state', '$scope', '$http', 'ServiceTemplate'];

    function SvcsController ($state, $scope, $http, ServiceTemplate) {
      console.log("hello, otsc, from service controller");
      $scope.template_type = "Service";
      
      $http.get("/api/templates/services/").then(
		  function(data, status, headers, config) {
	          console.log("in services controller:");
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
			    "title": "Save"
			  };
			
			  update_form();
			  
		      function update_form(tmpl) {
		          console.log("inside update_form");

		          if(tmpl !== null && tmpl !== undefined) {
		        	  ServiceTemplate.get_template_schema_form(tmpl).then(function(jsf) {
		              console.log("return value from get_tsf");
		              console.log(jsf);
		            
		              $scope.serviceschema = { type: "object", properties: jsf[0] };
		              jsf[1].unshift(template_select);
		              jsf[1].push(submit_btn);
		              $scope.serviceform = jsf[1];
		              $scope.$broadcast('schemaFormRedraw');
		              $scope._type = jsf[2];
		            });
		          } else {
		            console.log("no template selected yet");
		            $scope.serviceschema = {
		              type: "object",
		              properties: { template_name : { "type": "string" } }
		            };

		            $scope.serviceform = [template_select,submit_btn];
		            $scope.servicemodel = {};
		          }
		      }

		      $scope.onSubmit = function(form) {
		          // First we broadcast an event so all fields validate themselves
		          $scope.$broadcast('schemaFormValidate');

		          // Then we check if the form is valid
		          if (form.$valid) {
		            console.log("form validation here");
		            console.log("model:");
		            console.log(JSON.stringify($scope.servicemodel));
		            console.log("_type is:");
		            console.log(JSON.stringify($scope._type));
		            // ... do whatever you need to do with your data.
		            $state.go('save-service', {servicemodel: $scope.servicemodel, template_type: $scope._type});
		          }
		      };
		  }

      );
    }

})();

