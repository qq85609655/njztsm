package com.sklay.core.support;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.PersistenceException;
import javax.servlet.ServletContext;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.TypeFilter;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactoryBean;
import org.springframework.util.ClassUtils;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.handler.SimpleUrlHandlerMapping;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.sklay.core.support.Option;
import com.sklay.core.support.WidgetInfo;
import com.sklay.core.annotation.NameAndValue;
import com.sklay.core.annotation.Setting;
import com.sklay.core.annotation.Widget;
import com.sklay.core.annotation.Widgets;
import com.sklay.core.enums.InputType;
import com.sklay.core.enums.WidgetLevel;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;

public class SimpleSklayManager implements WidgetManager,
		ApplicationContextAware, ServletContextAware, InitializingBean {

	private static final Map<String, Map<String, WidgetInfo>> WIDGET_INFOS = new HashMap<String, Map<String, WidgetInfo>>();
	private static final Map<String, WidgetInfo> WIDGETS = new HashMap<String, WidgetInfo>();
	private static final Map<String, Object> REGION_HANDLERS = new HashMap<String, Object>();

	private String[] packagesToScan;
	private ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();

	private ApplicationContext applicationContext;
	private ServletContext servletContext;

	private static final String WIDGET_CLASS_RESOURCE_PATTERN = "/**/*.class";

	private static final TypeFilter[] widgetTypeFilters = new TypeFilter[] { new AnnotationTypeFilter(
			Widgets.class, false) };

	private static final Map<Object, Configuration> HANLDER_CONFIGURATIONS = new HashMap<Object, Configuration>();

	private static final String WIDGETS_ROOT = "classpath:com/sklay/widgets";

	private String staticResourceLocation = "static/";

	public void setStaticResourceLocation(String staticResourceLocation) {
		this.staticResourceLocation = staticResourceLocation;
	}

	public void setPackagesToScan(String... packagesToScan) {
		this.packagesToScan = packagesToScan;
	}

	private void buildWidgetInfos() throws BeansException,
			ClassNotFoundException, TemplateException {
		for (String pkg : this.packagesToScan) {
			try {
				String pattern = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX
						+ ClassUtils.convertClassNameToResourcePath(pkg)
						+ WIDGET_CLASS_RESOURCE_PATTERN;
				Resource[] resources = this.resourcePatternResolver
						.getResources(pattern);
				MetadataReaderFactory readerFactory = new CachingMetadataReaderFactory(
						this.resourcePatternResolver);

				SimpleUrlHandlerMapping handlerMapping = new SimpleUrlHandlerMapping();
				Map<String, HttpRequestHandler> urlMap = new LinkedHashMap<String, HttpRequestHandler>();

				boolean registerSingleton = false;
				for (Resource resource : resources) {
					if (resource.isReadable()) {
						MetadataReader reader = readerFactory
								.getMetadataReader(resource);
						if (matchesFilter(reader, readerFactory)) {
							String className = reader.getClassMetadata()
									.getClassName();
							Class<?> clazz = Class.forName(className);

							Widgets widgets = AnnotationUtils.findAnnotation(
									clazz, Widgets.class);

							splitModelWidgets(widgets, applicationContext,
									clazz);
							registerSingleton = splitCMSWidgets(urlMap,
									staticResourceLocation, widgets,
									applicationContext, servletContext, clazz,
									registerSingleton);
						}
					}
				}
				if (registerSingleton) {
					handlerMapping.setUrlMap(urlMap);
					applicationContext.getAutowireCapableBeanFactory()
							.initializeBean(handlerMapping, null);

					ConfigurableApplicationContext configurableApplicationContext = (ConfigurableApplicationContext) applicationContext;
					DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory) configurableApplicationContext
							.getBeanFactory();
					defaultListableBeanFactory.registerSingleton(
							"widgetResourceHandlerMapping", handlerMapping);

					// refresh context
					applicationContext.publishEvent(new ContextRefreshedEvent(
							applicationContext));
				}
			} catch (IOException ex) {
				throw new PersistenceException(
						"Failed to scan classpath for unlisted classes", ex);
			}
		}
	}

	private static boolean splitCMSWidgets(
			Map<String, HttpRequestHandler> urlMap,
			String staticResourceLocation, Widgets widgets,
			ApplicationContext applicationContext,
			ServletContext servletContext, Class<?> clazz,
			boolean registerSingleton) throws IOException, TemplateException {

		String region = widgets.value();
		RequestMapping clazzMapping = AnnotationUtils.getAnnotation(clazz,
				RequestMapping.class);
		if (StringUtils.isNotBlank(region) && null == clazzMapping) {
			registerSingleton = true;
			Map<String, WidgetInfo> regionMap = new HashMap<String, WidgetInfo>();
			WIDGET_INFOS.put(region, regionMap);

			Object handler = applicationContext.getBean(clazz);
			REGION_HANDLERS.put(region, handler);

			Method[] methods = handler.getClass().getDeclaredMethods();

			Package _pkg = handler.getClass().getPackage();
			String widgetRoot = "classpath:" + _pkg.getName().replace('.', '/');

			// freemarker configuration
			FreeMarkerConfigurationFactoryBean fcfb = new FreeMarkerConfigurationFactoryBean();
			fcfb.setTemplateLoaderPaths(widgetRoot, WIDGETS_ROOT);
			fcfb.setDefaultEncoding("UTF-8");
			Configuration cfg = fcfb.createConfiguration();
			HANLDER_CONFIGURATIONS.put(handler, cfg);

			// init static resources
			ResourceHttpRequestHandler requestHandler = new ResourceHttpRequestHandler();
			requestHandler.setLocations(Collections
					.singletonList(applicationContext.getResource(widgetRoot
							+ "/" + staticResourceLocation)));
			requestHandler.setCacheSeconds(31556926);
			requestHandler.setServletContext(servletContext);
			requestHandler.setApplicationContext(applicationContext);
			urlMap.put("/widgetResource/" + region + "/**", requestHandler);

			for (Method m : methods) {
				Widget widget = AnnotationUtils.getAnnotation(m, Widget.class);
				if (widget != null) {
					WidgetInfo info = new WidgetInfo();
					info.setHandler(handler);
					info.setMethod(m);
					info.setName(m.getName());
					info.setRegion(region);
					if (widget.settings() != null) {
						for (Setting setting : widget.settings()) {
							com.sklay.core.support.Setting st = new com.sklay.core.support.Setting();
							st.setKey(setting.key());
							st.setInputType(setting.inputType());
							NameAndValue[] options = setting.options();
							String optionLoader = StringUtils
									.trimToEmpty(setting.optionsLoader());
							boolean hasOptionLoader = !StringUtils
									.isEmpty(optionLoader);
							if ((hasOptionLoader || options.length > 1)
									&& setting.inputType() != InputType.RADIO
									&& setting.inputType() != InputType.CHECKBOX) {
								st.setInputType(InputType.SELECT);
							} else {
								st.setInputType(setting.inputType());
							}
							if (hasOptionLoader) {
								st.setOptionsLoader(com.sklay.core.util.ClassUtils
										.getMethodByName(clazz, optionLoader));
							}
							if (options.length > 1) {
								List<Option> opts = new ArrayList<Option>();
								for (NameAndValue valueAndName : options) {
									Option opt = new Option();
									opt.setValue(valueAndName.value());
									opt.setName(StringUtils
											.isBlank(valueAndName.name()) ? valueAndName
											.value() : valueAndName.name());
									opts.add(opt);
								}
								st.setOptions(opts.toArray(new Option[opts
										.size()]));
							}
							st.setName(setting.name());
							st.setValue(setting.value());
							info.addSetting(st);
						}
					}

					info.setShow(widget.show());

					info.setJs(widget.js());

					info.setCss(widget.css());

					if (StringUtils.isNotBlank(widget.onAdd())) {
						info.setOnAdd(com.sklay.core.util.ClassUtils
								.getMethodByName(clazz, widget.onAdd()));
					}

					if (StringUtils.isNotBlank(widget.onEdit())) {
						info.setOnEdit(com.sklay.core.util.ClassUtils
								.getMethodByName(clazz, widget.onEdit()));
					}

					if (StringUtils.isNotBlank(widget.onRemove())) {
						info.setOnRemove(com.sklay.core.util.ClassUtils
								.getMethodByName(clazz, widget.onRemove()));
					}

					regionMap.put(info.getName(), info);
				}
			}
		}
		return registerSingleton;
	}

	private static void splitModelWidgets(Widgets widgets,
			ApplicationContext applicationContext, Class<?> clazz) {

		String modelName = widgets.value();
		String modelDescription = widgets.description();
		RequestMapping clazzMapping = AnnotationUtils.getAnnotation(clazz,
				RequestMapping.class);
		if (StringUtils.isNotBlank(modelName) && null != clazzMapping) {

			String clazzMappingName = clazzMapping.value()[0];

			boolean isShowModel = widgets.isShow();

			Object handler = applicationContext.getBean(clazz);
			REGION_HANDLERS.put(modelName, handler);

			Method[] methods = clazz.getMethods();
			List<WidgetInfo> methodChild = Lists.newArrayList();
			for (Method m : methods) {
				Widget widget = AnnotationUtils.getAnnotation(m, Widget.class);

				if (widget != null) {
					RequestMapping methodMapping = AnnotationUtils
							.getAnnotation(m, RequestMapping.class);
					String methodMappingName = (null != methodMapping) ? methodMapping
							.value()[0] : "";
					String methodName = modelName + widget.name();
					String methodDescription = widget.description();
					WidgetLevel level = widget.level();
					boolean isShowMethod = widget.isShow();

					WidgetInfo childWidget = new WidgetInfo(methodName,
							methodDescription, clazzMappingName
									+ methodMappingName, level, isShowMethod);
					methodChild.add(childWidget);
				}
			}

			modelName += ":model";
			WidgetInfo widgetInfo = new WidgetInfo(modelName, modelDescription,
					WidgetLevel.FIRST, isShowModel, methodChild);

			WIDGETS.put(modelName, widgetInfo);
		}
	}

	/**
	 * Check whether any of the configured entity type filters matches the
	 * current class descriptor contained in the metadata reader.
	 */
	private boolean matchesFilter(MetadataReader reader,
			MetadataReaderFactory readerFactory) throws IOException {
		for (TypeFilter filter : widgetTypeFilters) {
			if (filter.match(reader, readerFactory)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		buildWidgetInfos();
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
	}

	@Override
	public WidgetInfo getWidgetInfo(String widgetName) {
		return Collections.unmodifiableMap(WIDGETS).get(widgetName);
	}

	@Override
	public Map<String, WidgetInfo> getModelWidgets() {
		return Collections.unmodifiableMap(WIDGETS);
	}

	@Override
	public void addRemoteWidgets(URL... urls) {

	}

	@Override
	public Object getRegionHandler(String region) {
		if (region == null) {
			return REGION_HANDLERS;
		} else {
			return REGION_HANDLERS.get(region);
		}
	}

	@Override
	public WidgetInfo getWidgetInfo(String region, String widgetName) {
		Map<String, WidgetInfo> infos = WIDGET_INFOS.get(region);
		return infos == null ? null : infos.get(widgetName);
	}

	@Override
	public Map<String, Map<String, WidgetInfo>> getWidgetInfos() {
		return WIDGET_INFOS;
	}

	@Override
	public Configuration getConfiguration(Object handler) {
		return HANLDER_CONFIGURATIONS.get(handler);
	}

	@Override
	public Map<String, AuthBase> authorModels() {
		Map<String, AuthBase> map = Maps.newHashMap();

		Map<String, WidgetInfo> base = Collections.unmodifiableMap(WIDGETS);
		Set<String> keys = base.keySet();
		for (String key : keys) {
			WidgetInfo widgetInfo = base.get(key);
			AuthBase authBase = splitAuthBase(widgetInfo);
			List<WidgetInfo> children = widgetInfo.getChild();
			List<AuthBase> child = Lists.newArrayList();
			if (CollectionUtils.isNotEmpty(children)) {
				for (WidgetInfo wChild : children)
					child.add(splitAuthBase(wChild));
			}
			if (CollectionUtils.isNotEmpty(child))
				authBase.setChildBase(child);
			map.put(key, authBase);
		}
		return map;
	}

	public static Map<String, WidgetInfo> models() {

		return Collections.unmodifiableMap(WIDGETS);
	}

	@Override
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	private static AuthBase splitAuthBase(WidgetInfo widgetInfo) {
		AuthBase authBase = null;
		String name = widgetInfo.getName();
		String description = widgetInfo.getDescription();
		String uri = widgetInfo.getUri();
		WidgetLevel level = widgetInfo.getLevel();
		boolean show = widgetInfo.isShow();
		authBase = new AuthBase(name, description, uri, level, show);

		return authBase;
	}
}
