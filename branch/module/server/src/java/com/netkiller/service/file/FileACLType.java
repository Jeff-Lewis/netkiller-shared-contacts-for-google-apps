package com.netkiller.service.file;

public enum FileACLType {
	/**
	 * Gives permission to the project team based on their roles. Anyone who is
	 * part of the team has READ permission and project owners and project
	 * editors have FULL_CONTROL permission. This is the default ACL for newly
	 * created buckets. This is also the default ACL for newly created objects
	 * unless the default object ACL for that bucket has been changed.
	 */
	PROJECT_PRIVATE,

	/**Gives the requester FULL_CONTROL permission for a bucket or object. */
	PRIVATE,

	/**
	 * Gives the requester FULL_CONTROL permission and gives all anonymous users
	 * READ permission. When you apply this to an object, anyone on the Internet
	 * can read the object without authenticating.
	 */
	PUBLIC_READ,

	/**
	 * Gives the requester FULL_CONTROL permission and gives all anonymous users
	 * READ and WRITE permission. This ACL applies only to buckets.
	 */
	PUBLIC_READ_WRITE,
	/**
	 * Gives the requester FULL_CONTROL permission and gives all authenticated
	 * Google account holders READ permission
	 */
	AUTHENTICATED_READ,
	/**
	 * Gives the requester FULL_CONTROL permission and gives the bucket owner
	 * READ permission. This is used only with objects.
	 */
	BUCKET_OWNER_READ,
	/**
	 * Gives the requester FULL_CONTROL permission and gives the bucket owner
	 * FULL_CONTROL permission. This is used only with objects.
	 */
	BUCKET_OWNER_FULL_CONTROL

	;

	public String toString() {
		String returnString = null;
		switch (this) {
		case PRIVATE:
			returnString = "private";
			break;
		case PROJECT_PRIVATE:
			returnString = "project-private";
			break;
		case PUBLIC_READ:
			returnString = "public-read";
			break;
		case PUBLIC_READ_WRITE:
			returnString = "public-read-write";
			break;

		case AUTHENTICATED_READ:
			returnString = "authenticated-read";
			break;

		case BUCKET_OWNER_READ:
			returnString = "bucket-owner-read";
			break;

		case BUCKET_OWNER_FULL_CONTROL:
			returnString = "bucket-owner-full-control";
			break;

		}
		return returnString;
	}

	public static FileACLType get(String fileACLTypeString) {
		for (FileACLType fileACLType : FileACLType.values()) {
			if (fileACLType != null && fileACLType.toString().equals(fileACLTypeString)) {
				return fileACLType;
			}
		}
		return null;
	}

}
