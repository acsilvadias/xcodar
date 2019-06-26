const express = require('express')


module.exports = function(server){
	/* Hanna  Api Routers */
	const router = express.Router()
	server.use(function ( req, res ,next) { 
		var mBody ='Datdos recebidos ' + JSON.stringify(req.body, null, 2);
		var mReq = "url: " + req.originalUrl;
		console.log(mBody)
		console.log(mReq)
		/*
		console.log(req.originalUrl) 
		console.log(req.baseUrl)
		console.log(req.path) 
		*/
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
	