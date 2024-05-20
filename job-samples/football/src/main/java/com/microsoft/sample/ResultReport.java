package com.microsoft.sample;

import java.util.Date;

public class ResultReport {
    
    private Date lastExecuted;

    private int summaryCount;

    /**
     * @return the lastExecuted
     */
    public Date getLastExecuted() {
        return lastExecuted;
    }

    /**
     * @param lastExecuted the lastExecuted to set
     */
    public void setLastExecuted(Date lastExecuted) {
        this.lastExecuted = lastExecuted;
    }

    /**
     * @return the summaryCount
     */
    public int getSummaryCount() {
        return summaryCount;
    }

    /**
     * @param summaryCount the summaryCount to set
     */
    public void setSummaryCount(int summaryCount) {
        this.summaryCount = summaryCount;
    }

}
