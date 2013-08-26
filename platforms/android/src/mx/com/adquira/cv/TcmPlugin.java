package mx.com.adquira.cv;

import mx.com.adquira.cv.dto.LoginRequestDto;
import mx.com.adquira.cv.helperobjects.Constants;
import mx.com.adquira.cv.helperobjects.TCMLoginResponse;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.ComponentName;
import android.content.Intent;

public class TcmPlugin extends CordovaPlugin {
	public static final String ACTION_TCM_LOGIN = "tcmLogin";
	public static final String ACTION_TCM_PAYMENT = "tcmPayment";
	private CallbackContext callback;

	@Override
	public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
		System.out.println("Inside Execute");
		callback = callbackContext;
		try {
			if (ACTION_TCM_LOGIN.equals(action)) {
				tcmLoginActionHandler(args, callbackContext);
				return true;
			}
			callbackContext.error("Invalid action");
			return false;
		} catch (Exception e) {
			System.err.println("Exception: " + e.getMessage());
			callbackContext.error(e.getMessage());
			return false;
		}
	}

	private boolean tcmLoginActionHandler(JSONArray args, CallbackContext callbackContext) throws JSONException {
		JSONObject arg_object = args.getJSONObject(0);
		
		String username = (String) arg_object.get("user");
		String password = (String)arg_object.get("password");
		Intent myIntent = new Intent();
		myIntent.setComponent(new ComponentName("mx.com.adquira.cv.tcmpintents", "mx.com.adquira.cv.tcmpintents.TCMLoginActivity"));
		myIntent.putExtra(Constants.LOGIN_REQUEST_DTO, new LoginRequestDto(username, password));
		this.cordova.startActivityForResult(this, myIntent,Constants.TCM_LOGIN_ACTIVITY);	
		return true;
	}
	
	public void onActivityResult(int requestCode, int resultCode, Intent data) {	
		if (requestCode == Constants.TCM_LOGIN_ACTIVITY) {
			TCMLoginResponse resp = (TCMLoginResponse) data.getSerializableExtra(Constants.LOGIN_RESPONSE);
		    callback.success(resp.to_json());
	    }
	}
	   
}
