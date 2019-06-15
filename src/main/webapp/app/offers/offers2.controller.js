(function() {
    'use strict';

    angular
    .module('otscApp')
        .controller('OfferProductControllerStep2', OfferProductControllerStep2);

    OfferProductControllerStep2.$inject = ['$state', '$scope', '$http', 'offermodel', 'template_type', 'OfferTemplate'];

    function OfferProductControllerStep2 ($state, $scope, $http, offermodel, template_type, OfferTemplate) {
      console.log("in offer-product-controller-step2");
      console.log(offermodel);

      function redrawForm(modelValue, form){
        $scope.$broadcast('schemaFormRedraw');
      }

      // var bundled_prods = [{"value":"A", "name":"a"}, {"value":"B", "name":"b"}, {"value":"C", "name":"c"}];
      // var bundle_select = {
      //   "title": "Bundled Products",
      //   "key": "bundled_products1",
      //   "type":"checkboxes",
      //   "titleMap":bundled_prods,
      //   "onChange": function (modelValue, form) {
      //    if(modelValue !== null && modelValue !== undefined) {
      //      var modelValueStr = JSON.stringify(modelValue);
      //      console.log("clicked:" + modelValueStr);
      //      //console.log("typeof clicked:" + typeof modelValueStr);
      //      //console.log("clicked contains B: " + modelValueStr.indexOf("B,2"));
      //      //console.log("clicked contains B: > -1" + (modelValueStr.indexOf("B,2") > -1));
      //      //$scope.$broadcast('schemaFormRedraw');
      //    }
      //  }
      // };
      var submit_btn = {
        "type": "submit",
        "style": "btn-info",
        "title": "Next"
      };
      $scope.offermodel2 = {};
      if(offermodel.template_name !== undefined && offermodel.template_name !== null) {
      OfferTemplate.get_template_schema_form_main(offermodel.template_name, "step2_parameters").then(function(step2jsf) {
        console.log("return value from get_tsf");
        console.log(step2jsf);
        //step2jsf[1].unshift(bundle_select);

        OfferTemplate.get_template_schema_form_offer(offermodel.template_name).then(function(offerparamsjsf) {
          var mergejsfschema = merge_options(step2jsf[0], offerparamsjsf[0]);
          var mergejsfform = step2jsf[1].concat(offerparamsjsf[1]);
          $scope.offerschema2 = {
            type: "object",
            properties: {
              "Plans":{
                type:"array",
                items: {
                  type: "object",
                  properties: mergejsfschema
                }
              }
            }
          };
          if(offermodel.General.offer_type === "single") {
            $scope.offerschema2.properties.Plans.maxItems = 1;
          }
          var Plans = [];
          Plans[0] = {"key":"Plans",
            "type":"array",
          "add":"Add More Plans",
          "style":{
            "add":"btn-success"
          }};
          Plans[0].items=mergejsfform;
          Plans.push(submit_btn);
          $scope.offerform2 = Plans;
          $scope.$broadcast('schemaFormRedraw');
          $scope._type = step2jsf[2];
          $scope.step2jsf = mergejsfschema;// save for next page
          $scope.template_message = "Create Offer - Step 2";
          console.log("in step2:");
          console.log("schema :" + JSON.stringify($scope.offerschema2));
          console.log("form:" + JSON.stringify($scope.offerform2));
        });
      });
      }

      $scope.onSubmit = function(form) {
        // First we broadcast an event so all fields validate themselves
        $scope.$broadcast('schemaFormValidate');

        // Then we check if the form is valid
        if (form.$valid) {
          console.log("form validation here");
          console.log("model:");
          console.log(JSON.stringify(offermodel));
          console.log(JSON.stringify($scope.offermodel2));
          console.log("_type is:");
          console.log(JSON.stringify($scope._type));
          // ... do whatever you need to do with your data.
          $state.go('link-offer-product', {offermodel: merge_options(offermodel, $scope.offermodel2), template_type:$scope._type});
        }
      };
    }

    function merge_options(obj1,obj2){
        var obj3 = {};
        for (var attrname1 in obj1) { obj3[attrname1] = obj1[attrname1]; }
        for (var attrname2 in obj2) { obj3[attrname2] = obj2[attrname2]; }
        return obj3;
    }

})();

