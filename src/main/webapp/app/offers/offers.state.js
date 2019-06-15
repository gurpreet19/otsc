(function() {
    'use strict';

    angular
        .module('otscApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('new-offer', {
            parent: 'app',
            url: '/new-offer',
            data: {
                authorities: []
            },
            views: {
                'content@': {
                    templateUrl: 'app/offers/offer-new.html',
                    controller: 'OffersController',
                    controllerAs: 'vm'
                }
            }
        }).state('list-offers', {
          parent: 'app',
          url: '/list-offers',
          data: {
            authorities: []
          },
          views: {
            'content@': {
              templateUrl: 'app/offers/offers.html',
              controller: 'ListOffersController',
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
        }).state('create-offer-step2', {
          parent: 'app',
          url: '/create-offer-step2',
          data: {
            authorities: []
          },
          params:{
            offermodel: {},
            template_type: {}
          },
          views: {
            'content@': {
              templateUrl: 'app/offers/offer-step2.html',
              controller: 'OfferProductControllerStep2',
              contollerAs: 'vm'
            }
          },
          resolve: {
            template_type: ['$stateParams', function($stateParams) {
                console.log("in resolve");
                console.log($stateParams);
              return $stateParams.template_type;
            }],
            offermodel: ['$stateParams', function($stateParams) {
                console.log("in resolve");
                console.log($stateParams);
                return $stateParams.offermodel;
            }]
          }
        }).state('link-offer-product', {
          parent: 'app',
          url: '/link-offer-product',
          data: {
            authorities: []
          },
          params:{
            offermodel: {},
            template_type: {}
          },
          views: {
            'content@': {
              templateUrl: 'app/offers/offer-product.html',
              controller: 'OfferProductController',
              contollerAs: 'vm'
            }
          },
          resolve: {
            template_type: ['$stateParams', function($stateParams) {
                console.log("in resolve");
                console.log($stateParams);
              return $stateParams.template_type;
            }],
            offermodel: ['$stateParams', function($stateParams) {
                console.log("in resolve");
                console.log($stateParams);
                return $stateParams.offermodel;
            }]
          }
        }).state('list-offers.offer-detail', {
          parent: 'list-offers',
          url: '/offer-detail',
          data: {
            authorities: []
          },
          params: {
            id: null
          },
          views: {
            'content@': {
              templateUrl: 'app/offers/offer-detail.html',
              controller: 'OfferDetailController',
              contollerAs: 'detailvm'
            }
          },
          resolve: {
            selectedOffer: ['$stateParams', 'Offer', function($stateParams, Offer) {
                console.log("in offer-detail state, resolve: stateParams.id=" + $stateParams.id);
              return Offer.get({id : $stateParams.id}).$promise;
            }
            ],
            previousState: ["$state", function ($state) {
                var currentStateData = {
                  name: $state.current.name || 'list-offers',
                  params: $state.params,
                  url: $state.href($state.current.name, $state.params)
                };
                return currentStateData;
            }]
          }
        }).state('link-offer-product.link', {
          parent: 'link-offer-product',
          url: '/link-offer-product/link',
          data: {
            authorities: []
          },
          params: {
            oid: null,
            pid: null,
            savedoffer: null
          },
          views: {
            'content@': {
              templateUrl: 'app/offers/offer-product-link.html',
              controller: 'OfferProductLinkController',
              contollerAs: 'vm'
            }
          }, 
          resolve: {
            oid: ['$stateParams', function($stateParams) {
              console.log("in link-offer-product.link");
              return $stateParams.oid;
            }],
            pid: ['$stateParams', function($stateParams) {
              console.log("in link-offer-product.link");
              return $stateParams.pid;
            }],
            savedoffer: ['$stateParams', function($stateParams) {
              console.log("in link-offer-product.link");
              return $stateParams.savedoffer;
            }]
          }
        });
    }
})();
