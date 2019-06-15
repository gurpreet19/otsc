(function() {
    'use strict';
    angular
        .module('otscApp')
        .factory('Offer', Offer);

    Offer.$inject = ['$resource', 'DateUtils'];

    function Offer ($resource, DateUtils) {
        var resourceUrl =  'api/offers/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                  console.log("in transformResponse...");
                  console.log(data);
                    if (data) {
                        data = angular.fromJson(data);
                        data.created_date = DateUtils.convertLocalDateFromServer(data.created_date);
                        data.offermodel = {};
                        data.offermodel["Basic Pricing Parameters"] = data.basic_pricing_params;
                        data.offermodel["Addon Pricing Parameters"] = data.addon_pricing_params;
                    }
                    console.log("after transform");
                    console.log(data);

                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.created_date = DateUtils.convertLocalDateToServer(copy.created_date);
                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.created_date = DateUtils.convertLocalDateToServer(copy.created_date);
                    return angular.toJson(copy);
                }
            }
        });
    }
})();
