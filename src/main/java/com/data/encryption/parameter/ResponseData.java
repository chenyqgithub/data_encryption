package com.data.encryption.parameter;



public class ResponseData<T> {
    /**
     * 响应信息
     */
    private String resultMessage = StaticVariable.OPERATION_SUCCESS;
    /**
     * 响应状态
     */
    private String resultCode = StaticVariable.REQUEST_SUCCESS;
    /**
     * 当前页数
     */
    private String currentPage = StaticVariable.CURRENTPAGE;
    /**
     * 响应数据
     */
    private T dataCollection;
    /**
     * 总页数
     */
    private String totalPage = StaticVariable.TOTALPAGE;


    public String getResultMessage() {
        return resultMessage;
    }

    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(String currentPage) {
        this.currentPage = currentPage;
    }

    public T getDataCollection() {
        return dataCollection;
    }

    public void setDataCollection(T dataCollection) {
        this.dataCollection = dataCollection;
    }

    public String getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(String totalPage) {
        this.totalPage = totalPage;
    }


    @Override
    public String toString() {
        return "ResponseData{" +
                "resultMessage='" + resultMessage + '\'' +
                ", resultCode='" + resultCode + '\'' +
                ", currentPage='" + currentPage + '\'' +
                ", dataCollection='" + dataCollection + '\'' +
                ", totalPage='" + totalPage + '\'' +
                '}';
    }

}
