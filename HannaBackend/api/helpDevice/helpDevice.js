const restful = require('node-restful')
const mongoose = restful.mongoose

const docHelpDeviceSchema = new mongoose.Schema({
    locationDeviceId: {type: String, required:true },
    snConfirma: {type: String, max: 1, require: true},
    location: {
        type: {
          type : String,
          enum: ['Point'],
           required: true
      }, coordinates: {
          type: [Number],
          required: true
      }}
});


module.exports = restful.model('helpDevice', docHelpDeviceSchema )