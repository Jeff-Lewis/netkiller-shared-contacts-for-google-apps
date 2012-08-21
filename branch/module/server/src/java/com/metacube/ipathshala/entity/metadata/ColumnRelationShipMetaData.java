package com.metacube.ipathshala.entity.metadata;

import com.metacube.ipathshala.entity.metadata.impl.RelationshipType;

/**
 * A class representing the relationship metadata of a an entity with
 * a related entity.
 * @author prateek
 *
 */
public class ColumnRelationShipMetaData {

	private EntityMetaData relatedEntityMetaData;
	private RelationshipType relationshipType;
	public ColumnRelationShipMetaData(EntityMetaData relatedEntityMetaData, RelationshipType relationshipType) {
		super();
		this.relatedEntityMetaData = relatedEntityMetaData;
		this.relationshipType = relationshipType;
	}
	public EntityMetaData getRelatedEntityMetaData() {
		return relatedEntityMetaData;
	}
	public RelationshipType getRelationshipType() {
		return relationshipType;
	}
	
}
