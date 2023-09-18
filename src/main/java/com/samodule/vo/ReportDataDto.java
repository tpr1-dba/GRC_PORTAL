package com.samodule.vo;

public class ReportDataDto {

	    private Long requestId;
	    private String requesterName;
	    private String requesterSapId;
	    private String finalApprovedBasis;
	    private String approvalRemarks;
	    private String requestReason;
	    private String requestTcode;
	    private String metigationYn;
	    private String status;
	    private Integer pendingDueDays;

	    // Getters and setters for each field

		public String getMetigationYn() {
			return metigationYn;
		}

		public void setMetigationYn(String metigationYn) {
			this.metigationYn = metigationYn;
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		public Integer getPendingDueDays() {
			return pendingDueDays;
		}

		public void setPendingDueDays(Integer pendingDueDays) {
			this.pendingDueDays = pendingDueDays;
		}

		public Long getRequestId() {
			return requestId;
		}

		public void setRequestId(Long requestId) {
			this.requestId = requestId;
		}

	    public String getRequesterName() {
	        return requesterName;
	    }

	    public void setRequesterName(String requesterName) {
	        this.requesterName = requesterName;
	    }

	    public String getRequesterSapId() {
	        return requesterSapId;
	    }

	    public void setRequesterSapId(String requesterSapId) {
	        this.requesterSapId = requesterSapId;
	    }

	    public String getFinalApprovedBasis() {
	        return finalApprovedBasis;
	    }

	    public void setFinalApprovedBasis(String finalApprovedBasis) {
	        this.finalApprovedBasis = finalApprovedBasis;
	    }

	    public String getApprovalRemarks() {
	        return approvalRemarks;
	    }

	    public void setApprovalRemarks(String approvalRemarks) {
	        this.approvalRemarks = approvalRemarks;
	    }

	    public String getRequestReason() {
	        return requestReason;
	    }

	    public void setRequestReason(String requestReason) {
	        this.requestReason = requestReason;
	    }

	    public String getRequestTcode() {
	        return requestTcode;
	    }

	    public void setRequestTcode(String requestTcode) {
	        this.requestTcode = requestTcode;
	    }

	
}
