package de.bluemx.stocktool.db.dao;

public class DBException extends RuntimeException {
    private Object failedObject;
    public DBException(String message, RuntimeException e, Object failedObject) {
        super(message, e);
        this.failedObject = failedObject;
    }

    public Object getFailedObject() {
        return failedObject;
    }

    public void setFailedObject(Object failedObject) {
        this.failedObject = failedObject;
    }
}
