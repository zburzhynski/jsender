angular.module('jsender', ['jsender.filters', 'jsender.services', 'jsender.directives', 'jsender.controllers']).
config(['$routeProvider', function ($routeProvider) {
    $routeProvider.when('/view1', {templateUrl: 'pages/settings.html', controller: 'SettingController'});
    $routeProvider.otherwise({redirectTo: '/view1'});
}]);

