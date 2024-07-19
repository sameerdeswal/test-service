var app = angular.module("AppController", []);

app.controller("AppController", function($scope, $http) {


    const key = CryptoJS.enc.Utf8.parse('aVerySecretKey12'); // 16-byte key for AES-128

    function encryptData(data, key) {
        const encrypted = CryptoJS.AES.encrypt(JSON.stringify(data), key, {
            mode: CryptoJS.mode.ECB,
            padding: CryptoJS.pad.Pkcs7
        });
        return encrypted.toString();
    }

    function decryptData(encryptedData, key) {
        const decrypted = CryptoJS.AES.decrypt(encryptedData, key, {
            mode: CryptoJS.mode.ECB,
            padding: CryptoJS.pad.Pkcs7
        });
        return JSON.parse(decrypted.toString(CryptoJS.enc.Utf8));
    }

    $scope.serverData = "";
    $scope.serverResponse = "";
    $scope.sendDataToServer = function(){
        $scope.serverResponse = "";
        $http({
            method : 'POST',
            url : 'http://localhost:8081/backend/encrypt/message',
            data : encryptData({message:$scope.serverData},key)
        }).then(function successCallback(response) {
           $scope.serverResponse = decryptData(response.data.response,key);
        }, function errorCallback(response) {
            $scope.serverResponse = decryptData(response.data.response,key)
        });
    }
});