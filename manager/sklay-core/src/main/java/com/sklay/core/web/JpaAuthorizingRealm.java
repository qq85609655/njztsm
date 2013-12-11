package com.sklay.core.web;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.config.ConfigurationException;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.hibernate.annotations.QueryHints;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSONArray;
import com.google.common.collect.Sets;

/**
 * 
 * .
 * <p/>
 * 
 * @author <a href="mailto:1988fuyu@163.com">fuyu</a>
 * 
 * @version v1.0 2013-7-5
 */
public class JpaAuthorizingRealm<T> extends AuthorizingRealm implements
		InitializingBean {

	public enum SaltStyle {
		NO_SALT, CRYPT, COLUMN, EXTERNAL
	};

	private CriteriaBuilder criteriaBuilder;

	@PersistenceContext
	private EntityManager entityManager;

	private Class<T> entityClass;

	private String groupField;

	private Class<T> groupClass;

	private String userPKField = "id";

	private String usernameField;

	private String passwordField;

	private String rolesField;

	private String permissionsField;

	protected SaltStyle saltStyle = SaltStyle.NO_SALT;

	private String saltField = "salt";

	@Override
	protected AuthorizationInfo getAuthorizationInfo(
			PrincipalCollection principals) {
		return this.doGetAuthorizationInfo(principals);
	}

	/**
	 * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用.
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
		// null usernames are invalid
		if (principals == null) {
			throw new AuthorizationException(
					"PrincipalCollection method argument cannot be null.");
		}
		// (entityClass)getAvailablePrincipal(principals);
		Object user = getAvailablePrincipal(principals);
		Long userId = (Long) getEntityFieldValue(entityClass, user, userPKField);

		user = getEntity(Collections.singletonMap(userPKField, userId));

		if (user == null) {
			return null;
		}
		Set<String> permissions = null;
		Set<String> roles = null;
		if (StringUtils.isNotBlank(permissionsField)
				&& StringUtils.isNotBlank(groupField)) {
			permissions = getEntityResources(user, permissionsField);
		}
		if (StringUtils.isNotBlank(rolesField)
				&& StringUtils.isNotBlank(groupField)) {
			roles = getEntityResources(user, rolesField);
		}
		SimpleAuthorizationInfo author = new SimpleAuthorizationInfo();
		author.setRoles(roles);
		author.setStringPermissions(permissions);
		return author;

	}

	/**
	 * 认证回调函数,登录时调用.
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {

		UsernamePasswordToken upToken = (UsernamePasswordToken) token;
		String username = upToken.getUsername();

		// Null username is invalid
		if (username == null) {
			throw new AccountException(
					"Null usernames are not allowed by this realm.");
		}

		Object user = getEntity(Collections.singletonMap(usernameField,
				username));

		if (user == null) {
			throw new UnknownAccountException();
		}

		SimpleAuthenticationInfo info = null;
		String password = null;
		String salt = null;
		switch (saltStyle) {
		case NO_SALT:
			password = (String) getEntityFieldValue(entityClass, user,
					passwordField);
			break;
		case CRYPT:
			// TODO: separate password and hash from getPasswordForUser[0]
			throw new ConfigurationException("Not implemented yet");
		case COLUMN:
			password = (String) getEntityFieldValue(entityClass, user,
					passwordField);
			salt = (String) getEntityFieldValue(entityClass, user, saltField);
			break;
		case EXTERNAL:
			password = (String) getEntityFieldValue(entityClass, user,
					passwordField);
			salt = (String) getEntityFieldValue(entityClass, user, saltField);
		}

		if (password == null) {
			throw new UnknownAccountException("No account found for user ["
					+ username + "]");
		}

		// info = new SimpleAuthenticationInfo(getEntityFieldValue(entityClass,
		// user, userPKField), password.toCharArray(), getName());
		info = new SimpleAuthenticationInfo(user, password.toCharArray(),
				getName());
		if (salt != null) {
			info.setCredentialsSalt(ByteSource.Util.bytes(salt));
		}

		return info;
	}

	private Object getEntityFieldValue(Class<T> entityClass, Object entity,
			String fieldName) {
		PropertyDescriptor propertyDescriptor = BeanUtils
				.getPropertyDescriptor(entityClass, fieldName);
		try {
			return propertyDescriptor.getReadMethod().invoke(entity);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}

	private Set<String> getEntityResources(Object entity, String field) {
		Set<String> resources = null;
		Object group = getEntityFieldValue(entityClass, entity, groupField);
		if (group == null)
			return null;
		String perms = (String) getEntityFieldValue(groupClass, group, field);

		if (StringUtils.isNotBlank(perms)) {
			List<String> permsList = JSONArray.parseArray(perms, String.class);
			resources = Sets.newHashSet(permsList);
		}
		return resources;

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private Object getEntity(Map<String, ?> propValueMap) {
		CriteriaQuery criteriaQuery = criteriaBuilder.createQuery(entityClass);
		Root entityRoot = criteriaQuery.from(entityClass);
		Predicate[] predicates = new Predicate[propValueMap.size()];
		int i = 0;
		for (Iterator<String> propNameIt = propValueMap.keySet().iterator(); propNameIt
				.hasNext(); i++) {
			String propName = propNameIt.next();
			predicates[i] = criteriaBuilder.equal(entityRoot.get(propName),
					propValueMap.get(propName));
		}
		criteriaQuery.select(entityRoot);
		criteriaQuery.where(predicates);
		Query query = entityManager.createQuery(criteriaQuery);
		query.setHint(QueryHints.CACHEABLE, true);
		List results = query.getResultList();
		if (CollectionUtils.isEmpty(results)) {
			return null;
		} else {
			return results.get(0);
		}
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		this.criteriaBuilder = entityManager.getCriteriaBuilder();
	}

	public void setSaltStyle(SaltStyle saltStyle) {
		this.saltStyle = saltStyle;
	}

	public void setUsernameField(String usernameField) {
		this.usernameField = usernameField;
	}

	public void setPasswordField(String passwordField) {
		this.passwordField = passwordField;
	}

	public void setRolesField(String rolesField) {
		this.rolesField = rolesField;
	}

	public void setPermissionsField(String permissionsField) {
		this.permissionsField = permissionsField;
	}

	public void setSaltField(String saltField) {
		this.saltField = saltField;
	}

	public void setGroupField(String groupField) {
		this.groupField = groupField;
	}

	public void setEntityClass(Class<T> entityClass) {
		this.entityClass = entityClass;
	}

	public void setGroupClass(Class<T> groupClass) {
		this.groupClass = groupClass;
	}
}
