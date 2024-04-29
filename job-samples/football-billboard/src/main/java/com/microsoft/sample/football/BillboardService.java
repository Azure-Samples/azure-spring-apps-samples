package com.microsoft.sample.football;

import org.springframework.stereotype.Service;

@Service
public class BillboardService {

    private ResultReport resultReport;

    /**
     * @return the resultReport
     */
    public ResultReport getResultReport() {
        return resultReport;
    }

<<<<<<< HEAD
	/**
	 * @param resultReport the resultReport to set
	 */
	public void setResultReport(ResultReport resultReport) {
		if (this.resultReport == null) {
			this.resultReport = resultReport;
		} else {
			this.resultReport.setSummaryCount(this.resultReport.getSummaryCount() + resultReport.getSummaryCount());
			this.resultReport.setLastExecuted(resultReport.getLastExecuted());
		}
	}
=======
    /**
     * @param resultReport the resultReport to set
     */
    public void setResultReport(ResultReport resultReport) {
        if(resultReport == null) {
            return;
        }
        
        if (this.resultReport == null) {
            this.resultReport = resultReport;
        } else {
            this.resultReport.setSummaryCount(this.resultReport.getSummaryCount() + resultReport.getSummaryCount());
            this.resultReport.setLastExecuted(resultReport.getLastExecuted());
        }
    }
>>>>>>> 378e8bcbb824676ace8e104cbab7740822c0a7ee

}
