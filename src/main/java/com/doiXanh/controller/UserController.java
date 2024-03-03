package com.doiXanh.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.doiXanh.entity.GroupUser;
import com.doiXanh.entity.User;
import com.doiXanh.repository.GroupUserRepository;
import com.doiXanh.repository.UserRepository;
import com.doiXanh.service.UserService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.persistence.EntityManager;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Controller
public class UserController {
	@Autowired
	UserService userService;
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	GroupUserRepository groupUserRepository;
	
	@Autowired
	EntityManager entityManager;

	@GetMapping("/export")
	public void exportExcel(HttpServletResponse response) throws IOException {
		userService.exportExcel(response);
	}

	@PostMapping("/upload")
	@ResponseBody
//	@ValidFile(type = {"file/xlsx","file/xls"},message = "Khong dung dinh dang")
	public List<User> uploadFile(@Valid @RequestPart("file") MultipartFile file) throws IOException, ParseException {

		return userService.findAllUser(file);
	}

//	@PutMapping("/update")
//	public ResponseEntity<String> putMethodName(@RequestBody Map<Long, Long> userGroupIdMap) {
//		// TODO: process PUT request
//		userService.updateAllUser(userGroupIdMap);
//		return new ResponseEntity<>(HttpStatus.OK);
//	}

	@Transactional
	@GetMapping("/testa")
	@ResponseBody
	public ResponseEntity<String> testApia(@RequestBody String json) throws IOException, ParseException {
		ObjectMapper objectMapper = new ObjectMapper();
        Map<Integer, Integer> userGroupMap = objectMapper.readValue(json, new TypeReference<Map<Integer, Integer>>() {});
        
        Set<Entry<Integer, Integer>> entries = userGroupMap.entrySet();
        for (Map.Entry<Integer, Integer> entry : entries) {
        	Integer userId = entry.getKey();
        	Integer groupId = entry.getValue();
            
            // Tìm người dùng theo ID
//            User user = userRepository.findById(userId).orElse(null);
//            if (user != null) {
//			userRepository.save(
        	entityManager.persist(
					User.builder().withId(userId)
									.withGroupUser(GroupUser.builder().withId(groupId).build())
									.withEmail(json)
									.withFirstName("aabc")
									.withLastName("aabc")
									.withPhone("abc")
									.withCreatedAt(new Date())
									.withIsActive(false)
									.withUpdatedAt(new Date())
									.build());
            
        }


		    
		return ResponseEntity.ok("Entities updated successfully");


	}
	
	@Transactional
	@GetMapping("/test")
	@ResponseBody
	public ResponseEntity<String> testApi(@RequestBody String json) throws IOException, ParseException {
		
		 ObjectMapper objectMapper = new ObjectMapper();
		    // Corrected the JSON parsing to handle keys as strings due to the input format
		    Map<String, Integer> userGroupMap = objectMapper.readValue(json, new TypeReference<Map<String, Integer>>() {});

		    // Group users by their groupId
		    Map<Integer, List<Integer>> groupedUsers = userGroupMap.entrySet().stream()
		            .collect(Collectors.groupingBy(Map.Entry::getValue, Collectors.mapping(entry -> Integer.parseInt(entry.getKey()), Collectors.toList())));

		    // Iterate over the grouped users and update their userGroup.id accordingly
		    for (Map.Entry<Integer, List<Integer>> entry : groupedUsers.entrySet()) {
		        Integer groupId = entry.getKey();
		        List<Integer> userIds = entry.getValue();
		        // Assuming userRepository.updateUsersGroup accepts a List<Integer> for userIds and a GroupUser object for the new group
		        userRepository.updateUsersGroup(userIds, GroupUser.builder().withId(groupId).build());
		    }
		
		
		
		
		
		
		
//		ObjectMapper objectMapper = new ObjectMapper();
//		Map<Integer, Integer> userGroupMap = objectMapper.readValue(json, new TypeReference<Map<Integer, Integer>>() {});
//		
//		Set<Entry<Integer, Integer>> entries = userGroupMap.entrySet();
//		for (Map.Entry<Integer, Integer> entry : entries) {
////			Integer userId = entry.getKey();	
//			Integer groupId = entry.getValue();
//			
//			List<Integer> userIds = new ArrayList<>(userGroupMap.keySet());
//	        userRepository.updateUsersGroup(userIds, GroupUser.builder().withId(2).build());
//
//			
//		}
		
		
		
		return ResponseEntity.ok("Entities updated successfully");
		
		
	}
}
