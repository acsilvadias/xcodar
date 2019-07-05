var helpDevice = require('./helpDevice')
require('../firebaseNotificationApi/publish')

helpDevice.methods(['post','get','put','delete'])
helpDevice.updateOptions({new: true, runValidators: true})

/*
var mytoken  = 'fD_N8nJeDK4:APA91bFB42XHuFAD-sa4FM6WpysWIXa1_6fydZE-DS_uZxtYPLjDb19atG_aeZDI8XAp3qPKIgMy2ILiPLPpDpVhC_lnSBe5T6PjjAN_u0iWirYwtvltLVa6lddh4KLRTz4NmN30WyBZ'

if(false)
   publishNotification(mytoken)
*/

module.exports = helpDevice
