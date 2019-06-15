(function() {
  'use strict';

  angular.module('otscApp')
  .config(stateConfig);

  stateConfig.$inject = ['$stateProvider'];

  function stateConfig($stateProvider) {
    $stateProvider.state('new-service', {
      parent: 'app',
      url: '/new-service',
      data: {
        authorities: []
      },
      views: {
          'content@': {
            templateUrl: 'app/svcs/service-new.html',
            controller: 'SvcsController',
            controllerAs: 'vm'
          }
      }
    }).state('list-services', {
      parent: 'app',
      url: '/list-services',
      data: {
        authorities: []
      },
      views: {
          'content@': {
            templateUrl: 'app/svcs/service-list.html',
            controller: 'SvcsListController',
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
    }).state('save-service', {
        parent: 'app',
        url: '/save-service',
        data: {
          authorities: []
        },
        params:{
          servicemodel: {},
          template_type: {}
        },
        views: {
          'content@': {
            templateUrl: 'app/svcs/save-service.html',
            controller: 'SvcServiceController',
            contollerAs: 'vm'
          }
        },
        resolve: {
            template_type: ['$stateParams', function($stateParams) {
                console.log("in resolve");
                console.log($stateParams);
              return $stateParams.template_type;
            }],
          servicemodel: ['$stateParams', function($stateParams) {
              console.log("in resolve");
              console.log($stateParams);
              return $stateParams.servicemodel;
          }]
        }
      });
  }
})();
