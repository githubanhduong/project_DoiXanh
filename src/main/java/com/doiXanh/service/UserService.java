package com.doiXanh.service;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.doiXanh.entity.User;
import com.doiXanh.repository.UserRepository;

import jakarta.servlet.http.HttpServletResponse;

@Service
public class UserService {

	private static final Logger log = LoggerFactory.getLogger(UserService.class);

	@Autowired
	UserRepository userRepository;

	public void exportExcel(List<User> listUser, HttpServletResponse response) throws IOException {
		XSSFWorkbook workbook = new XSSFWorkbook();

		// Create a new sheet
		XSSFSheet sheet = workbook.createSheet("Data Sheet");

		// Create sample data
		String[] headers = { "ID", "Group ID", "First Name", "Last Name", "Email", "Phone", "Created Date",
				"Updated Date" };
//		String[] data = { "183", "3", "Winston", "Schaden", "ebert.zora@hotmail.com", "1-699-329-3148x7873", "2006-01-01 17:57:10", "1991-12-30 19:58:15" };

		// Add header row
		XSSFRow headerRow = sheet.createRow(0);
		for (int i = 0; i < headers.length; i++) {
			XSSFCell cell = headerRow.createCell(i);
			cell.setCellValue(headers[i]);
		}

		// Add data row
		int n = 1;
		for (User user : listUser) {
			if (user.getGroupId() != 3) continue;
			XSSFRow dataRow = sheet.createRow(n);
			dataRow.createCell(0).setCellValue(String.valueOf(user.getId())); // Convert data to string for other
			dataRow.createCell(1).setCellValue(String.valueOf(user.getGroupId())); // Convert data to string for other
			dataRow.createCell(2).setCellValue(String.valueOf(user.getFirstName())); // Convert data to string for other
			dataRow.createCell(3).setCellValue(String.valueOf(user.getLastName())); // Convert data to string for other
			dataRow.createCell(4).setCellValue(String.valueOf(user.getEmail())); // Convert data to string for other
			dataRow.createCell(5).setCellValue(String.valueOf(user.getPhone())); // Convert data to string for other
			dataRow.createCell(6).setCellValue(String.valueOf(user.getCreatedAt())); // Convert data to string for other
			dataRow.createCell(7).setCellValue(String.valueOf(user.getUpdatedAt())); // Convert data to string for other
			n++;
		}

	

//		for (int i = 0; i < data.length; i++) {
//			XSSFCell cell = dataRow.createCell(i);
//			if (i == 1) { // Set data type for age column as integer
//				cell.setCellValue(data[i]);
//			} else {
//				cell.setCellValue(String.valueOf(data[i])); // Convert data to string for other columns
//			}
//		}

	// Set response content type
	response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

	// Set suggested filename
	response.setHeader("Content-Disposition","attachment; filename=dataUser.xlsx");

	// Write workbook to response stream
	workbook.write(response.getOutputStream());workbook.close();

	}

	public List<User> readFileExcel(MultipartFile file) throws IOException, ParseException {
		List<User> users = new ArrayList<>();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		InputStream fis = file.getInputStream();

		try (Workbook workbook = new XSSFWorkbook(fis)) {
			Sheet sheet = (Sheet) workbook.getSheetAt(0);

			for (Row row : sheet) {
				if (row.getRowNum() == 0) // Skip header row
					continue;

				User user = new User();
				for (Cell cell : row) {

					switch (cell.getCellType()) {
					case STRING:
						switch (cell.getColumnIndex()) {
						case 2:
							user.setFirstName(cell.getStringCellValue());
							break;
						case 3:
							user.setLastName(cell.getStringCellValue());
							break;
						case 4:
							user.setEmail(cell.getStringCellValue());
							break;
						case 5:
							user.setPhone(cell.getStringCellValue());
							break;
						case 6:
							user.setCreatedAt(sdf.parse(cell.getStringCellValue()));
							break;
						case 7:
							user.setUpdatedAt(sdf.parse(cell.getStringCellValue()));
							break;
						}

						break;
					case NUMERIC:
						switch (cell.getColumnIndex()) {
						case 0:
							user.setId((int) cell.getNumericCellValue());
							break;
						case 1:
							user.setGroupId((int) cell.getNumericCellValue());
							break;
						}

						break;
					case BLANK:
						System.out.print("[BLANK]\t");
						break;
					default:
						System.out.print("[UNKNOWN]\t");
					}
				}
				users.add(user);
			}
		}

		fis.close();
		return users;
	}

	public List<User> findAllUser(MultipartFile file) throws IOException, ParseException {
		List<User> listUserExcels = readFileExcel(file);
		List<User> listUsers = userRepository
				.findAllByIdInAndGroupId(listUserExcels.stream().map(User::getId).collect(Collectors.toList()));

		List<User> listUsersRemove = new ArrayList<>();
		for (var user : listUserExcels) {
			if (listUsers.contains(user)) {
				user.setError(false);
			} else if (user.getGroupId() == 3) {
				user.setError(true);
			} else {
				listUsersRemove.add(user);
			}
		}
		listUserExcels.removeAll(listUsersRemove);

		return listUserExcels;
	}

//	public void updateAllUser(Map<Long, Long> userGroupIdMap) {
//		// TODO Auto-generated method stub
//		List<User> usersToUpdate = new ArrayList<>();
//	    for (Map.Entry<Long, Long> entry : userGroupIdMap.entrySet()) {
//	      Long userId = entry.getKey();
//	      Long newGroupId = entry.getValue();
//	      User userToUpdate = getUserById(userId); // Fetch user by ID
//	      if (userToUpdate != null) {
//	        userToUpdate.setGroupId(newGroupId);
//	        usersToUpdate.add(userToUpdate);
//	      } else {
//	        // Handle case where user with ID is not found (optional)
//	        // You can return an appropriate error message here
//	      }
//	    }
//	    updateUsers(usersToUpdate);
//
//	}
//
//	private void updateUsers(List<User> usersToUpdate) {
//		// TODO Auto-generated method stub
//		
//	}

}
