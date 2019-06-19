const helpDevice = require('./helpDevice')


helpDevice.methods(['post','get','put','delete'])
helpDevice.updateOptions({new: true, runValidators: true})

module.exports = helpDevice
