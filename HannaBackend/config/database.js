const mongoose = require('mongoose')

module.exports = mongoose.connect("mongodb://localhost:27017/db_hannasocial",
   { useNewUrlParser: true }
   , err => {
    if (err) {
      console.log('[SERVER_ERROR] MongoDB Connection:', err)
      process.exit(1) /*encerar o processo para que o nodemon tente novamente*/
   }
   else{
	  console.log('DB Hanna Social Connection:OK')  
   }}) 