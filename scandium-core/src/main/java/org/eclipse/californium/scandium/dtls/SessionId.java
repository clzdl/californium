/*******************************************************************************
 * Copyright (c) 2015 Institute for Pervasive Computing, ETH Zurich and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Eclipse Distribution License v1.0 which accompany this distribution.
 * 
 * The Eclipse Public License is available at
 *    http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at
 *    http://www.eclipse.org/org/documents/edl-v10.html.
 * 
 * Contributors:
 *    Matthias Kovatsch - creator and main architect
 *    Stefan Jucker - DTLS implementation
 *    Kai Hudalla (Bosch Software Innovations GmbH) - add equals() & hashCode()
 *    Kai Hudalla (Bosch Software Innovations GmbH) - add toString()
 ******************************************************************************/
package org.eclipse.californium.scandium.dtls;

import java.security.SecureRandom;
import java.util.Arrays;

import org.eclipse.californium.scandium.util.ByteArrayUtils;

/**
 * A session identifier is a value generated by a server that identifies a
 * particular session.
 */
public final class SessionId {

	// Members ////////////////////////////////////////////////////////
	private static final SessionId EMPTY_SESSION_ID = new SessionId(new byte[0]);
	private final byte[] id; // opaque SessionID<0..32>

	// Constructors ///////////////////////////////////////////////////

	public SessionId() {
		id = new Random(new SecureRandom()).getRandomBytes();
	}

	/**
	 * Creates a session identifier based on given bytes.
	 * 
	 * @param sessionId the bytes constituting the identifier
	 * @throws NullPointerException if the byte array is <code>null</code>
	 */
	public SessionId(byte[] sessionId) {
		if (sessionId == null) {
			throw new NullPointerException("Session ID must not be null");
		}
		this.id = Arrays.copyOf(sessionId, sessionId.length);
	}

	// Methods ////////////////////////////////////////////////////////

	public int length() {
		return id.length;
	}

	public byte[] getId() {
		return id;
	}

	/**
	 * Creates a new instance with an empty byte array as the ID.
	 * 
	 * @return a new (empty) session ID object
	 */
	public static SessionId emptySessionId() {
		return EMPTY_SESSION_ID;
	}

	/**
	 * Computes a hash code based on the <em>sessionId</em> field.
	 * 
	 * @return the hash code
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(id);
		return result;
	}

	/**
	 * Checks if this session identifier is equal to another object.
	 * 
	 * Session identifiers are considered equal if all bytes of the
	 * <em>sessionId</em> are equal.
	 * 
	 * @param obj the object to compare to
	 * @return <code>true</code> if the other object is the same as
	 * this one
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SessionId other = (SessionId) obj;
		if (!Arrays.equals(id, other.id))
			return false;
		return true;
	}

	/**
	 * Creates a string representation of this session ID.
	 * 
	 * @return the hexadecimal string representation of the <code>id</code> property value
	 */
	@Override
	public String toString() {
		return ByteArrayUtils.toHexString(id);
	}
}
