import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@RestController
public class JasperReportController {
    @GetMapping("/getLangingList")
    public void getLangingListReport(HttpServletResponse response) throws JRException, IOException {
        InputStream jasperStream = this.getClass().getResourceAsStream("/landinglist.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperStream);
        LandingListDto l = new LandingListDto();
        List<LandingListStudentsDto> list = new ArrayList<>();
        List<LandingListDto> listT = new ArrayList<>();
        for(int i=0; i<17; i++){
            LandingListStudentsDto ll = new LandingListStudentsDto();
            ll.setId(i+1);
            ll.setIin("123");
            ll.setFio("123");
            ll.setLogin("123");
            ll.setPassword("123");
            ll.setAudience("123");
            ll.setPlace("123");
            ll.setSignature("123");
            list.add(ll);
        }

        l.setAddress("123");
        l.setStartTime("123");
        l.setEnterTime("123");
        l.setFlowNumber("123");
        l.setDate("123");
        //l.setList(null);
        l.setCountTesting("123");
        l.setDateAndTime("123");
        listT.add(l);


        //JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(listT);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("address", l.getAddress());
        parameters.put("startTime", l.getStartTime());
        parameters.put("enterTime", l.getEnterTime());
        parameters.put("flowNumber", l.getFlowNumber());
        parameters.put("date", l.getDate());
        parameters.put("countTesting", l.getCountTesting());
        parameters.put("dateAndTime", l.getDateAndTime());
        parameters.put("list", new JRBeanCollectionDataSource((Collection<?>) list));
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());

        response.setContentType("application/x-pdf");
        response.setHeader("Content-disposition", "inline; filename=studentsList.pdf");

        ServletOutputStream outputStream = response.getOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);

    }

}
