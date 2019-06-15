(function() {
    'use strict';

    angular
        .module('otscApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('new-bundle-offer', {
            parent: 'app',
            url: '/new-bundle-offer',
            data: {
                authorities: []
            },
            views: {
                'content@': {
                    templateUrl: 'app/bundle-offers/bundle-offer-new.html',
                    controller: 'BundleOffersController',
                    controllerAs: 'vm'
                }
            }
        }).state('list-bundle-offers', {
          parent: 'app',
          url: '/list-bundle-offers',
          data: {
            authorities: []
          },
          views: {
            'content@': {
              templateUrl: 'app/bundle-offers/bundle-offers-list.html',
              controller: 'ListBundleOffersController',
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
        }).state('save-bundle-offer', {
            parent: 'app',
            url: '/save-bundle-offer',
            data: {
              authorities: []
            },
            params:{
              bundleoffermodel: {},
              template_type: {},
              selectedOffers: {}
            },
            views: {
              'content@': {
                templateUrl: 'app/bundle-offers/save-bundle-offer.html',
                controller: 'BundleOfferServiceController',
                contollerAs: 'vm'
              }
            },
            resolve: {
                template_type: ['$stateParams', function($stateParams) {
                    console.log("in resolve");
                    console.log($stateParams);
                  return $stateParams.template_type;
                }],
                bundleoffermodel: ['$stateParams', function($stateParams) {
                  console.log("in resolve");
                  console.log($stateParams);
                  return $stateParams.bundleoffermodel;
                }],
                selectedOffers: ['$stateParams', function($stateParams) {
                    console.log("in resolve");
                    console.log($stateParams);
                    return $stateParams.selectedOffers;
                  }]
            }
          }).state('create-bundle-offer-step2', {
              parent: 'app',
              url: '/create-bundle-offer-step2',
              data: {
                authorities: []
              },
              params:{
                bundleoffermodel: {},
                template_type: {}
              },
              views: {
                'content@': {
                  templateUrl: 'app/bundle-offers/bundle.offer-step2.html',
                  controller: 'BundleOfferServiceControllerStep2',
                  contollerAs: 'vm'
                }
              },
              resolve: {
                  template_type: ['$stateParams', function($stateParams) {
                      console.log("in resolve");
                      console.log($stateParams);
                    return $stateParams.template_type;
                  }],
                  bundleoffermodel: ['$stateParams', function($stateParams) {
                    console.log("in resolve");
                    console.log($stateParams);
                    return $stateParams.bundleoffermodel;
                }]
              }
            }).state('publish-bundle-offer', {
                parent: 'app',
                url: '/publish-bundle-offer',
                data: {
                    authorities: []
                },
                views: {
                    'content@': {
                        templateUrl: 'app/bundle-offers/bundle-offers-publish.html',
                        controller: 'PublishBundleOffersController',
                        controllerAs: 'vm'
                    }
                }
            });
    }
})();
