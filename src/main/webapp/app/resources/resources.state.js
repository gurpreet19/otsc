(function() {
    'use strict';

    angular
        .module('otscApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('new-resource', {
            parent: 'app',
            url: '/new-resource',
            data: {
                authorities: []
            },
            views: {
                'content@': {
                    templateUrl: 'app/resources/resource-new.html',
                    controller: 'ResourcesController',
                    controllerAs: 'vm'
                }
            }
        }).state('list-resources', {
          parent: 'app',
          url: '/list-resources',
          data: {
            authorities: []
          },
          views: {
            'content@': {
              templateUrl: 'app/resources/resources.html',
              controller: 'ResourcesListController',
              controllerAs: 'vm'
            }
          },
          params: {
            page: {
              value: '1',
              squash: true
            },
            sort: {
              value: 'id,asc',
              squash: true
            },
            search: null
          },
          resolve: {
            pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                return {
                  page: PaginationUtil.parsePage($stateParams.page),
                  sort: $stateParams.sort,
                  predicate: PaginationUtil.parsePredicate($stateParams.sort),
                  ascending: PaginationUtil.parseAscending($stateParams.sort),
                  search: $stateParams.search
                };
          }]
        }
        }).state('save-resource', {
          parent: 'app',
          url: '/save-resource',
          data: {
            authorities: []
          },
          params:{
            resourcemodel: {}
          },
          views: {
            'content@': {
              templateUrl: 'app/resources/resource-service.html',
              controller: 'ResourceServiceController',
              contollerAs: 'vm'
            }
          },
          resolve: {
            resourcemodel: ['$stateParams', function($stateParams) {
                console.log("in resolve");
                console.log($stateParams);
                return $stateParams.resourcemodel;
            }]
          }
        });
    }
})();
