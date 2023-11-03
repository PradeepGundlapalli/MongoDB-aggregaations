package com.howtodoinjava.demo.web;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.bson.BsonReader;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.conversions.Bson;
import org.bson.json.JsonReader;
import org.json.simple.JSONObject;
//import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationExpression;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperationContext;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.Fields;
import org.springframework.data.mongodb.core.aggregation.LookupOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.aggregation.TypedAggregation;
import org.springframework.data.mongodb.core.aggregation.UnwindOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.howtodoinjava.demo.model.Book;
import com.howtodoinjava.demo.repository.BookRepo;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Field;
import com.mongodb.operation.GroupOperation;

// Annotation
@RestController pradeep

// Class
public class BookController {

	@Autowired
	private BookRepo repo;

	private final MongoTemplate mongoTemplate;

	@Autowired
	public BookController(BookRepo repo, MongoTemplate mongoTemplate) {
		this.repo = repo;
		this.mongoTemplate = mongoTemplate;
	}

	@PostMapping("/addBook")
	public String saveBook(@RequestBody Book book) {
		repo.save(book);

		return "Added Successfully";
	}

	@GetMapping("/findAllBooks")
	public List<Book> getBooks()  {
		
		 SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
	        // Parse the date string into a Date object
	        try {
				Date date = simpleDateFormat.parse("2023-10-20 18:30:00");
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();  
			}

	        // Convert the Date object to a Timestamp (if needed)
	       // Timestamp startDate = new Timestamp(date.getTime());
	       
			/*
			 * Calendar calendar = Calendar.getInstance(); calendar.setTime(startDate);
			 * calendar.add(Calendar.DAY_OF_MONTH, 5); Timestamp endDate = new
			 * Timestamp(calendar.getTimeInMillis());
			 */
	    
	       String dateString = "2023-10-20T18:30:00";
	       String format = "yyyy-MM-dd'T'HH:mm:ss";
	       
	 
	       
	       SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	       
	       
	       dateFormat.setLenient(false);
	       
	       String timestamp = "2023-15-24 18:30:00";
	       Date xmlDate;
	       Criteria dateCriteria=null;
		try {
			xmlDate = dateFormat.parse(timestamp);
		
	       dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
	       Instant startDate = Instant.parse(dateFormat.format(xmlDate));
	       
	       
	       dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	       Date xmlDate1 = dateFormat.parse("2023-13-25 18:30:00");
	       dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
	       Instant endDate1 = Instant.parse(dateFormat.format(xmlDate1));
	        dateCriteria = Criteria.where("createdDate").gte(startDate).lte(endDate1);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
        Query query = new Query(dateCriteria);
        List<Book> results = mongoTemplate.find(query, Book.class);

		return results;
	}

	@GetMapping("/getDistinct")
	public Page<Book> getDistinct() throws Exception {
		String[] a = { "authorName.fullname._id","bookName","authorName.fullname.fname","authorName.fullname.lname","authorName.name"};
		 
		org.springframework.data.mongodb.core.aggregation.Field[] gfields= {};
		List<String> sl=Arrays.asList(a);
		
		List<AggregationOperation> aggOperations = new ArrayList<>();
		 ProjectionOperation projectionOperation = Aggregation.project();
		 UnwindOperation unwindOperation = Aggregation.unwind("fullname");
			
			int i=0;
		for (String field : sl) {
			String f1=field;
			if(field.contains(".")) {
				f1= field.substring(field.lastIndexOf('.')+1,field.length());
				if(a.length>1) {
				if(f1.equals("_id")){
					f1="_id"+i;
					i++;
				}
				}
			} 
			gfields = Arrays.copyOf(gfields, gfields.length + 1);
			gfields[gfields.length - 1] = Fields.field(f1, field);
		
			if(a.length>1) {
			if(f1.equals("_id") ) f1="_id._id";
			
			}
			projectionOperation = projectionOperation
		            .and(f1).as(field);
			aggOperations.add(projectionOperation);
		}
		
		Fields f=Fields.from(gfields);
	 
		org.springframework.data.mongodb.core.aggregation.GroupOperation groupOperation = Aggregation.group(f);
		
		org.springframework.data.mongodb.core.aggregation.ProjectionOperation pnew=
	        		(ProjectionOperation)aggOperations.get(aggOperations.size()-1);
		
		@SuppressWarnings("unchecked")
		Aggregation aggregation1 = Aggregation.newAggregation(
				groupOperation,pnew.andExclude("_id")
			 
        );

		AggregationResults<Object> results = mongoTemplate.aggregate(
	            aggregation1,
	          "Book",
	            Object.class
	        );
		ObjectMapper mapper = new ObjectMapper();
	 
		List<Object> resultList = results.getMappedResults();
		List<Book> l=new ArrayList<>();
		resultList.stream().forEach(json -> {
			try {
				String tJson=mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);
				l.add(mapper.readValue(tJson,Book.class));
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		});
		
		/*
		 * for(Object o:resultList) { String json =
		 * mapper.writerWithDefaultPrettyPrinter().writeValueAsString(resultList.get(0))
		 * ;
		 * 
		 * l.add(mapper.readValue(json.toString(),Book.class)); }
		 */
		
		//List<Book> myObjects = mapper.readValue((JsonParser)resultList, mapper.getTypeFactory().constructCollectionType(List.class, Book.class));
		
		
        int totalResults = resultList.size();
		int page=1;
		int pageSize=2;
		 

	        // Create a PageRequest for pagination
	        Pageable pageRequest = PageRequest.of(page-1, pageSize);
	   
	       return PageableExecutionUtils.getPage(l, pageRequest, () -> totalResults);
	     // return   results.getMappedResults();
	    }
	
	public String stojson(String s) {
		 String[] parts = s.split("\\.");

	        // Create a JSON-like structure
	        JSONObject result = new JSONObject();
	        JSONObject currentObject = result;

	        for (int i = 0; i < parts.length; i++) {
	            String part = parts[i];

	            if (i == parts.length - 1) {
	                // Last part should be an empty string as a placeholder
	                currentObject.put(part, "");
	            } else {
	                // Create a nested JSONObject
	                JSONObject nestedObject = new JSONObject();
	                currentObject.put(part, nestedObject);
	                currentObject = nestedObject;
	            }
	        }

	        return result.toJSONString();
	}
	
	 private Object addToSet(String string) {
		// TODO Auto-generated method stub
		return null;
	}

	private Aggregation newAggregation(Bson project, GroupOperation groupOperation) {
		// TODO Auto-generated method stub
		return null;
	}

	private GroupOperation group(Document groupKeys) {
		// TODO Auto-generated method stub
		return null;
	}

	public Book convertDocumentToModel(Document result) {
	        // Create a codec registry with the PojoCodecProvider
	        CodecRegistry pojoCodecRegistry = CodecRegistries.fromRegistries(
	            MongoClientSettings.getDefaultCodecRegistry(),
	            CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build())
	        );

	        // Get the codec for MyModel
	        org.bson.codecs.Codec<Book> codec = pojoCodecRegistry.get(Book.class);
	        String json = "{ \"bookName\" : \"ppp\", \"fname\" : \"Sree\", \"lname\" : \"Nandhini\" }";
	        BsonReader bsonReader = new JsonReader(json);
	        // Decode the Document into a MyModel object
	        return codec.decode(bsonReader, null);
	    }

	/*
	 * public List<Book> getDistinctValuesForMultipleFields(String collectionName,
	 * String [] fieldNames) { Aggregation aggregation = Aggregation.newAggregation(
	 * Aggregation.project(fieldNames), Aggregation.group(fieldNames) );
	 * 
	 * AggregationResults<Book> results = mongoTemplate.aggregate(aggregation,
	 * collectionName, Book.class); return results.getMappedResults(); }
	 */

	@DeleteMapping("/delete/{id}")
	public String deleteBook(@PathVariable int id) {
		repo.deleteById(id);

		return "Deleted Successfully";
	}

}
