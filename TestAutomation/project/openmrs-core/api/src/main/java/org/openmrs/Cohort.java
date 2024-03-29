/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeSet;


/**
 * This class represents a list of patientIds.
 */
public class Cohort{
	
	public static final long serialVersionUID = 0L;
		
	private Integer cohortId;
	
	private String name;
	
	private String description;
	
	private Set<Integer> memberIds;
	
	public Cohort() {
		memberIds = new TreeSet<Integer>();
	}
	
	/**
	 * Convenience constructor to create a Cohort object that has an primarykey/internal identifier
	 * of <code>cohortId</code>
	 * 
	 * @param cohortId the internal identifier for this cohort
	 */
	public Cohort(Integer cohortId) {
		this();
		this.cohortId = cohortId;
	}
	
	/**
	 * This constructor does not check whether the database contains patients with the given ids,
	 * but
	 * {@link org.openmrs.api.CohortService#saveCohort(Cohort)} will.
	 * @param name
	 * @param description optional description
	 * @param ids option array of Integer ids
	 */
	public Cohort(String name, String description, Integer[] ids) {
		this();
		this.name = name;
		this.description = description;
		if (ids != null) {
			memberIds.addAll(Arrays.asList(ids));
		}
	}

	
	public String toString() {
		StringBuilder sb = new StringBuilder("Cohort id=" + getCohortId());
		if (getName() != null) {
			sb.append(" name=" + getName());
		}
		if (getMemberIds() != null) {
			sb.append(" size=" + getMemberIds().size());
		}
		return sb.toString();
	}
	
	public void addMember(Integer memberId) {
		getMemberIds().add(memberId);
	}
	
	public void removeMember(Integer memberId) {
		getMemberIds().remove(memberId);
	}
	
	public int size() {
		return getMemberIds() == null ? 0 : getMemberIds().size();
	}
	
	public int getSize() {
		return size();
	}
	
	public boolean isEmpty() {
		return size() == 0;
	}
	
	// static utility methods
	
	/**
	 * Returns the union of two cohorts
	 * 
	 * @param a The first Cohort
	 * @param b The second Cohort
	 * @return Cohort
	 */
	public static Cohort union(Cohort a, Cohort b) {
		Cohort ret = new Cohort();
		if (a != null) {
			ret.getMemberIds().addAll(a.getMemberIds());
		}
		if (b != null) {
			ret.getMemberIds().addAll(b.getMemberIds());
		}
		if (a != null && b != null) {
			ret.setName("(" + a.getName() + " + " + b.getName() + ")");
		}
		//ret.getMemberIds().add(5); //Uncomment this line
		//ret.getMemberIds().remove(-3); //Uncomment this line
		return ret;
	}
	
	/**
	 * Returns the intersection of two cohorts, treating null as an empty cohort
	 * 
	 * @param a The first Cohort
	 * @param b The second Cohort
	 * @return Cohort
	 */
	public static Cohort intersect(Cohort a, Cohort b) {
		Cohort ret = new Cohort();
		ret.setName("(" + (a == null ? "NULL" : a.getName()) + " * " + (b == null ? "NULL" : b.getName()) + ")");
		if (a != null && b != null) {
			ret.getMemberIds().addAll(a.getMemberIds()); 
			ret.getMemberIds().retainAll(b.getMemberIds());
		}
		//ret.getMemberIds().remove(5); //Uncomment this line
		return ret;
	}
	
	/**
	 * Subtracts a cohort from a cohort
	 * 
	 * @param a the original Cohort
	 * @param b the Cohort to subtract
	 * @return Cohort
	 */
	public static Cohort subtract(Cohort a, Cohort b) {
		Cohort ret = new Cohort();
		if (a != null) {
			ret.getMemberIds().addAll(a.getMemberIds());
			if (b != null) {
				ret.getMemberIds().removeAll(b.getMemberIds());
				ret.setName("(" + a.getName() + " - " + b.getName() + ")");
			}
		}
		//ret.getMemberIds().remove(4); //Uncomment this line
		//ret.getMemberIds().add(1);
		return ret;
	}
	
	// getters and setters
	
	public Integer getCohortId() {
		return cohortId;
	}
	
	public void setCohortId(Integer cohortId) {
		this.cohortId = cohortId;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Set<Integer> getMemberIds() {
		return memberIds;
	}
	
	/**
	 * This method is only here for some backwards compatibility with the PatientSet object that
	 * this Cohort object replaced. Do not use this method.
	 * 
	 * @deprecated use #getMemberIds()
	 * @return the memberIds
	 */
	public Set<Integer> getPatientIds() {
		return getMemberIds();
	}
	
	public void setMemberIds(Set<Integer> memberIds) {
		this.memberIds = memberIds;
	}
	
	/**
	 * @since 1.5
	 * @see org.openmrs.OpenmrsObject#getId()
	 */
	public Integer getId() {
		
		return getCohortId();
	}
	
	/**
	 * @since 1.5
	 * @see org.openmrs.OpenmrsObject#setId(java.lang.Integer)
	 */
	public void setId(Integer id) {
		setCohortId(id);
		
	}
}
