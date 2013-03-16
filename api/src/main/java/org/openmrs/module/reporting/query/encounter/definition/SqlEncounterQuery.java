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
package org.openmrs.module.reporting.query.encounter.definition;

import org.openmrs.Encounter;
import org.openmrs.module.reporting.query.BaseSqlQuery;

/**
 * SQL-based Encounter Query
 */
public class SqlEncounterQuery extends BaseSqlQuery<Encounter> implements EncounterQuery {

    public static final long serialVersionUID = 1L;
	
	//***** CONSTRUCTORS *****

	/**
	 * Default Constructor
	 */
	public SqlEncounterQuery() {
		super();
	}
	
	/**
	 * 
	 * @param query the query to evaluate
	 */
	public SqlEncounterQuery(String query) { 
		super(query);
	}
}
