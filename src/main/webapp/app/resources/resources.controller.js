(function() {
    'use strict';

    angular
    .module('otscApp')
        .controller('ResourcesController', ResourcesController);

        ResourcesController.$inject = ['$state', '$scope', '$http', 'ResourceTemplate'];

    function ResourcesController ($state, $scope, $http, ResourceTemplate) {
      console.log("hello resources");

      $http.get("/api/templates/resources/").then(
        function(data, status, headers, config) {
          console.log("in resources controller:");
          var templates = data.data;
          console.log(templates);

      var template_select = {
        "title": "Template Name",
        "key": "template_name",
        "type":"select",
        "titleMap":templates,
        "onChange":function(modelValue, form) {
          if(modelValue !== null && modelValue !== undefined) {
            $scope.template_type = "Resource";
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
          ResourceTemplate.get_template_schema_form(tmpl).then(function(jsf) {
            console.log("return value from get_tsf");
            console.log(jsf);
          
            $scope.resourceschema = { type: "object", properties: jsf[0] };
            jsf[1].unshift(template_select);
            jsf[1].push(submit_btn);
            $scope.resourceform = jsf[1];
            $scope.$broadcast('schemaFormRedraw');
          });
        } else {
          console.log("no template selected yet");
          $scope.resourceschema = {
            type: "object",
            properties: { template_name : { "type": "string" } }
          };

          $scope.resourceform = [template_select,submit_btn];
          $scope.resourcemodel = {};
        }
      }

      $scope.onSubmit = function(form) {
        // First we broadcast an event so all fields validate themselves
        $scope.$broadcast('schemaFormValidate');

        // Then we check if the form is valid
        if (form.$valid) {
          console.log("form validation here");
          console.log("model:");
          console.log(JSON.stringify($scope.resourcemodel));
          // ... do whatever you need to do with your data.
          $state.go('save-resource', {resourcemodel: $scope.resourcemodel});
        }
      };
        }
      );
    }

})();
