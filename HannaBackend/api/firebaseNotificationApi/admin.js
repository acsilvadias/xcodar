
var admin = require("firebase-admin");

var serviceAccount = require("../../config/helphanna-firebase-adminsdk-rzrmd-d7e163566a.json");

admin.initializeApp({
  credential: admin.credential.cert(serviceAccount),
  databaseURL: "https://helphanna.firebaseio.com"
});
