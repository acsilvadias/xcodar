let mongoose = require('mongoose')
var helpDevice = require('./helpDevice')
var db = require('../../config/database')
require('../firebaseNotificationApi/publish')

helpDevice.methods(['post','get','put','delete'])
helpDevice.updateOptions({new: true, runValidators: true})

helpDevice.after('post', (req, res, next) => {
   //var tmp = req.body // Permita trocar os campos do título e do ano
   var myBody = JSON.parse(JSON.stringify(req.body, null, 2));
   console.log('helpDevice Location: ' +  myBody['location.coordinates'] )
     
   myBody['location.coordinates'] = '-8.1218475, -34.917313,'

   const conn = db.connection
   const Schema = mongoose.Schema

   const schema = new Schema({deviceId: {type: String}});

   var locDevice = db.Mongoose.model('LocationDevice');
   console.log('Retorno')
   locDevice.find({
      'location.coordinates': {
         $geoWithin: {
            $centerSphere: [
               [myBody['location.coordinates']],
               0.007 / 3963.2
            ]
         }
      }
   }).sort({dataTimeLocation:-1}).lean().exec(function (e, docs) {
      
      console.log(docs)
      if (docs)
            for(var k in docs) {

         var pares = JSON.parse(JSON.stringify( docs[k], null, 2))  
         console.log(k,'deviceId', pares['deviceId'] );
         console.log(k,'token', pares['token_Firebase'] );
     
         messagenotfication(pares['token_Firebase'])
     
      }
      else
      {
         console.log('Nenhum par encontrado!')
      }
     

   }); 
       
   
   next() // Não se esqueça de ligar para a próxima!
 })


module.exports = helpDevice
