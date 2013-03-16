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
package org.openmrs.module.reporting.report.service;

import java.io.File;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.openmrs.api.OpenmrsService;
import org.openmrs.module.reporting.ReportingConstants;
import org.openmrs.module.reporting.evaluation.EvaluationException;
import org.openmrs.module.reporting.report.Report;
import org.openmrs.module.reporting.report.ReportData;
import org.openmrs.module.reporting.report.ReportDesign;
import org.openmrs.module.reporting.report.ReportProcessorConfiguration;
import org.openmrs.module.reporting.report.ReportRequest;
import org.openmrs.module.reporting.report.ReportRequest.Status;
import org.openmrs.module.reporting.report.definition.ReportDefinition;
import org.openmrs.module.reporting.report.processor.ReportProcessor;
import org.openmrs.module.reporting.report.renderer.RenderingMode;
import org.openmrs.module.reporting.report.renderer.ReportRenderer;
import org.springframework.transaction.annotation.Transactional;

/**
 * ReportService API
 */
public interface ReportService extends OpenmrsService {
	
	//****** REPORT RENDERERS AND DESIGNS *****
		
	/**
	 * @return the ReportDesign with the given uuid
	 */
	@Transactional(readOnly = true)
	public ReportDesign getReportDesignByUuid(String uuid);
	
	/**
	 * @return the {@link ReportDesign} with the given id
	 */
	@Transactional(readOnly = true)
	public ReportDesign getReportDesign(Integer id);
	
	/**
	 * @return return a list of {@link ReportDesign}, optionally including those that are retired
	 */
	@Transactional(readOnly = true)
	public List<ReportDesign> getAllReportDesigns(boolean includeRetired);
	
	/**
	 * Return a list of {@link ReportDesign}s for {@link ReportDefinition} that match the passed parameters
	 * Each input parameter can be null, restricting the returned results only if it is not null.  This allows you
	 * to retrieve all ReportDesigns by ReportDefinition, by RendererType, by retired status, or a combination of these
	 * criteria.
	 * @param reportDefinitionId if not null, only {@link ReportDesign}s for this {@link ReportDefinition} will be returned
	 * @param rendererType if not null, only {@link ReportDesign}s for this {@link ReportRenderer} type will be returned
	 * @param includeRetired if true, indicates that retired {@link ReportDesign}s should also be included
	 * @return a List<ReportDesign> object containing all of the {@link ReportDesign}s
	 */
	@Transactional(readOnly = true)
	public List<ReportDesign> getReportDesigns(ReportDefinition reportDefinition, Class<? extends ReportRenderer> rendererType, boolean includeRetired);
	
	/**
	 * Save or update the given <code>ReportDesign</code> in the database. If this is a new
	 * ReportDesign, the returned ReportDesign will have a new
	 * {@link ReportDesign#getId()} inserted into it that was generated by the database
	 * @param reportDesign The <code>ReportDesign</code> to save or update
	 */
	@Transactional
	public ReportDesign saveReportDesign(ReportDesign reportDesign);
	
	/**
	 * Purges a <code>ReportDesign</code> from the database.
	 * @param reportDesign The <code>ReportDesign</code> to remove from the system
	 */
	@Transactional
	public void purgeReportDesign(ReportDesign reportDesign);
	
	/**
	 * @return a Collection<ReportRenderer> of all registered ReportRenderers
	 */
	@Transactional(readOnly = true)
	public Collection<ReportRenderer> getReportRenderers();
	
	/**
	 * @return the preferred ReportRenderer for the given class name
	 */
	@Transactional(readOnly = true)
	public ReportRenderer getReportRenderer(String className);
	
	/**
	 * @return	the preferred ReportRenderer for the given object type
	 */
	@Transactional(readOnly = true)
	public ReportRenderer getPreferredReportRenderer(Class<Object> objectType);
	
	/**
	 * @return a List of {@link RenderingMode}s that the passed {@link ReportDefinition} supports, in their preferred order
	 */
	@Transactional(readOnly = true)
	public List<RenderingMode> getRenderingModes(ReportDefinition schema);
	
	//****** REPORT REQUESTS *****
	
	/**
	 * Saves a {@link ReportRequest} to the database and returns it
	 */
	@Transactional
	public ReportRequest saveReportRequest(ReportRequest request);

	/**
	 * @return the {@link ReportRequest} with the passed id
	 */
	@Transactional(readOnly = true)
	public ReportRequest getReportRequest(Integer id);

	/**
	 * @return the {@link ReportRequest} with the passed uuid
	 */
	@Transactional(readOnly = true)
	public ReportRequest getReportRequestByUuid(String uuid);
	
	/**
	 * @return all {@link ReportRequest} in the system that match the passed parameters
	 * @should retrieve report requests by definition
	 */
	@Transactional(readOnly = true)
	public List<ReportRequest> getReportRequests(ReportDefinition reportDefinition, Date requestOnOrAfter, Date requestOnOrBefore, Status...statuses);

	/**
	 * @return all {@link ReportRequest} in the system that match the passed parameters
	 * @should retrieve report requests by definition
	 */
	@Transactional(readOnly = true)
	public List<ReportRequest> getReportRequests(ReportDefinition reportDefinition, Date requestOnOrAfter, Date requestOnOrBefore, Integer mostRecentNum, Status...statuses);

	/**
	 * Deletes the passed {@link ReportRequest}
	 */
	@Transactional
	public void purgeReportRequest(ReportRequest request);
	
	//****** REPORT PROCESSOR CONFIGURATIONS *****
	
	/**
	 * Saves a {@link ReportProcessorConfiguration} to the database and returns it
	 * @should save a report processor configuration
	 */
	@Transactional
	public ReportProcessorConfiguration saveReportProcessorConfiguration(ReportProcessorConfiguration processorConfiguration);

	/**
	 * @return the {@link ReportProcessorConfiguration} with the passed id
	 * @should retrieve a saved report processor configuration by id
	 */
	@Transactional(readOnly = true)
	public ReportProcessorConfiguration getReportProcessorConfiguration(Integer id);

	/**
	 * @return the {@link ReportProcessorConfiguration} with the passed uuid
	 * @should retrieve a saved report processor configuration by uuid
	 */
	@Transactional(readOnly = true)
	public ReportProcessorConfiguration getReportProcessorConfigurationByUuid(String uuid);
	
	/**
	 * @return all the {@link ReportProcessorConfiguration}s
	 * @should retrieve all saved report processor configurations including retired if specified
	 */
	@Transactional(readOnly = true)
	public List<ReportProcessorConfiguration> getAllReportProcessorConfigurations(boolean includeRetired);
	
	/**
	 * @return all the {@link ReportProcessorConfiguration}s that aren't associated with a specific ReportDesign
	 * @should retrieve all saved report processor configurations with reportDesign = null, and retired = false;
	 */
	@Transactional(readOnly = true)
	public List<ReportProcessorConfiguration> getGlobalReportProcessorConfigurations();
	
	/**
	 * @return all {@link ReportProcessorConfiguration} in the system that match the passed parameters
	 * @should retrieve all non-retired report processor configurations that are assignable to the passed type
	 */
	@Transactional(readOnly = true)
	public List<ReportProcessorConfiguration> getReportProcessorConfigurations(Class<? extends ReportProcessor> processorType);

	/**
	 * Deletes the passed {@link ReportProcessorConfiguration}
	 * @should delete a saved report processor configuration
	 */
	@Transactional
	public void purgeReportProcessorConfiguration(ReportProcessorConfiguration processorConfiguration);
	
	//***** REPORTS *****
	
	/**
	 * @return the File that may contain the serialized {@link ReportData} for a given {@link ReportRequest}
	 */
	@Transactional(readOnly = true)
	public File getReportDataFile(ReportRequest request);
	
	/**
	 * @return the File that may contain any errors when evaluating a given {@link ReportRequest}
	 */
	@Transactional(readOnly = true)
	public File getReportErrorFile(ReportRequest request);
	
	/**
	 * @return the File that may contain the rendered output from the evaluation of a {@link ReportRequest}
	 */
	@Transactional(readOnly = true)
	public File getReportOutputFile(ReportRequest request);
	
	/**
	 * @return the File that may contain any log messages when evaluating a given {@link ReportRequest}
	 */
	@Transactional(readOnly = true)
	public File getReportLogFile(ReportRequest request);
	
	/**
	 * <pre>
	 * Runs a report synchronously, blocking until the report is ready. This method populates the uuid
	 * field on the ReportRequest that is passed in, and adds the Request to the history.
	 * 
	 * If request specifies a WebRenderer, then the ReportDefinition will be evaluated, and the Report
	 * returned will contain the raw ReportData output, but no rendering will happen.
	 * 
	 * If request specifies a non-WebRenderer, the ReportDefinition will be evaluated <i>and</i> the
	 * data will be rendered, and the Report returned will include raw ReportData and a File.
	 * 
	 * Implementations of this service may choose to run the report directly, or to queue it,
	 * but if they queue it they should do so with HIGHEST priority.
	 * </pre>
	 * 
	 * @param request
	 * @return the result of running the report.
	 * @throws EvaluationException if the report could not be evaluated
	 * 
	 * @should set uuid on the request
	 * @should render the report if a plain renderer is specified
	 * @should not render the report if a web renderer is specified
	 * @should execute any configured report processors
	 */
	public Report runReport(ReportRequest request);

	/**
	 * Adds a {@link ReportRequest} to the queue to be run asynchronously
	 */
	public ReportRequest queueReport(ReportRequest request);
	
	/**
	 * Returns the number in the queue for this report request, or null if processing has already started
	 */
	public Integer getPositionInQueue(ReportRequest request);
	
	/**
	 * Immediately try to process the next reports scheduled for processing off of the queue
	 */
	public void processNextQueuedReports();
	
	/**
	 * Saves a Report, including the underlying report data, optionally providing a description
	 */
	@Transactional
	public Report saveReport(Report report, String description);
	
	/**
	 * Loads the ReportData previously generated Report for the given ReportRequest, first checking the cache
	 */
	@Transactional(readOnly = true)
	public ReportData loadReportData(ReportRequest request);
	
	/**
	 * Loads the Rendered Output for a previously generated Report for the given ReportRequest, first checking the cache
	 */
	@Transactional(readOnly = true)
	public byte[] loadRenderedOutput(ReportRequest request);
	
	/**
	 * Loads the Error message for a previously generated Report for the given ReportRequest, first checking the cache
	 */
	@Transactional(readOnly = true)
	public String loadReportError(ReportRequest request);
	
	/**
	 * Loads the Log messages for a previously generated Report for the given ReportRequest, first checking the cache
	 */
	@Transactional(readOnly = true)
	public List<String> loadReportLog(ReportRequest request);
	
	/**
	 * @return the persisted Report for the given ReportRequest
	 */
	@Transactional(readOnly = true)
	public Report loadReport(ReportRequest request);
	
	/**
	 * @return any Reports that are currently cached
	 */
	@Transactional(readOnly = true)
	public Map<String, Report> getCachedReports();

	/**
	 * Deletes report requests that are not saved, and are older than the value specified by
	 * {@link ReportingConstants#GLOBAL_PROPERTY_DELETE_REPORTS_AGE_IN_HOURS}
	 */
	@Transactional
	public void deleteOldReportRequests();
	
	/**
	 * Persists reports that are cached but not on disk
	 * Removes from Cache the oldest reports if the max cache size is exceeded
	 * {@link ReportingConstants#GLOBAL_PROPERTY_MAX_CACHED_REPORTS()}
	 */
	@Transactional(readOnly = true)
	public void persistCachedReports();
	
	/**
	 * Saves the passed message to disk for the given report, in order to have a record of the report generation
	 * @param report
	 * @param message
	 */
	@Transactional(readOnly = true)
	public void logReportMessage(ReportRequest request, String message);
}
