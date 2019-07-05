    var FCM = require('fcm-node');
    var serverKey = 'AIzaSyBGwh0q8DGIUxAG3yYxzvF7oHryr5IFhO0';//require("../../config/privatekey.json"); //put your server key here
    var fcm = new FCM(serverKey);
    

    function messagenotfication  (tokenHanna){

        var message = { //this may vary according to the message type (single recipient, multicast, topic, et cetera)
            to: tokenHanna,  //'fD_N8nJeDK4:APA91bFB42XHuFAD-sa4FM6WpysWIXa1_6fydZE-DS_uZxtYPLjDb19atG_aeZDI8XAp3qPKIgMy2ILiPLPpDpVhC_lnSBe5T6PjjAN_u0iWirYwtvltLVa6lddh4KLRTz4NmN30WyBZ', 
            collapse_key: 'HannaSocial',
            
            notification: {
                title: 'HannaSocial', 
                body: 'Preciso de Ajuda, socorro!' 
            },
            
            data: {  //you can send only notification or only data(or include both)
                my_key: 'my value',
                my_another_key: 'my another value'
            }
        };
        
        fcm.send(message, function(err, response){
            if (err) {
                console.log("Something has gone wrong!");
            } else {
                console.log("Successfully sent with response: ", response);
            }
        });
    };

module.exports = messagenotfication