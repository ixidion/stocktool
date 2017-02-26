package de.bluemx.stocktool.analysis;

/**
 * Created by teclis on 27.01.17.
 */
public class AnalysisObject {
    private String fieldname;
    private int result;
    private RuntimeException ex;

    public AnalysisObject() {
    }

    public String getFieldname() {
        return fieldname;
    }

    public void setFieldname(String fieldname) {
        this.fieldname = fieldname;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public RuntimeException getEx() {
        return ex;
    }

    public void setEx(RuntimeException ex) {
        this.ex = ex;
    }
}
