package org.flywind.kissy.mixins;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ClientElement;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.InjectContainer;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Path;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;
import org.flywind.kissy.utils.KissyUtil;

@Import(stylesheet={"${kissy.path}/css/dpl/base.css","${kissy.path}/editor/theme/cool/editor.css"})
public class Editor {

	@Parameter
	private JSONObject params;
	
	@Parameter(defaultPrefix = BindingConstants.LITERAL)
	private String exclude;
	
	/**
	 * 上传图片的URL请求
	 * 
	 * en *
	 * Upload img url request.
	 */
	@Parameter(defaultPrefix=BindingConstants.LITERAL)
	private String url;
	
	/**
     * (可选, 默认: 0 = 无限制)
     * 文件大小限制.
     * 
     * en *
     * File size limit, 0 = No size limit
     */
    @Parameter(defaultPrefix = BindingConstants.LITERAL, value = "0")
    private String sizeLimit;
	
	@InjectContainer
	private ClientElement clientElement;
	
	@Inject
	private JavaScriptSupport javaScriptSupport;
	
	public void afterRender(){
		
		JSONObject data = new JSONObject();
		data.put("id", clientElement.getClientId());
		data.put("url", url);
		
		if(params == null){
			params = new JSONObject();
		}
		
		JSONObject defaults = new JSONObject();
		if(exclude != null){
			defaults.put("exclude", exclude);
		}
		if(sizeLimit != "0"){
			defaults.put("sizeLimit", sizeLimit);
		}
		KissyUtil.merge(defaults, params);
		toJson(defaults);
		data.put("params", defaults);
		
		javaScriptSupport.require("inits/editor").invoke("init").with(data);
	}
	
	protected void toJson(JSONObject json){}
}
