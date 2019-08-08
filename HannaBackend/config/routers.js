const express = require('express')

var  query = ''

module.exports = function(server){
	/* Hanna  Api Routers */
	const router = express.Router()
	server.use(function ( req, res ,next) { 
		var mBody = JSON.stringify(req.body, null, 2);
		var mReq = req.originalUrl;
		console.log('Request HTTP Version: ', req.httpVersion)
		console.log('Datdos recebidos ' + mBody)
		console.log("url: " + mReq)

		/*
		console.log(req.originalUrl) 
		console.log(req.baseUrl)
		console.log(req.path) 
		*/
		
		next() 	
	})
	
	server.use(function(req,res,next){
		if(req.originalUrl == '/api/helpDevices/')	{
			var myBody = JSON.parse(JSON.stringify(req.body, null, 2));
			var myLocation = myBody['location.coordinates'] 	
			//console.log('Location: ' + myLocation)

			/*
				for(var attributename in myBody){
					console.log(attributename+": "+ myBody[attributename]);
				}
			*/
		}
	
		next()
	})
 
	server.use('/api',router)

	/* Rotas da Api*/ 
	const locationDeviceService = require('../api/locationDevice/locationDeviceService')
	locationDeviceService.register(router,'/locationDevices')
	console.log('locationDevice Registrado! ')
	
	const helpDeviceService = require('../api/helpDevice/helpDeviceService')
	helpDeviceService.register(router,'/helpDevices')
	console.log('helpDevices Registrado! ')

	
}
	