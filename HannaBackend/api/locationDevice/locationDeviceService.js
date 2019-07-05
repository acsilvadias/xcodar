const LocationDevice = require('./locationDevice')
const notificar = require('../../api/firebaseNotificationApi/publish')

LocationDevice.methods(['post','get','put','delete'])
LocationDevice.updateOptions({new: true, runValidators: true})

module.exports = LocationDevice
