var services = angular.module('jsender.services', ['ngResource']);

services.factory('SettingFactory', function ($resource) {
    return $resource('/jsender/rest/settings', {}, {
        query: {
            method: 'GET',
            params: {},
            isArray: false
        }
    })
});