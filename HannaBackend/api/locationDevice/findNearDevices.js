var mongoose = require("mongoose");
var Schema = mongoose.Schema;
var MessageSchema = new Schema(
 {
  deviceId: String,
  token: String,  
  location: {
   type: { type: String },
   coordinates: []
  },
 {
  timestamps: true
 }
);
MessageSchema.index({ location: "2dsphere" });
var Message = mongoose.model("Message", MessageSchema);
module.exports = Message;