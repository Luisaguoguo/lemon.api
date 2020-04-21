package entityBase;

import cn.afterturn.easypoi.excel.annotation.Excel;

public class Case { //类的泛型

    @Excel(name = "用例编号")
    private int caseID;

    @Excel(name = "用例描述")
    private String description;

    @Excel(name = "参数")
    private String params;

    @Excel(name = "接口编号")
    private String apiId;

    @Excel(name = "期望响应数据")
    private String expectResult;

    @Excel(name = "实际响应数据")
    private String actualResult;

    @Excel(name="检验SQL")
    private String sql;


    public Case() {
    }

    public Case(int caseID, String description, String params, String apiId, String expectResult, String actualResult, String sql) {
        this.caseID = caseID;
        this.description = description;
        this.params = params;
        this.apiId = apiId;
        this.expectResult = expectResult;
        this.actualResult = actualResult;
        this.sql = sql;
    }

    public int getCaseID() {
        return caseID;
    }

    public void setCaseID(int caseID) {
        this.caseID = caseID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getApiId() {
        return apiId;
    }

    public void setApiId(String apiId) {
        this.apiId = apiId;
    }

    public String getExpectResult() {
        return expectResult;
    }

    public void setExpectResult(String expectResult) {
        this.expectResult = expectResult;
    }

    public String getActualResult() {
        return actualResult;
    }

    public void setActualResult(String actualResult) {
        this.actualResult = actualResult;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    @Override
    public String toString() {
        return "Case{" +
                "caseID=" + caseID +
                ", description='" + description + '\'' +
                ", params='" + params + '\'' +
                ", apiId='" + apiId + '\'' +
                ", expectResult='" + expectResult + '\'' +
                ", actualResult='" + actualResult + '\'' +
                ", sql='" + sql + '\'' +
                '}';
    }
}