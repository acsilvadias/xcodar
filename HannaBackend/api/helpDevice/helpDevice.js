const restful = require('node-restful')
const mongoose = restful.mongoose

const docHelpDeviceSchema = new mongoose.Schema({
    deviceId: {type: String, required:true },
    location: {
        type: {
          type : String,
          enum: ['Point'],
           required: true
      }, coordinates: {
          type: [Number],
          required: true
      }},
    helpDeviceId: {type: String, required:true },
    helpLocation: {
        type: {
          type : String,
          enum: ['Point'],
           required: false
      }, coordinates: {
          type: [Number],
          required: false
      }},
      snConfirma: {type: String, max: 1, require: true}

});


module.exports = restful.model('helpDevice', docHelpDeviceSchema )
