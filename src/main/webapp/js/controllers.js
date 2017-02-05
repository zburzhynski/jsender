var app = angular.module('jsender.controllers', []);

app.controller('SettingController', ['$scope', 'SettingFactory', function ($scope, SettingFactory) {
    SettingFactory.get({}, function (settingFactory) {
        $scope.settingName = settingFactory.name;
    })
}]);