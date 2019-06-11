const port = 3003

const bodyParser = require('body-parser')
const express = require('express')
const server = express()

server.use(bodyParser.urlencoded({ extended: true }))
server.use(bodyParser.json())

server.use((req, res, next) => {
	console.log('Datdos recebidos ' + JSON.stringify(req.body, null, 2))
	next()
})

server.listen(port, function() {
	console.log(`Hanna Backend is running on port ${port}.`)
})



module.exports = server