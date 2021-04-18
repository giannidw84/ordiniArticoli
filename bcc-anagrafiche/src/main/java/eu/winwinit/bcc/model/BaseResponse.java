package eu.winwinit.bcc.model;

public class BaseResponse {

	private Result result;

	public Result getResult() {
		return result;
	}

	public void setResult(Result result) {
		this.result = result;
	}

	public void success() {
		this.result = Result.createSuccessResult();
	}

	public void deleteSuccess() {
		this.result = Result.createdDeleteSuccessResult();
	}

	public void updateSuccess() {
		this.result = Result.createdUpdateSuccessResult();
	}

	public void error() {
		this.result = Result.createErrorResult();
	}

	public void noDataFound() {
		this.result = Result.createNoDataFoundResult();
	}

	public void accessDeniedNoAdmin() {
		this.result = Result.createAccessDeniedNoAdminResult();
	}
}
