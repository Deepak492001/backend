package com.example.room;

import java.util.List;

import com.example.user.User;
import com.example.watchlist.Watchlist;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@Entity
@NoArgsConstructor
public class Room {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long roomId;
	@Column(nullable = false)
	private Integer numberOfBalconies;
	@Column(nullable = false)
	private Integer bathRooms;
	@Column(nullable = false)
	private String floorNumber;
	@Column(nullable = false)
	private String roomArea;
	private String preference;

	@Column(length = 5000, nullable = false)
	private String roomImageUrl;
	private String roomImagePublicId;
	@Column(length = 5000, nullable = false)
	private String roomVideoUrl;
	@Column(nullable = false)
	private String roomVideoPublicId;
	@Column(nullable = false)
	private Double rent;
	private Double securityDeposit;
	@Column(length = 65535)
	private String description;
	@Column(nullable = false)
	private String landmark;
	@Column(nullable = false)
	private String state;
	@Column(nullable = false)
	private String city;
	@Column(nullable = false)
	private String address;
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private FurnishedStatus furnishedStatus;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = true)
	private User user;

	@OneToMany(mappedBy = "room", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnore
	private List<Watchlist> watchlists;

	@Column(columnDefinition = "BOOLEAN DEFAULT FALSE")
	private Boolean lift;
	@Column(columnDefinition = "BOOLEAN DEFAULT FALSE")
	private Boolean reservedParking;
	@Column(columnDefinition = "BOOLEAN DEFAULT FALSE")
	private Boolean security;
	@Column(columnDefinition = "BOOLEAN DEFAULT FALSE")
	private Boolean gym;
	@Column(columnDefinition = "BOOLEAN DEFAULT FALSE")
	private Boolean maintainanceStaff;
	@Column(columnDefinition = "BOOLEAN DEFAULT FALSE")
	private Boolean garden;
	@Column(columnDefinition = "BOOLEAN DEFAULT FALSE")
	private Boolean wifi;

	public Room( Integer numberOfBalconies, Integer bathRooms, String floorNumber, String roomArea,
			String preference, String roomImageUrl, String roomImagePublicId, String roomVideoUrl,
			String roomVideoPublicId, Double rent, Double securityDeposit, String description, String landmark,
			String state, String city, String address, FurnishedStatus furnishedStatus, User user,
			Boolean lift, Boolean reservedParking, Boolean security, Boolean gym,
			Boolean maintainanceStaff, Boolean garden, Boolean wifi) {
		this.numberOfBalconies = numberOfBalconies;
		this.bathRooms = bathRooms;
		this.floorNumber = floorNumber;
		this.roomArea = roomArea;
		this.preference = preference;
		this.roomImageUrl = roomImageUrl;
		this.roomImagePublicId = roomImagePublicId;
		this.roomVideoUrl = roomVideoUrl;
		this.roomVideoPublicId = roomVideoPublicId;
		this.rent = rent;
		this.securityDeposit = securityDeposit;
		this.description = description;
		this.landmark = landmark;
		this.state = state;
		this.city = city;
		this.address = address;
		this.furnishedStatus = furnishedStatus;
		this.user = user;
		this.lift = lift;
		this.reservedParking = reservedParking;
		this.security = security;
		this.gym = gym;
		this.maintainanceStaff = maintainanceStaff;
		this.garden = garden;
		this.wifi = wifi;
	}

}
