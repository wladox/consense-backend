package com.consense.controller;

import java.awt.Image;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.ServletContext;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.consense.model.User;
import com.consense.model.UserFeature;
import com.consense.service.IAccessControlManager;
import com.consense.service.IUserManager;



@RestController("userController")
@RequestMapping("/user")
public class UserController {

	private IUserManager userManager;
	private IAccessControlManager accessControlManager;
	
	@Autowired
	Environment env;
	
	@Autowired
	ServletContext ctx;

	
	// wiring required components
	@Autowired
	public void setUserManager(IUserManager userManager) {
		this.userManager = userManager;
	}
	
	@Autowired
	public void setAccessControlManager(IAccessControlManager accessControlManager) {
		this.accessControlManager = accessControlManager;
	}

	
	/**
	 * 
	 * @return list of all existing users
	 */
	@RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	List<User> findAll() {
		return userManager.getAllUsers();
	}

	/**
	 * this method adds new user
	 * 
	 * @param user
	 * @return HTTP Response with status 200
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	ResponseEntity<?> addUser(@RequestBody User user) {
		userManager.addUser(user);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	/**
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping(value="/login", method = RequestMethod.POST)
	ResponseEntity<String> login(@RequestBody User user) {
		
		User authenticatedUser = userManager.authenticateUser(user);
		
		if (authenticatedUser != null) {
			List<UserFeature> features = userManager.getUserFeatures(authenticatedUser.getUserId());
			
			JSONObject userObject = new JSONObject();
			userObject.put("userId", authenticatedUser);
			userObject.put("username", authenticatedUser.getName() + " " + authenticatedUser.getSurname());
			
			JSONArray featureArray = new JSONArray();
			for (UserFeature feature : features) {
				JSONObject featureObject = new JSONObject();
				featureObject.put("categoryId", feature.getCategoryId());
				featureObject.put("categoryName", feature.getCategoryName());
				featureObject.put("featureId", feature.getFeatureId());
				featureObject.put("featureName", feature.getFeatureName());
				featureArray.put(featureObject);
			}
			userObject.put("features", featureArray);
			return new ResponseEntity<String>(userObject.toString(), HttpStatus.OK);
		}
		
		return new ResponseEntity<String>("", HttpStatus.OK);
	}
	
	/**
	 * 
	 * this method adds new features to a user profile
	 * 
	 * @param userId 		id of the user who wants to add new feature
	 * @param featureName 	feature name
	 * @param catId 		categoryId to which the new feature belongs
	 */
	@RequestMapping(value = "/feature/add", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE) 
	public void addUserFeature(	@RequestParam(value = "userId") Integer userId, 
								@RequestParam(value="featureName") String featureName,
								@RequestParam(value="categoryId") Integer catId) {
		UserFeature feature = new UserFeature();
		feature.setFeatureName(featureName);
		feature.setCategoryId(catId);
		userManager.addUserFeature(userId, feature);
	}
	
	/**
	 * 
	 * @param userId
	 * @return
	 */
	@RequestMapping(value="/{userId}/profile", method = RequestMethod.GET)
	public ResponseEntity<List<UserFeature>> getUserProfile(@PathVariable(value="userId") Integer userId) { 
		List<UserFeature> features = userManager.getUserFeatures(userId);
		ResponseEntity<List<UserFeature>> response = new ResponseEntity<List<UserFeature>>(features, HttpStatus.OK);
		return response;
	}
	
	/**
	 * 
	 * this method returns user profile features of user2 based on access control rules
	 * 
	 * @param user1Id
	 * @param user2Id
	 * @return List of requested user features
	 */
	@RequestMapping(value="/profile", method = RequestMethod.POST)
	public ResponseEntity<List<UserFeature>> getUserProfile(@RequestParam(value ="user1Id") Integer user1Id, @RequestParam(value="user2Id") Integer user2Id) { 
		
		// here access control should happen
		
		List<UserFeature> features = userManager.getUserFeatures(user1Id);
		ResponseEntity<List<UserFeature>> response = new ResponseEntity<List<UserFeature>>(features, HttpStatus.OK);
		return response;
	}
	
	@RequestMapping(value = "/{userId}/imageupload",  method = RequestMethod.POST)
	public ResponseEntity<String> handleFileUpload(@PathVariable Integer userId, @RequestParam("image") MultipartFile image){
		
		if (!image.isEmpty()) {
			try {
				byte[] bytes = image.getBytes();
				String filename = "D://dev//" + userId.toString() + File.separator + image.getOriginalFilename();
				File file = new File(filename);
				file.getParentFile().mkdirs();
				file.createNewFile();
				BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(file));
				stream.write(bytes);
				stream.close();
				userManager.setUserImage(userId, filename);
				return new ResponseEntity<String>("You successfully uploaded new profile image", HttpStatus.OK);
			} catch (IOException e) {
				e.printStackTrace();
				return new ResponseEntity<String>("You failed to upload => " + e.getMessage(), HttpStatus.BAD_REQUEST);
			}
		}
		
		return new ResponseEntity<String>("You failed to upload because the file was empty.", HttpStatus.BAD_GATEWAY);
		
	}
	
	@RequestMapping(value="/image", method = RequestMethod.POST)
	public ResponseEntity<byte[]> getUserImage(@RequestParam(value="userId") Integer userId) {
		
		File image = userManager.getUserImage(userId);
		if (image != null && image.exists()) {
			try {
				InputStream inputStream = new FileInputStream(image);
				try {
					return new ResponseEntity<byte[]>(IOUtils.toByteArray(inputStream), HttpStatus.OK);
				} catch (IOException e) {
					e.printStackTrace();
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		return new ResponseEntity<byte[]>(HttpStatus.NOT_FOUND);
		
	}
}
