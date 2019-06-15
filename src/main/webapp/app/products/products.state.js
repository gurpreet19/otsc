(function() {
    'use strict';

    angular
        .module('otscApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('new-product', {
            parent: 'app',
            url: '/new-product',
            data: {
                authorities: []
            },
            views: {
                'content@': {
                    templateUrl: 'app/products/product-new.html',
                    controller: 'ProductsController',
                    controllerAs: 'vm'
                }
            }
        }).state('list-products', {
          parent: 'app',
          url: '/list-products',
          data: {
            authorities: []
          },
          views: {
            'content@': {
              templateUrl: 'app/products/products.html',
              controller: 'ProductsListController',
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
        }).state('link-product-service', {
          parent: 'app',
          url: '/link-product-service',
          data: {
            authorities: []
          },
          params:{
            productmodel: {},
            template_type: {}
          },
          views: {
            'content@': {
              templateUrl: 'app/products/product-service.html',
              controller: 'ProductServiceController',
              contollerAs: 'vm'
            }
          },
          resolve: {
        	  template_type: ['$stateParams', function($stateParams) {
                  console.log("in resolve");
                  console.log($stateParams);
                return $stateParams.template_type;
              }],        	  
            productmodel: ['$stateParams', function($stateParams) {
                console.log("in resolve");
                console.log($stateParams);
                return $stateParams.productmodel;
            }]
          }
        }).state('link-product-service.link', {
            parent: 'link-product-service',
            url: '/link-product-service/link',
            data: {
              authorities: []
            },
            params: {
              pid: null,
              sid: null,
              savedproduct: null
            },
            views: {
              'content@': {
                templateUrl: 'app/products/product-service-link.html',
                controller: 'ProductServiceLinkController',
                contollerAs: 'vm'
              }
            }, 
            resolve: {
              pid: ['$stateParams', function($stateParams) {
                console.log("in link-product-service.link");
                return $stateParams.pid;
              }],
              sid: ['$stateParams', function($stateParams) {
                console.log("in link-product-service.link");
                return $stateParams.sid;
              }],
              savedproduct: ['$stateParams', function($stateParams) {
                console.log("in link-product-service.link");
                return $stateParams.savedproduct;
              }]
            }
          });
    }
})();
