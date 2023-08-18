package com.example.demo.Report;

import com.example.demo.Dao.RoleRepository;
import com.example.demo.Dto.Report;
import com.example.demo.Entity.Role;
import com.example.demo.Entity.User;
import com.example.demo.Service.UserService;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserReportService {

    @Autowired
    private UserService userService;

//    public List<User> userList = userService.getAllUsers();

    public String generateReport() {
        try {
            //創建report需要的資料，從關聯表中拿到role
            List<Report> report = new ArrayList<>();
            for (User user : userService.getAllUsers()){
                String[] roles = new String[2];
                int i = 0;
                for(Role role : user.getRoles()){
                    roles[i] = role.getRole();
                    i++;
                }
                String stringRoles = "";
                if (roles[1] != null){
                    stringRoles = roles[0]+","+roles[1];
                }
                else {
                    stringRoles = roles[0];
                }
                report.add(new Report(user.getUserId(), user.getUserAccount(), user.getUserPassword(),
                        user.getUserName(), user.getUserPhone(), user.getUserEmail(), stringRoles));
            }

            // 定義報表模板的路徑
            String reportPath = "src\\main\\resources\\report";

            // 把子表轉成jrxml檔轉成jasper
            JasperCompileManager.compileReportToFile(reportPath + "\\roleSubreport.jrxml");

            // 編譯 .jrxml 文件成為 JasperReport 對象
            JasperReport jasperReport = JasperCompileManager.compileReport(reportPath + "\\user-rpt.jrxml");

//            // 創建 JRBeanCollectionDataSource 來作為報表的數據源
//            JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(userService.getAllUsers());
            JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(report);

            // 創建報表參數
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("createdBy", "YuHung.org");

            // 填充報表，得到 JasperPrint 對象
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, jrBeanCollectionDataSource);

            // 將報表導出為 PDF 文件
            JasperExportManager.exportReportToPdfFile(jasperPrint, reportPath + "\\user-Rpt-v3.pdf");

            System.out.println("Done");

            // 返回生成報表成功的消息
            return "Report successfully generated @path= " + reportPath;
        } catch (Exception e) {
            e.printStackTrace();
            // 如果出現錯誤，返回錯誤消息
            return e.getMessage();
        }
    }
}
