package org.flywind.kissy.test.pages;

import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;

public class Test {

	@Inject
	private JavaScriptSupport javaScriptSupport;
	
	@Inject
	private ComponentResources componentResources;
	
	public void afterRender(){
		String url = componentResources.createEventLink("del").toURI();
		javaScriptSupport.require("test").invoke("init").with(url);
	}
	
	public JSONObject onDel(){
		JSONObject spec = new JSONObject();
		spec.put("success", "成功了");
		return spec;
	}
	
	public void setupRender(){
		
	}
}
