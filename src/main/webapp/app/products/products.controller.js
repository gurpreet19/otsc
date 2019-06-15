(function() {
    'use strict';

    angular
    .module('otscApp')
        .controller('ProductsController', ProductsController);

        ProductsController.$inject = ['$state', '$scope', '$http', 'ProductTemplate'];

    function ProductsController ($state, $scope, $http, ProductTemplate) {
      console.log("hello products");

      $http.get("/api/templates/products/").then(
        function(data, status, headers, config) {
          console.log("in products controller:");
          var templates = data.data;
          console.log(templates);

      var template_select = {
        "title": "Template Name",
        "key": "template_name",
        "type":"select",
        "titleMap":templates,
        "onChange":function(modelValue, form) {
          if(modelValue !== null && modelValue !== undefined) {
            $scope.template_type = "Product";
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
          ProductTemplate.get_template_schema_form(tmpl).then(function(jsf) {
            console.log("return value from get_tsf");
            console.log(jsf);
          
            $scope.productschema = { type: "object", properties: jsf[0] };
            jsf[1].unshift(template_select);
            jsf[1].push(submit_btn);
            $scope.productform = jsf[1];
            $scope.$broadcast('schemaFormRedraw');
            $scope._type = jsf[2];
          });
        } else {
          console.log("no template selected yet");
          $scope.productschema = {
            type: "object",
            properties: { template_name : { "type": "string" } }
          };

          $scope.productform = [template_select,submit_btn];
          $scope.productmodel = {};
        }
      }

      $scope.onSubmit = function(form) {
        // First we broadcast an event so all fields validate themselves
        $scope.$broadcast('schemaFormValidate');

        // Then we check if the form is valid
        if (form.$valid) {
          console.log("form validation here");
          console.log("model:");
          console.log(JSON.stringify($scope.productmodel));
          console.log("_type is:");
          console.log(JSON.stringify($scope._type));
          // ... do whatever you need to do with your data.
          $state.go('link-product-service', {productmodel: $scope.productmodel, template_type: $scope._type});
        }
      };
        }
      );
    }

})();
