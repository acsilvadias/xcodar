const express = require('express')


module.exports = function(server){
	
	/* Hanna  Api Routers */
	const router = express.Router()
	server.use('/api',router)
	
	/* Rotas da Api*/ 
	const locationDeviceService = require('../api/locationDevice/locationDeviceService')
	locationDeviceService.register(router,'/locationDevices')
	
	/*
	router.route('/locationDevide').get(function(req, res, next){
		res.send(`DeviceId: ${req.params.deviceId}`)		
	})
	*/
}
///:deviceId/:lon/:lat/:datetime
//81995922332/-8.0071461/-38.9029396/28-06-1975