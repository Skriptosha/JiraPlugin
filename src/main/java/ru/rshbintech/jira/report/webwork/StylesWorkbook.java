package ru.rshbintech.jira.report.webwork;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import ru.rshbintech.jira.report.lib.FontFormat;
import ru.rshbintech.jira.report.lib.StyleFormat;

public class StylesWorkbook{

    private XSSFCellStyle cs_Data_String10BALLHLC, cs_DoubleBorderAllHCenter, cs_Data_Date10BAllHCVC, cs_Data_String10HCVCC
            , cs_IntegerBorderAllHCenter, cs_Data_String10HCVCCY, cs_DoubleBorderAllHRight, cs_DoubleBorderAllHRightBold
            , cs_Data_String10BALLHLCLightGreen;

    /**
     * Инициализация стилей
     */
    public XSSFCellStyle initStyle(Workbook workbook, String xssFCellStyleName) {
        StyleFormat style = new StyleFormat();
        FontFormat font = new FontFormat();
        XSSFWorkbook xssfWorkbook = (XSSFWorkbook) workbook;
        switch (xssFCellStyleName) {
            case "cs_Data_String10BALLHLC":
                if (cs_Data_String10BALLHLC == null) {
                    return cs_Data_String10BALLHLC = style.cs_StringBorderAllHCenterVCenterWTColor(font.fontOptions(xssfWorkbook, "Calibri"
                            , 10, "", ""), xssfWorkbook, "WHITE");
                } else return cs_Data_String10BALLHLC;
            case "cs_Data_String10BALLHLCLightGreen":
                if (cs_Data_String10BALLHLCLightGreen == null) {
                    return cs_Data_String10BALLHLCLightGreen = style.cs_StringBorderAllHCenterVCenterWTColor(font.fontOptions(xssfWorkbook, "Calibri"
                            , 10, "", ""), xssfWorkbook, "LIGHT_GREEN");
                } else return cs_Data_String10BALLHLCLightGreen;
            case "cs_DoubleBorderAllHCenter":
                if (cs_DoubleBorderAllHCenter == null) {
                    return cs_DoubleBorderAllHCenter = style.cs_DoubleBorderAllHCenterVCenter(font.fontOptions(xssfWorkbook, "Calibri"
                            , 10, "", ""), xssfWorkbook);
                } else return cs_DoubleBorderAllHCenter;
            case "cs_IntegerBorderAllHCenter":
                if (cs_IntegerBorderAllHCenter == null) {
                    return cs_IntegerBorderAllHCenter = style.cs_IntegerBorderAllHCenterVCenter(font.fontOptions(xssfWorkbook, "Calibri"
                            , 10, "", ""), xssfWorkbook);
                } else return cs_IntegerBorderAllHCenter;
            case "cs_DoubleBorderAllHRight":
                if (cs_DoubleBorderAllHRight == null) {
                    return cs_DoubleBorderAllHRight = style.cs_DoubleBorderAllRightWT(font.fontOptions(xssfWorkbook, "Calibri"
                            , 10, "", ""), xssfWorkbook);
                } else return cs_DoubleBorderAllHRight;
            case "cs_DoubleBorderAllHRightBold":
                if (cs_DoubleBorderAllHRightBold == null) {
                    return cs_DoubleBorderAllHRightBold = style.cs_DoubleBorderAllRightWT(font.fontOptions(xssfWorkbook, "Calibri"
                            , 10, "b", ""), xssfWorkbook);
                } else return cs_DoubleBorderAllHRightBold;
            case "cs_Data_Date10BAllHCVC":
                if (cs_Data_Date10BAllHCVC == null) {
                    return cs_Data_Date10BAllHCVC = style.cs_DateBorderAllHCenterVCenter(font.fontOptions(xssfWorkbook, "Calibri"
                            , 10, "", ""), xssfWorkbook, "WHITE");
                } else return cs_Data_Date10BAllHCVC;
            case "cs_Data_String10HCVCC":
                if (cs_Data_String10HCVCC == null) {
                    return cs_Data_String10HCVCC = style.cs_StringBorderAllHCenterVCenterWTColor(font.fontOptions(xssfWorkbook, "Calibri"
                            , 10, "b", ""), xssfWorkbook, "GREY");
                } else return cs_Data_String10HCVCC;
            case "cs_Data_String10HCVCCY":
                if (cs_Data_String10HCVCCY == null) {
                    return cs_Data_String10HCVCCY = style.cs_StringBorderAllHCenterVCenterWTColor(font.fontOptions(xssfWorkbook, "Calibri"
                            , 10, "b", ""), xssfWorkbook, "YELLOW");
                } else return cs_Data_String10HCVCCY;
            default:
                throw new IllegalArgumentException("Такой XSSFCellStyle не найден!");
        }
    }
}