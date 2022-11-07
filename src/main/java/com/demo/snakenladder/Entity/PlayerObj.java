package com.demo.snakenladder.Entity;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name="snakenladder")
@Data
public class PlayerObj {
	@Id
	@org.hibernate.annotations.Type(type="org.hibernate.type.PostgresUUIDType")
	public UUID id;
	@Column(name="lastposition")
	public Integer lastPosition;
	@Column(name="playername")
	public String playerName;
}
