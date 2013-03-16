/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package org.openmrs.module.reporting.report.service.db;

import java.util.Date;
import java.util.List;

import org.openmrs.api.db.DAOException;
import org.openmrs.module.reporting.report.ReportDesign;
import org.openmrs.module.reporting.report.ReportProcessorConfiguration;
import org.openmrs.module.reporting.report.ReportRequest;
import org.openmrs.module.reporting.report.ReportRequest.Status;
import org.openmrs.module.reporting.report.definition.ReportDefinition;
import org.openmrs.module.reporting.report.renderer.ReportRenderer;

/**
 * ReportService Database Access Interface
 */
public interface ReportDAO {
	
	//****** REPORT DESIGNS *****
	
	/**
	 * @param uuid
	 * @return the ReportDesign with the given uuid
	 */
	public ReportDesign getReportDesignByUuid(String uuid) throws DAOException;
	
	/**
	 * Get the {@link ReportDesign} with the given id
	 * @param id The Integer ReportDesign id
	 * @return the matching {@link ReportDesign} object
	 * @throws DAOException
	 */
	public ReportDesign getReportDesign(Integer id) throws DAOException;
		
	/**
	 * Return a list of {@link ReportDesign}s for {@link ReportDefinition} with the passed id,
	 * optionally including those that are retired
	 * @param includeRetired if true, indicates that retired {@link ReportDesign}s should also be included
	 * @return a List<ReportDesign> object containing all of the {@link ReportDesign}s
	 * @throws DAOException
	 */
	public List<ReportDesign> getReportDesigns(ReportDefinition reportDefinition, Class<? extends ReportRenderer> rendererType, 
											   boolean includeRetired) throws DAOException;
	
	/**
	 * Save or update the given <code>ReportDesign</code> in the database. If this is a new
	 * ReportDesign, the returned ReportDesign will have a new
	 * {@link ReportDesign#getId()} inserted into it that was generated by the database
	 * 
	 * @param reportDesign The <code>ReportDesign</code> to save or update
	 * @throws DAOException
	 */
	public ReportDesign saveReportDesign(ReportDesign reportDesign) throws DAOException;
	
	/**
	 * Purges a <code>ReportDesign</code> from the database.
	 * @param reportDesign The <code>ReportDesign</code> to remove from the system
	 * @throws DAOException
	 */
	public void purgeReportDesign(ReportDesign reportDesign);
	
	//****** REPORT PROCESSOR CONFIGURATIONS *****
	
	/**
	 * Saves a {@link ReportProcessorConfiguration} to the database and returns it
	 */
	public ReportProcessorConfiguration saveReportProcessorConfiguration(ReportProcessorConfiguration processorConfiguration);

	/**
	 * @return the {@link ReportProcessorConfiguration} with the passed id
	 */
	public ReportProcessorConfiguration getReportProcessorConfiguration(Integer id);

	/**
	 * @return the {@link ReportProcessorConfiguration} with the passed uuid
	 */
	public ReportProcessorConfiguration getReportProcessorConfigurationByUuid(String uuid);
	
	/**
	 * @return all the {@link ReportProcessorConfiguration}s
	 */
	public List<ReportProcessorConfiguration> getAllReportProcessorConfigurations(boolean includeRetired);
	
	
	/**
	 * 
	 * @return all the {@link ReportProcessorConfiguration}s that have no reportDesign associated
	 */
	public List<ReportProcessorConfiguration> getGlobalReportProcessorConfigurations();
	/**
	 * Deletes the passed {@link ReportProcessorConfiguration}
	 */
	public void purgeReportProcessorConfiguration(ReportProcessorConfiguration processorConfiguration);
	
	//****** REPORT REQUESTS *****
	
	/**
	 * Saves a {@link ReportRequest} to the database and returns it
	 */
	public ReportRequest saveReportRequest(ReportRequest request);

	/**
	 * @return the {@link ReportRequest} with the passed id
	 */
	public ReportRequest getReportRequest(Integer id);

	/**
	 * @return the {@link ReportRequest} with the passed uuid
	 */
	public ReportRequest getReportRequestByUuid(String uuid);
	
	/**
	 * @return all {@link ReportRequest} in the system that match the passed parameters
	 */
	public List<ReportRequest> getReportRequests(ReportDefinition reportDefinition, Date requestOnOrAfter, Date requestOnOrBefore, Integer mostRecentNum, Status...statuses);

	/**
	 * Deletes the passed {@link ReportRequest}
	 */
	public void purgeReportRequest(ReportRequest request);
}
