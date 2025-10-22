package com.biit.liferay.security;

/*-
 * #%L
 * Liferay Configuration Reader
 * %%
 * Copyright (C) 2022 - 2025 BiiT Sourcing Solutions S.L.
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * #L%
 */

public enum PasswordEncryptationAlgorithmType {

	// SHA is the default one.
	SHA("sha"),
	// PBKDF2 is for Liferay 6.2
	PBKDF2("pbkdf2");

	private String tag;

	PasswordEncryptationAlgorithmType(String tag) {
		this.tag = tag;
	}

	public static PasswordEncryptationAlgorithmType getPasswordEncryptationAlgorithms(String tag) {
		for (PasswordEncryptationAlgorithmType algorithm : values()) {
			if (algorithm.getTag().equals(tag.toLowerCase())) {
				return algorithm;
			}
		}
		return PasswordEncryptationAlgorithmType.SHA;
	}

	public String getTag() {
		return tag;
	}

}
