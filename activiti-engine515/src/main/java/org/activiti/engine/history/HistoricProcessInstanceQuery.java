/* Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.activiti.engine.history;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.query.Query;
import org.activiti.engine.runtime.ProcessInstanceQuery;

/**
 * Allows programmatic querying of {@link HistoricProcessInstance}s.
 * 
 * @author Tom Baeyens
 * @author Joram Barrez
 * @author Falko Menge
 */
public interface HistoricProcessInstanceQuery extends Query<HistoricProcessInstanceQuery, HistoricProcessInstance> {

  /** Only select historic process instances with the given process instance.
   * {@link ProcessInstance) ids and {@link HistoricProcessInstance} ids match. */
  HistoricProcessInstanceQuery processInstanceId(String processInstanceId);

  /** Only select historic process instances whose id is in the given set of ids.
   * {@link ProcessInstance) ids and {@link HistoricProcessInstance} ids match. */
  HistoricProcessInstanceQuery processInstanceIds(Set<String> processInstanceIds);

  /** Only select historic process instances for the given process definition */
  HistoricProcessInstanceQuery processDefinitionId(String processDefinitionId);
  
  /**Only select historic process instances for the given process definition set(zhanghuafeng added)*/
  HistoricProcessInstanceQuery processDefinitionIds(Set<String> processDefinitionIds);

  /**Only select historic process instances for the given process assignee with(zhanghuafeng added)*/
  HistoricProcessInstanceQuery assigneeWith(String assigneeWith);
  
  /**Only select historic process instances for the given process assigned by  add by libing*/
  HistoricProcessInstanceQuery AssignedBy(Set<String> paramSet);
  
  /**Only select historic process instances for the given process task finished with task definition key with(zhanghuafeng added)*/
  HistoricProcessInstanceQuery taskFinishedWithDefinitionKey(String taskFinishedWithDefinitionKey);
  
  /**Only select historic process instances for the given process task finished without task definition key with(zhanghuafeng added)*/
  HistoricProcessInstanceQuery taskFinishedWithoutDefinitionKey(String taskFinishedWithoutDefinitionKey);  

  /**Only select historic process instances for the given process task finished without task definition keys with(zhanghuafeng added)*/
  HistoricProcessInstanceQuery taskFinishedWithoutDefinitionKeys(Set<String> taskFinishedWithoutDefinitionKeys);  

  /** Only select historic process instances that are defined by a process
   * definition with the given key.  */
  HistoricProcessInstanceQuery processDefinitionKey(String processDefinitionKey);

  /** Only select historic process instances that don't have a process-definition of which the key is present in the given list */
  HistoricProcessInstanceQuery processDefinitionKeyNotIn(List<String> processDefinitionKeys);

  /** Only select historic process instances with the given business key */
  HistoricProcessInstanceQuery processInstanceBusinessKey(String processInstanceBusinessKey);

  /** Only select historic process instances that are completely finished. */
  HistoricProcessInstanceQuery finished();

  /** Only select historic process instance that are not yet finished. */
  HistoricProcessInstanceQuery unfinished();

  /** Only select the historic process instances with which the user with the given id is involved. */
  HistoricProcessInstanceQuery involvedUser(String userId);
  
  /** Only select process instances which had a global variable with the given value
   * when they ended. The type only applies to already ended
   * process instances, otherwise use a {@link ProcessInstanceQuery} instead! of
   * variable is determined based on the value, using types configured in
   * {@link ProcessEngineConfiguration#getVariableTypes()}. Byte-arrays and
   * {@link Serializable} objects (which are not primitive type wrappers) are
   * not supported. 
   * @param name of the variable, cannot be null. */
  HistoricProcessInstanceQuery variableValueEquals(String name, Object value);
  
  /** Only select process instances which had at least one global variable with the given value
   * when they ended. The type only applies to already ended
   * process instances, otherwise use a {@link ProcessInstanceQuery} instead! of
   * variable is determined based on the value, using types configured in
   * {@link ProcessEngineConfiguration#getVariableTypes()}. Byte-arrays and
   * {@link Serializable} objects (which are not primitive type wrappers) are
   * not supported. 
   */
  HistoricProcessInstanceQuery variableValueEquals(Object value);
  
  /** 
   * Only select historic process instances which have a local string variable with the 
   * given value, case insensitive.
   * @param name name of the variable, cannot be null.
   * @param value value of the variable, cannot be null.
   */
  HistoricProcessInstanceQuery variableValueEqualsIgnoreCase(String name, String value);

  /** Only select process instances which had a global variable with the given name, but
   * with a different value than the passed value when they ended. Only select
   * process instances which have a variable value greater than the passed
   * value. Byte-arrays and {@link Serializable} objects (which are not
   * primitive type wrappers) are not supported.
   * @param name of the variable, cannot be null. */
  HistoricProcessInstanceQuery variableValueNotEquals(String name, Object value);

  /** Only select process instances which had a global variable value greater than the
   * passed value when they ended. Booleans, Byte-arrays and
   * {@link Serializable} objects (which are not primitive type wrappers) are
   * not supported. Only select process instances which have a variable value
   * greater than the passed value.
   * @param name cannot be null.
   * @param value cannot be null. */
  HistoricProcessInstanceQuery variableValueGreaterThan(String name, Object value);

  /** Only select process instances which had a global variable value greater than or
   * equal to the passed value when they ended. Booleans, Byte-arrays and
   * {@link Serializable} objects (which are not primitive type wrappers) are
   * not supported. Only applies to already ended process instances, otherwise
   * use a {@link ProcessInstanceQuery} instead!
   * @param name cannot be null.
   * @param value cannot be null. */
  HistoricProcessInstanceQuery variableValueGreaterThanOrEqual(String name, Object value);

  /** Only select process instances which had a global variable value less than the
   * passed value when the ended. Only applies to already ended process
   * instances, otherwise use a {@link ProcessInstanceQuery} instead! Booleans,
   * Byte-arrays and {@link Serializable} objects (which are not primitive type
   * wrappers) are not supported.
   * @param name cannot be null.
   * @param value cannot be null. */
  HistoricProcessInstanceQuery variableValueLessThan(String name, Object value);

  /** Only select process instances which has a global variable value less than or equal
   * to the passed value when they ended. Only applies to already ended process
   * instances, otherwise use a {@link ProcessInstanceQuery} instead! Booleans,
   * Byte-arrays and {@link Serializable} objects (which are not primitive type
   * wrappers) are not supported.
   * @param name cannot be null.
   * @param value cannot be null. */
  HistoricProcessInstanceQuery variableValueLessThanOrEqual(String name, Object value);

  /** Only select process instances which had global variable value like the given value
   * when they ended. Only applies to already ended process instances, otherwise
   * use a {@link ProcessInstanceQuery} instead! This can be used on string
   * variables only.
   * @param name cannot be null.
   * @param value cannot be null. The string can include the
   *          wildcard character '%' to express like-strategy: starts with
   *          (string%), ends with (%string) or contains (%string%). */
  HistoricProcessInstanceQuery variableValueLike(String name, String value);

  /** Only select historic process instances that were started before the given date. */
  HistoricProcessInstanceQuery startedBefore(Date date);
  
  /** Only select historic process instances that were started after the given date. */
  HistoricProcessInstanceQuery startedAfter(Date date);
  
  /** Only select historic process instances that were started before the given date. */
  HistoricProcessInstanceQuery finishedBefore(Date date);
  
  /** Only select historic process instances that were started after the given date. */
  HistoricProcessInstanceQuery finishedAfter(Date date);
  
  /** Only select historic process instance that are started by the given user. */
  HistoricProcessInstanceQuery startedBy(String userId);
  
  /** Only select historic process instance that are started by the given user. */
  HistoricProcessInstanceQuery startedByUserIds(Set<String> userIds);
  

  /** Only select historic process instance that are end before the given date. add by libing */ 
  HistoricProcessInstanceQuery validByTaskEndDate(Date date);
  
	/** Only select process instances that have the given tenant id. */
  HistoricProcessInstanceQuery processInstanceTenantId(String tenantId);

	/** Only select process instances with a tenant id like the given one. */
  HistoricProcessInstanceQuery processInstanceTenantIdLike(String tenantIdLike);
	
	/** Only select process instances that do not have a tenant id. */
  HistoricProcessInstanceQuery processInstanceWithoutTenantId();

  /** Order by the process instance id (needs to be followed by {@link #asc()} or {@link #desc()}). */
  HistoricProcessInstanceQuery orderByProcessInstanceId();
  
  /** Order by the process definition id (needs to be followed by {@link #asc()} or {@link #desc()}). */
  HistoricProcessInstanceQuery orderByProcessDefinitionId();
  
  /** Order by the business key (needs to be followed by {@link #asc()} or {@link #desc()}). */
  HistoricProcessInstanceQuery orderByProcessInstanceBusinessKey();

  /** Order by the start time (needs to be followed by {@link #asc()} or {@link #desc()}). */
  HistoricProcessInstanceQuery orderByProcessInstanceStartTime();
  
  /** Order by the end time (needs to be followed by {@link #asc()} or {@link #desc()}). */
  HistoricProcessInstanceQuery orderByProcessInstanceEndTime();
  
  /** Order by the duration of the process instance (needs to be followed by {@link #asc()} or {@link #desc()}). */
  HistoricProcessInstanceQuery orderByProcessInstanceDuration();
  
	/** Order by tenant id (needs to be followed by {@link #asc()} or {@link #desc()}). */
  HistoricProcessInstanceQuery orderByTenantId();
  
  /** Only select historic process instances started by the given process
   * instance. {@link ProcessInstance) ids and {@link HistoricProcessInstance}
   * ids match. */
  HistoricProcessInstanceQuery superProcessInstanceId(String superProcessInstanceId);
  
  /**
   * Exclude sub processes from the query result;
   */
  HistoricProcessInstanceQuery excludeSubprocesses(boolean excludeSubprocesses);
  
  /**
   * Include process variables in the process query result
   */
  HistoricProcessInstanceQuery includeProcessVariables();
}
