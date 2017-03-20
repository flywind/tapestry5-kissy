package org.flywind.kissy.services;

import java.io.IOException;

import org.apache.tapestry5.*;
import org.apache.tapestry5.annotations.Path;
import org.apache.tapestry5.ioc.Configuration;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.OrderedConfiguration;
import org.apache.tapestry5.ioc.Resource;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.Contribute;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Local;
import org.apache.tapestry5.ioc.services.ApplicationDefaults;
import org.apache.tapestry5.ioc.services.FactoryDefaults;
import org.apache.tapestry5.ioc.services.SymbolProvider;
import org.apache.tapestry5.services.*;
import org.apache.tapestry5.services.javascript.AMDWrapper;
import org.apache.tapestry5.services.javascript.JavaScriptModuleConfiguration;
import org.apache.tapestry5.services.javascript.JavaScriptStack;
import org.apache.tapestry5.services.javascript.ModuleManager;
import org.apache.tapestry5.services.javascript.StackExtension;
import org.apache.tapestry5.services.javascript.StackExtensionType;
import org.flywind.kissy.KissySymbolConstants;
import org.slf4j.Logger;

/**
 * This module is automatically included as part of the Tapestry IoC Registry, it's a good place to
 * configure and extend Tapestry, or to place your own service definitions.
 */
public class KissyModule
{
	/**
     * 提供工厂默认配置，也可自己定义
     * @param configuration IOC配置
     */
    @Contribute(SymbolProvider.class)
	@FactoryDefaults
	public static void contributeFactoryDefaults(
			MappedConfiguration<String, Object> configuration) {
    	//定义资源路径
        configuration.add(KissySymbolConstants.KISSY_PATH,"classpath:/META-INF/modules/kissy");
    }
    
    /**
     * 组件名称定义
     * @param configuration
     */
    public static void contributeComponentClassResolver(Configuration<LibraryMapping> configuration)
	{
		configuration.add(new LibraryMapping("kissy", "org.flywind.kissy"));
	}
    
    /**
     * 贡献组件静态资源目录，组件开发时未贡献的目录在项目中无法调用
     * @param configuration
     */
    public static void contributeClasspathAssetAliasManager(
			MappedConfiguration<String, String> configuration) {
		//定义modules资源路径,如果是modules也可以不定义，默认会是modules这个文件夹
		configuration.add("modules.path", "META-INF/modules");
	}
    
    /*@Contribute(JavaScriptStack.class)
    @Core
    public static void setupInternalJavaScriptStack(OrderedConfiguration<StackExtension> configuration){
    	String kissyPath = "${" + KissySymbolConstants.PLUGINS_PATH + "}/kissy/seed-min.js";
    	add(configuration, StackExtensionType.LIBRARY,kissyPath);
    }
    
    private static void add(OrderedConfiguration<StackExtension> configuration, StackExtensionType type, String... paths)
    {
        for (String path : paths)
        {
            int slashx = path.lastIndexOf('/');
            String id = path.substring(slashx + 1);

            configuration.add(id, new StackExtension(type, path));
        }
    }*/
    @Contribute(ModuleManager.class)
    public static void setupBaseModules(MappedConfiguration<String, Object> configuration,
                                        @Path("${kissy.path}/seed.js")
                                        Resource kissyShim)
    {
    	configuration.add("kissy", new JavaScriptModuleConfiguration(kissyShim));
    	for (String name : new String[]{"node","promise","io","dom/base","dom/ie","dom/class-list","dom/selector","dom/shake","dom/focusin","event","event/dom/base","event/dom/ie","event/dom/shake","event/dom/focusin","event/base","event/custom","event/dom/touch","anim","anim/base","anim/timer","anim/transition",
    			"base","html-parser","attribute","runtime","xtemplate/runtime","component/control","component/manager","component/plugin/drag","component/container","component/extension/shim","component/extension/align","component/extension/content-xtpl",
    			"component/extension/content-render","component/extension/delegate-children","button","overlay","menubutton","menu","dd","dd/plugin/constrain","swf","tabs","toolbar","json"})
        {
    		Resource path = kissyShim.forFile(name + ".js");
    		configuration.add(name, new JavaScriptModuleConfiguration(path));
        }
    	
    	for (String editor : new String[]{"editor","editor/plugin/source-area","editor/plugin/button","editor/plugin/code","editor/plugin/dialog-loader","editor/plugin/separator","editor/plugin/bold","editor/plugin/font/ui","editor/plugin/menubutton",
    			"editor/plugin/bold/cmd","editor/plugin/font/cmd","editor/plugin/italic","editor/plugin/italic/cmd","editor/plugin/font-family","editor/plugin/font-family/cmd","editor/plugin/font-size","editor/plugin/font-size/cmd","editor/plugin/strike-through",
    			"editor/plugin/strike-through/cmd","editor/plugin/underline","editor/plugin/underline/cmd","editor/plugin/checkbox-source-area","editor/plugin/image","editor/plugin/bubble","editor/plugin/contextmenu","editor/plugin/focus-fix","editor/plugin/link",
    			"editor/plugin/link/utils","editor/plugin/fore-color","editor/plugin/color/btn","editor/plugin/overlay","editor/plugin/fore-color/cmd","editor/plugin/color/cmd","editor/plugin/back-color","editor/plugin/back-color/cmd","editor/plugin/resize","editor/plugin/draft",
    			"editor/plugin/local-storage","editor/plugin/flash-bridge","editor/plugin/undo","editor/plugin/undo/btn","editor/plugin/undo/cmd","editor/plugin/indent","editor/plugin/indent/cmd","editor/plugin/dent-cmd","editor/plugin/list-utils","editor/plugin/outdent",
    			"editor/plugin/outdent/cmd","editor/plugin/unordered-list","editor/plugin/list-utils/btn","editor/plugin/unordered-list/cmd","editor/plugin/list-utils/cmd","editor/plugin/ordered-list","editor/plugin/ordered-list/cmd","editor/plugin/element-path","editor/plugin/page-break",
    			"editor/plugin/fake-objects","editor/plugin/preview","editor/plugin/maximize","editor/plugin/maximize/cmd","editor/plugin/remove-format","editor/plugin/remove-format/cmd","editor/plugin/heading","editor/plugin/heading/cmd","editor/plugin/justify-left","editor/plugin/justify-left/cmd",
    			"editor/plugin/justify-right","editor/plugin/table","editor/plugin/smiley","editor/plugin/flash","editor/plugin/flash-common/base-class","editor/plugin/flash-common/utils","editor/plugin/video","editor/plugin/xiami-music","editor/plugin/drag-upload","editor/plugin/justify-center","editor/plugin/justify-cmd",
    			"editor/plugin/justify-center/cmd","editor/plugin/justify-right/cmd","editor/plugin/image/dialog","editor/plugin/dialog","editor/plugin/code/dialog","editor/plugin/link/dialog","editor/plugin/table/dialog","editor/plugin/flash/dialog","editor/plugin/xiami-music/dialog","editor/plugin/video/dialog","editor/plugin/color/dialog"})
        {
    		Resource path = kissyShim.forFile(editor + ".js");
    		configuration.add(editor, new JavaScriptModuleConfiguration(path));
        }
    }
}
