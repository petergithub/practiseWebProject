package org.peter.web.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.peter.util.Constants;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

/**
 * @author Shang Pu
 * @version Date: Sep 21, 2015 11:53:15 AM
 */
public class BaseController {

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat(
				Constants.dateFormat), true));
		// binder.registerCustomEditor(int.class, new
		// CustomNumberEditor(int.class, true));
		// binder.registerCustomEditor(int.class, new IntegerEditor());
		// binder.registerCustomEditor(long.class, new
		// CustomNumberEditor(long.class, true));
		// binder.registerCustomEditor(long.class, new LongEditor());
		// binder.registerCustomEditor(double.class, new DoubleEditor());
		// binder.registerCustomEditor(float.class, new FloatEditor());
		
		// Trim String
		binder.registerCustomEditor( String.class, new StringTrimmerEditor( true ) );
	}
}
