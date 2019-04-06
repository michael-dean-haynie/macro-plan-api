function fn(){
	var env = karate.env; // get java system property 'karate.env'
	  karate.log('karate.env system property was:', env);
	  if (!env) {
	    env = 'dev'; // a custom 'intelligent' default
	  }
	  var config = {
	    baseUrl: 'http://localhost:8080/'
	  };
	  // don't waste time waiting for a connection or if servers don't respond within 5 seconds
	  karate.configure('connectTimeout', 5000);
	  karate.configure('readTimeout', 5000);
	  
	  // prepare schemas
	  var result = karate.callSingle('classpath:schemas.feature', config);
	  config.schemas = result.schemas;
	  
	  return config;
}