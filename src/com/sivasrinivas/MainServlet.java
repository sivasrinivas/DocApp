package com.sivasrinivas;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class MainServlet
 */
@WebServlet("/storage/documents/*")
public class MainServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private StorageManager manager;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MainServlet() {
        super();
        manager = new StorageManager();
    }

	/**
	 * A document can be queried by sending a GET request to /storage/documents/{docId}, where {docId} is the document ID issued during creation. The content of the
	 * GET response is the document exactly as it was created or last updated. On success, a 200 OK response is sent. A 404 Not Found HTTP response is returned if the
	 * document ID is invalid.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getPathInfo();
		if(path==null || path.length()<2){
			System.out.println("No Id found in request");
			response.setStatus(404);
			return;
		}
		
		String id = path.substring(1);
		File content = manager.getDocument(id);
		
		if(content==null){
			System.out.println("No document found for the given Id: "+id);
			response.setStatus(404);
		}else{
			PrintWriter writer = response.getWriter();
			BufferedReader br = new BufferedReader(new FileReader(content));
			String line;
			
			while((line=br.readLine())!=null){
				System.out.println(line);
				writer.write(line);
			}
			br.close();
			System.out.println("Document found for the given Id: "+id);
			response.setStatus(200);
		}
	}

	/**
	 * A document can be created by sending a POST request with document contents to /storage/documents. The document is simply the HTTP request payload. All
	 * content types are supported. The content of the POST response is a unique alphanumeric document ID with a length of 20 characters. The HTTP response has a
	 * 201 Created status code.
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BufferedReader reader = request.getReader();
		String id = manager.getNextRandomId();
		
		System.out.println("Content Type: "+request.getContentType());
		/*Can create different types of files based on content type using switch or if else*/
		File content = new File(id);
		String line;
		BufferedWriter writer = new BufferedWriter(new FileWriter(content));
		while((line=reader.readLine())!=null){
			System.out.println(line);
			writer.write(line);
			writer.newLine();
		}
		writer.close();
		manager.postDocument(id, content);
		
		System.out.println("New document was created and stored with Id: "+id);
		response.setContentType(request.getContentType());
		response.setStatus(201);
		response.getWriter().write(id);
	}

	/**
	 * A document can be updated by sending a PUT request with document contents to /storage/documents/{docId}, where {docId} is the document ID issued during
	 * creation. The document is simply the HTTP request payload. On success, a 204 No Content response is sent. A 404 Not Found HTTP response is returned if the
	 * document ID is invalid.
	 */
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getPathInfo();
		if(path==null || path.length()<2){
			System.out.println("No Id found in request");
			response.setStatus(404);
			return;
		}
		String id = path.substring(1);
		File content = manager.getDocument(id);
		if(content!=null){
			BufferedReader reader = request.getReader();
			String line;
			BufferedWriter writer = new BufferedWriter(new FileWriter(content));
			while((line=reader.readLine())!=null){
				System.out.println(line);
				writer.write(line);
				writer.newLine();
			}
			writer.close();
			manager.putDocument(id, content);
			System.out.println("Document updated for the given Id: "+id);
			response.setStatus(204);
		}else{
			System.out.println("No document found to update for the given Id: "+id);
			response.setStatus(404);
		}
	}

	/**
	 * A document can be deleted by sending a DELETE request with no content to /storage/documents/{docId}, where {docId} is the document ID issued during creation.
	 * On success, a 204 No Content HTTP response is sent. A 404 Not Found HTTP response is returned if the document ID is invalid.
	 */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getPathInfo();
		if(path==null || path.length()<2){
			System.out.println("No Id found in request");
			response.setStatus(404);
			return;
		}
		String id = path.substring(1);
		if(manager.deleteDocument(id)){
			System.out.println("Doument deleted successfully for the given Id: "+id);
			response.setStatus(204);
		}
		else{
			System.out.println("No doucment found to delete for the given Id: "+id);
			response.setStatus(404);
		}
	}

}

/**
 * This class maintains all documents and requests to get, create, update and delete
 * @author Siva
 *
 */
class StorageManager{
	//contains all documents and corresponding id
	private HashMap<String, File> storage;
	
	public StorageManager(){
		storage = new HashMap<String, File>();
	}
	
	public File getDocument(String id){
		return storage.get(id);
	}
	
	public void postDocument(String id, File content){
		storage.put(id, content);
	}
	//update existing file with given id
	public boolean putDocument(String id, File newContent){
		if(!storage.containsKey(id))
			return false;
		storage.put(id, newContent);
		return true;
	}
	
	public boolean deleteDocument(String id){
		if(storage.containsKey(id)){
			storage.remove(id);
			return true;
		}
		return false;
	}
	
	public static String getNextRandomId(){
		return UUID.randomUUID().toString().replace("-", "").substring(0,20);
	}
	
}
