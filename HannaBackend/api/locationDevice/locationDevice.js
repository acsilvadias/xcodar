const restful = require('node-restful')
const mongoose = restful.mongoose

const docLocationSchema = new mongoose.Schema({
    deviceId: {type: String, required: [true, "deviceId é obrigatório!"] },
    longitude: {type: Number , required: true },
    latitude: {type: Number, required: true },
    dataTimeLocation: {type: Date , required: true },
    location: {
      type: {
        type : String,
        enum: ['Point'],
         required: true
    }, coordinates: {
        type: [Number],
        required: true
    }
  },
  token: {type:  String, require: false}
});


module.exports = restful.model('LocationDevice', docLocationSchema )