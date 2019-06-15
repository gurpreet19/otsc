(function() {
    'use strict';
    angular
        .module('otscApp')
        .factory('Resource', Resource);

    Resource.$inject = ['$resource', 'DateUtils'];

    function Resource ($resource, DateUtils) {
        var resourceUrl =  'api/resources/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.created_date = DateUtils.convertLocalDateFromServer(data.created_date);
                    }
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
