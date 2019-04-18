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
	  // 
	  // at first callSingle() seemed more appropriate but after a LOT of debugging I finally
	  // figured out that it doesn't quite work how you would think. It is guaranteed to only run once, but 
	  // in a race-condition seeming situation it will not always complete before the tests start running. 
	  // This resulted in add schema validation errors where the double nested schema seemed to have not been parsed yet and 
	  // was participating as a string literal instead of the actual schema ???
	  //
	  // ex: com.intuit.karate.exception.KarateException: food.feature:161 - path: $[0].unit, actual: {id=1, unitSystem=METRIC, unitType=MASS, unit=MILLIGRAM, properName=Milligram, abbreviation=mg, unitTypeRatio=0.001}, expected: '#java.util.LinkedHashMap', reason: not equal
	  var result = karate.call('classpath:schemas.feature', config);
	  config.schemas = result.schemas;
	  
	  return config;
}