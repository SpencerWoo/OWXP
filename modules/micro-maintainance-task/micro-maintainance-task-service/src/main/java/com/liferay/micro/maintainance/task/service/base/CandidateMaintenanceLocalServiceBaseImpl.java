/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.micro.maintainance.task.service.base;

import aQute.bnd.annotation.ProviderType;

import com.liferay.micro.maintainance.task.model.CandidateMaintenance;
import com.liferay.micro.maintainance.task.service.CandidateMaintenanceLocalService;
import com.liferay.micro.maintainance.task.service.persistence.CandidateMaintenancePersistence;
import com.liferay.micro.maintainance.task.service.persistence.TaskEntryPersistence;

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdate;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdateFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DefaultActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.module.framework.service.IdentifiableOSGiService;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.service.BaseLocalServiceImpl;
import com.liferay.portal.kernel.service.PersistedModelLocalServiceRegistry;
import com.liferay.portal.kernel.service.persistence.ClassNamePersistence;
import com.liferay.portal.kernel.service.persistence.UserPersistence;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.util.List;

import javax.sql.DataSource;

/**
 * Provides the base implementation for the candidate maintenance local service.
 *
 * <p>
 * This implementation exists only as a container for the default service methods generated by ServiceBuilder. All custom service methods should be put in {@link com.liferay.micro.maintainance.task.service.impl.CandidateMaintenanceLocalServiceImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.micro.maintainance.task.service.impl.CandidateMaintenanceLocalServiceImpl
 * @see com.liferay.micro.maintainance.task.service.CandidateMaintenanceLocalServiceUtil
 * @generated
 */
@ProviderType
public abstract class CandidateMaintenanceLocalServiceBaseImpl
	extends BaseLocalServiceImpl implements CandidateMaintenanceLocalService,
		IdentifiableOSGiService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link com.liferay.micro.maintainance.task.service.CandidateMaintenanceLocalServiceUtil} to access the candidate maintenance local service.
	 */

	/**
	 * Adds the candidate maintenance to the database. Also notifies the appropriate model listeners.
	 *
	 * @param candidateMaintenance the candidate maintenance
	 * @return the candidate maintenance that was added
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CandidateMaintenance addCandidateMaintenance(
		CandidateMaintenance candidateMaintenance) {
		candidateMaintenance.setNew(true);

		return candidateMaintenancePersistence.update(candidateMaintenance);
	}

	/**
	 * Creates a new candidate maintenance with the primary key. Does not add the candidate maintenance to the database.
	 *
	 * @param candidateMaintenanceId the primary key for the new candidate maintenance
	 * @return the new candidate maintenance
	 */
	@Override
	public CandidateMaintenance createCandidateMaintenance(
		long candidateMaintenanceId) {
		return candidateMaintenancePersistence.create(candidateMaintenanceId);
	}

	/**
	 * Deletes the candidate maintenance with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param candidateMaintenanceId the primary key of the candidate maintenance
	 * @return the candidate maintenance that was removed
	 * @throws PortalException if a candidate maintenance with the primary key could not be found
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public CandidateMaintenance deleteCandidateMaintenance(
		long candidateMaintenanceId) throws PortalException {
		return candidateMaintenancePersistence.remove(candidateMaintenanceId);
	}

	/**
	 * Deletes the candidate maintenance from the database. Also notifies the appropriate model listeners.
	 *
	 * @param candidateMaintenance the candidate maintenance
	 * @return the candidate maintenance that was removed
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public CandidateMaintenance deleteCandidateMaintenance(
		CandidateMaintenance candidateMaintenance) {
		return candidateMaintenancePersistence.remove(candidateMaintenance);
	}

	@Override
	public DynamicQuery dynamicQuery() {
		Class<?> clazz = getClass();

		return DynamicQueryFactoryUtil.forClass(CandidateMaintenance.class,
			clazz.getClassLoader());
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	@Override
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery) {
		return candidateMaintenancePersistence.findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.micro.maintainance.task.model.impl.CandidateMaintenanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @return the range of matching rows
	 */
	@Override
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery, int start,
		int end) {
		return candidateMaintenancePersistence.findWithDynamicQuery(dynamicQuery,
			start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.micro.maintainance.task.model.impl.CandidateMaintenanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching rows
	 */
	@Override
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery, int start,
		int end, OrderByComparator<T> orderByComparator) {
		return candidateMaintenancePersistence.findWithDynamicQuery(dynamicQuery,
			start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(DynamicQuery dynamicQuery) {
		return candidateMaintenancePersistence.countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(DynamicQuery dynamicQuery,
		Projection projection) {
		return candidateMaintenancePersistence.countWithDynamicQuery(dynamicQuery,
			projection);
	}

	@Override
	public CandidateMaintenance fetchCandidateMaintenance(
		long candidateMaintenanceId) {
		return candidateMaintenancePersistence.fetchByPrimaryKey(candidateMaintenanceId);
	}

	/**
	 * Returns the candidate maintenance with the primary key.
	 *
	 * @param candidateMaintenanceId the primary key of the candidate maintenance
	 * @return the candidate maintenance
	 * @throws PortalException if a candidate maintenance with the primary key could not be found
	 */
	@Override
	public CandidateMaintenance getCandidateMaintenance(
		long candidateMaintenanceId) throws PortalException {
		return candidateMaintenancePersistence.findByPrimaryKey(candidateMaintenanceId);
	}

	@Override
	public ActionableDynamicQuery getActionableDynamicQuery() {
		ActionableDynamicQuery actionableDynamicQuery = new DefaultActionableDynamicQuery();

		actionableDynamicQuery.setBaseLocalService(candidateMaintenanceLocalService);
		actionableDynamicQuery.setClassLoader(getClassLoader());
		actionableDynamicQuery.setModelClass(CandidateMaintenance.class);

		actionableDynamicQuery.setPrimaryKeyPropertyName(
			"candidateMaintenanceId");

		return actionableDynamicQuery;
	}

	@Override
	public IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		IndexableActionableDynamicQuery indexableActionableDynamicQuery = new IndexableActionableDynamicQuery();

		indexableActionableDynamicQuery.setBaseLocalService(candidateMaintenanceLocalService);
		indexableActionableDynamicQuery.setClassLoader(getClassLoader());
		indexableActionableDynamicQuery.setModelClass(CandidateMaintenance.class);

		indexableActionableDynamicQuery.setPrimaryKeyPropertyName(
			"candidateMaintenanceId");

		return indexableActionableDynamicQuery;
	}

	protected void initActionableDynamicQuery(
		ActionableDynamicQuery actionableDynamicQuery) {
		actionableDynamicQuery.setBaseLocalService(candidateMaintenanceLocalService);
		actionableDynamicQuery.setClassLoader(getClassLoader());
		actionableDynamicQuery.setModelClass(CandidateMaintenance.class);

		actionableDynamicQuery.setPrimaryKeyPropertyName(
			"candidateMaintenanceId");
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public PersistedModel deletePersistedModel(PersistedModel persistedModel)
		throws PortalException {
		return candidateMaintenanceLocalService.deleteCandidateMaintenance((CandidateMaintenance)persistedModel);
	}

	@Override
	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException {
		return candidateMaintenancePersistence.findByPrimaryKey(primaryKeyObj);
	}

	/**
	 * Returns a range of all the candidate maintenances.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.micro.maintainance.task.model.impl.CandidateMaintenanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of candidate maintenances
	 * @param end the upper bound of the range of candidate maintenances (not inclusive)
	 * @return the range of candidate maintenances
	 */
	@Override
	public List<CandidateMaintenance> getCandidateMaintenances(int start,
		int end) {
		return candidateMaintenancePersistence.findAll(start, end);
	}

	/**
	 * Returns the number of candidate maintenances.
	 *
	 * @return the number of candidate maintenances
	 */
	@Override
	public int getCandidateMaintenancesCount() {
		return candidateMaintenancePersistence.countAll();
	}

	/**
	 * Updates the candidate maintenance in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param candidateMaintenance the candidate maintenance
	 * @return the candidate maintenance that was updated
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CandidateMaintenance updateCandidateMaintenance(
		CandidateMaintenance candidateMaintenance) {
		return candidateMaintenancePersistence.update(candidateMaintenance);
	}

	/**
	 * Returns the candidate maintenance local service.
	 *
	 * @return the candidate maintenance local service
	 */
	public CandidateMaintenanceLocalService getCandidateMaintenanceLocalService() {
		return candidateMaintenanceLocalService;
	}

	/**
	 * Sets the candidate maintenance local service.
	 *
	 * @param candidateMaintenanceLocalService the candidate maintenance local service
	 */
	public void setCandidateMaintenanceLocalService(
		CandidateMaintenanceLocalService candidateMaintenanceLocalService) {
		this.candidateMaintenanceLocalService = candidateMaintenanceLocalService;
	}

	/**
	 * Returns the candidate maintenance persistence.
	 *
	 * @return the candidate maintenance persistence
	 */
	public CandidateMaintenancePersistence getCandidateMaintenancePersistence() {
		return candidateMaintenancePersistence;
	}

	/**
	 * Sets the candidate maintenance persistence.
	 *
	 * @param candidateMaintenancePersistence the candidate maintenance persistence
	 */
	public void setCandidateMaintenancePersistence(
		CandidateMaintenancePersistence candidateMaintenancePersistence) {
		this.candidateMaintenancePersistence = candidateMaintenancePersistence;
	}

	/**
	 * Returns the task entry local service.
	 *
	 * @return the task entry local service
	 */
	public com.liferay.micro.maintainance.task.service.TaskEntryLocalService getTaskEntryLocalService() {
		return taskEntryLocalService;
	}

	/**
	 * Sets the task entry local service.
	 *
	 * @param taskEntryLocalService the task entry local service
	 */
	public void setTaskEntryLocalService(
		com.liferay.micro.maintainance.task.service.TaskEntryLocalService taskEntryLocalService) {
		this.taskEntryLocalService = taskEntryLocalService;
	}

	/**
	 * Returns the task entry persistence.
	 *
	 * @return the task entry persistence
	 */
	public TaskEntryPersistence getTaskEntryPersistence() {
		return taskEntryPersistence;
	}

	/**
	 * Sets the task entry persistence.
	 *
	 * @param taskEntryPersistence the task entry persistence
	 */
	public void setTaskEntryPersistence(
		TaskEntryPersistence taskEntryPersistence) {
		this.taskEntryPersistence = taskEntryPersistence;
	}

	/**
	 * Returns the counter local service.
	 *
	 * @return the counter local service
	 */
	public com.liferay.counter.kernel.service.CounterLocalService getCounterLocalService() {
		return counterLocalService;
	}

	/**
	 * Sets the counter local service.
	 *
	 * @param counterLocalService the counter local service
	 */
	public void setCounterLocalService(
		com.liferay.counter.kernel.service.CounterLocalService counterLocalService) {
		this.counterLocalService = counterLocalService;
	}

	/**
	 * Returns the class name local service.
	 *
	 * @return the class name local service
	 */
	public com.liferay.portal.kernel.service.ClassNameLocalService getClassNameLocalService() {
		return classNameLocalService;
	}

	/**
	 * Sets the class name local service.
	 *
	 * @param classNameLocalService the class name local service
	 */
	public void setClassNameLocalService(
		com.liferay.portal.kernel.service.ClassNameLocalService classNameLocalService) {
		this.classNameLocalService = classNameLocalService;
	}

	/**
	 * Returns the class name persistence.
	 *
	 * @return the class name persistence
	 */
	public ClassNamePersistence getClassNamePersistence() {
		return classNamePersistence;
	}

	/**
	 * Sets the class name persistence.
	 *
	 * @param classNamePersistence the class name persistence
	 */
	public void setClassNamePersistence(
		ClassNamePersistence classNamePersistence) {
		this.classNamePersistence = classNamePersistence;
	}

	/**
	 * Returns the resource local service.
	 *
	 * @return the resource local service
	 */
	public com.liferay.portal.kernel.service.ResourceLocalService getResourceLocalService() {
		return resourceLocalService;
	}

	/**
	 * Sets the resource local service.
	 *
	 * @param resourceLocalService the resource local service
	 */
	public void setResourceLocalService(
		com.liferay.portal.kernel.service.ResourceLocalService resourceLocalService) {
		this.resourceLocalService = resourceLocalService;
	}

	/**
	 * Returns the user local service.
	 *
	 * @return the user local service
	 */
	public com.liferay.portal.kernel.service.UserLocalService getUserLocalService() {
		return userLocalService;
	}

	/**
	 * Sets the user local service.
	 *
	 * @param userLocalService the user local service
	 */
	public void setUserLocalService(
		com.liferay.portal.kernel.service.UserLocalService userLocalService) {
		this.userLocalService = userLocalService;
	}

	/**
	 * Returns the user persistence.
	 *
	 * @return the user persistence
	 */
	public UserPersistence getUserPersistence() {
		return userPersistence;
	}

	/**
	 * Sets the user persistence.
	 *
	 * @param userPersistence the user persistence
	 */
	public void setUserPersistence(UserPersistence userPersistence) {
		this.userPersistence = userPersistence;
	}

	public void afterPropertiesSet() {
		persistedModelLocalServiceRegistry.register("com.liferay.micro.maintainance.task.model.CandidateMaintenance",
			candidateMaintenanceLocalService);
	}

	public void destroy() {
		persistedModelLocalServiceRegistry.unregister(
			"com.liferay.micro.maintainance.task.model.CandidateMaintenance");
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return CandidateMaintenanceLocalService.class.getName();
	}

	protected Class<?> getModelClass() {
		return CandidateMaintenance.class;
	}

	protected String getModelClassName() {
		return CandidateMaintenance.class.getName();
	}

	/**
	 * Performs a SQL query.
	 *
	 * @param sql the sql query
	 */
	protected void runSQL(String sql) {
		try {
			DataSource dataSource = candidateMaintenancePersistence.getDataSource();

			DB db = DBManagerUtil.getDB();

			sql = db.buildSQL(sql);
			sql = PortalUtil.transformSQL(sql);

			SqlUpdate sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(dataSource,
					sql);

			sqlUpdate.update();
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	@BeanReference(type = CandidateMaintenanceLocalService.class)
	protected CandidateMaintenanceLocalService candidateMaintenanceLocalService;
	@BeanReference(type = CandidateMaintenancePersistence.class)
	protected CandidateMaintenancePersistence candidateMaintenancePersistence;
	@BeanReference(type = com.liferay.micro.maintainance.task.service.TaskEntryLocalService.class)
	protected com.liferay.micro.maintainance.task.service.TaskEntryLocalService taskEntryLocalService;
	@BeanReference(type = TaskEntryPersistence.class)
	protected TaskEntryPersistence taskEntryPersistence;
	@ServiceReference(type = com.liferay.counter.kernel.service.CounterLocalService.class)
	protected com.liferay.counter.kernel.service.CounterLocalService counterLocalService;
	@ServiceReference(type = com.liferay.portal.kernel.service.ClassNameLocalService.class)
	protected com.liferay.portal.kernel.service.ClassNameLocalService classNameLocalService;
	@ServiceReference(type = ClassNamePersistence.class)
	protected ClassNamePersistence classNamePersistence;
	@ServiceReference(type = com.liferay.portal.kernel.service.ResourceLocalService.class)
	protected com.liferay.portal.kernel.service.ResourceLocalService resourceLocalService;
	@ServiceReference(type = com.liferay.portal.kernel.service.UserLocalService.class)
	protected com.liferay.portal.kernel.service.UserLocalService userLocalService;
	@ServiceReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	@ServiceReference(type = PersistedModelLocalServiceRegistry.class)
	protected PersistedModelLocalServiceRegistry persistedModelLocalServiceRegistry;
}