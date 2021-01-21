package com.biit.liferay.security;

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
