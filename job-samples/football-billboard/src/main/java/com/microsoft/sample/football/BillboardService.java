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

}
