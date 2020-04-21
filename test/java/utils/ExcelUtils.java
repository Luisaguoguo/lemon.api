package utils;

import Constant.constant;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import entityBase.API;
import entityBase.Case;
import entityBase.WriteBackExcel;
import org.apache.poi.ss.usermodel.*;

import java.io.Closeable;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static Constant.constant.EXCEL_PATH;

public class ExcelUtils<T> {

    //所有API的集合
    public static List<API> apiList;

    //所有Case的集合
    public static List<Case> caseList;

    //excel 回写集合
    public static List<WriteBackExcel> wbdlist;

    /*
    读取excel中的sheet转成list集合
    <T>  实体类型
    @param  sheetNumIndex  sheet 开始的索引
    @param  headRowNum  在sheet 开始读取的首行号码
    @param  sheetNum 读取几个sheet
    @param return  List<实体类型>的集合

     */

    public static Object[][] getApiIDAndCase(String apiId) {

       //所有API 集合 读取
       //读取所有case集合


        API wantApi = null;//创建一个需要的API

        List<Case> wantList = new ArrayList<>();//创建一个集合存储wantApiID 对应的case
        for (API api : apiList) {
            if (apiId.equals(api.getApiID())) {
                wantApi = api;//将apiList集合里面合适的apiID 放进wantApi
                break;
            }
        }
        for (Case c : caseList) {//遍历所有的case
            if (apiId.equals(c.getApiId())) {//当apiID 和wantApi相同的时候，将case 加入wantlist集合
                wantList.add(c);
            }
        }
        //wantApi和wantlist 都有值，放进二维数组里

        Object[][] data=new Object[wantList.size()][2];//apiID 对应的case数量，2个参数
        for (int i=0;i<data.length;i++){
            data[i][0]=wantApi;
            data[i][1]=wantList.get(i);

        }


    return data;
    }


    public static<T> List<T> easyApiReadExcel(int sheetNumIndex,int sheetNum,Class<T> clazz) {


        List<T> list= null;//jin
        try {

            FileInputStream file=new FileInputStream(EXCEL_PATH);

            ImportParams params=new ImportParams();
            params.setStartSheetIndex(sheetNumIndex);
         //   params.setHeadRows(headRowNum);
            params.setSheetNum(sheetNum);
            list = ExcelImportUtil.importExcel(file,clazz,params);
        } catch (Exception e) {
            e.printStackTrace();
        }
   //     for (T element:list){
               // System.out.println(element);
      //      }

        return list;
    }



    public static Object[][] readExcel() {
        FileInputStream excel = null;
        Object[][] datas = null;
        try {
            excel = new FileInputStream("/Users/44104909_edgar/com.lemon2020/src/main/resources/cases_v1.xls");
            Workbook workbook = WorkbookFactory.create(excel);
            Sheet sheet = workbook.getSheetAt(0);//ctrls+1，//获取表单
            int lastRowNum = sheet.getLastRowNum();//获取最后一行
            datas = new Object[lastRowNum][4];
            for (int i = 1; i <= lastRowNum; i++) {
                Row row = sheet.getRow(i);
                int lastCellNum = row.getLastCellNum();
                int indexOfCell = 0;//
                for (int j = 0; j <= lastCellNum; j++) {

                    if ( j == 2 || j == 3 || j == 5 || j == 6) {
                        Cell cell = row.getCell(j, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                        cell.setCellType(CellType.STRING);
                        String stringCellValue = cell.getStringCellValue();
                       // System.out.println(stringCellValue);
                        datas[i-1][indexOfCell] = stringCellValue;//把每个列的值赋给datas每行的
                        indexOfCell++;//每一个单元格赋值结束后移到xia
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            //关流核心代码
            closeFileStream(excel);

        }
        return datas;
    }

    /**
     *  关流
      * @param stream  文件
     */
    public static void closeFileStream(Closeable stream) {
        try {
            if (stream != null)
                stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 批量回写excel
     */
    public static void writeBack(){

        FileInputStream excel=null;
        Workbook workbook=null;
        FileOutputStream fileOutputStream=null;

        try {

             excel = new FileInputStream(constant.EXCEL_PATH);//读取excel
             workbook = WorkbookFactory.create(excel);//创建workbook 对象

            for (WriteBackExcel wbd:wbdlist) {//遍历wbdlist集合，读取excel 后可以将WriteBackExcel 读取的值赋值给excel
                Sheet sheet = workbook.getSheetAt(wbd.getSheetIndex());//获取对应的表单

                Row row=sheet.getRow(wbd.getRowNum());//获取列
                Cell cell=row.getCell(wbd.getCellNum(),Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);//获取列
                cell.setCellType(CellType.STRING);

                cell.setCellValue(wbd.getContent());//单元格赋值

            }

            fileOutputStream=new FileOutputStream(constant.EXCEL_PATH);//输出流写到excel 路径
            workbook.write(fileOutputStream);//完成回写excel

        }

        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            closeFileStream(excel);  //关流
            closeFileStream(fileOutputStream);
        }


    }


}

