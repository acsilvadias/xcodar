const LocationDevice = require('./locationDevice')


LocationDevice.methods(['post','get','put','delete'])
LocationDevice.updateOptions({new: true, runValidators: true})

module.exports = LocationDevice
