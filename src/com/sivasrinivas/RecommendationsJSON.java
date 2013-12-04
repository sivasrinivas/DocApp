package com.sivasrinivas;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

//import org.apache.mahout.cf.taste.recommender.Recommender;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

//import com.nimbus.RecFlix.UserBased.RecommendationManager;


public class RecommendationsJSON {

//	String userId;
//	//String jsonFileDirPath;
//    Recommendation result = null;
//	boolean userBased;
//	boolean itemBased;
	
//	public RecommendationsJSON(String userId ,boolean userBased ){
//	//	this.jsonFileDirPath = jsonFileDirPath;
//		this.userBased = userBased;
//		this.itemBased = itemBased;
//		this.userId  = userId;
//		try{
//			if(itemBased){
//					result = temp.formRecommendations(userId);				
//	
//			} else{
//				// result = temp.formRecommendationsForUser(userId);
//			}
//		} catch(IOException ioe){
//			
//		}
//		
//	}
	
	 public static void Driver(String [] args){
//		 if(args.length != 4){
//			 System.out.println("Visualization: Check the usage");
//			 usage();
//			 return;
//		 }
		 String mahoutFileName = "UserBasedOutputnew.txt";
		 boolean userBased = Boolean.parseBoolean(args[2]);
		 boolean itemBased = Boolean.parseBoolean(args[3]);
		// RecommendationsJSON rJson = new RecommendationsJSON(args[0],args[1] , userBased, itemBased);
		// rJson.createJsonForAUser();
		// rJson.createJsonFromMahout(mahoutFileName, userBased);
		 String userId = "214144";
		 createJsonForAUser(userId,false);
		 createJsonForAUser(userId,true);
		 
	 }
	 
	 public static void createJsonFromMahout(String userId, boolean userbased){
		 String UserBasedFileDir = "MahoutUserBasedRec";
		 String ItemBasedFileDir = "MahoutItemBasedRec";
		 File dir = null;
		 FileInputStream fstream = null ;
		 String line;
		 BufferedReader br = null;
		 String fileName = "userJson.json";
			
				try{
					if(userbased){
						dir = new File(UserBasedFileDir);

					}
					else{
					   dir = new File(ItemBasedFileDir);


					}
					
	                FileInputStream fstream2 = new FileInputStream("movie_titles.txt");
	                BufferedReader brMovieTitles = new BufferedReader(new InputStreamReader(fstream2));
					String[] children = dir.list();
					  for (int i=0; i<children.length; i++) {
					        // Get filename of file or directory
							fstream = new FileInputStream(children[i]);
							br = new BufferedReader(new InputStreamReader(fstream));
					   
						while ((line = br.readLine()) != null) {
							String [] tokens = line.split("\\t");
							if(tokens[0].equals(userId)){
								tokens[1].replace("[" ,"");
								tokens[1].replace("]" ,"");
								String [] movieIdRating = tokens[1].split(",");
								
//								if(userbased)
//									fileName.append("mahout_item_");
//								else
//									fileName.append("mahout_user_");	
//								
//	
//								fileName.append(userId);
//								fileName.append(".json");
			
								JSONArray jsonRecArray = new JSONArray();
								for(int j= 0 ; j<movieIdRating.length; j++){
									// adding last level of hierarchy that is rating
									
									// processing to get movie title from name
									String[] movieidratingTokens = movieIdRating[j].split(":");
									String movieName = "";
									while ((line = br.readLine()) != null) {
										String [] movietitles = line.split(",");
										if(movieidratingTokens[0].equals(movietitles[0]))
											movieName = movietitles[2];
									}
									
									JSONObject jsonRatingObj = new JSONObject();
									jsonRatingObj.put("name" ,movieidratingTokens[1]);
									JSONArray jsonRatingArray = new JSONArray();
									jsonRatingArray.add(jsonRatingObj);
									
									JSONObject jsonMovieObj = new JSONObject();
									jsonMovieObj.put("name",movieName);
									jsonMovieObj.put("children",jsonRatingArray );
									jsonRecArray.add(jsonMovieObj);
								}
								JSONObject jsonUserObj = new JSONObject();
								jsonUserObj.put("name", userId );
								jsonUserObj.put("children", jsonRecArray);
								
								writeJsonFile(fileName.toString(),jsonUserObj);
								
							}
							
							
						}
					  }
				} catch(IOException e){
					
				}
		 
	 }
	public static void createJsonForAUser(String userId ,boolean userBased){
		Recommendation result = null;
		StringBuilder fileName = new StringBuilder("userJSON.json");
		try{
		if(userBased){
			// result = temp.formRecommendationsForUser(userId);	

		} else{
			result = temp.formRecommendations(userId);	
//			fileName.append("item_");
			
		}
	} catch(IOException ioe){
		
	}

		if(result == null)
			return;
		
//		StringBuilder fileNameForRecGenere = new StringBuilder("");
		
		//	StringBuilder fileName = new StringBuilder(jsonFileDirPath);
		//	StringBuilder fileNameForRecGenere = new StringBuilder(jsonFileDirPath);
		//	fileName.append("/");

			
//			fileNameForRecGenere.append("itemBasedGenereForUser_");

//			fileName.append(result.userid);
//			fileName.append(".json");
//			fileNameForRecGenere.append(result.userid);
//			fileNameForRecGenere.append(".json");
			
			JSONArray jsonRecArray = new JSONArray();

			for(int j= 0 ; j<result.recomCount; j++){
				// adding last level of hierarchy that is rating
				JSONObject jsonRatingObj = new JSONObject();
				jsonRatingObj.put("name" ,result.movieid[j].rating );
				//jsonRatingObj.put("name" ,"10");
				JSONArray jsonRatingArray = new JSONArray();
				jsonRatingArray.add(jsonRatingObj);
				
				JSONObject jsonMovieObj = new JSONObject();
				jsonMovieObj.put("name",result.movieid[j].movieName);
				//jsonMovieObj.put("name","100");
				jsonMovieObj.put("children",jsonRatingArray );
				jsonRecArray.add(jsonMovieObj);
			}
			JSONObject jsonUserObj = new JSONObject();
			jsonUserObj.put("name", result.userid );
			jsonUserObj.put("children", jsonRecArray);
			
			writeJsonFile(fileName.toString(),jsonUserObj);
//			
//			// Writegenere file
//			JSONArray jsonGenereArray = null;
//			for(int j= 0 ; j<result.genre.length; j++){
//				jsonGenereArray.add(result.genre[j]);
//			}
//			JSONObject jsonUserGenereObj = new JSONObject();
//			jsonUserGenereObj.put("name", result.userid );
//			jsonUserObj.put("children", jsonGenereArray);
//			
//			writeJsonFile(fileNameForRecGenere.toString(),jsonUserGenereObj);
			
		
	}
	
	 public static void writeJsonFile(String jsonFileFullPath, JSONObject jsonUserObj ){
			try {
	
				FileWriter jsonFileWriter = new FileWriter(jsonFileFullPath);
				jsonFileWriter.write(jsonUserObj.toJSONString());
				jsonFileWriter.flush();
				jsonFileWriter.close();
	
			} catch (IOException e) {
				e.printStackTrace();
			}
	 }
	 public static void usage(){
		 System.out.println("usage for calling RecommendationJSON:  userID <ResultingJSONDirPath> " +
		 		"true/false (this represents userBased) true/false(this represents ItemBasedFlag)");
		 
	 }
	 public static void main(String[] args){
		 Driver(args);
	 }

}
