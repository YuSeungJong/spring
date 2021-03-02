package kr.or.ddit.view;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.servlet.view.AbstractView;

import kr.or.ddit.user.model.UserVo;

public class ExcelDownloadView extends AbstractView{

	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		response.setContentType("application/vnd.ms-excel; charset=UTF-8");
		
		response.setHeader("Content-Disposition", "attachment; filename=test.xlsx");
		
		
		// header : List<String>
		// data : List<UserVo>
		
		List<String> header = (List<String>)model.get("header");
		List<UserVo> data = (List<UserVo>)model.get("data");
		
		//excel 파일 생성
		XSSFWorkbook book = new XSSFWorkbook();
		Sheet sheet = book.createSheet("users");
		
		int rownum = 0;
		int colnum = 0;
		Row row = sheet.createRow(rownum++);
		
		//루프를 돌면서 셀의 갯수를 header의 갯수만큼 증가
		for(String h : header) {
			Cell cell = row.createCell(colnum++);
			cell.setCellValue(h);
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd");
		for(UserVo user : data) {
			row = sheet.createRow(rownum++);
			colnum = 0;
			Cell cell = row.createCell(colnum++);
			cell.setCellValue(user.getUserid());
			cell = row.createCell(colnum++);
			cell.setCellValue(user.getUsernm());
			cell = row.createCell(colnum++);
			cell.setCellValue(user.getAlias());
			cell = row.createCell(colnum++);
			if(user.getReg_dt()!=null) {
				cell.setCellValue(sdf.format(user.getReg_dt()));				
			}else {
				cell.setCellValue("");
			}
			cell = row.createCell(colnum++);
			cell.setCellValue(user.getAddr1());
			cell = row.createCell(colnum++);
			cell.setCellValue(user.getAddr2());
			cell = row.createCell(colnum++);
			cell.setCellValue(user.getZipcode());
		}
		
		for(UserVo user : data) {
			colnum = 0;
			Row r = sheet.createRow(rownum++);
			r.createCell(colnum++).setCellValue(user.getUserid());
			r.createCell(colnum++).setCellValue(user.getUsernm());
			r.createCell(colnum++).setCellValue(user.getAlias());
		}
		

		
		
		
		//data는 나중에
		book.write(response.getOutputStream());
		
	}
	
}




















