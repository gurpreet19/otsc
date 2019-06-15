(function() {
    'use strict';

    angular
    .module('otscApp')
        .controller('OffersController', OffersController);

        OffersController.$inject = ['$state', '$scope', '$http', 'OfferTemplate'];

    function OffersController ($state, $scope, $http, OfferTemplate) {
      console.log("hello, otsc");

      $http.get("/api/templates/offers/").then(
        function(data, status, headers, config) {
          console.log("in offers controller:");
          var templates = data.data;
          console.log(templates);

          //var templates = [
            //{ value:"HomeBB_Offer_Template.yaml", name:"Home BB Offer Template" },
            //{ value:"PCRF.yaml", name:"PCRF Resource Template" }
      //];

      var template_select = {
        "title": "Template Name",
        "key": "template_name",
        "type":"select",
        "titleMap":templates,
        "onChange":function(modelValue, form) {
          if(modelValue !== null && modelValue !== undefined) {
            $scope.template_message = "Create Offer - Step 1";
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
          OfferTemplate.get_template_schema_form_main(tmpl, "step1_parameters").then(function(jsf) {
            console.log("return value from get_tsf");
            console.log(jsf);

            $scope.offerschema = { type: "object", properties: jsf[0] };
            jsf[1].unshift(template_select);
            jsf[1].push(submit_btn);
            $scope.offerform = jsf[1];
            $scope.$broadcast('schemaFormRedraw');
            $scope._type = jsf[2];
            $scope.jsf = jsf;// save for next page
            $scope.tmpl = tmpl;
            console.log("schema :" + JSON.stringify($scope.offerschema));
            console.log("form:" + JSON.stringify($scope.offerform));
          });
        } else {
          console.log("no template selected yet");
          $scope.offerschema = {
            type: "object",
            properties: { template_name : { "type": "string" } }
          };

          $scope.offerform = [template_select,submit_btn];
          $scope.offermodel = {};
        }
      }

      $scope.onSubmit = function(form) {
        // First we broadcast an event so all fields validate themselves
        $scope.$broadcast('schemaFormValidate');

        // Then we check if the form is valid
        if (form.$valid) {
          console.log("form validation here");
          console.log("model:");
          console.log(JSON.stringify($scope.offermodel));
          console.log("_type is:");
          console.log(JSON.stringify($scope._type));
          // ... do whatever you need to do with your data.
          $state.go('create-offer-step2', {offermodel: $scope.offermodel, template_type:$scope._type});
        }
      };
        }
      );
    }

})();
