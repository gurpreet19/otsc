(function() {
    'use strict';

    angular
        .module('otscApp')
        .controller('OfferDetailController', OfferDetailController);

        OfferDetailController.$inject = ['$scope', '$stateParams', 'Offer', 'selectedOffer', 'previousState', 'OfferTemplate'];

        function OfferDetailController($scope, $stateParams, Offer, selectedOffer, previousState, OfferTemplate) {
          console.log("now in offer detail controller - this");
          console.log(this);
          var vm = this;

          $scope.selectedOffer = selectedOffer;
          $scope.previousState = previousState.name;

          console.log("now in offer detail controller - vm");
          console.log($scope);

          OfferTemplate.get_template_schema_form(selectedOffer.template).then(function(jsf) {
            console.log("return value from get_tsf");
            console.log(jsf);

            $scope.offerdetailschema = { type: "object", properties: jsf[0] };
            $scope.offerdetailform = jsf[1];
            $scope.offerdetailmodel = selectedOffer.offermodel;
            $scope.$broadcast('schemaFormRedraw');
          });
        }
})();
