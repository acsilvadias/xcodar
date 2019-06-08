const mongoose = require('mongoose')
/* acesso ao atlas
	user: hannaUserdb
	psw:  H@nna2019.1
	atlas: mongodb+srv://hannaUserdb:<password>@cluster0-jryiw.mongodb.net/test?retryWrites=true&w=majority
 
 atlasDB: "mongodb+srv://hannaUserdb:H@nna2019.1@cluster0-jryiw.mongodb.net/db_hannasocial?retryWrites=true&w=majority" 
local: mongodb://localhost:27017/db_hannasocial
	*/


module.exports = mongoose.connect("mongodb://localhost:27017/db_hannasocial" ,
   { useNewUrlParser: true }
   , err => {
    if (err) {
      console.log('[SERVER_ERROR] MongoDB Connection:', err)
      process.exit(1) /*encerar o processo para que o nodemon tente novamente*/
   }
   else{
     console.log('DB Hanna Social Connection:OK')  
   }}) 