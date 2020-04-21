package entityBase;

import cn.afterturn.easypoi.excel.annotation.Excel;

public class API{//类的泛型
    @Excel(name = "接口编号")
    private String apiID;

    @Excel(name = "接口名称")
    private String apiName;

    @Excel(name = "接口提交方式")
    private String apiMethod;

    @Excel(name = "接口地址")
    private String url;

    @Excel(name = "参数类型")
    private String contentType;

    public API() {
    }

    public API(String apiID, String apiName, String apiMethod, String url, String contentType) {
        this.apiID = apiID;
        this.apiName = apiName;
        this.apiMethod = apiMethod;
        this.url = url;
        this.contentType = contentType;
    }

    public String getApiID() {
        return apiID;
    }

    public void setApiID(String apiID) {
        this.apiID = apiID;
    }

    public String getApiName() {
        return apiName;
    }

    public void setApiName(String apiName) {
        this.apiName = apiName;
    }

    public String getApiMethod() {
        return apiMethod;
    }

    public void setApiMethod(String apiMethod) {
        this.apiMethod = apiMethod;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    @Override
    public String toString() {
        return "API{" +
                "apiID='" + apiID + '\'' +
                ", apiName='" + apiName + '\'' +
                ", apiMethod='" + apiMethod + '\'' +
                ", url='" + url + '\'' +
                ", contentType='" + contentType + '\'' +
                '}';
    }

    public static class Case { //类的泛型

        @Excel(name = "用例编号")
        private String caseID;

        @Excel(name = "用例描述")
        private String description;

        @Excel(name = "参数")
        private String params;

        @Excel(name = "接口编号")
        private String apiId;


        public Case() {
        }

        public Case(String caseID, String description, String params, String apiId) {
            this.caseID = caseID;
            this.description = description;
            this.params = params;
            this.apiId = apiId;
        }

        public String getCaseID() {
            return caseID;
        }

        public void setCaseID(String caseID) {
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

        @Override
        public String toString() {
            return "Case{" +
                    "caseID='" + caseID + '\'' +
                    ", description='" + description + '\'' +
                    ", params='" + params + '\'' +
                    ", apiId='" + apiId + '\'' +
                    '}';
        }
    }
}
