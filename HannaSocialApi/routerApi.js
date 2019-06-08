const express = require('express')
const router = express.Router()

router.use((req, res, next) => {
	next()
})

router.get('/messages/:id',(req, res) =>{
	res.json({id: req.params.id, messages: { message: 'Olá'} })
} )

router.get('/getpeoples/:id',(req, res) =>{
	res.json({id: req.params.id, peoples: '8'})
} )

module.exports = router