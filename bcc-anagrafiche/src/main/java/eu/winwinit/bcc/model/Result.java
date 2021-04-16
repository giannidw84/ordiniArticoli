package eu.winwinit.bcc.model;

public class Result {

	public static final int SUCCESS_CODE = 0;
	public static final String SUCCESS_DESCRIPTION = "success";
	public static final int ERROR_CODE = -1;
	public static final String ERROR_DESCRIPTION = "error";
	public static final int NO_DATA_FOUND_CODE = 404;
	public static final String NO_DATA_FOUND_DESCRIPTION = "no data found";
	public static final int ACCESS_DENIED_NO_ADMIN_CODE = 999;
	public static final String ACCESS_DENIED_NO_ADMIN_DESCRIPTION = "Administrator access only";
	private int code;
	private String description;
	private String exceptionDetails;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getExceptionDetails() {
		return exceptionDetails;
	}

	public void setExceptionDetails(String exceptionDetails) {
		this.exceptionDetails = exceptionDetails;
	}

	public static Result createSuccessResult() {
		Result result = new Result();
		result.setCode(SUCCESS_CODE);
		result.setDescription(SUCCESS_DESCRIPTION);
		return result;
	}

	public static Result createErrorResult() {
		Result result = new Result();
		result.setCode(ERROR_CODE);
		result.setDescription(ERROR_DESCRIPTION);
		return result;
	}

	public static Result createNoDataFoundResult() {
		Result result = new Result();
		result.setCode(NO_DATA_FOUND_CODE);
		result.setDescription(NO_DATA_FOUND_DESCRIPTION);
		return result;
	}


	public static Result createAccessDeniedNoAdminResult() {
		Result result = new Result();
		result.setCode(ACCESS_DENIED_NO_ADMIN_CODE);
		result.setDescription(ACCESS_DENIED_NO_ADMIN_DESCRIPTION);
		return result;
	}
	
}
