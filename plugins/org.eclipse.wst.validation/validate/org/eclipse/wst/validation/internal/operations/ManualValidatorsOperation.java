/*******************************************************************************
 * Copyright (c) 2001, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.wst.validation.internal.operations;


import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.wst.validation.internal.RegistryConstants;


/**
 * Implemented Validators must not be called directly by anyone other than instances of
 * ValidationOperation, because some initialization of the validator, and handling of error
 * conditions, is done in the operation. The initialization is separated because some of the
 * information needed to initialize the validator (i.e., the project) isn't known until runtime.
 * 
 * Instances of this operation run every enabled validator (both full and incremental) on the
 * project.
 * 
 * This operation is not intended to be subclassed outside of the validation framework.
 */
public class ManualValidatorsOperation extends ValidatorSubsetOperation {


	/**
	 * Run all enabled validators on the project.
	 * 
	 * IProject must exist and be open.
	 * 
	 * If async is true, the validation will run all thread-safe validators in the background
	 * validation thread, and all other validators in the main thread. If async is false, all
	 * validators will run in in the main thread.
	 */
	public ManualValidatorsOperation(IProject project, boolean async) {
		this(project, RegistryConstants.ATT_RULE_GROUP_DEFAULT, DEFAULT_FORCE, async);
	}
	
	/**
	 * Run all enabled validators on the project.
	 * 
	 * IProject must exist and be open.
	 * 
	 * If async is true, the validation will run all thread-safe validators in the background
	 * validation thread, and all other validators in the main thread. If async is false, all
	 * validators will run in in the main thread.
	 */
	public ManualValidatorsOperation(IProject project, IWorkbenchContext aWorkbenchContext, boolean async) {
		this(project, aWorkbenchContext, RegistryConstants.ATT_RULE_GROUP_DEFAULT, DEFAULT_FORCE, async);
	}
	
	/**
	 * Run all enabled validators on the project with the identified ruleGroup.
	 * 
	 * IProject must exist and be open.
	 * 
	 * If async is true, the validation will run all thread-safe validators in the background
	 * validation thread, and all other validators in the main thread. If async is false, all
	 * validators will run in in the main thread.
	 */
	public ManualValidatorsOperation(IProject project, IWorkbenchContext aWorkbenchContext, int ruleGroup, boolean force, boolean async) {
		this(project, aWorkbenchContext, ValidatorManager.getManager().getManualEnabledValidators(project), ruleGroup, force, async);
	}
	

	/**
	 * Run all enabled validators on the project with the identified ruleGroup.
	 * 
	 * IProject must exist and be open.
	 * 
	 * If async is true, the validation will run all thread-safe validators in the background
	 * validation thread, and all other validators in the main thread. If async is false, all
	 * validators will run in in the main thread.
	 */
	public ManualValidatorsOperation(IProject project, int ruleGroup, boolean force, boolean async) {
		this(project, ValidatorManager.getManager().getManualEnabledValidators(project), ruleGroup, force, async);
	}

	/**
	 * Run the identified validators on the project.
	 * 
	 * IProject must exist and be open.
	 * 
	 * If async is true, the validation will run all thread-safe validators in the background
	 * validation thread, and all other validators in the main thread. If async is false, all
	 * validators will run in in the main thread.
	 */
	protected ManualValidatorsOperation(IProject project, Set enabledValidators, boolean async) {
		this(project, enabledValidators, RegistryConstants.ATT_RULE_GROUP_DEFAULT, DEFAULT_FORCE, async); 
		// true = force validation to run whether or not auto-validate is on
	}
	
	/**
	 * Run the identified validators on the project.
	 * 
	 * IProject must exist and be open.
	 * 
	 * If async is true, the validation will run all thread-safe validators in the background
	 * validation thread, and all other validators in the main thread. If async is false, all
	 * validators will run in in the main thread.
	 */
	protected ManualValidatorsOperation(IProject project,IWorkbenchContext aWorkbenchContext, Set enabledValidators, boolean async) {
		this(project,aWorkbenchContext, enabledValidators, RegistryConstants.ATT_RULE_GROUP_DEFAULT, DEFAULT_FORCE, async); 
		// true = force validation to run whether or not auto-validate is on
	}

	/**
	 * Run the identified validators on the project with the ruleGroup.
	 * 
	 * IProject must exist and be open.
	 * 
	 * If async is true, the validation will run all thread-safe validators in the background
	 * validation thread, and all other validators in the main thread. If async is false, all
	 * validators will run in in the main thread.
	 */
	protected ManualValidatorsOperation(IProject project, Set manualEnabledValidators, int ruleGroup, boolean force, boolean async) {
		super(project, force, ruleGroup, async);
		setEnabledValidators(manualEnabledValidators);
	}
	
	/**
	 * Run the identified validators on the project with the ruleGroup.
	 * 
	 * IProject must exist and be open.
	 * 
	 * If async is true, the validation will run all thread-safe validators in the background
	 * validation thread, and all other validators in the main thread. If async is false, all
	 * validators will run in in the main thread.
	 */
	protected ManualValidatorsOperation(IProject project, IWorkbenchContext aWorkbenchContext, Set enabledValidators, int ruleGroup, boolean force, boolean async) {
		super(project,aWorkbenchContext,force, ruleGroup, async);
		setEnabledValidators(enabledValidators);
	}
}