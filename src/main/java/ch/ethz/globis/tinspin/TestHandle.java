/*
 * Copyright 2011-2016 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 */
package ch.ethz.globis.tinspin;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Random;

import ch.ethz.globis.tinspin.data.AbstractTest;

public interface TestHandle {

	String name();

	String getTestClassName();

	boolean isRangeData();
	
	@SuppressWarnings("unchecked")
	default AbstractTest createInstance(Random R, TestStats S) {
		try {
			String className = S.TEST.getTestClassName();
			Class<AbstractTest> cls = (Class<AbstractTest>) Class.forName(className);
			Constructor<AbstractTest> ctr = cls.getConstructor(Random.class, TestStats.class);
			return (AbstractTest) ctr.newInstance(R, S);
		} catch (ClassNotFoundException 
				| NoSuchMethodException 
				| SecurityException 
				| InstantiationException 
				| IllegalAccessException 
				| IllegalArgumentException 
				| InvocationTargetException e) {
			throw new RuntimeException(e);
		}
	}

}
