package com.demo.snakenladder.Entity;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import com.vladmihalcea.hibernate.type.json.JsonStringType;

import lombok.Data;
import lombok.ToString;

@Entity
@Table(name="boardstore")
@TypeDefs({
    @TypeDef(name = "json", typeClass = JsonStringType.class)
    ,
    @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
})
@Data
@ToString
public class BoardObj {
	@Id
	Integer id;
	@Type(type="json")
	@Column(name="boarddata",columnDefinition="json")
	public String boardData;
}




