const express = require('express')


module.exports = function(server){
	/* Hanna  Api Routers */
	const router = express.Router()
	server.use(function ( req, res ,next) { 
		var mBody = req.bo;
		console.log(mBody)

		console.log('Novo: ' + mBody)
		next() 
	})
	server.use('/api',router)

	/* Rotas da Api*/ 
	const locationDeviceService = require('../api/locationDevice/locationDeviceService')
	locationDeviceService.register(router,'/locationDevices')
}
	