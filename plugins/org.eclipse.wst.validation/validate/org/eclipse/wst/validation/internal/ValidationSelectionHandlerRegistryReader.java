/*
 * Created on Nov 23, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package org.eclipse.wst.validation.internal;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.jem.util.RegistryReader;
import org.eclipse.wst.validation.internal.plugin.ValidationPlugin;

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
public class ValidationSelectionHandlerRegistryReader extends RegistryReader {

	public static final String VALIDATION_SELECTION_HANDLER = "validationSelectionHandler"; //$NON-NLS-1$
	static final String ATT_ID = "id"; //$NON-NLS-1$ 
	static final String ATT_HANDLER_CLASS = "handlerClass"; //$NON-NLS-1$
	static final String ATT_SELECTION_TYPE = "selectionType"; //$NON-NLS-1$
	private static ValidationSelectionHandlerRegistryReader INSTANCE = null;
	private List validationSelectionHandlers = null;
	
	/**
	 * Default constructor
	 */
	public ValidationSelectionHandlerRegistryReader() {
		super(ValidationPlugin.PLUGIN_ID, VALIDATION_SELECTION_HANDLER);
	}
	
	public static ValidationSelectionHandlerRegistryReader getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new ValidationSelectionHandlerRegistryReader();
			INSTANCE.readRegistry();
		}
		return INSTANCE;
	}

	

	/* (non-Javadoc)
	 * @see org.eclipse.jem.util.RegistryReader#readElement(org.eclipse.core.runtime.IConfigurationElement)
	 */
	public boolean readElement(IConfigurationElement element) {
		if (!element.getName().equals(VALIDATION_SELECTION_HANDLER))
			return false;
		
//		String handlerClass = element.getAttribute(ATT_HANDLER_CLASS);
		String selectionType = element.getAttribute(ATT_SELECTION_TYPE); 
		
		IValidationSelectionHandler handler = null;
		try {
			handler = (IValidationSelectionHandler) element.createExecutableExtension(ATT_HANDLER_CLASS);
			handler.setValidationTypeString(selectionType);
			getValidationSelectionHandlers().add(handler);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
	   return false;
	}
	
	private List getValidationSelectionHandlers() {
		if (validationSelectionHandlers == null)
			validationSelectionHandlers = new ArrayList();
		return validationSelectionHandlers;
	}
	
	public Object getExtendedType(Object selection) {
		Object result = null;
		for (int i=0; i<getValidationSelectionHandlers().size(); i++ ) {
			IValidationSelectionHandler handler = (IValidationSelectionHandler) getValidationSelectionHandlers().get(i);
			result = handler.getBaseValidationType(selection);
			if (result != null)
				break;
		}
		return result;
	}
}