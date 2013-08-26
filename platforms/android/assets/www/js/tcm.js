var tcmPlugin = {
  login: function(user, password, successCallback, errorCallback) {
    console.log("Inside login from tcm.js");
    cordova.exec(
      successCallback, // success callback function
      errorCallback, // error callback function
      'TcmPlugin', // mapped to our native Java class called "CalendarPlugin"
      'tcmLogin', // with this action name
      [{                  // and this array of custom arguments to create our entry
        "user": user,
        "password": password,
      }]
    ); 
  }
}


